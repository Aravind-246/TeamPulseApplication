package uk.ac.tees.mad.teampulse.ui.tasksscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.gson.Gson
import uk.ac.tees.mad.teampulse.taskscomponents.model.TaskMembers
import uk.ac.tees.mad.teampulse.ui.theme.fredokaFam
import uk.ac.tees.mad.teampulse.ui.theme.poppinsFam


@Composable
fun MemberDetailsScreen(
    navController: NavHostController,
    memberJson: String
) {
    val member = Gson().fromJson(memberJson, TaskMembers::class.java)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Member Details",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = poppinsFam
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Name: ${member.members.name ?: "Unknown"}",
            fontSize = 20.sp,
            fontFamily = fredokaFam
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Username: ${member.members.username ?: "Unknown"}",
            fontSize = 18.sp,
            fontFamily = fredokaFam
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Role: ${member.role}",
            fontSize = 18.sp,
            fontFamily = fredokaFam
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.popBackStack() }) {
            Text(text = "Back")
        }
    }
}
