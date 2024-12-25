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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.ac.tees.mad.teampulse.taskscomponents.model.TaskInfo
import uk.ac.tees.mad.teampulse.taskscomponents.model.TaskMembers
import uk.ac.tees.mad.teampulse.ui.theme.fredokaFam
import uk.ac.tees.mad.teampulse.ui.theme.poppinsFam


@Composable
fun TaskDetailsScreen(taskInfo: TaskInfo) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Spacer(modifier = Modifier.weight(0.1f))

        Text(
            text = "Task Details",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = poppinsFam
        )

        Spacer(modifier = Modifier.weight(0.1f))

        Text(
            text = "Title:",
            fontSize = 20.sp,
            fontFamily = fredokaFam
        )
        Text(
            text = taskInfo.title,
            fontSize = 20.sp,
            fontFamily = fredokaFam
        )

        Spacer(modifier = Modifier.weight(0.1f))

        Text(
            text = "Goal:",
            fontSize = 18.sp,
            fontFamily = poppinsFam
        )
        Text(
            text = taskInfo.goal,
            fontSize = 18.sp,
            fontFamily = poppinsFam
        )

        Spacer(modifier = Modifier.weight(0.1f))

        Text(
            text = "Description:",
            fontSize = 18.sp,
            fontFamily = poppinsFam
        )
        Text(
            text = taskInfo.description,
            fontSize = 18.sp,
            fontFamily = poppinsFam
        )

        Spacer(modifier = Modifier.weight(0.1f))

        Text(
            text = "Due Date:",
            fontSize = 18.sp,
            fontFamily = poppinsFam
        )
        Text(
            text = taskInfo.dueDate,
            fontSize = 18.sp,
            fontFamily = poppinsFam
        )

        Spacer(modifier = Modifier.weight(0.1f))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ){
            items(
                taskInfo.assignees
            ){members->
                Members(members)
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun Members(
    members: TaskMembers
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
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
            members.members.name?.let {
                Text(
                    modifier = Modifier
                        .padding(5.dp, 1.dp),
                    text = it,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    fontFamily = fredokaFam
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
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
                text = members.role,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                fontFamily = fredokaFam
            )
        }
    }
}
