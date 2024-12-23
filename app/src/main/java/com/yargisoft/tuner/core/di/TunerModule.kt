package com.yargisoft.tuner.core.di

import com.yargisoft.tuner.domain.usecase.FrequencyAnalyzer
import com.yargisoft.tuner.domain.usecase.FrequencyToNoteMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TunerModule {

    @Provides
    @Singleton
    fun provideFrequencyAnalyzer(): FrequencyAnalyzer {
        return FrequencyAnalyzer()
    }

    @Provides
    @Singleton
    fun provideFrequencyToNoteMapper(): FrequencyToNoteMapper {
        return FrequencyToNoteMapper()
    }
}
