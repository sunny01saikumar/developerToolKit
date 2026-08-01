package com.devtoolkit.pro.di;

import com.devtoolkit.pro.data.local.LocalStorage;
import com.devtoolkit.pro.domain.repository.DevToolkitRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
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
public final class AppModule_ProvideDevToolkitRepositoryFactory implements Factory<DevToolkitRepository> {
  private final Provider<LocalStorage> localStorageProvider;

  public AppModule_ProvideDevToolkitRepositoryFactory(Provider<LocalStorage> localStorageProvider) {
    this.localStorageProvider = localStorageProvider;
  }

  @Override
  public DevToolkitRepository get() {
    return provideDevToolkitRepository(localStorageProvider.get());
  }

  public static AppModule_ProvideDevToolkitRepositoryFactory create(
      Provider<LocalStorage> localStorageProvider) {
    return new AppModule_ProvideDevToolkitRepositoryFactory(localStorageProvider);
  }

  public static DevToolkitRepository provideDevToolkitRepository(LocalStorage localStorage) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideDevToolkitRepository(localStorage));
  }
}
