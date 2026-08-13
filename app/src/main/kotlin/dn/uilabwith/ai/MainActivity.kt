package com.example

import android.content.Context
import android.content.res.Configuration
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.ui.theme.AppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

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
    var showAddItemScreen by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableIntStateOf(0) }

    val navItems =
        listOf(
            BottomNavItem("Home", R.drawable.ic_home),
            BottomNavItem("Accounts", R.drawable.ic_accounts),
            BottomNavItem("Settings", R.drawable.ic_settings),
        )

    Scaffold(
        topBar = {
            if (!showAddItemScreen) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(text = "ShoppingApp")
                    },
                )
            }
        },
        bottomBar = {
            if (!showAddItemScreen) {
                FloatingNavigationBar(
                    items = navItems,
                    selectedItemIndex = selectedIndex,
                    onItemSelected = { index ->
                        selectedIndex = index
                    },
                )
            }
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
                0 -> {
                    Home(
                        showAddItemScreen = showAddItemScreen,
                        onOpenAddItem = { showAddItemScreen = true },
                        onCloseAddItem = { showAddItemScreen = false },
                    )
                }

                1 -> {}

                2 -> {
                    Settings()
                }
            }
        }
    }
}

data class ProductDetails(
    val name: String = "",
    val mrp: String = "",
    val sp: String = "",
    val imageRes: String = "",
    val description: String = "",
    val id: String? = null,
)

@Composable
fun ShoppingGridScreen(
    items: List<ProductDetails>,
    onItemClick: (ProductDetails) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 160.dp),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(items = items) { item ->
            ShoppingItemCard(
                item = item,
                onClick = { onItemClick(item) },
            )
        }
    }
}

@Composable
fun ShoppingItemCard(
    item: ProductDetails,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable { onClick() },
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
                val bitmap =
                    remember(item.imageRes) {
                        if (item.imageRes.isNotEmpty()) {
                            BitmapFactory.decodeFile(item.imageRes)?.asImageBitmap()
                        } else {
                            null
                        }
                    }

                if (bitmap != null) {
                    Image(
                        bitmap = bitmap,
                        contentDescription = item.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize(),
                    )
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_bag),
                            contentDescription = item.name,
                            modifier = Modifier.size(48.dp),
                        )
                    }
                }
            }

            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
            ) {
                Text(text = item.name)
            }
        }
    }
}

@Composable
fun Home(
    showAddItemScreen: Boolean,
    onOpenAddItem: () -> Unit,
    onCloseAddItem: () -> Unit,
) {
    val sampleItems =
        listOf(
            ProductDetails(
                name = "Wireless Noise-Canceling Headphones",
                mrp = "4999",
                sp = "2999",
                imageRes = "",
                description = "Noise-canceling over-ear headphones",
            ),
        )

    var isEditMode by remember { mutableStateOf(false) }
    var selectedProduct by remember { mutableStateOf(ProductDetails()) }

    if (showAddItemScreen) {
        AddNewItem(
            product = selectedProduct,
            isEdit = isEditMode,
            onBack = {
                onCloseAddItem()
                isEditMode = false
            },
        )
    } else {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        isEditMode = false
                        selectedProduct = ProductDetails()
                        onOpenAddItem()
                    },
                    modifier = Modifier.padding(end = 12.dp),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_plus),
                        contentDescription = "Create",
                    )
                }
            },
        ) { innerPadding ->
            ShoppingGridScreen(
                items = sampleItems,
                onItemClick = { clickedItem ->
                    selectedProduct = clickedItem
                    isEditMode = true
                    onOpenAddItem()
                },
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}

@Composable
fun Settings() {
    GitHubTokenScreen()
}

@Composable
fun BottomCartBar() {
    Surface(
        color = MaterialTheme.colorScheme.surfaceContainer,
        shadowElevation = 8.dp,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Button(
                onClick = {},
                modifier =
                    Modifier
                        .height(56.dp)
                        .weight(0.16f),
                shape = RoundedCornerShape(16.dp),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                    ),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_upload),
                    contentDescription = "Upload",
                    modifier = Modifier.size(32.dp),
                )
            }

            Spacer(modifier = Modifier.width(32.dp))

            Button(
                onClick = {},
                modifier =
                    Modifier
                        .height(56.dp)
                        .weight(0.56f),
                shape = RoundedCornerShape(16.dp),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                    ),
            ) {
                Text(
                    text = "Save",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AddNewItem(
    product: ProductDetails = ProductDetails(),
    onBack: () -> Unit,
    isEdit: Boolean,
) {
    var name by remember(product) { mutableStateOf(product.name) }
    var description by remember(product) { mutableStateOf(product.description) }
    var mrp by remember(product) { mutableStateOf(product.mrp) }
    var sp by remember(product) { mutableStateOf(product.sp) }
    var imageRes by remember(product) { mutableStateOf(product.imageRes) }

    val context = LocalContext.current
    val isKeyboardOpen = WindowInsets.isImeVisible

    val photoPickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri: Uri? ->
                if (uri != null) {                
                    imageRes = uri.toString()
                }
            },
        )

    var bitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(imageRes) {
        if (imageRes.isNotEmpty()) {
            withContext(Dispatchers.IO) {
                try {
                    val uri = Uri.parse(imageRes)
                     context.contentResolver.openInputStream(uri)?.use { inputStream ->
                        bitmap = BitmapFactory.decodeStream(inputStream)?.asImageBitmap()
                    }
                } catch (e: Exception) {
                    bitmap = BitmapFactory.decodeFile(imageRes)?.asImageBitmap()
                }
            }
        } else {
            bitmap = null
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = if (isEdit) "Edit Item" else "Add New Item")
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back), // Update your drawable
                            contentDescription = "Back",
                            modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                        )
                    }
                },
            )
        },
        bottomBar = {
            if (!isKeyboardOpen) {
                BottomCartBar()           
            }
        },
    ) { paddingValues ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(paddingValues)
                    .imePadding()
                    .verticalScroll(rememberScrollState()),
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .aspectRatio(2f)
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .clickable {
                            // 3. Launch the image picker when clicked
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
                            )
                        },
                contentAlignment = Alignment.Center,
            ) {              
                if (bitmap != null) {
                    Image(
                        bitmap = bitmap!!,
                        contentDescription = "Uploaded Product Image",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize(),
                    )
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_image_upload), // Update your drawable
                            contentDescription = "Upload Product Image",
                            modifier = Modifier.size(48.dp),
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Tap to upload image",
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Product Title") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    OutlinedTextField(
                        value = sp,
                        onValueChange = { sp = it },
                        label = { Text("SP") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(12.dp),
                    )

                    OutlinedTextField(
                        value = mrp,
                        onValueChange = { mrp = it },
                        label = { Text("MRP") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(12.dp),
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Product Description") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                )

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun GitHubTokenScreen() {
    val context = LocalContext.current
    val sharedPrefs =
        remember {
            context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        }

    var tokenText by remember {
        mutableStateOf(sharedPrefs.getString("GITHUB_TOKEN", "") ?: "")
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedTextField(
                value = tokenText,
                onValueChange = { tokenText = it },
                label = { Text("GitHub Token") },
                placeholder = { Text("ghp_xxxxxxxxxxxx") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    sharedPrefs
                        .edit()
                        .putString("GITHUB_TOKEN", tokenText)
                        .apply()

                    Toast.makeText(context, "Token saved successfully!", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = tokenText.isNotBlank(),
            ) {
                Text("Save Token")
            }
        }
    }
}
