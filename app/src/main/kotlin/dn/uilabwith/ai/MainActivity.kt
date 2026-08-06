package com.example

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.ui.theme.AppTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                var showSplash by rememberSaveable { mutableStateOf(true) }

                Surface(modifier = Modifier.fillMaxSize()) {
                    if (showSplash) {
                        SplashScreen(
                            onNavigateToHome = {
                                showSplash = false
                            },
                        )
                    } else {
                        HomeScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun SplashScreen(onNavigateToHome: () -> Unit) {
    LaunchedEffect(key1 = true) {
        delay(2000L)
        onNavigateToHome()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon),
            contentDescription = "App Logo",
            modifier = Modifier.size(180.dp),
        )
    }
}

data class BottomNavItem(
    val title: String,
    @DrawableRes val iconRes: Int,
)

@Composable
fun FloatingNavigationBar(
    items: List<BottomNavItem>,
    selectedItemIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
        shape = RoundedCornerShape(32.dp),
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
        tonalElevation = 8.dp,
        shadowElevation = 12.dp,
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            items.forEachIndexed { index, item ->
                val isSelected = selectedItemIndex == index

                NavigationBarItem(
                    selected = isSelected,
                    onClick = { onItemSelected(index) },
                    icon = {
                        Icon(
                            painter = painterResource(id = item.iconRes),
                            contentDescription = item.title,
                        )
                    },
                    label = {
                        Text(
                            text = item.title,
                        )
                    },
                    alwaysShowLabel = false,
                    colors =
                        NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            selectedTextColor = MaterialTheme.colorScheme.onSurface,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        ),
                    modifier = Modifier.clip(RoundedCornerShape(24.dp)),
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "ShoppingApp")
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
            var selectedIndex by remember { mutableIntStateOf(0) }

            val navItems =
                listOf(
                    BottomNavItem("Home", R.drawable.ic_home),
                )

            Scaffold(
                bottomBar = {
                    FloatingNavigationBar(
                        items = navItems,
                        selectedItemIndex = selectedIndex,
                        onItemSelected = { index ->
                            selectedIndex = index
                        },
                    )
                },
            ) { innerPadding ->
                Box(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(innerPadding),
                ) {
                    when (selectedIndex) {
                        0 -> Home()
                    }
                }
            }
        }
    }
}

data class ShoppingItem(
    val name: String,
    val mrp: Int,
    val sp: Int,
    @DrawableRes val imageRes: Int,
    val id: String? = null,
)

@Composable
fun ShoppingGridScreen(
    items: List<ShoppingItem>,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 160.dp),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(
            items = items,
        ) { item ->
            ShoppingItemCard(
                item = item,
            )
        }
    }
}

@Composable
fun ShoppingItemCard(
    item: ShoppingItem,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors =
            CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
        elevation =
            CardDefaults.elevatedCardElevation(
                defaultElevation = 2.dp,
                pressedElevation = 6.dp,
            ),
    ) {
        Column {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .background(MaterialTheme.colorScheme.surfaceContainerHighest),
            ) {
                Image(
                    painter = painterResource(id = item.imageRes),
                    contentDescription = item.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
            ) {
                Text(
                    text = item.name,
                )
            }
        }
    }
}

@Composable
fun Home() {
    val sampleItems =
        listOf(
            ShoppingItem("Wireless Noise-Canceling Headphones", 4999, 2999, android.R.drawable.ic_menu_gallery),
            ShoppingItem("Smart Fitness Watch Series 5", 3499, 1899, android.R.drawable.ic_menu_gallery),
            ShoppingItem("Ergonomic Mechanical Keyboard", 2999, 2199, android.R.drawable.ic_menu_gallery),
            ShoppingItem("Fast Charging Power Bank 20000mAh", 1999, 999, android.R.drawable.ic_menu_gallery),
            ShoppingItem("Bluetooth Portable Speaker", 2499, 1499, android.R.drawable.ic_menu_gallery),
            ShoppingItem("HD Webcam 1080p with Mic", 1799, 1299, android.R.drawable.ic_menu_gallery),
            ShoppingItem("Wireless Noise-Canceling Headphones", 4999, 2999, android.R.drawable.ic_menu_gallery),
            ShoppingItem("Smart Fitness Watch Series 5", 3499, 1899, android.R.drawable.ic_menu_gallery),
            ShoppingItem("Ergonomic Mechanical Keyboard", 2999, 2199, android.R.drawable.ic_menu_gallery),
            ShoppingItem("Fast Charging Power Bank 20000mAh", 1999, 999, android.R.drawable.ic_menu_gallery),
            ShoppingItem("Bluetooth Portable Speaker", 2499, 1499, android.R.drawable.ic_menu_gallery),
            ShoppingItem("HD Webcam 1080p with Mic", 1799, 1299, android.R.drawable.ic_menu_gallery),
        )

    Scaffold { innerPadding ->
        ShoppingGridScreen(
            items = sampleItems,
            modifier = Modifier.padding(innerPadding),
        )
    }
}
