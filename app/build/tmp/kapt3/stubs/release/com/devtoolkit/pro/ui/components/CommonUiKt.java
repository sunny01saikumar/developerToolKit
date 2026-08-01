package com.devtoolkit.pro.ui.components;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000<\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a6\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\u0010\b\u0002\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\bH\u0007\u001a\u001a\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0007\u001a8\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\u00032\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0007\u001a>\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0007\u001a\u000e\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0003\u001a\u0010\u0010\u001a\u001a\u00020\u00032\u0006\u0010\u001b\u001a\u00020\u0003H\u0002\u00a8\u0006\u001c"}, d2 = {"CodeViewer", "", "code", "", "modifier", "Landroidx/compose/ui/Modifier;", "title", "onCopy", "Lkotlin/Function0;", "CustomMarkdown", "markdownText", "SearchBar", "query", "onQueryChange", "Lkotlin/Function1;", "placeholder", "ToolCard", "tool", "Lcom/devtoolkit/pro/domain/model/Tool;", "isBookmarked", "", "onBookmarkClick", "onClick", "getIconByName", "Landroidx/compose/ui/graphics/vector/ImageVector;", "name", "parseBoldText", "text", "app_release"})
public final class CommonUiKt {
    
    @androidx.compose.runtime.Composable()
    public static final void ToolCard(@org.jetbrains.annotations.NotNull()
    com.devtoolkit.pro.domain.model.Tool tool, boolean isBookmarked, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBookmarkClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SearchBar(@org.jetbrains.annotations.NotNull()
    java.lang.String query, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onQueryChange, @org.jetbrains.annotations.NotNull()
    java.lang.String placeholder, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void CodeViewer(@org.jetbrains.annotations.NotNull()
    java.lang.String code, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> onCopy) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void CustomMarkdown(@org.jetbrains.annotations.NotNull()
    java.lang.String markdownText, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    private static final java.lang.String parseBoldText(java.lang.String text) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.ui.graphics.vector.ImageVector getIconByName(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
        return null;
    }
}