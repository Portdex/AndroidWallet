package com.application.portdex.di

import android.content.Context
import com.application.portdex.data.errors.ErrorRepository
import com.application.portdex.data.errors.ErrorRepositoryImpl
import com.application.portdex.data.remote.ApiService
import com.application.portdex.data.repository.LoginRepositoryImpl
import com.application.portdex.data.repository.ProfileRepositoryImpl
import com.application.portdex.data.repository.ServicesRepositoryImpl
import com.application.portdex.data.repository.StorageRepositoryImpl
import com.application.portdex.domain.repository.LoginRepository
import com.application.portdex.domain.repository.ProfileRepository
import com.application.portdex.domain.repository.ServicesRepository
import com.application.portdex.domain.repository.StorageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideErrorRepository(): ErrorRepository {
        return ErrorRepositoryImpl()
    }

    @Provides
    @ViewModelScoped
    fun provideServicesRepository(
        @ApplicationContext context: Context,
        apiService: ApiService,
        error: ErrorRepository
    ): ServicesRepository {
        return ServicesRepositoryImpl(context, apiService, error)
    }

    @Provides
    @ViewModelScoped
    fun provideLoginRepository(error: ErrorRepository): LoginRepository {
        return LoginRepositoryImpl(error)
    }

    @Provides
    @ViewModelScoped
    fun provideProfileRepository(
        apiService: ApiService,
        storage: StorageRepository,
        error: ErrorRepository
    ): ProfileRepository {
        return ProfileRepositoryImpl(apiService, storage, error)
    }

    @Provides
    fun provideStorageRepository(@ApplicationContext context: Context): StorageRepository {
        return StorageRepositoryImpl(context)
    }

}