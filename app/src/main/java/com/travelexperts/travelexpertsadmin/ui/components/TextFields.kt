package com.travelexperts.travelexpertsadmin.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import com.travelexperts.travelexpertsadmin.R

@Composable
fun EmailTextField(
    email: String,
    onEmailChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = email,
        onValueChange = { onEmailChange(it) },
        label = { Text("Email Address") },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    )
}
@Composable
fun TextField(
    email: String,
    onEmailChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = email,
        onValueChange = { onEmailChange(it) },
        label = { Text("Email Address") },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    )
}

@Composable
fun PasswordTextField(
    password: String,
    onPasswordChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onToggleVisibility: () -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Password"
) {
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = onToggleVisibility) {
                Image(

                    painter =  painterResource(id = if (isPasswordVisible) R.drawable.visibility_off_24 else R.drawable.visibility_24 ),

                    contentDescription = if (isPasswordVisible) "Hide Password" else "Show Password"
                )
            }
        },
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    )
}

@Composable
fun NumberTextField(
    number: String,
    onNumberChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = number,
        onValueChange = {
            if (it.all { char -> char.isDigit() }) { // Ensure only digits
                onNumberChange(it)
            }
        },
        label = { Text("Number") },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    )
}
