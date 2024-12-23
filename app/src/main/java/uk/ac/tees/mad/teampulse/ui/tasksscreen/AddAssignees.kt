package uk.ac.tees.mad.teampulse.ui.tasksscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
) {
    var searchText by remember { mutableStateOf("") }
    var selectedUser by remember { mutableStateOf<CurrentUser?>(null) }
    var roleText by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(false) }
    val searchedUsers by taskViewModel.searchedUsers.collectAsState()

    if (searchText.isNotEmpty()) isActive = true

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Assign a New Member to Task",
            fontSize = 20.sp,
            fontFamily = poppinsFam,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.weight(0.1f))

        // Username input field
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = searchText,
            onValueChange = {
                searchText = it
                taskViewModel.findByUsername(searchText)
            },
            label = { Text("Enter username") },
            shape = RoundedCornerShape(15.dp),
            leadingIcon = {
                Icon(imageVector = Icons.Outlined.Search, contentDescription = "Search Icon")
            },
            trailingIcon = {
                if (isActive) {
                    IconButton(onClick = {
                        if (searchText.isNotEmpty()) searchText = ""
                        isActive = false
                    }) {
                        Icon(imageVector = Icons.Outlined.Close, contentDescription = "Close search")
                    }
                }
            }
        )


        // Display search results
        if (searchText.isNotEmpty()){
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                items(searchedUsers) { user ->
                    SearchResultItem(user, onUserSelected = {
                        selectedUser = it
                        searchText = it.username ?: ""
                        isActive = false
                    })
                }
            }
        }

        Spacer(modifier = Modifier.weight(0.1f))

        // Role input field, visible only when a user is selected
        if (selectedUser != null && searchText.isNotEmpty()) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = roleText,
                onValueChange = { roleText = it },
                label = { Text("Assign role") },
                shape = RoundedCornerShape(15.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    taskViewModel.updateSearchList()
                }
            ) {
                Text("Add Member")
            }
        }

        Spacer(modifier = Modifier.weight(0.1f))

        Button(
            onClick = {
                navController.popBackStack()
                taskViewModel.updateSearchList()
            }
        ) {
            Text(
                text = "Done",
                fontSize = 15.sp,
                fontFamily = poppinsFam
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}


@Composable
fun SearchResultItem(
    user: CurrentUser,
    onUserSelected: (CurrentUser) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onUserSelected(user) }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = user.username ?: "Unknown User",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                fontFamily = fredokaFam
            )
        }
    }
}
