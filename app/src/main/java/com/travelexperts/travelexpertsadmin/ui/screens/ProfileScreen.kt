package com.travelexperts.travelexpertsadmin.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.travelexperts.travelexpertsadmin.R
import com.travelexperts.travelexpertsadmin.data.ProfileData
import com.travelexperts.travelexpertsadmin.ui.components.CustomCardView
import com.travelexperts.travelexpertsadmin.ui.components.ProfileField
import com.travelexperts.travelexpertsadmin.ui.components.StatusBadge
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.travelexperts.travelexpertsadmin.ui.components.ProfileField
import com.travelexperts.travelexpertsadmin.ui.components.StatusBadge
import com.travelexperts.travelexpertsadmin.ui.theme.Primary

@Composable
fun ProfileScreen(navController: NavController) {
    var isEditMode by remember { mutableStateOf(false) }
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current

    // Gallery Picker
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> if (uri != null) profileImageUri = uri }
    )

    // Camera Capture
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { bitmap ->
            // Convert Bitmap to Uri if needed (requires extra logic)
        }
    )

    // Sample Profile Data
    var profile by remember { mutableStateOf(
        ProfileData(
            profileImage = R.drawable.woman,
            firstName = "John",
            lastName = "Doe",
            phoneNumber = "123-456-7890",
            email = "johndoe@example.com",
            position = "Manager",
            agency = "Agency A",
            status = "Active"
        )
    )}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Image with Camera Icon
        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = Modifier.size(120.dp)
        ) {
            if (profileImageUri != null) {
                AsyncImage(
                    model = profileImageUri,
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = profile.profileImage),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                )
            }

            if (isEditMode) {
                IconButton(
                    onClick = { galleryLauncher.launch("image/*") },
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_linked_camera),
                        contentDescription = "Edit Profile Picture",
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Name
        Text("${profile.firstName} ${profile.lastName}", fontSize = 24.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)

        // Status Badge
        StatusBadge(status = profile.status)

        Spacer(modifier = Modifier.height(16.dp))

        // Profile Fields
        ProfileField("Phone Number", profile.phoneNumber, isEditMode, onValueChange = { profile = profile.copy(phoneNumber = it) })
        ProfileField("Email", profile.email, isEditMode, onValueChange = { profile = profile.copy(email = it) })
        ProfileField("Position", profile.position, isEditMode, onValueChange = { profile = profile.copy(position = it) })
        ProfileField("Agency", profile.agency, isEditMode, onValueChange = { profile = profile.copy(agency = it) })

        // Status (Not Editable)
        ProfileField("Status", profile.status, isEditMode = false)

        Spacer(modifier = Modifier.height(16.dp))

        // Toggle Edit Button
        Button(
            onClick = { isEditMode = !isEditMode },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Primary )// Only changes background, keeps other defaults
        ) {
            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
            Spacer(modifier = Modifier.width(8.dp))
            Text(if (isEditMode) "Save Changes" else "Edit Profile")
        }
    }
}
