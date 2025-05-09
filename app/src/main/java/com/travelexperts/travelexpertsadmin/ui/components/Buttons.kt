package com.travelexperts.travelexpertsadmin.ui.components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.travelexperts.travelexpertsadmin.ui.navigation.Routes
import com.travelexperts.travelexpertsadmin.ui.theme.Primary

@Composable
fun SolidButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    buttonColor: Color = Primary,
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
fun RegisterTextButton(
    onClick: () -> Unit,) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Don't have an account?")
        Spacer(modifier = Modifier.weight(1f)) // Push the "Create One" text to the end

        val annotatedText = buildAnnotatedString {
            pushStyle(
                SpanStyle(
                    color = MaterialTheme.colorScheme.primary, // Use your theme's primary color
                    textDecoration = TextDecoration.Underline
                )
            )
            append("Create One")
            pop()
        }

        Text(
            text = annotatedText,
            modifier = Modifier.clickable { onClick() }
        )
    }
}
@Composable
fun OutlineButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    borderColor: Color = Primary,
    disabledBorderColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
    textColor: Color = Primary
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

