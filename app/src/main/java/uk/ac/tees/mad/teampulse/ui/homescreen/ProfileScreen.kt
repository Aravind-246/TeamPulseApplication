package uk.ac.tees.mad.teampulse.ui.homescreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import uk.ac.tees.mad.teampulse.R
import uk.ac.tees.mad.teampulse.authentication.viewmodel.AuthViewmodel
import uk.ac.tees.mad.teampulse.ui.theme.merrisFam
import uk.ac.tees.mad.teampulse.ui.theme.poppinsFam


@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun ProfileScreen(
    authViewmodel: AuthViewmodel,
    navController: NavHostController
){

    val width = LocalConfiguration.current.screenWidthDp.dp * 0.6f

    val currentUser by authViewmodel.currentUser.collectAsState()

    LaunchedEffect(key1 = Unit) {
        authViewmodel.fetchCurrentUser()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Profile",
                        fontSize = 25.sp,
                        fontFamily = merrisFam
                    )
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("edit_profile")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "EditProfile"
                        )
                    }
                }
            )
        }
    ){innerpadding->
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
                Spacer(modifier = Modifier.weight(0.2f))

                GlideImage(
                    modifier = Modifier
                        .size(width)
                        .fillMaxWidth(),
                    model = currentUser?.profileImgUrl,
                    contentDescription = "Profile Picture",
                    failure = placeholder(R.drawable.avatar)
                )

                Spacer(modifier = Modifier.weight(0.4f))

                Card(
                    modifier = Modifier.fillMaxWidth(0.88f)
                ){
                    currentUser?.name?.let {name->
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = name,
                            fontSize = 15.sp,
                            fontFamily = poppinsFam
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(0.1f))

                Card(
                    modifier = Modifier.fillMaxWidth(0.88f)
                ){
                    currentUser?.phoneNumber?.let {num->
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = num,
                            fontSize = 15.sp,
                            fontFamily = poppinsFam
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(0.1f))

                Button(
                    modifier = Modifier.fillMaxWidth(0.88f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    ),
                    onClick = {
                        authViewmodel.signOut()
                        navController.navigate("auth_graph")
                    }
                ){
                    Text(
                        text = "Log Out",
                        fontSize = 15.sp,
                        fontFamily = poppinsFam
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}