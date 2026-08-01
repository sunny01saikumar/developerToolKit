package com.devtoolkit.pro.ui.features

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devtoolkit.pro.domain.model.Note
import com.devtoolkit.pro.domain.repository.DevToolkitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

// --- Notes ViewModel ---
@HiltViewModel
class NotesViewModel @Inject constructor(
    private val repository: DevToolkitRepository
) : ViewModel() {

    val notes: StateFlow<List<Note>> = repository.notes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addOrUpdateNote(note: Note) {
        viewModelScope.launch {
            repository.addOrUpdateNote(note)
        }
    }

    fun deleteNote(noteId: String) {
        viewModelScope.launch {
            repository.deleteNote(noteId)
        }
    }
}

// --- Notes Screen Composable ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    viewModel: NotesViewModel,
    onBackClick: () -> Unit
) {
    val notes by viewModel.notes.collectAsState()
    val context = LocalContext.current

    var showEditDialog by remember { mutableStateOf(false) }
    var selectedNote by remember { mutableStateOf<Note?>(null) }

    var titleInput by remember { mutableStateOf("") }
    var contentInput by remember { mutableStateOf("") }

    fun openNoteEditor(note: Note?) {
        selectedNote = note
        titleInput = note?.title ?: ""
        contentInput = note?.content ?: ""
        showEditDialog = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Developer Notes", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { openNoteEditor(null) }) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (notes.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No local notes. Tap + to create one.", color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16dp),
                    horizontalArrangement = Arrangement.spacedBy(12dp),
                    verticalArrangement = Arrangement.spacedBy(12dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(notes) { note ->
                        NoteCardItem(
                            note = note,
                            onClick = { openNoteEditor(note) },
                            onDelete = {
                                viewModel.deleteNote(note.id)
                                Toast.makeText(context, "Note deleted", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            }
        }
    }

    // --- Editor Dialog ---
    if (showEditDialog) {
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title = { Text(if (selectedNote == null) "New Note" else "Edit Note", fontWeight = FontWeight.Bold) },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = titleInput,
                        onValueChange = { titleInput = it },
                        label = { Text("Title") },
                        modifier = Modifier.fillMaxWidth().padding(bottom = 8dp),
                        shape = RoundedCornerShape(8dp),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = contentInput,
                        onValueChange = { contentInput = it },
                        label = { Text("Content") },
                        modifier = Modifier.fillMaxWidth().height(160dp),
                        shape = RoundedCornerShape(8dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (titleInput.isBlank() && contentInput.isBlank()) {
                            Toast.makeText(context, "Cannot save empty note", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        val noteToSave = Note(
                            id = selectedNote?.id ?: UUID.randomUUID().toString(),
                            title = titleInput.trim(),
                            content = contentInput.trim(),
                            timestamp = System.currentTimeMillis()
                        )
                        viewModel.addOrUpdateNote(noteToSave)
                        showEditDialog = false
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun NoteCardItem(
    note: Note,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    val dateStr = remember(note.timestamp) {
        val sdf = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
        sdf.format(Date(note.timestamp))
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(12dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = note.title.ifEmpty { "Untitled" },
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onDelete, modifier = Modifier.size(24dp)) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(16dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(6dp))
            
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 3
            )
            
            Spacer(modifier = Modifier.height(10dp))
            
            Text(
                text = dateStr,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }
    }
}
