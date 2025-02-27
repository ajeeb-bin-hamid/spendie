package com.ajeeb.spendie.main.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.ajeeb.spendie.main.data.repository.MainDataStoreRepositoryImpl
import com.ajeeb.spendie.main.domain.repository.MainDataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MainDatastoreRepositoryModule {


    @Provides
    @Singleton
    fun provideMainDatastoreRepository(datastore: DataStore<Preferences>): MainDataStoreRepository {
        return MainDataStoreRepositoryImpl(datastore)
    }
}