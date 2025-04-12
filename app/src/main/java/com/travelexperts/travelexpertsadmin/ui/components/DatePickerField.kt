package com.travelexperts.travelexpertsadmin.ui.components

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DatePickerField(
    label: String,
    date: String,
    isEnabled: Boolean,
    onDateSelected: (String) -> Unit
) {
    val context = LocalContext.current
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    var selectedDate by remember { mutableStateOf(date) }

    val calendar = Calendar.getInstance()
    try {
        val parsedDate = dateFormat.parse(selectedDate)
        parsedDate?.let { calendar.time = it }
    } catch (_: Exception) {}

    //  Wrapping Box to make clickable
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .let {
                if (isEnabled) it.clickable {
                    Log.i("DatePickerField", "showDatePicker clicked called")
                    DatePickerDialog(
                        context,
                        { _, year, month, dayOfMonth ->
                            val newDate = "%04d-%02d-%02d".format(year, month + 1, dayOfMonth)
                            selectedDate = newDate
                            onDateSelected(newDate)
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                } else it
            }
    ) {
        OutlinedTextField(
            value = selectedDate,
            onValueChange = {},
            label = { Text(label) },
            readOnly = true, // <--- Ensure this is true
            enabled = isEnabled,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

private fun showDatePicker(context: Context, onDateSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    DatePickerDialog(context, { _, year, month, day ->
        val selectedDate = "$year-${month + 1}-$day"
        onDateSelected(selectedDate)
    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
}
