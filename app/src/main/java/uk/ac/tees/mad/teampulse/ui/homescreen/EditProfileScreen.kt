package uk.ac.tees.mad.teampulse.ui.homescreen

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import uk.ac.tees.mad.teampulse.R
import uk.ac.tees.mad.teampulse.authentication.viewmodel.AuthViewmodel
import uk.ac.tees.mad.teampulse.ui.theme.poppinsFam
import java.io.File
import java.io.FileOutputStream
import java.util.UUID


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun EditProfileScreen(
    authViewmodel: AuthViewmodel,
    navController: NavHostController
){

    val context = LocalContext.current

    val currentUser by authViewmodel.currentUser.collectAsState()
    var updatedname by remember { mutableStateOf(currentUser?.name ?: "")}
    var updatedPhoneNumber by remember { mutableStateOf(currentUser?.phoneNumber ?: "")}
    var showDialog by remember { mutableStateOf(false) }
    val width = LocalConfiguration.current.screenWidthDp.dp * 0.6f
    val cameraPermissionGranted = remember { mutableStateOf(false)}


    //Sync the user updates
    LaunchedEffect(currentUser) {
        authViewmodel.fetchCurrentUser()
    }

    //Launching Gallery to select the picture
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {uri: Uri? ->
        uri?.let {
            authViewmodel.updateProfileImage(uri)
        }
    }

    // Launcher for taking a picture with the camera
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap?.let {
            authViewmodel.updateProfileImage(bitmapToUri(context, bitmap))
        }
    }

    // Permission launcher for requesting camera permission
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        cameraPermissionGranted.value = permissions[Manifest.permission.CAMERA] == true
        Log.i("Camera Permission: ", permissions[Manifest.permission.CAMERA].toString())
        if (cameraPermissionGranted.value) {
            cameraLauncher.launch(null)
        }else {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }


    //Showing the dialog box to ask for camera or gallery
    if (showDialog){
        ImageSelectionSource(
            onDismiss = { showDialog = false },
            onCameraClick = {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                    cameraLauncher.launch(null)
                } else {
                    permissionLauncher.launch(arrayOf(Manifest.permission.CAMERA))
                }
            },
            onGalleryClick = {
                galleryLauncher.launch("image/*")
            }
        )
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Spacer(modifier = Modifier.weight(0.3f))
            GlideImage(
                modifier = Modifier
                    .size(width)
                    .fillMaxWidth(),
                model = currentUser?.profileImgUrl,
                contentDescription = "Profile Picture",
                failure = placeholder(R.drawable.avatar)
            )

            Spacer(modifier = Modifier.weight(0.1f))

            TextButton(onClick = {
                showDialog = true
            }) {
                Text(
                    text = "Edit Profile image",
                    fontSize = 14.sp,
                    fontFamily = poppinsFam
                )
            }

            Spacer(modifier = Modifier.weight(0.1f))

            Text(
                modifier = Modifier.fillMaxWidth(0.85f),
                text = "Name",
                fontSize = 15.sp,
                fontFamily = poppinsFam
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(0.88f),
                value = updatedname,
                onValueChange = {
                    updatedname = it
                },
                shape = RoundedCornerShape(15.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "name"
                    )
                },
                trailingIcon = {
                    Icon(imageVector = Icons.Outlined.Edit, contentDescription = "Edit Name")
                }
            )

            Spacer(modifier = Modifier.weight(0.1f))

            Text(
                modifier = Modifier.fillMaxWidth(0.85f),
                text = "Phone Number",
                fontSize = 15.sp,
                fontFamily = poppinsFam
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(0.88f),
                value = updatedPhoneNumber,
                onValueChange = {
                    updatedPhoneNumber = it
                },
                shape = RoundedCornerShape(15.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "name"
                    )
                },
                trailingIcon = {
                    Icon(imageVector = Icons.Outlined.Edit, contentDescription = "Edit Name")
                }
            )

            Spacer(modifier = Modifier.weight(0.1f))

            Button(
                modifier = Modifier.fillMaxWidth(0.88f),
                onClick = {
                    authViewmodel.updateCurrentUser(updatedname, updatedPhoneNumber)
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Gray
                ),
                border = BorderStroke(1.dp, Color.Gray)
            ) {
                Text(
                    text = "Update User Details",
                    fontFamily = poppinsFam,
                    fontSize = 15.sp
                )
            }

            Spacer(modifier = Modifier.weight(0.3f))
        }
    }
}


@Composable
fun ImageSelectionSource(
    onDismiss: ()->Unit,
    onCameraClick: ()->Unit,
    onGalleryClick: ()->Unit
){
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Choose Image Source")
        },
        text = {
            Column {
                Button(
                    onClick = {
                        onCameraClick()
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Take a Photo",
                        fontSize = 13.sp,
                        fontFamily = poppinsFam
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        onGalleryClick()
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Choose from Gallery",
                        fontFamily = poppinsFam,
                        fontSize = 13.sp
                    )
                }
            }
        },
        confirmButton = {},
        dismissButton = {}
    )
}

fun bitmapToUri(context: Context, bt: Bitmap): Uri{
    val image = File(context.cacheDir, "${UUID.randomUUID()}.jpg")
    val outStream = FileOutputStream(image)
    bt.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
    outStream.flush()
    outStream.close()
    return Uri.fromFile(image)
}
