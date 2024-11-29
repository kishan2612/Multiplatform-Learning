package org.kmp.learning

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun App() {
    val otp = "1234"
    MaterialTheme {
        var otpError by remember { mutableStateOf(false) }
        Column(
            Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MoonCheckboxGroupExample()
        }
    }
}

@Composable
fun MoonRadioButtonGroupExample() {
    val options = listOf("Option A", "Option B", "Option C")
    var selectedOption by remember { mutableStateOf(options[0]) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Select an Option:",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                MoonRadioButton(
                    selected = selectedOption == option,
                    onClick = { selectedOption = option },
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = option, style = MaterialTheme.typography.body1)
            }
        }
    }
}

@Composable
fun MoonCheckboxGroupExample() {
    val options = listOf("Option A", "Option B", "Option C")
    var selectedOption by remember { mutableStateOf("Option A") }
    var partialOption by remember { mutableStateOf("Option B") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Select Options:",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                MoonCheckbox(
                    state = when (option) {
                        selectedOption -> CheckboxState.SELECTED
                        partialOption -> CheckboxState.PARTIAL
                        else -> CheckboxState.UNSELECTED
                    },
                    onClick = {
                        when (option) {
                            selectedOption -> selectedOption = "" // Deselect
                            partialOption -> partialOption = "" // Deselect
                            else -> selectedOption = option // Select
                        }
                    },
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = option, style = MaterialTheme.typography.body1)
            }
        }
    }
}

