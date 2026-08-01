package com.devtoolkit.pro.di;

import android.content.Context;
import com.devtoolkit.pro.data.local.LocalStorage;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class AppModule_ProvideLocalStorageFactory implements Factory<LocalStorage> {
  private final Provider<Context> contextProvider;

  public AppModule_ProvideLocalStorageFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public LocalStorage get() {
    return provideLocalStorage(contextProvider.get());
  }

  public static AppModule_ProvideLocalStorageFactory create(Provider<Context> contextProvider) {
    return new AppModule_ProvideLocalStorageFactory(contextProvider);
  }

  public static LocalStorage provideLocalStorage(Context context) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideLocalStorage(context));
  }
}
