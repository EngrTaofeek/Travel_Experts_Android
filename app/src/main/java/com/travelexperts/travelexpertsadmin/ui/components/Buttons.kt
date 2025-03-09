package com.travelexperts.travelexpertsadmin.ui.components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SolidButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    buttonColor: Color = MaterialTheme.colorScheme.primary,
    disabledColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
    textColor: Color = Color.White
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (enabled) buttonColor else disabledColor,
            disabledContainerColor = disabledColor
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.padding(8.dp),
        enabled = enabled
    ) {
        Text(
            text = text,
            color = if (enabled) textColor else Color.Gray,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun OutlineButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    borderColor: Color = MaterialTheme.colorScheme.primary,
    disabledBorderColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
    textColor: Color = MaterialTheme.colorScheme.primary
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .padding(8.dp),
        enabled = enabled,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, if (enabled) borderColor else disabledBorderColor),


        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = if (enabled) textColor else Color.Gray,
            disabledContentColor = Color.Gray,

        )
    ) {
        Text(
            text = text,
            color = if (enabled) textColor else Color.Gray,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
@Composable
fun IconButton(
    icon: ImageVector,
    onClick: () -> Unit,
    contentDescription: String,
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colorScheme.primary
) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(imageVector = icon, contentDescription = contentDescription, tint = tint)
    }
}

