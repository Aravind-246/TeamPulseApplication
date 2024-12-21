package uk.ac.tees.mad.teampulse.ui.homescreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import uk.ac.tees.mad.teampulse.authentication.viewmodel.AuthViewmodel
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
                        text = "Home",
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
                Text(
                    text = "This is Home Screen!",
                    fontSize = 20.sp,
                    fontFamily = poppinsFam
                )
                Button(onClick = {
                    authViewmodel.signOut()
                    navController.navigate("auth_graph"){
                        popUpTo(navController.graph.startDestinationId){
                            inclusive = true
                        }
                    }
                }) {
                    Text(
                        text = "Log Out",
                        fontSize = 16.sp,
                        fontFamily = poppinsFam
                    )
                }
                authViewmodel.fetchCurrentUser()
            }
        }
    }
}