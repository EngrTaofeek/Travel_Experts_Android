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
import android.widget.Toast
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
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.travelexperts.travelexpertsadmin.data.api.response.Agency
import com.travelexperts.travelexpertsadmin.data.api.response.Agent
import com.travelexperts.travelexpertsadmin.datastore.UserPreferences
import com.travelexperts.travelexpertsadmin.ui.components.DropdownMenuComponent
import com.travelexperts.travelexpertsadmin.ui.components.ProfileField
import com.travelexperts.travelexpertsadmin.ui.components.StatusBadge
import com.travelexperts.travelexpertsadmin.ui.theme.Primary
import com.travelexperts.travelexpertsadmin.utils.NetworkResult
import com.travelexperts.travelexpertsadmin.utils.resolveImageUrl
import com.travelexperts.travelexpertsadmin.viewmodels.AgencyViewModel
import com.travelexperts.travelexpertsadmin.viewmodels.AgentViewModel
import java.io.File

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: AgentViewModel = hiltViewModel(),

) {
    val context = LocalContext.current
    val agentState by viewModel.agentDetail.collectAsState()
    val updateState by viewModel.updateResult.collectAsState()
    val agencyViewModel: AgencyViewModel = hiltViewModel()
    val agencyState by agencyViewModel.agencies.collectAsState()
    val agencies = when (agencyState) {
        is NetworkResult.Success -> (agencyState as NetworkResult.Success<List<Agency>>).data
        else -> emptyList()
    }
    val userIdString by UserPreferences.getUserId(context).collectAsState(initial = null)

    LaunchedEffect(userIdString) {
        userIdString?.toIntOrNull()?.let { id ->
            viewModel.fetchAgentById(id)
        }
    }


    var isEditMode by remember { mutableStateOf(false) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val imageFile = remember { File(context.cacheDir, "profile.jpg") }
    val imageUri = remember {
        FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            imageFile
        )
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            selectedImageUri = uri
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            selectedImageUri = imageUri
        }
    }


    LaunchedEffect(updateState) {
        when (updateState) {
            is NetworkResult.Success -> Toast.makeText(context, "Profile updated!", Toast.LENGTH_SHORT).show()
            is NetworkResult.Failure -> Toast.makeText(context, "Error: ${(updateState as NetworkResult.Failure).message}", Toast.LENGTH_SHORT).show()
            else -> Unit
        }
    }

    when (agentState) {
        is NetworkResult.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }

        is NetworkResult.Success -> {
            val agent = (agentState as NetworkResult.Success<Agent>).data
            var localAgent by remember { mutableStateOf(agent) }

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(contentAlignment = Alignment.BottomEnd) {
                    AsyncImage(
                        model = selectedImageUri ?: resolveImageUrl(agent.profileImageUrl),
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    if (isEditMode) {
                        IconButton(
                            onClick = { galleryLauncher.launch("image/*") },
                            modifier = Modifier
                                .offset(x = (-8).dp, y = (-8).dp)
                                .background(MaterialTheme.colorScheme.primary, CircleShape)
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit Image", tint = Color.White)
                        }
                    }
                }
                if (isEditMode) {
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        TextButton(onClick = { galleryLauncher.launch("image/*") }) {
                            Text("Gallery")
                        }
                        TextButton(onClick = { cameraLauncher.launch(imageUri) }) {
                            Text("Camera")
                        }
                    }
                }
                // Status Badge
                StatusBadge(status = agent.status ?: "pending")

                Spacer(modifier = Modifier.height(16.dp))


                Spacer(Modifier.height(16.dp))

                ProfileField(
                    value = localAgent.agtfirstname,
                    onValueChange = { localAgent = localAgent.copy(agtfirstname = it) },
                    label = "First Name",
                    isEditMode = isEditMode,
                )

                ProfileField(
                    value = localAgent.agtmiddleinitial ?: "",
                    onValueChange = { localAgent = localAgent.copy(agtmiddleinitial = it) },
                    label = "Middle Initial",
                    isEditMode = isEditMode,
                )

                ProfileField(
                    value = localAgent.agtlastname,
                    onValueChange = { localAgent = localAgent.copy(agtlastname = it) },
                    label = "Last Name",
                    isEditMode = isEditMode,
                )

                ProfileField(
                    value = localAgent.agtbusphone,
                    onValueChange = { localAgent = localAgent.copy(agtbusphone = it) },
                    label = "Phone Number",
                    isEditMode = isEditMode,
                )

                ProfileField(
                    value = localAgent.agtemail,
                    onValueChange = {},
                    label = "Email",
                    isEditMode = false,
                )

                ProfileField(
                    value = localAgent.agtposition,
                    onValueChange = { localAgent = localAgent.copy(agtposition = it) },
                    label = "Position",
                    isEditMode = isEditMode,
                )

                val agencyOptions = agencies.map { "${it.agncycity}, ${it.agncyprov}" }

                DropdownMenuComponent(
                    label = "Agency",
                    items = agencyOptions,
                    selectedItem = "${localAgent.agencyid.agncycity}, ${localAgent.agencyid.agncyprov}",
                    enabled = isEditMode,
                    onItemSelected = { selected ->
                        val selectedAgency = agencies.find { "${it.agncycity}, ${it.agncyprov}" == selected }
                        selectedAgency?.let {
                            localAgent = localAgent.copy(agencyid = it)
                        }
                    }
                )

                ProfileField("Status", localAgent.status ?: "pending", isEditMode = false)

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (isEditMode) {
                            userIdString?.toIntOrNull()?.let { id ->

                                viewModel.updateAgent(id,localAgent, selectedImageUri, context.contentResolver)
                            }
                        }
                        isEditMode = !isEditMode
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (isEditMode) "Save Changes" else "Edit Profile")
                }
            }
        }

        is NetworkResult.Failure -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = (agentState as NetworkResult.Failure).message, color = Color.Red)
        }

        null -> {}
    }
}
