package com.devtoolkit.pro.ui.home;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u0007J\u0006\u0010\"\u001a\u00020 J\u000e\u0010#\u001a\u00020 2\u0006\u0010$\u001a\u00020\u0007J\u000e\u0010%\u001a\u00020 2\u0006\u0010!\u001a\u00020\u0007R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\r0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001d\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u001d\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000fR\u001d\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\t0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000fR)\u0010\u0017\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00190\u00180\t0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00070\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u000fR\u001d\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u000f\u00a8\u0006&"}, d2 = {"Lcom/devtoolkit/pro/ui/home/HomeViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/devtoolkit/pro/domain/repository/DevToolkitRepository;", "(Lcom/devtoolkit/pro/domain/repository/DevToolkitRepository;)V", "_searchQuery", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_tools", "", "Lcom/devtoolkit/pro/domain/model/Tool;", "bookmarkedToolIds", "Lkotlinx/coroutines/flow/StateFlow;", "", "getBookmarkedToolIds", "()Lkotlinx/coroutines/flow/StateFlow;", "bookmarkedTools", "getBookmarkedTools", "filteredTools", "getFilteredTools", "historyItems", "Lcom/devtoolkit/pro/domain/model/HistoryItem;", "getHistoryItems", "recentTools", "Lkotlin/Pair;", "", "getRecentTools", "searchQuery", "getSearchQuery", "tools", "getTools", "addToolToHistory", "", "toolId", "clearHistory", "onQueryChange", "query", "toggleBookmark", "app_release"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class HomeViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.devtoolkit.pro.domain.repository.DevToolkitRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _searchQuery = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> searchQuery = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.devtoolkit.pro.domain.model.Tool>> _tools = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.devtoolkit.pro.domain.model.Tool>> tools = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.Set<java.lang.String>> bookmarkedToolIds = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.devtoolkit.pro.domain.model.HistoryItem>> historyItems = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.devtoolkit.pro.domain.model.Tool>> filteredTools = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.devtoolkit.pro.domain.model.Tool>> bookmarkedTools = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<kotlin.Pair<com.devtoolkit.pro.domain.model.Tool, java.lang.Long>>> recentTools = null;
    
    @javax.inject.Inject()
    public HomeViewModel(@org.jetbrains.annotations.NotNull()
    com.devtoolkit.pro.domain.repository.DevToolkitRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getSearchQuery() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.devtoolkit.pro.domain.model.Tool>> getTools() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.Set<java.lang.String>> getBookmarkedToolIds() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.devtoolkit.pro.domain.model.HistoryItem>> getHistoryItems() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.devtoolkit.pro.domain.model.Tool>> getFilteredTools() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.devtoolkit.pro.domain.model.Tool>> getBookmarkedTools() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<kotlin.Pair<com.devtoolkit.pro.domain.model.Tool, java.lang.Long>>> getRecentTools() {
        return null;
    }
    
    public final void onQueryChange(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    public final void toggleBookmark(@org.jetbrains.annotations.NotNull()
    java.lang.String toolId) {
    }
    
    public final void addToolToHistory(@org.jetbrains.annotations.NotNull()
    java.lang.String toolId) {
    }
    
    public final void clearHistory() {
    }
}