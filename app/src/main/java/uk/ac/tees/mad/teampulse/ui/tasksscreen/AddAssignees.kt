package uk.ac.tees.mad.teampulse.ui.tasksscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import uk.ac.tees.mad.teampulse.authentication.model.CurrentUser
import uk.ac.tees.mad.teampulse.taskscomponents.viewmodel.TaskViewModel
import uk.ac.tees.mad.teampulse.ui.theme.fredokaFam
import uk.ac.tees.mad.teampulse.ui.theme.poppinsFam


@Composable
fun AddAssignees(
    navController: NavController,
    taskViewModel: TaskViewModel
){

    var searchText by remember { mutableStateOf("") }
    var addedUser by remember { mutableStateOf(emptyList<CurrentUser>()) }
    val searchedUser by taskViewModel.searchedUsers.collectAsState()
    var isActive by remember { mutableStateOf(false) }

    if (searchText.isNotEmpty())isActive=true

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){

        item {
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                "Add New Memebers for task!!",
                fontSize = 20.sp,
                fontFamily = poppinsFam,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.9f),
                value = searchText,
                onValueChange = {
                    searchText = it
                },
                shape = RoundedCornerShape(15.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "Search Icon"
                    )
                },
                trailingIcon = {
                    if (isActive){
                        IconButton(
                            onClick = {
                                if (searchText.isNotEmpty())searchText=""
                                isActive =false
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Close,
                                contentDescription = "Close search"
                            )
                        }
                    }
                }
            )
            Button(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Text(
                    text = "Done",
                    fontSize = 15.sp,
                    fontFamily = poppinsFam
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.9f),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                SearchedUser(
                    taskViewModel
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.9f),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                AddedUser(
                    taskViewModel
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
        }

    }
}



@Composable
fun SearchedUser(
    taskViewModel: TaskViewModel
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 5.dp)
    ){
        Row(
            modifier = Modifier.padding(10.dp, 2.dp),
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
            modifier = Modifier.padding(10.dp, 2.dp),
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


@Composable
fun AddedUser(
    taskViewModel: TaskViewModel
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 5.dp)
    ){
        Row(
            modifier = Modifier
                .padding(10.dp, 2.dp),
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
            modifier = Modifier
            .padding(10.dp, 2.dp),
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
        Button(
            modifier = Modifier
                .padding(10.dp, 2.dp)
                .fillMaxWidth(),
            onClick = {}
        ) {
            Text(
                text = "Remove This",
                fontSize = 15.sp,
                fontFamily = poppinsFam
            )
        }
    }
}


