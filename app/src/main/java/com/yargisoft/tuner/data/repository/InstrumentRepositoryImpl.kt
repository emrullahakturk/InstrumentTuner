package com.yargisoft.tuner.data.repository

import com.yargisoft.tuner.data.constants.InstrumentType
import com.yargisoft.tuner.domain.repository.InstrumentRepository
import com.yargisoft.tuner.domain.usecase.FrequencyToNoteMapper
import com.yargisoft.tuner.domain.usecase.InstrumentFrequencyMapper
import javax.inject.Inject

class InstrumentRepositoryImpl @Inject constructor(
    private val frequencyToNoteMapper: FrequencyToNoteMapper
) : InstrumentRepository {
    override fun getInstrumentMapper(instrumentType: InstrumentType): InstrumentFrequencyMapper {
        return when (instrumentType) {
            InstrumentType.GUITAR -> InstrumentFrequencyMapper.GuitarMapper(frequencyToNoteMapper)
            InstrumentType.UKULELE -> InstrumentFrequencyMapper.UkuleleMapper(frequencyToNoteMapper)
            InstrumentType.VIOLIN -> InstrumentFrequencyMapper.ViolinMapper(frequencyToNoteMapper)
        }
    }
}
