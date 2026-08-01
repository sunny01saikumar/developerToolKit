package com.devtoolkit.pro.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\u001aJ\u0016\u0010\u001b\u001a\u00020\u00182\u0006\u0010\u001c\u001a\u00020\u0013H\u0096@\u00a2\u0006\u0002\u0010\u001dJ\u000e\u0010\u001e\u001a\u00020\u0018H\u0096@\u00a2\u0006\u0002\u0010\u001fJ\u0016\u0010 \u001a\u00020\u00182\u0006\u0010!\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\u001aJ\u000e\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0\u000fH\u0016J\u000e\u0010$\u001a\b\u0012\u0004\u0012\u00020#0\u000fH\u0016J\u000e\u0010%\u001a\b\u0012\u0004\u0012\u00020&0\u000fH\u0016J\u000e\u0010\'\u001a\b\u0012\u0004\u0012\u00020(0\u000fH\u0016J\u000e\u0010)\u001a\b\u0012\u0004\u0012\u00020#0\u000fH\u0016J\u000e\u0010*\u001a\b\u0012\u0004\u0012\u00020+0\u000fH\u0016J\u0016\u0010,\u001a\u00020\u00182\u0006\u0010-\u001a\u00020\fH\u0096@\u00a2\u0006\u0002\u0010.J\u0016\u0010/\u001a\u00020\u00182\u0006\u00100\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\u001aJ\u0016\u00101\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\u001aR \u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0006X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\nR \u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u0006X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u000f0\u0006X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\nR\u001a\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\b0\u0006X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\n\u00a8\u00062"}, d2 = {"Lcom/devtoolkit/pro/data/repository/DevToolkitRepositoryImpl;", "Lcom/devtoolkit/pro/domain/repository/DevToolkitRepository;", "localStorage", "Lcom/devtoolkit/pro/data/local/LocalStorage;", "(Lcom/devtoolkit/pro/data/local/LocalStorage;)V", "bookmarkedTools", "Lkotlinx/coroutines/flow/Flow;", "", "", "getBookmarkedTools", "()Lkotlinx/coroutines/flow/Flow;", "dynamicColors", "", "getDynamicColors", "historyItems", "", "Lcom/devtoolkit/pro/domain/model/HistoryItem;", "getHistoryItems", "notes", "Lcom/devtoolkit/pro/domain/model/Note;", "getNotes", "themeMode", "getThemeMode", "addHistory", "", "toolId", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addOrUpdateNote", "note", "(Lcom/devtoolkit/pro/domain/model/Note;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "clearHistory", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteNote", "noteId", "getDockerCommands", "Lcom/devtoolkit/pro/domain/model/CommandItem;", "getGitCommands", "getHttpHeaders", "Lcom/devtoolkit/pro/domain/model/HttpHeaderItem;", "getHttpStatusCodes", "Lcom/devtoolkit/pro/domain/model/HttpStatusItem;", "getLinuxCommands", "getTools", "Lcom/devtoolkit/pro/domain/model/Tool;", "setDynamicColors", "enabled", "(ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setThemeMode", "mode", "toggleBookmark", "app_release"})
public final class DevToolkitRepositoryImpl implements com.devtoolkit.pro.domain.repository.DevToolkitRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.devtoolkit.pro.data.local.LocalStorage localStorage = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.String> themeMode = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.Boolean> dynamicColors = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.Set<java.lang.String>> bookmarkedTools = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.devtoolkit.pro.domain.model.HistoryItem>> historyItems = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.devtoolkit.pro.domain.model.Note>> notes = null;
    
    public DevToolkitRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.devtoolkit.pro.data.local.LocalStorage localStorage) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.lang.String> getThemeMode() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object setThemeMode(@org.jetbrains.annotations.NotNull()
    java.lang.String mode, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.lang.Boolean> getDynamicColors() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object setDynamicColors(boolean enabled, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.Set<java.lang.String>> getBookmarkedTools() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object toggleBookmark(@org.jetbrains.annotations.NotNull()
    java.lang.String toolId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.devtoolkit.pro.domain.model.HistoryItem>> getHistoryItems() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object addHistory(@org.jetbrains.annotations.NotNull()
    java.lang.String toolId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object clearHistory(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.devtoolkit.pro.domain.model.Note>> getNotes() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object addOrUpdateNote(@org.jetbrains.annotations.NotNull()
    com.devtoolkit.pro.domain.model.Note note, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object deleteNote(@org.jetbrains.annotations.NotNull()
    java.lang.String noteId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.util.List<com.devtoolkit.pro.domain.model.Tool> getTools() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.util.List<com.devtoolkit.pro.domain.model.CommandItem> getLinuxCommands() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.util.List<com.devtoolkit.pro.domain.model.CommandItem> getGitCommands() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.util.List<com.devtoolkit.pro.domain.model.CommandItem> getDockerCommands() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.util.List<com.devtoolkit.pro.domain.model.HttpStatusItem> getHttpStatusCodes() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.util.List<com.devtoolkit.pro.domain.model.HttpHeaderItem> getHttpHeaders() {
        return null;
    }
}