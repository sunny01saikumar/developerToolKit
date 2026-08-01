# DevToolkit Pro - Everything a Developer Needs

DevToolkit Pro is a completely offline, production-ready developer utility suite built for Android. Built using Jetpack Compose, Material 3, and Clean Architecture, it bundles 20+ offline developer utilities without requiring any remote server connection, cloud database, or account creation. User data never leaves the device.

---

## Architecture Diagram

The app follows **Clean Architecture** layered design combined with **MVVM**:

```
 ┌─────────────────────────────────────────────────────────┐
 │                        UI Layer                         │
 │  Compose UI (Screens) ──► ViewModels (StateFlows)       │
 └────────────────────────────┬────────────────────────────┘
                              │
 ┌────────────────────────────▼────────────────────────────┐
 │                      Domain Layer                       │
 │  Use Cases ──► Domain Models ──► Repository Interface   │
 └────────────────────────────┬────────────────────────────┘
                              │
 ┌────────────────────────────▼────────────────────────────┐
 │                       Data Layer                        │
 │  RepositoryImpl ──► DataStore Preferences (Local)       │
 │                 ──► Static Resource Providers (Offline) │
 └─────────────────────────────────────────────────────────┘
```

---

## Project Folder Structure

```
C:\Users\saiku\Desktop\developerToolKit\
├── gradle/
│   └── libs.versions.toml             # Centralized Version Catalog
├── app/
│   ├── build.gradle.kts               # Module level dependencies configuration
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml    # Permissions, Launcher, AdMob ID configuration
│           ├── java/com/devtoolkit/pro/
│           │   ├── DevToolkitApp.kt   # App Entry (Hilt and Ads Initialization)
│           │   ├── MainActivity.kt    # Navigation Host & Interstitial Ads handler
│           │   ├── data/
│           │   │   ├── local/
│           │   │   │   └── LocalStorage.kt     # Preferences DataStore serialization
│           │   │   └── repository/
│           │   │       └── DevToolkitRepositoryImpl.kt # Repository implementation
│           │   ├── domain/
│           │   │   ├── model/
│           │   │   │   └── DomainModels.kt     # Core data classes
│           │   │   └── repository/
│           │   │       └── DevToolkitRepository.kt # Repository interface
│           │   └── ui/
│           │       ├── components/
│           │       │   ├── AdComponents.kt     # AdMob integrations
│           │       │   └── CommonUi.kt         # Custom markdown, code viewer, cards
│           │       ├── features/
│           │       │   ├── ColorTools.kt
│           │       │   ├── CommandsScreen.kt
│           │       │   ├── EncodersAndHashes.kt
│           │       │   ├── Generators.kt
│           │       │   ├── HttpToolsScreen.kt
│           │       │   ├── JsonSqlFormatter.kt
│           │       │   ├── JwtDecoder.kt
│           │       │   ├── NotesScreen.kt
│           │       │   ├── RegexTester.kt
│           │       │   └── UnixCalcQrMarkdown.kt
│           │       ├── home/
│           │       │   ├── HomeScreen.kt
│           │       │   └── HomeViewModel.kt
│           │       ├── settings/
│           │       │   ├── SettingsScreen.kt
│           │       │   └── SettingsViewModel.kt
│           │       └── theme/
│           │           ├── Color.kt
│           │           ├── Theme.kt
│           │           └── Type.kt
│           └── res/                   # Layout, values, XMLs, and Launcher icons
├── build.gradle.kts                   # Project level Gradle script
├── settings.gradle.kts                # Repositories & module inclusion definition
└── gradle.properties                  # Memory and compiler options configuration
```

---

## Play Store Listing Details

### Short Description
> The ultimate offline utility kit for developers, formatters, generators & references.

### Long Description
```
DevToolkit Pro is the ultimate, completely offline workspace for developers, engineers, and designers. Speed up your workflow with 20+ utility modules built to run on-device. No accounts, no internet requirements, and no cloud databases—your data is safe and kept locally.

Core Features:
1. Formatters: Validate and pretty-print JSON or SQL queries with single-click minify.
2. JWT Decoder: Extract header and payload properties locally, check expiry dates, and review signature algorithms.
3. Cryptographic Hashes: Instantly hash strings with MD5, SHA-1, SHA-256, and SHA-512 algorithms.
4. Encoders/Decoders: Convert text to and from Base64 or URL component encodings safely.
5. Generators: Produce Version-4 UUIDs in batches or generate cryptographically secure random passwords (custom options for numbers, symbols, and casing).
6. Regex Tester: Validate regular expressions with matching occurrences and visual highlights.
7. Color Tools: Convert HEX/RGB/ARGB values, pick colors, and generate CSS/Compose gradient brushes.
8. HTTP Hub: Browse HTTP Status Codes, HTTP Headers, and construct CURL commands visually.
9. Unix Timestamp: Convert date-times to Epoch seconds and vice-versa.
10. Calculator: Shift numeric bases seamlessly between Hex, Dec, Oct, and Bin.
11. Markdown & QR: Write Markdown syntax with a live native preview, generate QR Codes (URL, WiFi, Text), or scan barcodes via the built-in CameraX scanner.
12. Offline Notes: Store scratch snippets and ideas in a secure local database.

Engineered with Jetpack Compose, Hilt Dependency Injection, and modern Material Design 3 guidelines supporting light, dark, and dynamic wallpaper schemes.
```

---

## Monetization Strategy

DevToolkit Pro utilizes a developer-friendly, non-intrusive monetization layout:
1. **AdMob Banner Ads**: Placed at the bottom of the screens to maintain continuous utility focus.
2. **AdMob Interstitial Ads**: Configured to display only when transition from the main tools dashboard into individual utility categories, minimizing flow disruption.
3. **Offline Native Placeholders**: Themed cards promoting custom offline premium offerings when network services are inactive.
4. **Premium Upgrade**: Ready path in code to disable advertisements via in-app purchases.

---

## Color Palette (Material 3)

The design adopts a vibrant cyber-gradient system combined with standard M3 light/dark tokens:
- **Primary**: Deep Indigo (`#6366F1`)
- **Secondary**: Neon Cyan/Teal (`#00F2FE`)
- **Tertiary**: Rose Pink (`#EC4899`)
- **Dark Surface**: Charcoal Black (`#0C0E14` / `#141724`)
- **Light Surface**: Slate White (`#F8FAFC` / `#FFFFFF`)

---

## Typography

Typographic scale maps:
- `displayLarge` (32sp, Bold, SansSerif) - Big numeric readouts (Calculator & Unix clocks).
- `titleLarge` / `titleMedium` (20sp/16sp, SemiBold) - Category headings and card headers.
- `bodyLarge` / `bodyMedium` (16sp/14sp) - Labels and lists.
- `fontFamily = FontFamily.Monospace` - Code viewers, formatted outputs, and command lines.

---

## Setup & Execution

### Prerequisites
- Android Studio Koala / Ladybug or newer
- JDK 17+
- Android SDK 34 (API 34)

### Building
Open the project root directory in Android Studio. Wait for the Gradle sync to finish.
To build via terminal:
```powershell
# Build debug APK
./gradlew assembleDebug
```
