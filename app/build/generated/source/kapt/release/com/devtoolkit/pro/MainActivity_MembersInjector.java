package com.devtoolkit.pro;

import com.devtoolkit.pro.domain.repository.DevToolkitRepository;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
  private final Provider<DevToolkitRepository> repositoryProvider;

  public MainActivity_MembersInjector(Provider<DevToolkitRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  public static MembersInjector<MainActivity> create(
      Provider<DevToolkitRepository> repositoryProvider) {
    return new MainActivity_MembersInjector(repositoryProvider);
  }

  @Override
  public void injectMembers(MainActivity instance) {
    injectRepository(instance, repositoryProvider.get());
  }

  @InjectedFieldSignature("com.devtoolkit.pro.MainActivity.repository")
  public static void injectRepository(MainActivity instance, DevToolkitRepository repository) {
    instance.repository = repository;
  }
}
