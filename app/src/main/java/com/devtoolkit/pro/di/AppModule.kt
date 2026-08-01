package com.devtoolkit.pro.di

import android.content.Context
import com.devtoolkit.pro.data.local.LocalStorage
import com.devtoolkit.pro.data.repository.DevToolkitRepositoryImpl
import com.devtoolkit.pro.domain.repository.DevToolkitRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalStorage(
        @ApplicationContext context: Context
    ): LocalStorage {
        return LocalStorage(context)
    }

    @Provides
    @Singleton
    fun provideDevToolkitRepository(
        localStorage: LocalStorage
    ): DevToolkitRepository {
        return DevToolkitRepositoryImpl(localStorage)
    }
}
