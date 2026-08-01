package com.devtoolkit.pro.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devtoolkit.pro.ui.components.AdBanner
import com.devtoolkit.pro.ui.components.SearchBar
import com.devtoolkit.pro.ui.components.ToolCard
import com.devtoolkit.pro.ui.components.getIconByName

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onToolClick: (route: String, toolId: String) -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToNotes: () -> Unit
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val filteredTools by viewModel.filteredTools.collectAsState()
    val bookmarkedToolIds by viewModel.bookmarkedToolIds.collectAsState()
    val bookmarkedTools by viewModel.bookmarkedTools.collectAsState()
    val recentTools by viewModel.recentTools.collectAsState()

    Scaffold(
        bottomBar = {
            AdBanner(modifier = Modifier.navigationBarsPadding())
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(horizontal = 16dp, vertical = 8dp),
            horizontalArrangement = Arrangement.spacedBy(12dp),
            verticalArrangement = Arrangement.spacedBy(12dp)
        ) {
            // Header Section
            item(span = { GridItemSpan(2) }) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16dp, bottom = 8dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "DevToolkit Pro",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "Everything a Developer Needs.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                            )
                        }

                        Row {
                            IconButton(onClick = onNavigateToNotes) {
                                Icon(
                                    imageVector = Icons.Default.Notes,
                                    contentDescription = "Notes",
                                    tint = MaterialTheme.colorScheme.onBackground
                                )
                            }
                            IconButton(onClick = onNavigateToSettings) {
                                Icon(
                                    imageVector = Icons.Default.Settings,
                                    contentDescription = "Settings",
                                    tint = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16dp))

                    SearchBar(
                        query = searchQuery,
                        onQueryChange = { viewModel.onQueryChange(it) },
                        placeholder = "Search 20+ offline tools..."
                    )
                }
            }

            // Bookmarks / Favorites Carousel (only show when query is empty and bookmarks present)
            if (searchQuery.isEmpty() && bookmarkedTools.isNotEmpty()) {
                item(span = { GridItemSpan(2) }) {
                    Column(modifier = Modifier.padding(vertical = 4dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 8dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Bookmark,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(18dp)
                            )
                            Spacer(modifier = Modifier.width(6dp))
                            Text(
                                text = "Favorites",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(10dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(bookmarkedTools) { tool ->
                                val gradientColors = tool.gradients.map { Color(android.graphics.Color.parseColor(it)) }
                                Box(
                                    modifier = Modifier
                                        .width(140dp)
                                        .height(76dp)
                                        .clip(RoundedCornerShape(12dp))
                                        .background(Brush.linearGradient(gradientColors))
                                        .clickable { onToolClick(tool.route, tool.id) }
                                        .padding(10dp)
                                ) {
                                    Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxSize()) {
                                        Icon(
                                            imageVector = getIconByName(tool.iconName),
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier.size(20dp)
                                        )
                                        Text(
                                            text = tool.title,
                                            style = MaterialTheme.typography.labelMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White,
                                            maxLines = 1
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Recent/History Section (only show when query is empty and history present)
            if (searchQuery.isEmpty() && recentTools.isNotEmpty()) {
                item(span = { GridItemSpan(2) }) {
                    Column(modifier = Modifier.padding(vertical = 4dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.History,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.size(18dp)
                                )
                                Spacer(modifier = Modifier.width(6dp))
                                Text(
                                    text = "Recents",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                            Text(
                                text = "Clear",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.clickable { viewModel.clearHistory() }
                            )
                        }

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(10dp),
                            modifier = Modifier.fillMaxWidth().padding(top = 8dp)
                        ) {
                            items(recentTools) { (tool, _) ->
                                Card(
                                    modifier = Modifier
                                        .width(130dp)
                                        .clickable { onToolClick(tool.route, tool.id) },
                                    shape = RoundedCornerShape(10dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier.padding(10dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = getIconByName(tool.iconName),
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(18dp)
                                        )
                                        Spacer(modifier = Modifier.width(8dp))
                                        Text(
                                            text = tool.title,
                                            style = MaterialTheme.typography.labelMedium,
                                            fontWeight = FontWeight.Medium,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            maxLines = 1
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Grid Section Title
            item(span = { GridItemSpan(2) }) {
                Text(
                    text = if (searchQuery.isNotEmpty()) "Search Results" else "All Tools",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(vertical = 4dp)
                )
            }

            // Tools Grid List
            if (filteredTools.isEmpty()) {
                item(span = { GridItemSpan(2) }) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No tools matched your search",
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                        )
                    }
                }
            } else {
                items(filteredTools) { tool ->
                    ToolCard(
                        tool = tool,
                        isBookmarked = bookmarkedToolIds.contains(tool.id),
                        onBookmarkClick = { viewModel.toggleBookmark(tool.id) },
                        onClick = { onToolClick(tool.route, tool.id) }
                    )
                }
            }

            // Bottom Spacing
            item(span = { GridItemSpan(2) }) {
                Spacer(modifier = Modifier.height(24dp))
            }
        }
    }
}
