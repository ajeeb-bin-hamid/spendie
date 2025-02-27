package com.ajeeb.spendie.common.data.di

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.ajeeb.spendie.common.data.SPENDIE_DB
import com.ajeeb.spendie.common.data.db.ExpensesDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
}