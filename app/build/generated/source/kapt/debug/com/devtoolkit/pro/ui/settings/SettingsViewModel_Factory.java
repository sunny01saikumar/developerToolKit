package com.devtoolkit.pro.ui.settings;

import com.devtoolkit.pro.domain.repository.DevToolkitRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class SettingsViewModel_Factory implements Factory<SettingsViewModel> {
  private final Provider<DevToolkitRepository> repositoryProvider;

  public SettingsViewModel_Factory(Provider<DevToolkitRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static SettingsViewModel_Factory create(
      Provider<DevToolkitRepository> repositoryProvider) {
    return new SettingsViewModel_Factory(repositoryProvider);
  }

  public static SettingsViewModel newInstance(DevToolkitRepository repository) {
    return new SettingsViewModel(repository);
  }
}
