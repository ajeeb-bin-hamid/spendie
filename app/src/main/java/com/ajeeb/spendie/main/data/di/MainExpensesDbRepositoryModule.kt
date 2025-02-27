package com.ajeeb.spendie.main.data.di

import com.ajeeb.spendie.common.data.db.ExpensesDb
import com.ajeeb.spendie.main.data.repository.MainExpensesDbRepositoryImpl
import com.ajeeb.spendie.main.domain.repository.MainExpensesDbRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MainExpensesDbRepositoryModule {

    @Provides
    @Singleton
    fun provideMainExpensesDbRepository(expensesDb: ExpensesDb): MainExpensesDbRepository {
        return MainExpensesDbRepositoryImpl(expensesDb.mainExpensesDbDao)
    }
}