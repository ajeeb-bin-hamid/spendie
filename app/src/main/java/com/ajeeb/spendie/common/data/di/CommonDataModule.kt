package com.ajeeb.spendie.common.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.ajeeb.spendie.common.data.SPENDIE_DATA_STORE
import com.ajeeb.spendie.common.data.SPENDIE_DB
import com.ajeeb.spendie.common.data.db.ExpensesDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okio.Path.Companion.toPath
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CommonDataModule {

    @Provides
    @Singleton
    fun provideExpensesDb(context: Context): ExpensesDb {
        val dbFile = context.getDatabasePath(SPENDIE_DB)
        return Room.databaseBuilder<ExpensesDb>(
            context = context, name = dbFile.absolutePath
        ).setDriver(BundledSQLiteDriver()).build()
    }

    @Provides
    @Singleton
    fun provideDataStore(context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.createWithPath(produceFile = {
            context.filesDir.resolve(SPENDIE_DATA_STORE).absolutePath.toPath()
        })
    }
}