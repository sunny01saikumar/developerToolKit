package com.devtoolkit.pro.ui.features

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.devtoolkit.pro.ui.components.CodeViewer
import java.security.SecureRandom
import java.util.UUID

// --- UUID Generation Utility ---
object UuidUtil {
    fun generateBatch(count: Int): String {
        val safeCount = count.coerceIn(1, 100)
        return (1..safeCount).joinToString("\n") { UUID.randomUUID().toString() }
    }
}

// --- Password Generation Utility ---
object PasswordUtil {
    private val UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private val LOWER = "abcdefghijklmnopqrstuvwxyz"
    private val DIGITS = "0123456789"
    private val SYMBOLS = "!@#$%^&*()_+-=[]{}|;':\",./<>?"

    fun generate(
        length: Int,
        uppercase: Boolean,
        lowercase: Boolean,
        numbers: Boolean,
        symbols: Boolean
    ): String {
        val pool = StringBuilder()
        if (uppercase) pool.append(UPPER)
        if (lowercase) pool.append(LOWER)
        if (numbers) pool.append(DIGITS)
        if (symbols) pool.append(SYMBOLS)

        if (pool.isEmpty()) return ""

        val sr = SecureRandom()
        return (1..length)
            .map { pool[sr.nextInt(pool.length)] }
            .joinToString("")
    }
}

// --- UUID Generator Screen ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UuidGeneratorScreen(onBackClick: () -> Unit) {
    var quantity by remember { mutableFloatStateOf(10f) }
    var output by remember { mutableStateOf("") }

    // Initial load
    LaunchedEffect(Unit) {
        output = UuidUtil.generateBatch(quantity.toInt())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("UUID V4 Generator", fontWeight = FontWeight.Bold) },
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Batch Size: ${quantity.toInt()}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Slider(
                        value = quantity,
                        onValueChange = { quantity = it },
                        valueRange = 1f..50f,
                        steps = 49
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            output = UuidUtil.generateBatch(quantity.toInt())
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Regenerate UUIDs")
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            CodeViewer(code = output, title = "Generated UUIDs (V4)")
        }
    }
}

// --- Password Generator Screen ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordGeneratorScreen(onBackClick: () -> Unit) {
    var length by remember { mutableFloatStateOf(16f) }
    var uppercase by remember { mutableStateOf(true) }
    var lowercase by remember { mutableStateOf(true) }
    var numbers by remember { mutableStateOf(true) }
    var symbols by remember { mutableStateOf(true) }

    var output by remember { mutableStateOf("") }

    fun refreshPassword() {
        output = PasswordUtil.generate(
            length.toInt(),
            uppercase,
            lowercase,
            numbers,
            symbols
        )
    }

    // Trigger password regeneration when params change
    LaunchedEffect(length, uppercase, lowercase, numbers, symbols) {
        refreshPassword()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Password Generator", fontWeight = FontWeight.Bold) },
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Password Length: ${length.toInt()}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Slider(
                        value = length,
                        onValueChange = { length = it },
                        valueRange = 6f..64f,
                        steps = 58
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Include Characters",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // Options Grid/List
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(checked = uppercase, onCheckedChange = { uppercase = it })
                                Text("A-Z (Uppercase)")
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(checked = lowercase, onCheckedChange = { lowercase = it })
                                Text("a-z (Lowercase)")
                            }
                        }
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(checked = numbers, onCheckedChange = { numbers = it })
                                Text("0-9 (Numbers)")
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(checked = symbols, onCheckedChange = { symbols = it })
                                Text("!@#$ (Symbols)")
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { refreshPassword() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Casino, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Generate Password")
                    }
                }
            }

            CodeViewer(code = output, title = "Secure Password")
        }
    }
}
