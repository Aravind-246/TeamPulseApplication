package uk.ac.tees.mad.teampulse.ui.tasksscreen

import android.icu.text.SimpleDateFormat
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import uk.ac.tees.mad.teampulse.taskscomponents.model.TaskMembers
import uk.ac.tees.mad.teampulse.taskscomponents.viewmodel.TaskViewModel
import uk.ac.tees.mad.teampulse.ui.theme.fredokaFam
import uk.ac.tees.mad.teampulse.ui.theme.poppinsFam
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddingNewTask(
    navController: NavHostController,
    taskViewModel: TaskViewModel
) {

    val context = LocalContext.current

    var title by remember { mutableStateOf("") }
    var goal by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""
    val addedMembers by taskViewModel.addedMembers.collectAsState()

    if (showDatePicker) {
        Popup(
            onDismissRequest = { showDatePicker = false },
            alignment = Alignment.TopStart
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = 64.dp)
                    .shadow(elevation = 4.dp)
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
            ) {
                DatePicker(
                    state = datePickerState,
                    showModeToggle = true
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp, 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp, 1.dp),
            text = "Add a new Task",
            fontSize = 25.sp,
            fontFamily = fredokaFam,
            textAlign = TextAlign.Center
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, fill = true), // Ensures LazyColumn takes up available space but not the whole screen
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.95f),
                    elevation = CardDefaults.elevatedCardElevation(10.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp, 1.dp),
                            text = "Title of the task",
                            fontSize = 18.sp,
                            fontFamily = fredokaFam
                        )
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = title,
                            onValueChange = {
                                title = it
                            },
                            shape = RoundedCornerShape(15.dp),
                            maxLines = 2
                        )

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp, 1.dp),
                            text = "Goal of the task",
                            fontSize = 18.sp,
                            fontFamily = fredokaFam
                        )
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = goal,
                            onValueChange = {
                                goal = it
                            },
                            shape = RoundedCornerShape(15.dp),
                            maxLines = 2
                        )

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp, 1.dp),
                            text = "Description",
                            fontSize = 18.sp,
                            fontFamily = fredokaFam
                        )
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp),
                            value = description,
                            onValueChange = {
                                description = it
                            },
                            shape = RoundedCornerShape(15.dp),
                            maxLines = 10
                        )

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp, 1.dp),
                            text = "Due Date of the task",
                            fontSize = 18.sp,
                            fontFamily = fredokaFam
                        )
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = selectedDate,
                            onValueChange = {},
                            trailingIcon = {
                                IconButton(onClick = {
                                    showDatePicker = !showDatePicker
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.DateRange,
                                        contentDescription = "Date Picker"
                                    )
                                }
                            },
                            shape = RoundedCornerShape(15.dp),
                            readOnly = true
                        )

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp, 1.dp),
                            text = "Assigned to: ",
                            fontSize = 18.sp,
                            fontFamily = fredokaFam
                        )
                        LazyColumn(
                            modifier = Modifier.height(200.dp)
                        ) {
                            items(addedMembers){members->
                                AssigneesInfo(members)
                            }
                        }
                        TextButton(
                            onClick = {
                                navController.navigate("add_assignees")
                            }
                        ) {
                            Text(
                                text = "Show More",
                                fontSize = 15.sp,
                                fontFamily = poppinsFam
                            )
                        }
                    }
                }
            }
        }

        Button(
            onClick = {
                if (
                    title.isNotEmpty() &&
                    goal.isNotEmpty() &&
                    description.isNotEmpty() &&
                    selectedDate.isNotEmpty()
                ) {
                    navController.popBackStack()
                    taskViewModel.addNewTask(
                        title,
                        goal,
                        description,
                        selectedDate,
                        addedMembers
                    )
                } else {
                    Toast.makeText(context, "All the details are mandatory", Toast.LENGTH_LONG).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Create Task",
                fontSize = 18.sp,
                fontFamily = poppinsFam
            )
        }
    }
}




@Composable
fun AssigneesInfo(
    members: TaskMembers
){
    Card {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                modifier = Modifier
                    .padding(5.dp, 1.dp),
                text = "Name:",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                fontFamily = fredokaFam
            )
            members.members.name?.let {
                Text(
                    modifier = Modifier
                        .padding(5.dp, 1.dp),
                    text = it,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    fontFamily = fredokaFam
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                modifier = Modifier
                    .padding(5.dp, 1.dp),
                text = "Role:",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                fontFamily = fredokaFam
            )
            Text(
                modifier = Modifier
                    .padding(5.dp, 1.dp),
                text = members.role,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                fontFamily = fredokaFam
            )
        }
    }
}


fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}
