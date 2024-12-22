package uk.ac.tees.mad.teampulse.taskscomponents.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import uk.ac.tees.mad.teampulse.authentication.model.CurrentUser
import javax.inject.Inject


@HiltViewModel
class TaskViewModel @Inject constructor(
    private val firestore: FirebaseFirestore
): ViewModel() {

    private var _searchResult = MutableStateFlow<Boolean?>(null)

    private var _searchedUsers = MutableStateFlow<List<CurrentUser>>(emptyList())
    val searchedUsers = _searchedUsers.asStateFlow()

    fun findByUsername(query: String){
        firestore.collection("users")
            .whereGreaterThanOrEqualTo("username", query)
            .whereLessThanOrEqualTo("username", query + "\uf8ff")
            .get()
            .addOnSuccessListener {
                _searchResult.value = true
                _searchedUsers.value = it.mapNotNull { it.toObject(CurrentUser::class.java) }
            }
            .addOnFailureListener {
                _searchResult.value = false
            }
    }

}