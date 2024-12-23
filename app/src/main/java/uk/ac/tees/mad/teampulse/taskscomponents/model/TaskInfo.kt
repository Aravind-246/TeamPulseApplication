package uk.ac.tees.mad.teampulse.taskscomponents.model

import uk.ac.tees.mad.teampulse.authentication.model.CurrentUser

data class TaskInfo(
    val title: String,
    val goal: String,
    val description: String,
    val dueDate: String,
    val assignees: List<TaskMembers>
)

data class TaskMembers(
    val members: CurrentUser,
    val role: String
)
