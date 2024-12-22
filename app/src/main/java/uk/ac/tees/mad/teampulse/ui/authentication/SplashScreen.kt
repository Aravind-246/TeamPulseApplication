package uk.ac.tees.mad.teampulse.ui.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import uk.ac.tees.mad.teampulse.R
import uk.ac.tees.mad.teampulse.authentication.viewmodel.AuthViewmodel
import uk.ac.tees.mad.teampulse.ui.theme.poppinsFam


@Composable
fun CustomSplashScreen(
    authViewModel: AuthViewmodel,
    navController: NavHostController
) {

    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier
                    .size(200.dp),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo"
            )
            Text(
                text = "TeamPulse",
                fontSize = 30.sp,
                fontFamily = poppinsFam,
                fontWeight = FontWeight.Bold
            )

            LaunchedEffect(key1 = Unit){
                delay( 3000L)
                if (isLoggedIn){
                    navController.navigate("home_graph"){
                        popUpTo(navController.graph.startDestinationId){
                            inclusive = true
                        }
                    }
                }else{
                    navController.navigate("auth_graph"){
                        popUpTo(navController.graph.startDestinationId){
                            inclusive = true
                        }
                    }
                }
            }

        }
    }
}