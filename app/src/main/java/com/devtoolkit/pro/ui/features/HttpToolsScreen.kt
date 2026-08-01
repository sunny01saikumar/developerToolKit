package com.devtoolkit.pro.ui.features

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.devtoolkit.pro.domain.model.HttpHeaderItem
import com.devtoolkit.pro.domain.model.HttpStatusItem
import com.devtoolkit.pro.domain.repository.DevToolkitRepository
import com.devtoolkit.pro.ui.components.CodeViewer
import com.devtoolkit.pro.ui.components.SearchBar

// --- HTTP Status Screen ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HttpStatusScreen(
    repository: DevToolkitRepository,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    val statuses = remember { repository.getHttpStatusCodes() }

    val categories = listOf("All", "Informational", "Success", "Redirection", "Client Error", "Server Error")
    var selectedCategory by remember { mutableStateOf("All") }

    val filtered = remember(searchQuery, selectedCategory) {
        statuses.filter { item ->
            val matchCategory = selectedCategory == "All" || item.category == selectedCategory
            val matchSearch = item.code.toString().contains(searchQuery) ||
                              item.name.contains(searchQuery, ignoreCase = true) ||
                              item.description.contains(searchQuery, ignoreCase = true)
            matchCategory && matchSearch
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("HTTP Status Codes", fontWeight = FontWeight.Bold) },
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
        ) {
            Column(modifier = Modifier.padding(horizontal = 16dp)) {
                SearchBar(query = searchQuery, onQueryChange = { searchQuery = it }, placeholder = "Search by code or status...")
                ScrollableTabRow(
                    selectedTabIndex = categories.indexOf(selectedCategory).coerceAtLeast(0),
                    edgePadding = 0.dp,
                    divider = {},
                    containerColor = Color.Transparent
                ) {
                    categories.forEach { cat ->
                        Tab(selected = selectedCategory == cat, onClick = { selectedCategory = cat }, text = { Text(cat) })
                    }
                }
            }

            Spacer(modifier = Modifier.height(8dp))

            if (filtered.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize().weight(1f), contentAlignment = Alignment.Center) {
                    Text("No status codes found", color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    contentPadding = PaddingValues(horizontal = 16dp, vertical = 8dp),
                    verticalArrangement = Arrangement.spacedBy(12dp)
                ) {
                    items(filtered) { item ->
                        StatusCardItem(item = item, context = context)
                    }
                }
            }
        }
    }
}

@Composable
fun StatusCardItem(item: HttpStatusItem, context: Context) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = item.code.toString(),
                        fontWeight = FontWeight.ExtraBold,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(12dp))
                    Text(
                        text = item.name,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Badge(
                    containerColor = when (item.code) {
                        in 200..299 -> Color(0xFF10B981)
                        in 300..399 -> Color(0xFF3B82F6)
                        in 400..499 -> Color(0xFFF59E0B)
                        else -> Color(0xFFEF4444)
                    }
                ) {
                    Text(item.category, modifier = Modifier.padding(horizontal = 6.dp), color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(8dp))
            Text(text = item.description, style = MaterialTheme.typography.bodyMedium)
            
            Spacer(modifier = Modifier.height(12dp))

            // Raw response example
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                    .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f), RoundedCornerShape(8dp))
                    .clickable {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        clipboard.setPrimaryClip(ClipData.newPlainText("http", item.example))
                        Toast.makeText(context, "Copied response", Toast.LENGTH_SHORT).show()
                    }
                    .padding(12dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Example Header Response:", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                        Text(text = item.example, fontFamily = FontFamily.Monospace, style = MaterialTheme.typography.bodySmall)
                    }
                    Icon(Icons.Default.ContentCopy, contentDescription = "Copy", modifier = Modifier.size(16dp), tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f))
                }
            }
        }
    }
}

// --- HTTP Headers Screen ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HttpHeadersScreen(
    repository: DevToolkitRepository,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    val headers = remember { repository.getHttpHeaders() }

    val categories = listOf("All", "Request", "Response", "Request/Response")
    var selectedCategory by remember { mutableStateOf("All") }

    val filtered = remember(searchQuery, selectedCategory) {
        headers.filter { item ->
            val matchCategory = selectedCategory == "All" || item.category == selectedCategory
            val matchSearch = item.name.contains(searchQuery, ignoreCase = true) ||
                              item.description.contains(searchQuery, ignoreCase = true)
            matchCategory && matchSearch
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("HTTP Headers", fontWeight = FontWeight.Bold) },
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
        ) {
            Column(modifier = Modifier.padding(horizontal = 16dp)) {
                SearchBar(query = searchQuery, onQueryChange = { searchQuery = it }, placeholder = "Search headers...")
                ScrollableTabRow(
                    selectedTabIndex = categories.indexOf(selectedCategory).coerceAtLeast(0),
                    edgePadding = 0.dp,
                    divider = {},
                    containerColor = Color.Transparent
                ) {
                    categories.forEach { cat ->
                        Tab(selected = selectedCategory == cat, onClick = { selectedCategory = cat }, text = { Text(cat) })
                    }
                }
            }

            Spacer(modifier = Modifier.height(8dp))

            if (filtered.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize().weight(1f), contentAlignment = Alignment.Center) {
                    Text("No headers found", color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    contentPadding = PaddingValues(horizontal = 16dp, vertical = 8dp),
                    verticalArrangement = Arrangement.spacedBy(12dp)
                ) {
                    items(filtered) { item ->
                        HeaderCardItem(item = item, context = context)
                    }
                }
            }
        }
    }
}

@Composable
fun HeaderCardItem(item: HttpHeaderItem, context: Context) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.name,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Badge {
                    Text(item.category, modifier = Modifier.padding(horizontal = 4.dp))
                }
            }
            Spacer(modifier = Modifier.height(6dp))
            Text(text = item.description, style = MaterialTheme.typography.bodyMedium)
            
            Spacer(modifier = Modifier.height(10dp))
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                    .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f), RoundedCornerShape(8dp))
                    .clickable {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        clipboard.setPrimaryClip(ClipData.newPlainText("header_val", item.example))
                        Toast.makeText(context, "Copied example", Toast.LENGTH_SHORT).show()
                    }
                    .padding(12dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text("Example Syntax:", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                        Text(text = item.example, fontFamily = FontFamily.Monospace, style = MaterialTheme.typography.bodySmall)
                    }
                    Icon(Icons.Default.ContentCopy, contentDescription = "Copy", modifier = Modifier.size(16dp), tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f))
                }
            }
        }
    }
}

// --- Curl Generator Screen ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurlGeneratorScreen(onBackClick: () -> Unit) {
    var method by remember { mutableStateOf("GET") }
    var url by remember { mutableStateOf("https://api.example.com/data") }
    
    // Header pairs state
    val headersList = remember { mutableStateListOf(Pair("Content-Type", "application/json")) }
    
    var authType by remember { mutableStateOf("None") }
    var authValue by remember { mutableStateOf("") }
    
    var body by remember { mutableStateOf("{\n  \"key\": \"value\"\n}") }
    var outputCurl by remember { mutableStateOf("") }

    val methods = listOf("GET", "POST", "PUT", "DELETE")
    val authTypes = listOf("None", "Bearer Token", "Basic Auth")

    fun generateCurl() {
        val sb = StringBuilder()
        sb.append("curl -X $method \\\n")
        
        // Add headers
        headersList.forEach { (key, value) ->
            if (key.isNotBlank()) {
                sb.append("  -H \"$key: $value\" \\\n")
            }
        }

        // Add auth headers
        if (authType == "Bearer Token" && authValue.isNotBlank()) {
            sb.append("  -H \"Authorization: Bearer $authValue\" \\\n")
        } else if (authType == "Basic Auth" && authValue.isNotBlank()) {
            sb.append("  -H \"Authorization: Basic $authValue\" \\\n")
        }

        // Add body if applicable
        if ((method == "POST" || method == "PUT") && body.isNotBlank()) {
            val escapedBody = body.replace("\"", "\\\"").replace("\n", "")
            sb.append("  -d \"$escapedBody\" \\\n")
        }

        sb.append("  \"$url\"")
        outputCurl = sb.toString()
    }

    // Auto-generate on form modifications
    LaunchedEffect(method, url, headersList.size, authType, authValue, body) {
        generateCurl()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Curl Generator", fontWeight = FontWeight.Bold) },
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
            // Method selection
            Text("HTTP Method", fontWeight = FontWeight.Bold)
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8dp),
                horizontalArrangement = Arrangement.spacedBy(8dp)
            ) {
                methods.forEach { m ->
                    FilterChip(
                        selected = method == m,
                        onClick = { method = m },
                        label = { Text(m) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12dp))

            // URL Input
            OutlinedTextField(
                value = url,
                onValueChange = { url = it },
                label = { Text("Request URL") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12dp)
            )

            Spacer(modifier = Modifier.height(16dp))

            // Custom Headers List
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Headers", fontWeight = FontWeight.Bold)
                IconButton(onClick = { headersList.add(Pair("", "")) }) {
                    Icon(Icons.Default.Add, contentDescription = "Add header", tint = MaterialTheme.colorScheme.primary)
                }
            }

            headersList.forEachIndexed { index, pair ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4dp),
                    horizontalArrangement = Arrangement.spacedBy(8dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    var keyState by remember { mutableStateOf(pair.first) }
                    var valState by remember { mutableStateOf(pair.second) }

                    OutlinedTextField(
                        value = keyState,
                        onValueChange = {
                            keyState = it
                            headersList[index] = Pair(it, valState)
                        },
                        placeholder = { Text("Key") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8dp),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = valState,
                        onValueChange = {
                            valState = it
                            headersList[index] = Pair(keyState, it)
                        },
                        placeholder = { Text("Value") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8dp),
                        singleLine = true
                    )

                    IconButton(onClick = { headersList.removeAt(index) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16dp))

            // Authorization Settings
            Text("Authorization", fontWeight = FontWeight.Bold)
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8dp),
                horizontalArrangement = Arrangement.spacedBy(8dp)
            ) {
                authTypes.forEach { type ->
                    FilterChip(
                        selected = authType == type,
                        onClick = { authType = type },
                        label = { Text(type) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            if (authType != "None") {
                OutlinedTextField(
                    value = authValue,
                    onValueChange = { authValue = it },
                    label = { Text(if (authType == "Bearer Token") "Token" else "Username:Password / Encoded Base64") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12dp),
                    shape = RoundedCornerShape(8dp)
                )
            }

            // Body payload for POST/PUT
            if (method == "POST" || method == "PUT") {
                Spacer(modifier = Modifier.height(12dp))
                Text("Body Payload", fontWeight = FontWeight.Bold)
                OutlinedTextField(
                    value = body,
                    onValueChange = { body = it },
                    placeholder = { Text("JSON body, Form Data, etc.") },
                    modifier = Modifier.fillMaxWidth().height(120dp).padding(vertical = 8dp),
                    shape = RoundedCornerShape(12dp)
                )
            }

            Spacer(modifier = Modifier.height(20dp))

            // CodeViewer result
            CodeViewer(code = outputCurl, title = "Generated Curl Command")
        }
    }
}
