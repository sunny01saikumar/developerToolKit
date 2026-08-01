package com.devtoolkit.pro

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devtoolkit.pro.domain.repository.DevToolkitRepository
import com.devtoolkit.pro.ui.components.AdManager
import com.devtoolkit.pro.ui.features.*
import com.devtoolkit.pro.ui.home.HomeScreen
import com.devtoolkit.pro.ui.home.HomeViewModel
import com.devtoolkit.pro.ui.settings.SettingsScreen
import com.devtoolkit.pro.ui.settings.SettingsViewModel
import com.devtoolkit.pro.ui.theme.DevToolkitTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var repository: DevToolkitRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Load initial AdMob Ads
        AdManager.loadAd(this)

        setContent {
            val settingsViewModel: SettingsViewModel = hiltViewModel()
            val themeMode by settingsViewModel.themeMode.collectAsState()
            val dynamicColors by settingsViewModel.dynamicColors.collectAsState()

            val isDarkTheme = when (themeMode) {
                "dark" -> true
                "light" -> false
                else -> isSystemInDarkTheme()
            }

            DevToolkitTheme(
                darkTheme = isDarkTheme,
                dynamicColor = dynamicColors
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(repository)
                }
            }
        }
    }
}

@Composable
fun AppNavigation(repository: DevToolkitRepository) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val homeViewModel: HomeViewModel = hiltViewModel()

    val activity = remember(context) { context.findActivity() }

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                viewModel = homeViewModel,
                onToolClick = { route, toolId ->
                    // Trigger Interstitial Ad before navigating to tool category
                    if (activity != null) {
                        AdManager.showInterstitial(activity) {
                            homeViewModel.addToolToHistory(toolId)
                            navController.navigate(route)
                        }
                    } else {
                        homeViewModel.addToolToHistory(toolId)
                        navController.navigate(route)
                    }
                },
                onNavigateToSettings = {
                    navController.navigate("settings")
                },
                onNavigateToNotes = {
                    navController.navigate("notes")
                }
            )
        }

        composable("settings") {
            val settingsViewModel: SettingsViewModel = hiltViewModel()
            SettingsScreen(
                viewModel = settingsViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("notes") {
            val notesViewModel: NotesViewModel = hiltViewModel()
            NotesScreen(
                viewModel = notesViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }

        // --- JSON & SQL Formatters ---
        composable("json_formatter") {
            JsonFormatterScreen(onBackClick = { navController.popBackStack() })
        }
        composable("sql_formatter") {
            SqlFormatterScreen(onBackClick = { navController.popBackStack() })
        }

        // --- JWT Decoder ---
        composable("jwt_decoder") {
            JwtDecoderScreen(onBackClick = { navController.popBackStack() })
        }

        // --- Encoders ---
        composable("base64") {
            Base64Screen(onBackClick = { navController.popBackStack() })
        }
        composable("url_encode") {
            UrlScreen(onBackClick = { navController.popBackStack() })
        }

        // --- Generators ---
        composable("hash_gen") {
            HashGeneratorScreen(onBackClick = { navController.popBackStack() })
        }
        composable("uuid_gen") {
            UuidGeneratorScreen(onBackClick = { navController.popBackStack() })
        }
        composable("password_gen") {
            PasswordGeneratorScreen(onBackClick = { navController.popBackStack() })
        }

        // --- Regex Tester ---
        composable("regex_tester") {
            RegexTesterScreen(onBackClick = { navController.popBackStack() })
        }

        // --- Colors ---
        composable("color_tools") {
            ColorToolsScreen(onBackClick = { navController.popBackStack() })
        }

        // --- HTTP status & headers ---
        composable("http_status") {
            HttpStatusScreen(repository = repository, onBackClick = { navController.popBackStack() })
        }
        composable("http_headers") {
            HttpHeadersScreen(repository = repository, onBackClick = { navController.popBackStack() })
        }
        composable("curl_gen") {
            CurlGeneratorScreen(onBackClick = { navController.popBackStack() })
        }

        // --- Commands sheets (Linux, Git, Docker) ---
        composable("linux_commands") {
            CommandsScreen(type = "linux", repository = repository, onBackClick = { navController.popBackStack() })
        }
        composable("git_commands") {
            CommandsScreen(type = "git", repository = repository, onBackClick = { navController.popBackStack() })
        }
        composable("docker_commands") {
            CommandsScreen(type = "docker", repository = repository, onBackClick = { navController.popBackStack() })
        }

        // --- Unix & Calculator ---
        composable("unix_timestamp") {
            UnixTimestampScreen(onBackClick = { navController.popBackStack() })
        }
        composable("dev_calc") {
            DevCalculatorScreen(onBackClick = { navController.popBackStack() })
        }

        // --- Markdown & QR ---
        composable("markdown_preview") {
            MarkdownPreviewScreen(onBackClick = { navController.popBackStack() })
        }
        composable("qr_tool") {
            QrToolScreen(onBackClick = { navController.popBackStack() })
        }
    }
}

// Context extension helper to resolve Activity references
fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}
