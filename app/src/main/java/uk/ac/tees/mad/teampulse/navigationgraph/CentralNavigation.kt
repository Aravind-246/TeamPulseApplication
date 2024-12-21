package uk.ac.tees.mad.teampulse.navigationgraph

import android.window.SplashScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.internal.composableLambda
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import uk.ac.tees.mad.teampulse.authentication.viewmodel.AuthViewmodel
import uk.ac.tees.mad.teampulse.ui.authentication.CustomSplashScreen
import uk.ac.tees.mad.teampulse.ui.authentication.LogInScreen
import uk.ac.tees.mad.teampulse.ui.authentication.SignUpScreen
import uk.ac.tees.mad.teampulse.ui.homescreen.EditProfileScreen
import uk.ac.tees.mad.teampulse.ui.homescreen.HomeScreen
import uk.ac.tees.mad.teampulse.ui.homescreen.ProfileScreen
import uk.ac.tees.mad.teampulse.ui.tasksscreen.AddingNewTask


@Composable
fun CentralNavigation(
    authViewmodel: AuthViewmodel,
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = "splash_graph"){
        navigation(
            startDestination = "splash_screen",
            route = "splash_graph"
        ){
            composable("splash_screen"){
                CustomSplashScreen(authViewmodel,
                    navController )
            }
        }
        navigation(
            startDestination = "login_screen",
            route = "auth_graph"
        ){
            composable("login_screen") {
                LogInScreen(
                    authViewmodel,
                    navController
                )
            }

            composable("signup_screen") {
                SignUpScreen(
                    authViewmodel,
                    navController
                )
            }
        }

        navigation(
            startDestination = "home_screen",
            route = "home_graph"
        ){
            composable("home_screen") {
                HomeScreen(
                    authViewmodel,
                    navController
                )
            }

            composable("profile_screen"){
                ProfileScreen(
                    authViewmodel,
                    navController
                )
            }
            composable("edit_profile") {
                EditProfileScreen(
                    authViewmodel,
                    navController
                )
            }
            composable("add_task_screen") {
                AddingNewTask()
            }
        }
    }

}