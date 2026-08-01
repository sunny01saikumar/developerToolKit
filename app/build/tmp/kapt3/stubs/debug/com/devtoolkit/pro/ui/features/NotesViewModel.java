package com.devtoolkit.pro.ui.features;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\bJ\u000e\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0010R\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/devtoolkit/pro/ui/features/NotesViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/devtoolkit/pro/domain/repository/DevToolkitRepository;", "(Lcom/devtoolkit/pro/domain/repository/DevToolkitRepository;)V", "notes", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/devtoolkit/pro/domain/model/Note;", "getNotes", "()Lkotlinx/coroutines/flow/StateFlow;", "addOrUpdateNote", "", "note", "deleteNote", "noteId", "", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class NotesViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.devtoolkit.pro.domain.repository.DevToolkitRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.devtoolkit.pro.domain.model.Note>> notes = null;
    
    @javax.inject.Inject()
    public NotesViewModel(@org.jetbrains.annotations.NotNull()
    com.devtoolkit.pro.domain.repository.DevToolkitRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.devtoolkit.pro.domain.model.Note>> getNotes() {
        return null;
    }
    
    public final void addOrUpdateNote(@org.jetbrains.annotations.NotNull()
    com.devtoolkit.pro.domain.model.Note note) {
    }
    
    public final void deleteNote(@org.jetbrains.annotations.NotNull()
    java.lang.String noteId) {
    }
}