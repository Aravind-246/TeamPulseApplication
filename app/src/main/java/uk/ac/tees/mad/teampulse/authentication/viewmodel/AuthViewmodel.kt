package uk.ac.tees.mad.teampulse.authentication.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.google.rpc.context.AttributeContext.Auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.mad.teampulse.authentication.model.CurrentUser
import uk.ac.tees.mad.teampulse.authentication.response.AuthState
import javax.inject.Inject


@HiltViewModel
class AuthViewmodel @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
): ViewModel() {

    val storage = Firebase.storage.reference

    private val _authstate = MutableStateFlow<AuthState>(AuthState.idle)
    val authState = _authstate.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    private val _currentUser = MutableStateFlow<CurrentUser?>(null)
    val currentUser = _currentUser.asStateFlow()

    init {
        checkIfUserLoggedIn()
    }

    private fun checkIfUserLoggedIn(){
        if (IsLoggedIn()){
            Log.i("Authentication: ", "Authenticated user")
            _isLoggedIn.value = true
            fetchCurrentUser()
        }else{
            Log.i("Authentication: ", "Not authenticated user")
            _isLoggedIn.value = false
            fetchCurrentUser()
        }
    }

    private fun IsLoggedIn(): Boolean {
        val user = auth.currentUser
        if (user!=null)return true
        else return false
    }


    fun LogIn(email: String, password: String) = viewModelScope.launch {
        _authstate.value = AuthState.loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    fetchCurrentUser()
                    _isLoggedIn.value = true
                    _authstate.value = AuthState.success
                    Log.i("Login", "User logged in successfully")
                } else {
                    _authstate.value = AuthState.failure(task.exception.toString())
                    Log.e("Login", "Failed to log in: ${task.exception}")
                }
            }
            .addOnFailureListener {
                _authstate.value = AuthState.failure(it.message.toString())
                Log.e("Login", "Failed to log in: ${it.message}")
            }
    }


    fun SignUp(name: String, username: String, email: String, phoneNumber: String, password: String) = viewModelScope.launch {
        _authstate.value = AuthState.loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val currentUser = auth.currentUser
                    if (currentUser != null) {

                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build()

                        currentUser.updateProfile(profileUpdates)
                            .addOnCompleteListener { profileTask ->
                                if (profileTask.isSuccessful) {

                                    val userId = currentUser.uid
                                    val userData = hashMapOf(
                                        "name" to name,
                                        "phoneNumber" to phoneNumber,
                                        "profilepictureurl" to "",
                                        "username" to username
                                    )

                                    firestore.collection("users")
                                        .document(userId)
                                        .set(userData)
                                        .addOnSuccessListener {
                                            Log.i("SignUp", "User profile saved to Firestore successfully.")
                                            fetchCurrentUser()
                                            _authstate.value = AuthState.success
                                            _isLoggedIn.value = true
                                        }
                                        .addOnFailureListener { firestoreError ->
                                            Log.e("SignUp", "Failed to save user profile to Firestore: ${firestoreError.message}")
                                            _authstate.value = AuthState.failure(firestoreError.message.toString())
                                        }
                                } else {
                                    Log.e("SignUp", "Failed to update display name: ${profileTask.exception?.message}")
                                    _authstate.value = AuthState.failure(profileTask.exception?.message.toString())
                                }
                            }
                    }
                } else {
                    val errorMessage = task.exception?.message ?: "Sign-up failed"
                    _authstate.value = AuthState.failure(errorMessage)
                    Log.e("SignUp", "Failed to sign up: $errorMessage")
                }
            }
            .addOnFailureListener { exception ->
                _authstate.value = AuthState.failure(exception.message.toString())
                Log.e("SignUp", "Error during sign-up: ${exception.message}")
            }
    }


    fun signOut(){
        auth.signOut()
        _authstate.value = AuthState.idle
        _isLoggedIn.value = false
    }

    fun fetchCurrentUser(){
        val user = auth.currentUser
        _authstate.value = AuthState.loading
        if (user != null){
            val userId = user.uid
            firestore.collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener {
                    if (it.exists()){
                        val name = it.getString("name") ?: ""
                        val username = it.getString("username")?: ""
                        val email = it.getString("phoneNumber") ?: ""
                        val imgUrl = it.getString("profilepictureurl") ?: ""
                        val fetchedUser = CurrentUser(name, username, email, imgUrl)
                        _currentUser.value = fetchedUser
                        _authstate.value = AuthState.success
                    }else{
                        signOut()
                    }
                }
                .addOnFailureListener {
                    signOut()
                    _authstate.value = AuthState.failure(it.message.toString())
                    Log.i("The error: ", "Cannot fetch the user")
                }
        }
    }

    fun updateCurrentUser(name: String, phoneNumber: String){
        val currUser = auth.currentUser
        if (currUser!=null){
            val userId = currUser.uid
            val userData = hashMapOf(
                "name" to name,
                "phoneNumber" to phoneNumber
            )
            firestore.collection("users")
                .document(userId)
                .update(userData as Map<String, Any>)
            currUser.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build())
                .addOnSuccessListener {
                    Log.i("User is updated: ", "Process Completed")
                }
                .addOnFailureListener {
                    Log.i("User is updated: ", "Process Incomplete")
                }
        }else{
            _authstate.value = AuthState.failure("Unable to authenticate")
        }
    }

    fun updateProfileImage(uri: Uri){
        val currentUser = auth.currentUser
        if (currentUser!=null){
            val userId = currentUser.uid
            val imageRef = storage.child("users/${userId}/profile.jpg")

            imageRef.putFile(uri)
                .addOnSuccessListener {
                    imageRef.downloadUrl.addOnSuccessListener {
                        val imageLink = it.toString()

                        val userData = hashMapOf(
                            "profilepictureurl" to imageLink)
                        firestore.collection("users")
                            .document(userId)
                            .update(userData as Map<String, Any>)
                            .addOnSuccessListener {
                                fetchCurrentUser()
                            }
                    }
                }
                .addOnFailureListener{
                    Log.i("Error Encountered: ", "Unable to update the profile picture.")
                }
        }else{
            Log.i("Error update:", "Current User is null")
        }
    }
}