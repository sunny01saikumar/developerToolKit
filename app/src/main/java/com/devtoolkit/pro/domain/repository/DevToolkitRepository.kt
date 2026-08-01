package com.devtoolkit.pro.domain.repository

import com.devtoolkit.pro.domain.model.*
import kotlinx.coroutines.flow.Flow

interface DevToolkitRepository {
    // Settings
    val themeMode: Flow<String>
    suspend fun setThemeMode(mode: String)
    
    val dynamicColors: Flow<Boolean>
    suspend fun setDynamicColors(enabled: Boolean)

    // Bookmarks
    val bookmarkedTools: Flow<Set<String>>
    suspend fun toggleBookmark(toolId: String)

    // History
    val historyItems: Flow<List<HistoryItem>>
    suspend fun addHistory(toolId: String)
    suspend fun clearHistory()

    // Notes
    val notes: Flow<List<Note>>
    suspend fun addOrUpdateNote(note: Note)
    suspend fun deleteNote(noteId: String)

    // Local Metadata / Databases
    fun getTools(): List<Tool>
    fun getLinuxCommands(): List<CommandItem>
    fun getGitCommands(): List<CommandItem>
    fun getDockerCommands(): List<CommandItem>
    fun getHttpStatusCodes(): List<HttpStatusItem>
    fun getHttpHeaders(): List<HttpHeaderItem>
}
