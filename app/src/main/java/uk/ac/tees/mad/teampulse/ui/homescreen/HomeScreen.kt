package uk.ac.tees.mad.teampulse.ui.homescreen

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.gson.Gson
import uk.ac.tees.mad.teampulse.authentication.viewmodel.AuthViewmodel
import uk.ac.tees.mad.teampulse.taskscomponents.model.TaskInfo
import uk.ac.tees.mad.teampulse.taskscomponents.viewmodel.TaskViewModel
import uk.ac.tees.mad.teampulse.ui.theme.fredokaFam
import uk.ac.tees.mad.teampulse.ui.theme.merrisFam
import uk.ac.tees.mad.teampulse.ui.theme.poppinsFam


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    authViewmodel: AuthViewmodel,
    navController: NavHostController,
    taskViewModel: TaskViewModel
){

    val tasks by taskViewModel.tasks.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Tasks",
                        fontFamily = merrisFam,
                        fontSize = 25.sp
                    )
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("profile_screen")
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = "Profile"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("add_task_screen")
                },
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Task"
                )
            }
        }
    ) {innerpadding->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerpadding)
        ){
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ){
                LazyColumn {
                    items(tasks){task->
                        HomeTaskTile(task,navController)
                    }
                }
            }
        }
    }
}


@Composable
fun HomeTaskTile(
    taskInfo: TaskInfo,
    navController: NavHostController
){
    Card(
        modifier = Modifier
            .padding(0.dp, 10.dp)
            .fillMaxWidth(0.92f),
        colors = CardDefaults.cardColors(
            contentColor = Color.DarkGray
        ),
        elevation = CardDefaults.elevatedCardElevation(
            10.dp
        ),
        onClick = {
            val taskJson = Uri.encode(Gson().toJson(taskInfo))
            navController.navigate("task_details/$taskJson")
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 10.dp)
        ) {
            Text(
                text = taskInfo.title,
                fontSize = 20.sp,
                fontFamily = fredokaFam,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.weight(0.1f))
            Text(
                text = taskInfo.goal,
                fontSize = 15.sp,
                fontFamily = poppinsFam
            )

            Spacer(modifier = Modifier.weight(0.1f))
            Text(
                text = "Due date: ${taskInfo.dueDate}",
                fontSize = 15.sp,
                fontFamily = poppinsFam
            )
        }
    }
}