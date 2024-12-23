package com.yargisoft.tuner.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yargisoft.tuner.domain.usecase.FrequencyAnalyzer
import com.yargisoft.tuner.domain.usecase.FrequencyToNoteMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TunerViewModel @Inject constructor(
    private val frequencyAnalyzer: FrequencyAnalyzer,
    private val noteMapper: FrequencyToNoteMapper
) : ViewModel() {

    private val _currentFrequency = MutableLiveData<Float>()
    val currentFrequency: LiveData<Float> = _currentFrequency

    private val _currentNote = MutableLiveData<String>()
    val currentNote: LiveData<String> = _currentNote

    fun startTuning() {
        frequencyAnalyzer.startRecording { frequency ->
            _currentFrequency.postValue(frequency)
            _currentNote.postValue(noteMapper.mapFrequencyToNote(frequency))
        }
    }

    fun stopTuning() {
        frequencyAnalyzer.stopRecording()
    }
}
