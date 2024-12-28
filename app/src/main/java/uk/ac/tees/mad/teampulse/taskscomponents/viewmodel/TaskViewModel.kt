package uk.ac.tees.mad.teampulse.taskscomponents.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import uk.ac.tees.mad.teampulse.authentication.model.CurrentUser
import uk.ac.tees.mad.teampulse.taskscomponents.model.TaskInfo
import uk.ac.tees.mad.teampulse.taskscomponents.model.TaskMembers
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private var _flag = MutableStateFlow(true)
    val flag = _flag.asStateFlow()

    private var _searchResult = MutableStateFlow<Boolean?>(null)
    val searchResult = _searchResult.asStateFlow()

    private val _currentTask = MutableStateFlow<TaskInfo?>(null)
    val currentTask = _currentTask.asStateFlow()

    private val _searchedUsers = MutableStateFlow<List<CurrentUser>>(emptyList())
    val searchedUsers = _searchedUsers.asStateFlow()

    private val _addedMembers = MutableStateFlow<List<TaskMembers>>(emptyList())
    val addedMembers = _addedMembers.asStateFlow()

    private val _tasks = MutableStateFlow<List<TaskInfo>>(emptyList())
    val tasks = _tasks.asStateFlow()

    init {
        getAllTasks()
    }


    fun getAllTasks() {
        firestore.collection("tasks")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val fetchedTasks = querySnapshot.documents.mapNotNull { document ->
                    document.toObject(TaskInfo::class.java)
                }
                _tasks.value = fetchedTasks
            }
            .addOnFailureListener { exception ->
                Log.e("TaskViewModel", "Error fetching tasks", exception)
            }
    }

    fun addNewTask(
        title: String,
        goal: String,
        description: String,
        dueDate: String,
        members: List<TaskMembers>
    ) {
        val documentReference = firestore.collection("tasks").document()
        val taskId = documentReference.id
        val taskInfo = TaskInfo(
            id = taskId,
            title = title,
            goal = goal,
            description = description,
            dueDate = dueDate,
            assignees = members
        )

        documentReference
            .set(taskInfo)
            .addOnSuccessListener {
                Log.d("TaskViewModel", "Task added with ID: $taskId")

                members.forEach { member ->
                    val userRef = firestore.collection("users").document(member.members.username ?: "")

                    userRef.get().addOnSuccessListener { userDocument ->
                        if (userDocument.exists()) {
                            userRef.update("tasks", FieldValue.arrayUnion(taskId))
                                .addOnSuccessListener {
                                    Log.d("TaskViewModel", "Task associated with user: ${member.members.username}")
                                }
                                .addOnFailureListener { e ->
                                    Log.w("TaskViewModel", "Error associating task with user: ${member.members.username}", e)
                                }
                        } else {
                            Log.w("TaskViewModel", "User document does not exist: ${member.members.username}")
                        }
                    }.addOnFailureListener { e ->
                        Log.w("TaskViewModel", "Error fetching user document: ${member.members.username}", e)
                    }
                }
            }
            .addOnFailureListener { e ->
                Log.w("TaskViewModel", "Error adding task", e)
            }
    }


    fun findByUsername(query: String) {
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

    fun updateSearchList() {
        _searchedUsers.value = emptyList()
    }

    fun addMemberToTask(user: CurrentUser, role: String) {
        val newMember = TaskMembers(members = user, role = role)
        _addedMembers.value = _addedMembers.value + newMember
    }

    fun updateAddedMember(){
        _addedMembers.value = emptyList()
    }

    fun removeMemberFromTask(user: CurrentUser) {
        _addedMembers.value = _addedMembers.value.filter { it.members.username != user.username }
    }

    fun initializeAddedMembers(taskMembersList: List<TaskMembers>) {
        _addedMembers.value = taskMembersList

    }

    fun updateTaskMembersInFirestore(taskId: String) {
        val updatedMembers = _addedMembers.value
        firestore.collection("tasks").document(taskId)
            .update("assignees", updatedMembers)
            .addOnSuccessListener {
                Log.i("Added List to task: ", "The List is updated${it}")
            }
            .addOnFailureListener {
                Log.i("Added List to task: ", "The List is not updated ${it.localizedMessage}")
            }
    }

    fun updateFlag(){
        _flag.value = false
    }
}
