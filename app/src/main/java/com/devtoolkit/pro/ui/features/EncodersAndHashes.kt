package com.devtoolkit.pro.ui.features

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.devtoolkit.pro.ui.components.CodeViewer
import java.net.URLDecoder
import java.net.URLEncoder
import java.security.MessageDigest

// --- Hashing Utility ---
object HashUtil {
    fun generate(input: String, algorithm: String): String {
        if (input.isEmpty()) return ""
        return try {
            val digest = MessageDigest.getInstance(algorithm)
            val hashBytes = digest.digest(input.toByteArray(Charsets.UTF_8))
            hashBytes.joinToString("") { "%02x".format(it) }
        } catch (e: Exception) {
            "Error: ${e.localizedMessage}"
        }
    }
}

// --- Base64 Screen ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Base64Screen(onBackClick: () -> Unit) {
    var input by remember { mutableStateOf("") }
    var output by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Base64 Encoder/Decoder", fontWeight = FontWeight.Bold) },
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
                .padding(16dp)
        ) {
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                label = { Text("Input Text") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160dp),
                shape = RoundedCornerShape(12dp)
            )

            Spacer(modifier = Modifier.height(12dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10dp)
            ) {
                Button(
                    onClick = {
                        output = try {
                            android.util.Base64.encodeToString(
                                input.toByteArray(Charsets.UTF_8),
                                android.util.Base64.NO_WRAP
                            )
                        } catch (e: Exception) {
                            "Encoding error: ${e.localizedMessage}"
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Lock, contentDescription = null)
                    Spacer(modifier = Modifier.width(6dp))
                    Text("Encode")
                }

                Button(
                    onClick = {
                        output = try {
                            val decodedBytes = android.util.Base64.decode(input, android.util.Base64.DEFAULT)
                            String(decodedBytes, Charsets.UTF_8)
                        } catch (e: Exception) {
                            "Decoding error: Invalid Base64 format."
                        }
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Icon(Icons.Default.LockOpen, contentDescription = null)
                    Spacer(modifier = Modifier.width(6dp))
                    Text("Decode")
                }
            }

            Spacer(modifier = Modifier.height(20dp))

            CodeViewer(code = output, title = "Output Results")
        }
    }
}

// --- URL Screen ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UrlScreen(onBackClick: () -> Unit) {
    var input by remember { mutableStateOf("") }
    var output by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("URL Encoder/Decoder", fontWeight = FontWeight.Bold) },
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
                .padding(16dp)
        ) {
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                label = { Text("Input URL/String") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160dp),
                shape = RoundedCornerShape(12dp)
            )

            Spacer(modifier = Modifier.height(12dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10dp)
            ) {
                Button(
                    onClick = {
                        output = try {
                            URLEncoder.encode(input, "UTF-8")
                        } catch (e: Exception) {
                            "Encoding error: ${e.localizedMessage}"
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Lock, contentDescription = null)
                    Spacer(modifier = Modifier.width(6dp))
                    Text("Encode")
                }

                Button(
                    onClick = {
                        output = try {
                            URLDecoder.decode(input, "UTF-8")
                        } catch (e: Exception) {
                            "Decoding error: ${e.localizedMessage}"
                        }
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Icon(Icons.Default.LockOpen, contentDescription = null)
                    Spacer(modifier = Modifier.width(6dp))
                    Text("Decode")
                }
            }

            Spacer(modifier = Modifier.height(20dp))

            CodeViewer(code = output, title = "Output Results")
        }
    }
}

// --- Hash Generator Screen ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HashGeneratorScreen(onBackClick: () -> Unit) {
    var input by remember { mutableStateOf("") }
    var selectedAlgo by remember { mutableStateOf("SHA-256") }

    val algorithms = listOf("MD5", "SHA-1", "SHA-256", "SHA-512")
    var output by remember { mutableStateOf("") }

    // Re-calculate hash when input or algorithm changes
    LaunchedEffect(input, selectedAlgo) {
        output = HashUtil.generate(input, selectedAlgo)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hash Generator", fontWeight = FontWeight.Bold) },
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
                .padding(16dp)
        ) {
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                label = { Text("Enter Input Text") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140dp),
                shape = RoundedCornerShape(12dp)
            )

            Spacer(modifier = Modifier.height(16dp))

            Text(
                text = "Select Algorithm",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8dp)
            ) {
                algorithms.forEach { algo ->
                    val isSelected = selectedAlgo == algo
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedAlgo = algo },
                        label = { Text(algo) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24dp))

            CodeViewer(code = output, title = "$selectedAlgo Hash Output")
        }
    }
}
