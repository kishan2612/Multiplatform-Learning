package org.kmp.learning


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OTPInput(
    otpLength: Int,
    onOtpValueChange: (String) -> Unit,
    showError:Boolean = false
) {
    // State-aware list to hold OTP values as strings
    val otpValues = remember { mutableStateListOf(*List(otpLength) { "" }.toTypedArray()) }
    // FocusRequesters for each OTP field
    val focusRequesters = remember { List(otpLength) { FocusRequester() } }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(otpLength) { index ->
            OTPInputField(
                value = otpValues[index],
                focusRequester = focusRequesters[index],
                onOtpValueChange = { newValue ->
                    // Ensure only one digit is entered
                    if (newValue.length <= 1 && newValue.all { it.isDigit() }) {
                        otpValues[index] = newValue
                        onOtpValueChange(otpValues.joinToString(""))

                        // Move focus to next field if a digit is entered and it's not the last field
                        if (newValue.isNotEmpty() && index < otpLength - 1) {
                            focusRequesters[index + 1].requestFocus()
                        }
                    }
                },
                onBackspace = {
                    if (index > 0) {
                        otpValues[index - 1] = ""
                        focusRequesters[index - 1].requestFocus()
                        onOtpValueChange(otpValues.joinToString(""))
                    }
                },
                onFocusChanged = { isFocused ->
                    // Optional: handle focus state changes here, e.g., update the border color
                },
                showError = showError // Pass the index for semantics
            )
        }
    }
}

@Composable
fun OTPInputField(
    value: String,
    focusRequester: FocusRequester,
    onOtpValueChange: (String) -> Unit,
    onBackspace: () -> Unit,
    onFocusChanged: (Boolean) -> Unit,
    showError: Boolean
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            // Allow only single character input and digits
            if (newValue.length <= 1 && newValue.all { it.isDigit() }) {
                onOtpValueChange(newValue)
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        modifier = Modifier
            .width(48.dp)
            .height(56.dp)
            .focusRequester(focusRequester)
            .onKeyEvent { keyEvent ->
                // Detect backspace on an empty field
                if (keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.Backspace && value.isEmpty()) {
                    onBackspace()
                    true
                } else {
                    false
                }
            }
            .onFocusChanged { focusState ->
                onFocusChanged(focusState.isFocused)
            }
        ,shape = RoundedCornerShape(8.dp),
        textStyle = TextStyle(fontSize = 18.sp, textAlign = TextAlign.Center),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = if (showError) Color.Red else Color.Cyan,
            unfocusedBorderColor =if (showError) Color.Red else  Color.Gray,
            cursorColor = Color.Transparent // Ensure cursor color is transparent
        )
    )
}
