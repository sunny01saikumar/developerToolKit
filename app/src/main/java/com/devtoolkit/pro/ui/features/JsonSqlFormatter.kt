package com.devtoolkit.pro.ui.features

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Compress
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.devtoolkit.pro.ui.components.CodeViewer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement

// --- JSON Formatter Utility ---
object JsonFormatterUtil {
    private val jsonPretty = Json { prettyPrint = true }
    private val jsonMinify = Json { prettyPrint = false }

    fun prettyPrint(input: String): Result<String> {
        return try {
            val element = jsonPretty.parseToJsonElement(input)
            Result.success(jsonPretty.encodeToString(JsonElement.serializer(), element))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun minify(input: String): Result<String> {
        return try {
            val element = jsonMinify.parseToJsonElement(input)
            Result.success(jsonMinify.encodeToString(JsonElement.serializer(), element))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun validate(input: String): String {
        if (input.isBlank()) return "Input is empty"
        return try {
            jsonPretty.parseToJsonElement(input)
            "Valid JSON"
        } catch (e: Exception) {
            "Invalid JSON: ${e.localizedMessage}"
        }
    }
}

// --- SQL Formatter Utility ---
object SqlFormatterUtil {
    private val KEYWORDS = setOf(
        "SELECT", "FROM", "WHERE", "AND", "OR", "JOIN", "LEFT", "RIGHT", "INNER", 
        "ON", "GROUP", "ORDER", "BY", "HAVING", "LIMIT", "INSERT", "INTO", "VALUES", 
        "UPDATE", "SET", "DELETE", "CREATE", "TABLE", "INDEX", "DROP", "ALTER"
    )

    fun beautify(sql: String): String {
        if (sql.isBlank()) return ""
        // Split by whitespace
        val tokens = sql.split(Regex("\\s+"))
        val formatted = StringBuilder()
        var indentLevel = 0
        val indent = "    " // 4 spaces

        for (token in tokens) {
            val upperToken = token.uppercase()
            if (KEYWORDS.contains(upperToken)) {
                // Insert newline before major keywords
                if (formatted.isNotEmpty()) {
                    formatted.append("\n")
                }
                
                // Adjust indent level for brackets or specific clauses
                if (upperToken == "FROM" || upperToken == "WHERE" || upperToken == "JOIN" || 
                    upperToken == "LEFT" || upperToken == "RIGHT" || upperToken == "INNER" || 
                    upperToken == "ORDER" || upperToken == "GROUP" || upperToken == "SET" || 
                    upperToken == "VALUES") {
                    indentLevel = 1
                } else if (upperToken == "SELECT" || upperToken == "INSERT" || upperToken == "UPDATE" || upperToken == "DELETE") {
                    indentLevel = 0
                }
                
                repeat(indentLevel) { formatted.append(indent) }
                formatted.append(upperToken).append(" ")
            } else {
                formatted.append(token).append(" ")
            }
        }
        return formatted.toString().trim()
    }

    fun minify(sql: String): String {
        // Strip multiple spaces and newlines
        return sql.replace(Regex("\\s+"), " ").trim()
    }
}

// --- JSON Formatter Screen ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JsonFormatterScreen(onBackClick: () -> Unit) {
    var input by remember { mutableStateOf("") }
    var output by remember { mutableStateOf("") }
    var validationResult by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("JSON Formatter", fontWeight = FontWeight.Bold) },
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
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                label = { Text("Enter raw JSON") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Action row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        val result = JsonFormatterUtil.prettyPrint(input)
                        output = result.getOrElse { it.localizedMessage ?: "Invalid JSON" }
                        validationResult = ""
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Pretty", maxLines = 1)
                }

                Button(
                    onClick = {
                        val result = JsonFormatterUtil.minify(input)
                        output = result.getOrElse { it.localizedMessage ?: "Invalid JSON" }
                        validationResult = ""
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Compress, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Minify", maxLines = 1)
                }

                Button(
                    onClick = {
                        validationResult = JsonFormatterUtil.validate(input)
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Validate", maxLines = 1)
                }
            }

            if (validationResult.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (validationResult.startsWith("Valid")) 
                            MaterialTheme.colorScheme.primaryContainer 
                            else MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = validationResult,
                        modifier = Modifier.padding(12.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            CodeViewer(code = output, title = "Formatted Output")
        }
    }
}

// --- SQL Formatter Screen ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SqlFormatterScreen(onBackClick: () -> Unit) {
    var input by remember { mutableStateOf("") }
    var output by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("SQL Formatter", fontWeight = FontWeight.Bold) },
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
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                label = { Text("Enter raw SQL query") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Action row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        output = SqlFormatterUtil.beautify(input)
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Beautify")
                }

                Button(
                    onClick = {
                        output = SqlFormatterUtil.minify(input)
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Compress, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Minify")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            CodeViewer(code = output, title = "Formatted SQL Output")
        }
    }
}
