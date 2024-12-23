package uk.ac.tees.mad.teampulse.ui.tasksscreen

import android.icu.text.SimpleDateFormat
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import uk.ac.tees.mad.teampulse.taskscomponents.viewmodel.TaskViewModel
import uk.ac.tees.mad.teampulse.ui.theme.fredokaFam
import uk.ac.tees.mad.teampulse.ui.theme.poppinsFam
import java.util.Date
import java.util.Locale


@Composable
fun AddingNewTask(
    navController: NavHostController,
    taskViewModel: TaskViewModel
){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp, 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Spacer(modifier = Modifier.weight(0.2f))

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
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                AddingTaskTile(
                    navController,
                    taskViewModel
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddingTaskTile(
    navController: NavHostController,
    taskViewModel: TaskViewModel
){

    var title by remember { mutableStateOf("") }
    var goal by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    }?: ""


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


    Card (
        modifier = Modifier
            .fillMaxWidth(0.95f),
        elevation = CardDefaults.elevatedCardElevation(10.dp)
    ){
        Column(
            modifier = Modifier
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
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

            Spacer(modifier = Modifier.height(10.dp))

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

            Spacer(modifier = Modifier.height(10.dp))

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

            Spacer(modifier = Modifier.height(10.dp))

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

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp, 1.dp),
                text = "Asigned to: ",
                fontSize = 18.sp,
                fontFamily = fredokaFam
            )
            Spacer(Modifier.weight(1f))
            LazyColumn(
                modifier = Modifier.height(100.dp)
            ){
                item {
                    AssigneesInfo()
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


@Composable
fun AssigneesInfo(){
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
            Text(
                modifier = Modifier
                    .padding(5.dp, 1.dp),
                text = "Anubhav Singh",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                fontFamily = fredokaFam
            )
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
                text = "Android Developer",
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
