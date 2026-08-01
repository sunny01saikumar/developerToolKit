package com.devtoolkit.pro.domain.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\bf\u0018\u00002\u00020\u0001J\u0016\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0005H\u00a6@\u00a2\u0006\u0002\u0010\u0017J\u0016\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u0010H\u00a6@\u00a2\u0006\u0002\u0010\u001aJ\u000e\u0010\u001b\u001a\u00020\u0015H\u00a6@\u00a2\u0006\u0002\u0010\u001cJ\u0016\u0010\u001d\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020\u0005H\u00a6@\u00a2\u0006\u0002\u0010\u0017J\u000e\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020 0\fH&J\u000e\u0010!\u001a\b\u0012\u0004\u0012\u00020 0\fH&J\u000e\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0\fH&J\u000e\u0010$\u001a\b\u0012\u0004\u0012\u00020%0\fH&J\u000e\u0010&\u001a\b\u0012\u0004\u0012\u00020 0\fH&J\u000e\u0010\'\u001a\b\u0012\u0004\u0012\u00020(0\fH&J\u0016\u0010)\u001a\u00020\u00152\u0006\u0010*\u001a\u00020\tH\u00a6@\u00a2\u0006\u0002\u0010+J\u0016\u0010,\u001a\u00020\u00152\u0006\u0010-\u001a\u00020\u0005H\u00a6@\u00a2\u0006\u0002\u0010\u0017J\u0016\u0010.\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0005H\u00a6@\u00a2\u0006\u0002\u0010\u0017R\u001e\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0018\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u0007R\u001e\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u0007R\u001e\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\f0\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0007R\u0018\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00050\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0013\u0010\u0007\u00a8\u0006/"}, d2 = {"Lcom/devtoolkit/pro/domain/repository/DevToolkitRepository;", "", "bookmarkedTools", "Lkotlinx/coroutines/flow/Flow;", "", "", "getBookmarkedTools", "()Lkotlinx/coroutines/flow/Flow;", "dynamicColors", "", "getDynamicColors", "historyItems", "", "Lcom/devtoolkit/pro/domain/model/HistoryItem;", "getHistoryItems", "notes", "Lcom/devtoolkit/pro/domain/model/Note;", "getNotes", "themeMode", "getThemeMode", "addHistory", "", "toolId", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addOrUpdateNote", "note", "(Lcom/devtoolkit/pro/domain/model/Note;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "clearHistory", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteNote", "noteId", "getDockerCommands", "Lcom/devtoolkit/pro/domain/model/CommandItem;", "getGitCommands", "getHttpHeaders", "Lcom/devtoolkit/pro/domain/model/HttpHeaderItem;", "getHttpStatusCodes", "Lcom/devtoolkit/pro/domain/model/HttpStatusItem;", "getLinuxCommands", "getTools", "Lcom/devtoolkit/pro/domain/model/Tool;", "setDynamicColors", "enabled", "(ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setThemeMode", "mode", "toggleBookmark", "app_release"})
public abstract interface DevToolkitRepository {
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.String> getThemeMode();
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object setThemeMode(@org.jetbrains.annotations.NotNull()
    java.lang.String mode, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Boolean> getDynamicColors();
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object setDynamicColors(boolean enabled, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.Set<java.lang.String>> getBookmarkedTools();
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object toggleBookmark(@org.jetbrains.annotations.NotNull()
    java.lang.String toolId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.devtoolkit.pro.domain.model.HistoryItem>> getHistoryItems();
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object addHistory(@org.jetbrains.annotations.NotNull()
    java.lang.String toolId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object clearHistory(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.devtoolkit.pro.domain.model.Note>> getNotes();
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object addOrUpdateNote(@org.jetbrains.annotations.NotNull()
    com.devtoolkit.pro.domain.model.Note note, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteNote(@org.jetbrains.annotations.NotNull()
    java.lang.String noteId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.NotNull()
    public abstract java.util.List<com.devtoolkit.pro.domain.model.Tool> getTools();
    
    @org.jetbrains.annotations.NotNull()
    public abstract java.util.List<com.devtoolkit.pro.domain.model.CommandItem> getLinuxCommands();
    
    @org.jetbrains.annotations.NotNull()
    public abstract java.util.List<com.devtoolkit.pro.domain.model.CommandItem> getGitCommands();
    
    @org.jetbrains.annotations.NotNull()
    public abstract java.util.List<com.devtoolkit.pro.domain.model.CommandItem> getDockerCommands();
    
    @org.jetbrains.annotations.NotNull()
    public abstract java.util.List<com.devtoolkit.pro.domain.model.HttpStatusItem> getHttpStatusCodes();
    
    @org.jetbrains.annotations.NotNull()
    public abstract java.util.List<com.devtoolkit.pro.domain.model.HttpHeaderItem> getHttpHeaders();
}