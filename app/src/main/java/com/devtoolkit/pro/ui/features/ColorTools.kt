package com.devtoolkit.pro.ui.features

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.devtoolkit.pro.ui.components.CodeViewer
import android.content.ClipboardManager
import android.content.ClipData
import android.widget.Toast

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorToolsScreen(onBackClick: () -> Unit) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    // Conversions State
    var red by remember { mutableFloatStateOf(99f) }
    var green by remember { mutableFloatStateOf(102f) }
    var blue by remember { mutableFloatStateOf(241f) } // Default #6366F1
    var alpha by remember { mutableFloatStateOf(255f) }

    // Gradient Generator State
    var gRed1 by remember { mutableFloatStateOf(138f) }
    var gGreen1 by remember { mutableFloatStateOf(35f) }
    var gBlue1 by remember { mutableFloatStateOf(135f) } // #8A2387

    var gRed2 by remember { mutableFloatStateOf(233f) }
    var gGreen2 by remember { mutableFloatStateOf(64f) }
    var gBlue2 by remember { mutableFloatStateOf(87f) } // #E94057

    // Calculations for conversions
    val currentColor = Color(red.toInt(), green.toInt(), blue.toInt(), alpha.toInt())
    val hexString = String.format(
        "#%02X%02X%02X%02X",
        alpha.toInt(), red.toInt(), green.toInt(), blue.toInt()
    )
    val rgbString = "rgb(${red.toInt()}, ${green.toInt()}, ${blue.toInt()})"
    val argbString = "argb(${alpha.toInt()}, ${red.toInt()}, ${green.toInt()}, ${blue.toInt()})"

    // Calculations for gradient
    val gColor1 = Color(gRed1.toInt(), gGreen1.toInt(), gBlue1.toInt())
    val gColor2 = Color(gRed2.toInt(), gGreen2.toInt(), gBlue2.toInt())
    val gHex1 = String.format("#%02X%02X%02X", gRed1.toInt(), gGreen1.toInt(), gBlue1.toInt())
    val gHex2 = String.format("#%02X%02X%02X", gRed2.toInt(), gGreen2.toInt(), gBlue2.toInt())

    val cssGradient = "background: linear-gradient(135deg, $gHex1, $gHex2);"
    val composeGradient = "Brush.linearGradient(listOf(Color(0xFF${gHex1.substring(1)}), Color(0xFF${gHex2.substring(1)})))"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Color Tools", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            // --- Section 1: Color Picker & Converter ---
            Text(
                text = "Color Converter & Picker",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Preview box
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(currentColor)
                            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // RGB Sliders
                    SliderRow(label = "Red: ${red.toInt()}", value = red, onValueChange = { red = it }, max = 255f)
                    SliderRow(label = "Green: ${green.toInt()}", value = green, onValueChange = { green = it }, max = 255f)
                    SliderRow(label = "Blue: ${blue.toInt()}", value = blue, onValueChange = { blue = it }, max = 255f)
                    SliderRow(label = "Alpha: ${alpha.toInt()}", value = alpha, onValueChange = { alpha = it }, max = 255f)

                    Spacer(modifier = Modifier.height(16.dp))

                    // Values grid
                    listOf(
                        "HEX" to hexString,
                        "RGB" to rgbString,
                        "ARGB" to argbString
                    ).forEach { (label, value) ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(label, fontWeight = FontWeight.Bold)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(value, style = MaterialTheme.typography.bodyMedium)
                                Spacer(modifier = Modifier.width(8.dp))
                                IconButton(
                                    onClick = {
                                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                        clipboard.setPrimaryClip(ClipData.newPlainText("color", value))
                                        Toast.makeText(context, "Copied: $value", Toast.LENGTH_SHORT).show()
                                    },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(Icons.Default.ContentCopy, contentDescription = "Copy", modifier = Modifier.size(16.dp))
                                }
                            }
                        }
                    }
                }
            }

            // --- Section 2: Gradient Generator ---
            Text(
                text = "Gradient Generator",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Preview
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Brush.linearGradient(listOf(gColor1, gColor2)))
                            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Color 1 (Left)", fontWeight = FontWeight.Bold)
                    SliderRow(label = "R: ${gRed1.toInt()}", value = gRed1, onValueChange = { gRed1 = it }, max = 255f)
                    SliderRow(label = "G: ${gGreen1.toInt()}", value = gGreen1, onValueChange = { gGreen1 = it }, max = 255f)
                    SliderRow(label = "B: ${gBlue1.toInt()}", value = gBlue1, onValueChange = { gBlue1 = it }, max = 255f)

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("Color 2 (Right)", fontWeight = FontWeight.Bold)
                    SliderRow(label = "R: ${gRed2.toInt()}", value = gRed2, onValueChange = { gRed2 = it }, max = 255f)
                    SliderRow(label = "G: ${gGreen2.toInt()}", value = gGreen2, onValueChange = { gGreen2 = it }, max = 255f)
                    SliderRow(label = "B: ${gBlue2.toInt()}", value = gBlue2, onValueChange = { gBlue2 = it }, max = 255f)
                }
            }

            CodeViewer(code = cssGradient, title = "CSS Code")
            Spacer(modifier = Modifier.height(12.dp))
            CodeViewer(code = composeGradient, title = "Jetpack Compose Code")
        }
    }
}

@Composable
fun SliderRow(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    max: Float
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, modifier = Modifier.width(90.dp), style = MaterialTheme.typography.bodyMedium)
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = 0f..max,
            modifier = Modifier.weight(1f)
        )
    }
}
