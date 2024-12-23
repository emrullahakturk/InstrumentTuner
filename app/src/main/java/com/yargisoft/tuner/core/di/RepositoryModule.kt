package com.yargisoft.tuner.di

import com.yargisoft.tuner.data.repository.InstrumentRepositoryImpl
import com.yargisoft.tuner.domain.repository.InstrumentRepository
import com.yargisoft.tuner.domain.usecase.FrequencyToNoteMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideInstrumentRepository(
        frequencyToNoteMapper: FrequencyToNoteMapper
    ): InstrumentRepository {
        return InstrumentRepositoryImpl(frequencyToNoteMapper)
    }
}
