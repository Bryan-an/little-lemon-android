@file:Suppress("TooManyFunctions")

package com.example.littlelemon.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.R
import com.example.littlelemon.domain.model.MenuItem
import com.example.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onNavigateToProfile: () -> Unit,
    onSearchPhraseChanged: (String) -> Unit,
    onCategorySelected: (MenuCategory) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            HomeTopBar(
                onNavigateToProfile = onNavigateToProfile,
                modifier = Modifier.fillMaxWidth(),
            )
        },
    ) { innerPadding ->
        HomeContent(
            uiState = uiState,
            onSearchPhraseChanged = onSearchPhraseChanged,
            onCategorySelected = onCategorySelected,
            modifier =
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
        )
    }
}

@Composable
private fun HomeTopBar(
    onNavigateToProfile: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(modifier = modifier) {
        Box(
            modifier =
            Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp),
        ) {
            Image(
                painter = painterResource(R.drawable.little_lemon_logo),
                contentDescription = stringResource(R.string.logo_content_description),
                contentScale = ContentScale.Fit,
                modifier =
                Modifier
                    .align(Alignment.Center)
                    .height(40.dp)
                    .width(200.dp),
            )

            IconButton(
                onClick = onNavigateToProfile,
                modifier = Modifier.align(Alignment.CenterEnd),
            ) {
                Image(
                    painter = painterResource(R.drawable.profile),
                    contentDescription = stringResource(R.string.profile_icon_content_description),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(40.dp),
                )
            }
        }
    }
}

@Composable
private fun HomeContent(
    uiState: HomeUiState,
    onSearchPhraseChanged: (String) -> Unit,
    onCategorySelected: (MenuCategory) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        item {
            HeroSection(
                searchPhrase = uiState.searchPhrase,
                onSearchPhraseChanged = onSearchPhraseChanged,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        item {
            MenuSectionHeader(modifier = Modifier.fillMaxWidth())
        }

        item {
            MenuBreakdownSection(
                selectedCategory = uiState.selectedCategory,
                onCategorySelected = onCategorySelected,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        uiState.errorMessage?.let { message ->
            item {
                MenuErrorMessage(
                    message = message,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }

        items(
            items = uiState.menuItems,
            key = { it.id },
        ) { menuItem ->
            MenuItemRow(
                menuItem = menuItem,
                modifier = Modifier.fillMaxWidth(),
            )

            HorizontalDivider()
        }
    }
}

@Composable
private fun MenuSectionHeader(modifier: Modifier = Modifier) {
    val label = stringResource(R.string.order_for_delivery)

    Text(
        text = label.uppercase(),
        style = MaterialTheme.typography.titleMedium,
        modifier =
        modifier
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .clearAndSetSemantics { contentDescription = label },
    )
}

@Composable
private fun MenuErrorMessage(
    message: String,
    modifier: Modifier = Modifier,
) {
    val label = stringResource(R.string.menu_sync_failed, message)

    Text(
        text = label,
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier.padding(horizontal = 16.dp, vertical = 4.dp),
    )
}

@Composable
private fun MenuBreakdownSection(
    selectedCategory: MenuCategory,
    onCategorySelected: (MenuCategory) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier.padding(bottom = 8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        itemsIndexed(MenuCategory.entries) { _, category ->
            FilterChip(
                selected = category == selectedCategory,
                onClick = { onCategorySelected(category) },
                label = { Text(text = stringResource(category.labelRes)) },
                colors =
                FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    selectedLabelColor = MaterialTheme.colorScheme.onSecondaryContainer,
                ),
            )
        }
    }
}

@Composable
private fun HeroSection(
    searchPhrase: String,
    onSearchPhraseChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
        modifier
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        HeroHeader(modifier = Modifier.fillMaxWidth())

        HeroSearchField(
            value = searchPhrase,
            onValueChange = onSearchPhraseChanged,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun HeroHeader(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        HeroText(modifier = Modifier.weight(1f))
        HeroImage()
    }
}

@Composable
private fun HeroText(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.secondaryContainer,
        )

        Text(
            text = stringResource(R.string.restaurant_city),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onPrimary,
        )

        Text(
            text = stringResource(R.string.restaurant_description),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

@Composable
private fun HeroImage(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.hero_image),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier =
        modifier
            .size(120.dp)
            .clip(RoundedCornerShape(12.dp)),
    )
}

@Composable
private fun HeroSearchField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = stringResource(R.string.search_icon_content_description),
                tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.70f),
            )
        },
        placeholder = {
            Text(
                text = stringResource(R.string.search_placeholder),
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.70f),
            )
        },
        singleLine = true,
        colors =
        OutlinedTextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
            cursorColor = MaterialTheme.colorScheme.secondary,
            focusedBorderColor = MaterialTheme.colorScheme.secondary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.60f),
            focusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.12f),
            unfocusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.12f),
        ),
        modifier = modifier,
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun MenuItemRow(
    menuItem: MenuItem,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = menuItem.title,
                style = MaterialTheme.typography.titleMedium,
            )

            Text(
                text = menuItem.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
            )

            Text(
                text = "$${menuItem.price}",
                style = MaterialTheme.typography.bodyLarge,
            )
        }

        GlideImage(
            model = menuItem.image,
            contentDescription =
            stringResource(
                R.string.menu_item_image_content_description,
                menuItem.title,
            ),
            contentScale = ContentScale.Crop,
            modifier =
            Modifier
                .size(88.dp)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(8.dp)),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    LittleLemonTheme {
        HomeScreen(
            uiState = HomeUiState(menuItems = previewMenuItems),
            onNavigateToProfile = {},
            onSearchPhraseChanged = {},
            onCategorySelected = {},
        )
    }
}

private val previewMenuItems =
    listOf(
        MenuItem(
            id = 1,
            title = "Greek Salad",
            description = "The famous Greek salad of crispy lettuce, peppers, and olives with our house dressing.",
            price = "12.99",
            image = "",
            category = "starters",
        ),
        MenuItem(
            id = 2,
            title = "Pasta",
            description =
            "Penne with fried aubergines, cherry tomatoes, tomato sauce, fresh chili, garlic, " +
                "basil & salted ricotta cheese.",
            price = "18.99",
            image = "",
            category = "mains",
        ),
    )
