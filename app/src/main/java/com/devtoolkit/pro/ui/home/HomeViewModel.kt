package com.devtoolkit.pro.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devtoolkit.pro.domain.model.HistoryItem
import com.devtoolkit.pro.domain.model.Tool
import com.devtoolkit.pro.domain.repository.DevToolkitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: DevToolkitRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _tools = MutableStateFlow(repository.getTools())
    val tools: StateFlow<List<Tool>> = _tools.asStateFlow()

    val bookmarkedToolIds: StateFlow<Set<String>> = repository.bookmarkedTools
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    val historyItems: StateFlow<List<HistoryItem>> = repository.historyItems
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // List of tools filtered by search query
    val filteredTools: StateFlow<List<Tool>> = combine(_tools, _searchQuery) { tools, query ->
        if (query.isBlank()) {
            tools
        } else {
            tools.filter {
                it.title.contains(query, ignoreCase = true) ||
                it.description.contains(query, ignoreCase = true) ||
                it.category.contains(query, ignoreCase = true)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), repository.getTools())

    // List of actual Tool objects that are favorited
    val bookmarkedTools: StateFlow<List<Tool>> = combine(_tools, bookmarkedToolIds) { tools, bookmarkedIds ->
        tools.filter { bookmarkedIds.contains(it.id) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // List of actual Tool objects representing recent usage history
    val recentTools: StateFlow<List<Pair<Tool, Long>>> = combine(_tools, historyItems) { tools, history ->
        history.mapNotNull { historyItem ->
            val tool = tools.find { it.id == historyItem.toolId }
            if (tool != null) Pair(tool, historyItem.timestamp) else null
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun toggleBookmark(toolId: String) {
        viewModelScope.launch {
            repository.toggleBookmark(toolId)
        }
    }

    fun addToolToHistory(toolId: String) {
        viewModelScope.launch {
            repository.addHistory(toolId)
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            repository.clearHistory()
        }
    }
}
