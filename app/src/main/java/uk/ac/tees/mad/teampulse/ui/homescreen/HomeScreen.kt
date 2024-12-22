package uk.ac.tees.mad.teampulse.ui.homescreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import uk.ac.tees.mad.teampulse.authentication.viewmodel.AuthViewmodel
import uk.ac.tees.mad.teampulse.ui.theme.fredokaFam
import uk.ac.tees.mad.teampulse.ui.theme.merrisFam
import uk.ac.tees.mad.teampulse.ui.theme.poppinsFam



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    authViewmodel: AuthViewmodel,
    navController: NavHostController
){



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
                verticalArrangement = Arrangement.Center
            ){
                LazyColumn {
                    items(100){
                        HomeTaskTile()
                    }
                }
            }
        }
    }
}


@Composable
fun HomeTaskTile(){
    Card(
        modifier = Modifier
            .padding(0.dp, 10.dp)
            .fillMaxWidth(0.92f),
        colors = CardDefaults.cardColors(
            contentColor = Color.DarkGray
        ),
        elevation = CardDefaults.elevatedCardElevation(
            10.dp
        )
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 10.dp)
        ) {
            Text(
                text = "This is the title of the Task",
                fontSize = 20.sp,
                fontFamily = fredokaFam,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.weight(0.1f))
            Text(
                text = "The Goal of the task is",
                fontSize = 15.sp,
                fontFamily = poppinsFam
            )

            Spacer(modifier = Modifier.weight(0.1f))
            Text(
                text = "Due date: 28/09/2024",
                fontSize = 15.sp,
                fontFamily = poppinsFam
            )
        }
    }
}