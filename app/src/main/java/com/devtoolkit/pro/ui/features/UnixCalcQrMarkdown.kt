package com.devtoolkit.pro.ui.features

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.devtoolkit.pro.ui.components.CodeViewer
import com.devtoolkit.pro.ui.components.CustomMarkdown
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import java.text.SimpleDateFormat
import java.util.*

// --- ZXing QR Generator ---
object QrCodeGenerator {
    fun generate(text: String, size: Int = 512): Bitmap? {
        if (text.isEmpty()) return null
        return try {
            val bitMatrix: BitMatrix = MultiFormatWriter().encode(
                text,
                BarcodeFormat.QR_CODE,
                size,
                size
            )
            val width = bitMatrix.width
            val height = bitMatrix.height
            val pixels = IntArray(width * height)
            for (y in 0 until height) {
                val offset = y * width
                for (x in 0 until width) {
                    pixels[offset + x] = if (bitMatrix.get(x, y)) {
                        android.graphics.Color.BLACK
                    } else {
                        android.graphics.Color.WHITE
                    }
                }
            }
            Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888).apply {
                setPixels(pixels, 0, width, 0, 0, width, height)
            }
        } catch (e: Exception) {
            null
        }
    }
}

// --- Unix Timestamp Screen ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnixTimestampScreen(onBackClick: () -> Unit) {
    val context = LocalContext.current
    var currentEpoch by remember { mutableLongStateOf(System.currentTimeMillis() / 1000L) }
    
    // Convert states
    var tsInput by remember { mutableStateOf("") }
    var tsOutputDate by remember { mutableStateOf("") }

    var dateInput by remember { mutableStateOf("2026-08-01 12:00:00") }
    var dateOutputTs by remember { mutableStateOf("") }

    // Update live clock
    LaunchedEffect(Unit) {
        while (true) {
            currentEpoch = System.currentTimeMillis() / 1000L
            kotlinx.coroutines.delay(1000)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Unix Timestamp Converter", fontWeight = FontWeight.Bold) },
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
            // Live clock Card
            Card(modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)) {
                Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Current Epoch Timestamp", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = currentEpoch.toString(),
                        style = MaterialTheme.typography.displayLarge,
                        fontFamily = FontFamily.Monospace,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(onClick = {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        clipboard.setPrimaryClip(ClipData.newPlainText("epoch", currentEpoch.toString()))
                        Toast.makeText(context, "Copied timestamp", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(Icons.Default.ContentCopy, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Copy Current Timestamp")
                    }
                }
            }

            // Timestamp -> Date
            Text("Epoch -> Date", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = tsInput,
                        onValueChange = { tsInput = it },
                        label = { Text("Epoch Timestamp (seconds)") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(onClick = {
                        tsOutputDate = try {
                            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss UTC", Locale.getDefault()).apply {
                                timeZone = TimeZone.getTimeZone("UTC")
                            }
                            sdf.format(Date(tsInput.toLong() * 1000L))
                        } catch (e: Exception) {
                            "Invalid Timestamp"
                        }
                    }, modifier = Modifier.fillMaxWidth()) {
                        Text("Convert to Date")
                    }
                    if (tsOutputDate.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        CodeViewer(code = tsOutputDate, title = "UTC Datetime")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Date -> Timestamp
            Text("Date -> Epoch", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = dateInput,
                        onValueChange = { dateInput = it },
                        label = { Text("Date String (yyyy-MM-dd HH:mm:ss)") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(onClick = {
                        dateOutputTs = try {
                            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                            val date = sdf.parse(dateInput)
                            (date.time / 1000L).toString()
                        } catch (e: Exception) {
                            "Invalid Date Format (Use yyyy-MM-dd HH:mm:ss)"
                        }
                    }, modifier = Modifier.fillMaxWidth()) {
                        Text("Convert to Timestamp")
                    }
                    if (dateOutputTs.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        CodeViewer(code = dateOutputTs, title = "Unix Timestamp")
                    }
                }
            }
        }
    }
}

// --- Developer Calculator Screen ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DevCalculatorScreen(onBackClick: () -> Unit) {
    var inputVal by remember { mutableStateOf("255") }
    var selectedBase by remember { mutableStateOf("Dec") }

    val bases = listOf("Hex", "Dec", "Oct", "Bin")

    // Outputs
    var outHex by remember { mutableStateOf("") }
    var outDec by remember { mutableStateOf("") }
    var outOct by remember { mutableStateOf("") }
    var outBin by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf("") }

    LaunchedEffect(inputVal, selectedBase) {
        if (inputVal.isBlank()) {
            outHex = ""; outDec = ""; outOct = ""; outBin = ""; errorMsg = ""
            return@LaunchedEffect
        }
        try {
            val baseRadix = when (selectedBase) {
                "Hex" -> 16
                "Oct" -> 8
                "Bin" -> 2
                else -> 10
            }
            val parsedVal = inputVal.trim().toLong(baseRadix)
            outHex = parsedVal.toString(16).uppercase()
            outDec = parsedVal.toString(10)
            outOct = parsedVal.toString(8)
            outBin = parsedVal.toString(2)
            errorMsg = ""
        } catch (e: Exception) {
            errorMsg = "Format parsing error for base $selectedBase"
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Developer Calculator", fontWeight = FontWeight.Bold) },
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
            // Base input selection
            Text("Input Base", fontWeight = FontWeight.Bold)
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                bases.forEach { b ->
                    FilterChip(
                        selected = selectedBase == b,
                        onClick = { selectedBase = b },
                        label = { Text(b) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = inputVal,
                onValueChange = { inputVal = it },
                label = { Text("Enter value") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            if (errorMsg.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)) {
                    Text(errorMsg, modifier = Modifier.padding(12.dp), color = MaterialTheme.colorScheme.onErrorContainer)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Conversion list results
            Text("Conversion Outputs", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(8.dp))

            val context = LocalContext.current
            listOf(
                "HEX" to outHex,
                "DEC" to outDec,
                "OCT" to outOct,
                "BIN" to outBin
            ).forEach { (baseName, valStr) ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
                    Row(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(baseName, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                            Text(
                                text = valStr.ifEmpty { "..." },
                                fontFamily = FontFamily.Monospace,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        IconButton(onClick = {
                            if (valStr.isNotEmpty()) {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                clipboard.setPrimaryClip(ClipData.newPlainText("calc", valStr))
                                Toast.makeText(context, "Copied $baseName output", Toast.LENGTH_SHORT).show()
                            }
                        }) {
                            Icon(Icons.Default.ContentCopy, contentDescription = "Copy")
                        }
                    }
                }
            }
        }
    }
}

// --- Markdown Preview Screen ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarkdownPreviewScreen(onBackClick: () -> Unit) {
    var input by remember { mutableStateOf("# DevToolkit Pro\n\n- **Offline** capabilities.\n- Build dynamic apps.\n\n```kotlin\nval list = listOf(\"Compose\", \"Hilt\")\n```") }
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Markdown Editor & Preview", fontWeight = FontWeight.Bold) },
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
            TabRow(selectedTabIndex = selectedTab) {
                Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }, text = { Text("Edit") })
                Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }, text = { Text("Preview") })
            }

            if (selectedTab == 0) {
                OutlinedTextField(
                    value = input,
                    onValueChange = { input = it },
                    placeholder = { Text("Enter Markdown text...") },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    shape = RoundedCornerShape(12.dp)
                )
            } else {
                CustomMarkdown(
                    markdownText = input,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
        }
    }
}

// --- QR Tool Screen (Generators + CameraX Scanner) ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrToolScreen(onBackClick: () -> Unit) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("QR Code Tool", fontWeight = FontWeight.Bold) },
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
            TabRow(selectedTabIndex = selectedTab) {
                Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }, text = { Text("Generate") })
                Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }, text = { Text("Scan Scanner") })
            }

            if (selectedTab == 0) {
                QrGeneratorTab()
            } else {
                QrScannerTab()
            }
        }
    }
}

@Composable
fun QrGeneratorTab() {
    var qrInput by remember { mutableStateOf("https://github.com") }
    var qrType by remember { mutableStateOf("URL") }
    var qrBitmap by remember { mutableStateOf<Bitmap?>(null) }

    val types = listOf("URL", "Text", "WiFi", "Email", "Phone")

    LaunchedEffect(qrInput) {
        qrBitmap = QrCodeGenerator.generate(qrInput)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScrollableTabRow(
            selectedTabIndex = types.indexOf(qrType).coerceAtLeast(0),
            edgePadding = 0.dp,
            divider = {},
            containerColor = Color.Transparent,
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
        ) {
            types.forEach { t ->
                Tab(selected = qrType == t, onClick = { qrType = t }, text = { Text(t) })
            }
        }

        OutlinedTextField(
            value = qrInput,
            onValueChange = { qrInput = it },
            label = { Text("Enter QR Value") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        qrBitmap?.let { bmp ->
            Card(
                modifier = Modifier
                    .size(240.dp)
                    .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Image(
                        bitmap = bmp.asImageBitmap(),
                        contentDescription = "QR Code Output",
                        modifier = Modifier.size(210.dp)
                    )
                }
            }
        } ?: Box(
            modifier = Modifier.size(240.dp).clip(RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Text("Enter input to generate")
        }
    }
}

@Composable
fun QrScannerTab() {
    val context = LocalContext.current
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted -> hasCameraPermission = granted }
    )

    LaunchedEffect(hasCameraPermission) {
        if (!hasCameraPermission) {
            launcher.launch(Manifest.permission.CAMERA)
        }
    }

    if (hasCameraPermission) {
        CameraScannerView()
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.Camera, contentDescription = null, modifier = Modifier.size(48.dp))
                Spacer(modifier = Modifier.height(12.dp))
                Text("Camera permission is required to scan QR codes.")
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { launcher.launch(Manifest.permission.CAMERA) }) {
                    Text("Grant Permission")
                }
            }
        }
    }
}

@OptIn(ExperimentalGetImage::class)
@Composable
fun CameraScannerView() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var scanResult by remember { mutableStateOf("") }
    
    val previewView = remember { PreviewView(context) }

    LaunchedEffect(Unit) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(context)) { imageProxy ->
                val mediaImage = imageProxy.image
                if (mediaImage != null) {
                    val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
                    val scanner = BarcodeScanning.getClient()
                    scanner.process(image)
                        .addOnSuccessListener { barcodes ->
                            for (barcode in barcodes) {
                                val rawValue = barcode.rawValue
                                if (rawValue != null) {
                                    scanResult = rawValue
                                    break
                                }
                            }
                        }
                        .addOnCompleteListener {
                            imageProxy.close()
                        }
                } else {
                    imageProxy.close()
                }
            }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageAnalysis)
            } catch (exc: Exception) {
                // Fail silently
            }
        }, ContextCompat.getMainExecutor(context))
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxWidth().weight(1.5f)) {
            AndroidView(
                factory = { previewView },
                modifier = Modifier.fillMaxSize()
            )
            
            // Scanner overlay guidelines
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(4.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (scanResult.isNotEmpty()) {
                Text("Scan Result:", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(8.dp))
                CodeViewer(code = scanResult, title = "Scanned Text")
            } else {
                Text("Align QR Code inside camera view to scan...", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
