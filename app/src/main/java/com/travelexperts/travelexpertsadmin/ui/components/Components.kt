package com.travelexperts.travelexpertsadmin.ui.components


import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.travelexperts.travelexpertsadmin.data.api.response.Agent
import com.travelexperts.travelexpertsadmin.data.api.response.Booking
import com.travelexperts.travelexpertsadmin.data.api.response.Customer
import com.travelexperts.travelexpertsadmin.data.getStatusColor

@Composable
fun CustomCardView(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    titleColor: Color = Color(0xFF2D5ADF),
    descriptionColor: Color = Color(0xFF333333),
    cardBackGroundColor: Color = Color(0xFFCAD6F7),
    image: Painter,
    onClick: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = cardBackGroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onClick,
        modifier = modifier
            .size(160.dp)
//            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Top
        ) {
            // Image (Top Left)
            Image(
                painter = image,
                contentDescription = "Card Icon",
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Title
            Text(
                text = title,
                fontSize = 14.sp,
                color = titleColor,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Description
            Text(
                text = description,
                fontSize = 11.sp,
                lineHeight = 13.sp,
                fontWeight = FontWeight.Normal,
                color = descriptionColor,
//                modifier = Modifier.padding(horizontal = 16.dp)
            )

        }
    }
}

@Composable
fun StatusBadge(status: String) {
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = getStatusColor(status)),
        modifier = Modifier.padding(4.dp)
    ) {
        Text(
            text = status,
            color = Color.White,
            modifier = Modifier.padding(6.dp),
            fontSize = 14.sp
        )
    }
}

@Composable
fun ProfileField(
    label: String,
    value: String,
    isEditMode: Boolean,
    onValueChange: (String) -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, fontSize = 14.sp, color = Color.Gray)

        if (isEditMode) {
            OutlinedTextField(
                value = value,
                onValueChange = { onValueChange(it) },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            Text(
                text = value,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }

    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun CustomerCard(
    customer: Customer,
    onViewBookings: () -> Unit,
    onViewEditCustomer: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onViewEditCustomer() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Person, contentDescription = "Customer", modifier = Modifier.size(48.dp))

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text("${customer.custfirstname} ${customer.custlastname}", style = MaterialTheme.typography.titleMedium)
                Text(customer.custemail, style = MaterialTheme.typography.bodySmall)
            }

            Column {
                Button(onClick = onViewBookings, contentPadding = PaddingValues(6.dp)) {
                    Text("Bookings")
                }
            }
        }
    }
}

@Composable
fun BookingCard(booking: Booking, onEdit: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Booking #: ${booking.bookingNo ?: "N/A"}", style = MaterialTheme.typography.titleMedium)
            Text("Date: ${booking.savedAt}")
            Text("Travelers: ${booking.travelerCount}")

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = onEdit) {
                Text("Edit Booking")
            }
        }
    }
}

@Composable
fun AgentApprovalItem(agent: Agent, onApprove: () -> Unit, onReject: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = agent.agtfirstname, style = MaterialTheme.typography.bodyLarge)
            Text(text = agent.agtemail, style = MaterialTheme.typography.bodySmall, color = Color.Gray)

            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = onApprove,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50), // Green
                        contentColor = Color.White
                    )
                ) {
                    Text("Approve")
                }

                Button(
                    onClick = onReject,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF44336), // Red
                        contentColor = Color.White
                    )
                ) {
                    Text("Reject")
                }
            }
        }
    }
}



