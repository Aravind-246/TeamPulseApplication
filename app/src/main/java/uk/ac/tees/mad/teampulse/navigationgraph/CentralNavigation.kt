package uk.ac.tees.mad.teampulse.navigationgraph

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.google.gson.Gson
import uk.ac.tees.mad.teampulse.authentication.viewmodel.AuthViewmodel
import uk.ac.tees.mad.teampulse.taskscomponents.model.TaskInfo
import uk.ac.tees.mad.teampulse.taskscomponents.model.TaskMembers
import uk.ac.tees.mad.teampulse.taskscomponents.viewmodel.TaskViewModel
import uk.ac.tees.mad.teampulse.ui.authentication.CustomSplashScreen
import uk.ac.tees.mad.teampulse.ui.authentication.LogInScreen
import uk.ac.tees.mad.teampulse.ui.authentication.SignUpScreen
import uk.ac.tees.mad.teampulse.ui.homescreen.EditProfileScreen
import uk.ac.tees.mad.teampulse.ui.homescreen.HomeScreen
import uk.ac.tees.mad.teampulse.ui.homescreen.ProfileScreen
import uk.ac.tees.mad.teampulse.ui.tasksscreen.AddAssignees
import uk.ac.tees.mad.teampulse.ui.tasksscreen.AddingNewTask
import uk.ac.tees.mad.teampulse.ui.tasksscreen.MemberDetailsScreen
import uk.ac.tees.mad.teampulse.ui.tasksscreen.TaskDetailsScreen
import uk.ac.tees.mad.teampulse.ui.tasksscreen.UpdateMembers


@Composable
fun CentralNavigation(
    authViewmodel: AuthViewmodel,
    navController: NavHostController,
    taskViewModel: TaskViewModel
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
                    navController,
                    taskViewModel
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
                AddingNewTask(
                    navController,
                    taskViewModel
                )
            }

            composable("add_assignees") {
                AddAssignees(
                    navController,
                    taskViewModel
                )
            }

            composable(
                route = "task_details/{taskInfo}",
                arguments = listOf(navArgument("taskInfo") {
                    type = NavType.StringType
                })
            ) { backStackEntry ->
                val taskJson = backStackEntry.arguments?.getString("taskInfo")
                val taskInfo = Gson().fromJson(taskJson, TaskInfo::class.java)
                TaskDetailsScreen(
                    taskInfo,
                    navController,
                    taskViewModel
                )
            }

            composable(
                route = "member_details_screen/{memberJson}",
                arguments = listOf(navArgument("memberJson") {
                    type = NavType.StringType
                })
            ) { backStackEntry ->
                val memberJson = backStackEntry.arguments?.getString("memberJson")
                memberJson?.let {
                    MemberDetailsScreen(
                        navController = navController,
                        memberJson = it
                    )
                }
            }

            composable(
                route = "update_members_screen/{taskId}",
                arguments = listOf(navArgument("taskId") {
                    type = NavType.StringType
                })
            ) { backStackEntry ->
                var taskId = backStackEntry.arguments?.getString("taskId")
                taskId = taskId?.trim('"')
                Log.d("TaskId: ", taskId?:"")

                taskId?.let {
                    UpdateMembers(
                        navController = navController,
                        taskViewModel = taskViewModel,
                        taskId = it
                    )
                }
            }


        }
    }

}