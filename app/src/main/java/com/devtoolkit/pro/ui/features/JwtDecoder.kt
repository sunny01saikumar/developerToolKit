package com.devtoolkit.pro.ui.features

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CompassCalibration
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.devtoolkit.pro.domain.model.JwtResult
import com.devtoolkit.pro.ui.components.CodeViewer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.longOrNull
import java.text.SimpleDateFormat
import java.util.*

object JwtDecoderUtil {
    fun decode(token: String): Result<JwtResult> {
        val parts = token.trim().split(".")
        if (parts.size != 3) {
            return Result.failure(IllegalArgumentException("Invalid JWT structure: Token must contain exactly 3 segments separated by dots."))
        }

        return try {
            val header = String(android.util.Base64.decode(parts[0], android.util.Base64.URL_SAFE or android.util.Base64.NO_PADDING or android.util.Base64.NO_WRAP))
            val payload = String(android.util.Base64.decode(parts[1], android.util.Base64.URL_SAFE or android.util.Base64.NO_PADDING or android.util.Base64.NO_WRAP))

            val prettyHeader = JsonFormatterUtil.prettyPrint(header).getOrDefault(header)
            val prettyPayload = JsonFormatterUtil.prettyPrint(payload).getOrDefault(payload)

            val payloadObj = Json.parseToJsonElement(payload).jsonObject
            val exp = payloadObj["exp"]?.jsonPrimitive?.longOrNull

            var isExpired = false
            var expiryDate = "No Expiration Date (exp) found"

            if (exp != null) {
                val expMillis = exp * 1000L
                val current = System.currentTimeMillis()
                isExpired = current > expMillis
                val diff = expMillis - current
                
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                expiryDate = sdf.format(Date(expMillis))
                if (isExpired) {
                    expiryDate += " (Expired)"
                } else {
                    val days = diff / (1000 * 60 * 60 * 24)
                    val hours = (diff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
                    val minutes = (diff % (1000 * 60 * 60)) / (1000 * 60)
                    expiryDate += " (Expires in ${days}d ${hours}h ${minutes}m)"
                }
            }

            val headerObj = Json.parseToJsonElement(header).jsonObject
            val alg = headerObj["alg"]?.jsonPrimitive?.content ?: "NONE"

            Result.success(JwtResult(prettyHeader, prettyPayload, isExpired, expiryDate, alg))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JwtDecoderScreen(onBackClick: () -> Unit) {
    var input by remember { mutableStateOf("") }
    var result by remember { mutableStateOf<JwtResult?>(null) }
    var error by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("JWT Decoder", fontWeight = FontWeight.Bold) },
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
                label = { Text("Enter JWT Token") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    val decodeResult = JwtDecoderUtil.decode(input)
                    if (decodeResult.isSuccess) {
                        result = decodeResult.getOrNull()
                        error = ""
                    } else {
                        error = decodeResult.exceptionOrNull()?.localizedMessage ?: "Decode failed"
                        result = null
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Decode Token")
            }

            if (error.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                ) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Warning, contentDescription = null, tint = MaterialTheme.colorScheme.error)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(text = error, color = MaterialTheme.colorScheme.onErrorContainer)
                    }
                }
            }

            result?.let { jwt ->
                Spacer(modifier = Modifier.height(24.dp))

                // Metadata Section
                Text(
                    text = "Token Metadata",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Card(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Algorithm:", fontWeight = FontWeight.Bold)
                            Text(jwt.signatureAlgorithm, color = MaterialTheme.colorScheme.secondary)
                        }
                        Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Expiration Status:", fontWeight = FontWeight.Bold)
                            Text(
                                text = jwt.expiryDate,
                                color = if (jwt.isExpired) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                // Header Decoded
                Text(
                    text = "Header (Algorithm & Type)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                CodeViewer(code = jwt.headerJson, title = "Header")

                Spacer(modifier = Modifier.height(16.dp))

                // Payload Decoded
                Text(
                    text = "Payload (Claims / Data)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                CodeViewer(code = jwt.payloadJson, title = "Payload")

                Spacer(modifier = Modifier.height(16.dp))

                // Signature Note (Security & Offline explanation)
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Offline Signature Notice",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "This application operates 100% offline. Cryptographic signature verification is not performed. Ensure secret keys are handled only in secure backend systems.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }
    }
}
