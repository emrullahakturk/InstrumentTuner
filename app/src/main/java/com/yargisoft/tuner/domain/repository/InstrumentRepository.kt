package com.yargisoft.tuner.domain.repository

import com.yargisoft.tuner.data.constants.InstrumentType
import com.yargisoft.tuner.domain.usecase.InstrumentFrequencyMapper

interface InstrumentRepository {
    fun getInstrumentMapper(instrumentType: InstrumentType): InstrumentFrequencyMapper
}
