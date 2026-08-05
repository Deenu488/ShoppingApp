package dn.uilabwith.ai

import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

val AppTypography = Typography()

val primaryLight = Color(0xFF546163)
val onPrimaryLight = Color(0xFFFFFFFF)
val primaryContainerLight = Color(0xFFEEFCFF)
val onPrimaryContainerLight = Color(0xFF677577)
val secondaryLight = Color(0xFF5A5F60)
val onSecondaryLight = Color(0xFFFFFFFF)
val secondaryContainerLight = Color(0xFFDCE0E1)
val onSecondaryContainerLight = Color(0xFF5F6364)
val tertiaryLight = Color(0xFF605D63)
val onTertiaryLight = Color(0xFFFFFFFF)
val tertiaryContainerLight = Color(0xFFFDF8FF)
val onTertiaryContainerLight = Color(0xFF747177)
val errorLight = Color(0xFFBA1A1A)
val onErrorLight = Color(0xFFFFFFFF)
val errorContainerLight = Color(0xFFFFDAD6)
val onErrorContainerLight = Color(0xFF93000A)
val backgroundLight = Color(0xFFF9F9FF)
val onBackgroundLight = Color(0xFF1B1C1C)
val surfaceLight = Color(0xFFF9F9FF)
val onSurfaceLight = Color(0xFF1B1C1C)
val surfaceVariantLight = Color(0xFFDFE3E4)
val onSurfaceVariantLight = Color(0xFF434849)
val outlineLight = Color(0xFF737879)
val outlineVariantLight = Color(0xFFC3C7C8)
val scrimLight = Color(0xFF000000)
val inverseSurfaceLight = Color(0xFF303030)
val inverseOnSurfaceLight = Color(0xFFF3F0F0)
val inversePrimaryLight = Color(0xFFBBC9CC)
val surfaceDimLight = Color(0xFFDCD9D9)
val surfaceBrightLight = Color(0xFFF9F9FF)
val surfaceContainerLowestLight = Color(0xFFFFFFFF)
val surfaceContainerLowLight = Color(0xFFF5F3F3)
val surfaceContainerLight = Color(0xFFF0EDED)
val surfaceContainerHighLight = Color(0xFFEAE8E7)
val surfaceContainerHighestLight = Color(0xFFE4E2E2)

val primaryDark = Color(0xFFFFFFFF)
val onPrimaryDark = Color(0xFF263235)
val primaryContainerDark = Color(0xFFD7E5E8)
val onPrimaryContainerDark = Color(0xFF596769)
val secondaryDark = Color(0xFFC3C7C8)
val onSecondaryDark = Color(0xFF2C3132)
val secondaryContainerDark = Color(0xFF434849)
val onSecondaryContainerDark = Color(0xFFB1B6B7)
val tertiaryDark = Color(0xFFFFFFFF)
val onTertiaryDark = Color(0xFF313035)
val tertiaryContainerDark = Color(0xFFE5E1E8)
val onTertiaryContainerDark = Color(0xFF666369)
val errorDark = Color(0xFFFFB4AB)
val onErrorDark = Color(0xFF690005)
val errorContainerDark = Color(0xFF93000A)
val onErrorContainerDark = Color(0xFFFFDAD6)
val backgroundDark = Color(0xFF11131A)
val onBackgroundDark = Color(0xFFE4E2E2)
val surfaceDark = Color(0xFF11131A)
val onSurfaceDark = Color(0xFFE4E2E2)
val surfaceVariantDark = Color(0xFF434849)
val onSurfaceVariantDark = Color(0xFFC3C7C8)
val outlineDark = Color(0xFF8D9293)
val outlineVariantDark = Color(0xFF434849)
val scrimDark = Color(0xFF000000)
val inverseSurfaceDark = Color(0xFFE4E2E2)
val inverseOnSurfaceDark = Color(0xFF303030)
val inversePrimaryDark = Color(0xFF546163)
val surfaceDimDark = Color(0xFF11131A)
val surfaceBrightDark = Color(0xFF393939)
val surfaceContainerLowestDark = Color(0xFF0E0E0E)
val surfaceContainerLowDark = Color(0xFF1B1C1C)
val surfaceContainerDark = Color(0xFF1F2020)
val surfaceContainerHighDark = Color(0xFF2A2A2A)
val surfaceContainerHighestDark = Color(0xFF353535)

private val lightScheme =
    lightColorScheme(
        primary = primaryLight,
        onPrimary = onPrimaryLight,
        primaryContainer = primaryContainerLight,
        onPrimaryContainer = onPrimaryContainerLight,
        secondary = secondaryLight,
        onSecondary = onSecondaryLight,
        secondaryContainer = secondaryContainerLight,
        onSecondaryContainer = onSecondaryContainerLight,
        tertiary = tertiaryLight,
        onTertiary = onTertiaryLight,
        tertiaryContainer = tertiaryContainerLight,
        onTertiaryContainer = onTertiaryContainerLight,
        error = errorLight,
        onError = onErrorLight,
        errorContainer = errorContainerLight,
        onErrorContainer = onErrorContainerLight,
        background = backgroundLight,
        onBackground = onBackgroundLight,
        surface = surfaceLight,
        onSurface = onSurfaceLight,
        surfaceVariant = surfaceVariantLight,
        onSurfaceVariant = onSurfaceVariantLight,
        outline = outlineLight,
        outlineVariant = outlineVariantLight,
        scrim = scrimLight,
        inverseSurface = inverseSurfaceLight,
        inverseOnSurface = inverseOnSurfaceLight,
        inversePrimary = inversePrimaryLight,
        surfaceDim = surfaceDimLight,
        surfaceBright = surfaceBrightLight,
        surfaceContainerLowest = surfaceContainerLowestLight,
        surfaceContainerLow = surfaceContainerLowLight,
        surfaceContainer = surfaceContainerLight,
        surfaceContainerHigh = surfaceContainerHighLight,
        surfaceContainerHighest = surfaceContainerHighestLight,
    )

private val darkScheme =
    darkColorScheme(
        primary = primaryDark,
        onPrimary = onPrimaryDark,
        primaryContainer = primaryContainerDark,
        onPrimaryContainer = onPrimaryContainerDark,
        secondary = secondaryDark,
        onSecondary = onSecondaryDark,
        secondaryContainer = secondaryContainerDark,
        onSecondaryContainer = onSecondaryContainerDark,
        tertiary = tertiaryDark,
        onTertiary = onTertiaryDark,
        tertiaryContainer = tertiaryContainerDark,
        onTertiaryContainer = onTertiaryContainerDark,
        error = errorDark,
        onError = onErrorDark,
        errorContainer = errorContainerDark,
        onErrorContainer = onErrorContainerDark,
        background = backgroundDark,
        onBackground = onBackgroundDark,
        surface = surfaceDark,
        onSurface = onSurfaceDark,
        surfaceVariant = surfaceVariantDark,
        onSurfaceVariant = onSurfaceVariantDark,
        outline = outlineDark,
        outlineVariant = outlineVariantDark,
        scrim = scrimDark,
        inverseSurface = inverseSurfaceDark,
        inverseOnSurface = inverseOnSurfaceDark,
        inversePrimary = inversePrimaryDark,
        surfaceDim = surfaceDimDark,
        surfaceBright = surfaceBrightDark,
        surfaceContainerLowest = surfaceContainerLowestDark,
        surfaceContainerLow = surfaceContainerLowDark,
        surfaceContainer = surfaceContainerDark,
        surfaceContainerHigh = surfaceContainerHighDark,
        surfaceContainerHighest = surfaceContainerHighestDark,
    )

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Set to false to maintain crisp custom brand styling
    content:
        @Composable()
        () -> Unit,
) {
    val colorScheme =
        when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }

            darkTheme -> {
                darkScheme
            }

            else -> {
                lightScheme
            }
        }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content,
    )
}

class MainActivityFull : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                var showSplash by remember { mutableStateOf(true) }

                Surface(modifier = Modifier.fillMaxSize()) {
                    if (showSplash) {
                        SplashScreenFull(
                            onNavigateToHome = {
                                showSplash = false
                            },
                        )
                    } else {
                        HomeScreenFull()
                    }
                }
            }
        }
    }
}

@Composable
fun SplashScreenFull(onNavigateToHome: () -> Unit) {
    LaunchedEffect(key1 = true) {
        delay(2000L)
        onNavigateToHome()
    }

    Box(
        modifier =
            Modifier
                .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher),
            contentDescription = "App Logo",
            modifier = Modifier.size(180.dp),
        )
    }
}

@Preview
@Composable
fun Main() {
    AppTheme {
        var showSplash by remember { mutableStateOf(true) }

        Surface(modifier = Modifier.fillMaxSize()) {
            if (showSplash) {
                SplashScreenFull(
                    onNavigateToHome = {
                        showSplash = false
                    },
                )
            } else {
                HomeScreenFull()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenFull() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Home Title")
                },
            )
        },
    ) { innerPadding ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = "Welcome Home!")
        }
    }
}
