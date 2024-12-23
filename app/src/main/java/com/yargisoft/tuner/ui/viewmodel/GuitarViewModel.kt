package com.yargisoft.tuner.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yargisoft.tuner.data.constants.InstrumentType
import com.yargisoft.tuner.domain.repository.InstrumentRepository
import com.yargisoft.tuner.domain.usecase.FrequencyAnalyzer
import com.yargisoft.tuner.domain.usecase.InstrumentFrequencyMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GuitarViewModel @Inject constructor(
    private val frequencyAnalyzer: FrequencyAnalyzer,
    private val instrumentRepository: InstrumentRepository
) : ViewModel() {

    private val _currentFrequency = MutableLiveData<Float>()
    val currentFrequency: LiveData<Float> = _currentFrequency

    private val _currentNote = MutableLiveData<String>()
    val currentNote: LiveData<String> = _currentNote

    private val instrumentMapper: InstrumentFrequencyMapper =
        instrumentRepository.getInstrumentMapper(InstrumentType.GUITAR)

    fun startTuning() {
        frequencyAnalyzer.startRecording { frequency ->
            _currentFrequency.postValue(frequency)
            _currentNote.postValue(instrumentMapper.mapFrequencyToNoteForInstrument(frequency))
        }
    }

    fun stopTuning() {
        frequencyAnalyzer.stopRecording()
    }
}
