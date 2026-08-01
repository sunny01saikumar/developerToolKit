package com.devtoolkit.pro.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.devtoolkit.pro.domain.model.HistoryItem
import com.devtoolkit.pro.domain.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import java.io.IOException

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "devtoolkit_prefs")

class LocalStorage(private val context: Context) {

    private val jsonSerializer = Json { ignoreUnknownKeys = true }

    companion object {
        val THEME_MODE = stringPreferencesKey("theme_mode")
        val DYNAMIC_COLORS = booleanPreferencesKey("dynamic_colors")
        val BOOKMARKED_TOOLS = stringSetPreferencesKey("bookmarked_tools")
        val HISTORY_LIST = stringPreferencesKey("history_list")
        val NOTES_LIST = stringPreferencesKey("notes_list")
    }

    // --- Settings ---
    val themeMode: Flow<String> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences()) else throw exception
        }.map { prefs ->
            prefs[THEME_MODE] ?: "system"
        }

    suspend fun setThemeMode(mode: String) {
        context.dataStore.edit { prefs ->
            prefs[THEME_MODE] = mode
        }
    }

    val dynamicColors: Flow<Boolean> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences()) else throw exception
        }.map { prefs ->
            prefs[DYNAMIC_COLORS] ?: true
        }

    suspend fun setDynamicColors(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[DYNAMIC_COLORS] = enabled
        }
    }

    // --- Bookmarks ---
    val bookmarkedTools: Flow<Set<String>> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences()) else throw exception
        }.map { prefs ->
            prefs[BOOKMARKED_TOOLS] ?: emptySet()
        }

    suspend fun toggleBookmark(toolId: String) {
        context.dataStore.edit { prefs ->
            val current = prefs[BOOKMARKED_TOOLS]?.toMutableSet() ?: mutableSetOf()
            if (current.contains(toolId)) {
                current.remove(toolId)
            } else {
                current.add(toolId)
            }
            prefs[BOOKMARKED_TOOLS] = current
        }
    }

    // --- History ---
    val historyItems: Flow<List<HistoryItem>> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences()) else throw exception
        }.map { prefs ->
            val raw = prefs[HISTORY_LIST] ?: ""
            if (raw.isBlank()) {
                emptyList()
            } else {
                try {
                    jsonSerializer.decodeFromString<List<HistoryItem>>(raw)
                } catch (e: Exception) {
                    emptyList()
                }
            }
        }

    suspend fun addHistory(toolId: String) {
        context.dataStore.edit { prefs ->
            val raw = prefs[HISTORY_LIST] ?: ""
            val currentList = if (raw.isBlank()) {
                emptyList()
            } else {
                try {
                    jsonSerializer.decodeFromString<List<HistoryItem>>(raw).toMutableList()
                } catch (e: Exception) {
                    mutableListOf()
                }
            }.toMutableList()

            // Remove existing entry for the same tool to bring it to the top
            currentList.removeAll { it.toolId == toolId }
            currentList.add(0, HistoryItem(toolId, System.currentTimeMillis()))

            // Keep max 20 items in history
            val trimmedList = if (currentList.size > 20) currentList.take(20) else currentList
            prefs[HISTORY_LIST] = jsonSerializer.encodeToString(trimmedList)
        }
    }

    suspend fun clearHistory() {
        context.dataStore.edit { prefs ->
            prefs[HISTORY_LIST] = ""
        }
    }

    // --- Notes ---
    val notes: Flow<List<Note>> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences()) else throw exception
        }.map { prefs ->
            val raw = prefs[NOTES_LIST] ?: ""
            if (raw.isBlank()) {
                emptyList()
            } else {
                try {
                    jsonSerializer.decodeFromString<List<Note>>(raw)
                        .sortedByDescending { it.timestamp }
                } catch (e: Exception) {
                    emptyList()
                }
            }
        }

    suspend fun addOrUpdateNote(note: Note) {
        context.dataStore.edit { prefs ->
            val raw = prefs[NOTES_LIST] ?: ""
            val currentList = if (raw.isBlank()) {
                emptyList()
            } else {
                try {
                    jsonSerializer.decodeFromString<List<Note>>(raw).toMutableList()
                } catch (e: Exception) {
                    mutableListOf()
                }
            }.toMutableList()

            currentList.removeAll { it.id == note.id }
            currentList.add(note)

            prefs[NOTES_LIST] = jsonSerializer.encodeToString(currentList)
        }
    }

    suspend fun deleteNote(noteId: String) {
        context.dataStore.edit { prefs ->
            val raw = prefs[NOTES_LIST] ?: ""
            if (raw.isNotBlank()) {
                try {
                    val currentList = jsonSerializer.decodeFromString<List<Note>>(raw).toMutableList()
                    currentList.removeAll { it.id == noteId }
                    prefs[NOTES_LIST] = jsonSerializer.encodeToString(currentList)
                } catch (e: Exception) {
                    // Ignore
                }
            }
        }
    }
}
