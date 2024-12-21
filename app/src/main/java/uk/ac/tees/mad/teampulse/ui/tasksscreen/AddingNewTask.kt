package uk.ac.tees.mad.teampulse.ui.tasksscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import uk.ac.tees.mad.teampulse.ui.theme.poppinsFam


@Preview
@Composable
fun AddingNewTask(

){
    Surface (
        modifier = Modifier.fillMaxSize()
    ){
        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text(
                text = "This is add new task screen",
                fontSize = 20.sp,
                fontFamily = poppinsFam
            )
        }
    }
}