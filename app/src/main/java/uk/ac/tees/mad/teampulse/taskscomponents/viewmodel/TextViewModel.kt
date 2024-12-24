package uk.ac.tees.mad.teampulse.taskscomponents.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
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

    private var _searchResult = MutableStateFlow<Boolean?>(null)
    private var _searchedUsers = MutableStateFlow<List<CurrentUser>>(emptyList())
    val searchedUsers = _searchedUsers.asStateFlow()

    private val _addedMembers = MutableStateFlow<List<TaskMembers>>(emptyList())
    val addedMembers = _addedMembers.asStateFlow()

    private val _tasks = MutableStateFlow<List<TaskInfo>>(emptyList())
    val tasks = _tasks.asStateFlow()

    init {
        getAllTasks()
    }

    private fun getAllTasks() {
        firestore.collection("tasks")
            .get()
            .addOnSuccessListener { querySnapshot ->
                // Map Firestore documents to TaskInfo objects
                val fetchedTasks = querySnapshot.documents.mapNotNull { document ->
                    document.toObject(TaskInfo::class.java)
                }
                _tasks.value = fetchedTasks
            }
            .addOnFailureListener { exception ->
                // Handle any errors that occur while fetching tasks
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
        // Create the task object
        val taskInfo = TaskInfo(
            title = title,
            goal = goal,
            description = description,
            dueDate = dueDate,
            assignees = members
        )

        // Add task to the "tasks" collection
        firestore.collection("tasks")
            .add(taskInfo)
            .addOnSuccessListener { documentReference ->
                val taskId = documentReference.id
                Log.d("TaskViewModel", "Task added with ID: $taskId")

                // For each member, update the user document with task info
                members.forEach { member ->
                    val userRef = firestore.collection("users").document(member.members.username ?: "")

                    userRef.update("tasks", FieldValue.arrayUnion(taskId))
                        .addOnSuccessListener {
                            Log.d("TaskViewModel", "Task associated with user: ${member.members.username}")
                        }
                        .addOnFailureListener { e ->
                            Log.w("TaskViewModel", "Error associating task with user: ${member.members.username}", e)
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

    fun removeMemberFromTask(user: CurrentUser) {
        _addedMembers.value = _addedMembers.value.filter { it.members.username != user.username }
    }
}
