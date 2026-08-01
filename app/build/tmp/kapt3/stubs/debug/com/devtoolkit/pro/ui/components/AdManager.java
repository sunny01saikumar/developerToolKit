package com.devtoolkit.pro.ui.components;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\u001c\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\n0\u0011R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/devtoolkit/pro/ui/components/AdManager;", "", "()V", "INTERSTITIAL_AD_UNIT_ID", "", "isLoading", "", "mInterstitialAd", "Lcom/google/android/gms/ads/interstitial/InterstitialAd;", "loadAd", "", "context", "Landroid/content/Context;", "showInterstitial", "activity", "Landroid/app/Activity;", "onAdClosed", "Lkotlin/Function0;", "app_debug"})
public final class AdManager {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712";
    @org.jetbrains.annotations.Nullable()
    private static com.google.android.gms.ads.interstitial.InterstitialAd mInterstitialAd;
    private static boolean isLoading = false;
    @org.jetbrains.annotations.NotNull()
    public static final com.devtoolkit.pro.ui.components.AdManager INSTANCE = null;
    
    private AdManager() {
        super();
    }
    
    public final void loadAd(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    public final void showInterstitial(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onAdClosed) {
    }
}