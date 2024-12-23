package com.yargisoft.tuner.domain.usecase

import kotlin.math.abs

sealed class InstrumentFrequencyMapper {

    abstract fun mapFrequencyToNoteForInstrument(frequency: Float): String

    class GuitarMapper(private val frequencyToNoteMapper: FrequencyToNoteMapper) : InstrumentFrequencyMapper() {
        override fun mapFrequencyToNoteForInstrument(frequency: Float): String {
            val guitarNotes = listOf(
                Pair("E2", 82.41f), Pair("A2", 110.0f), Pair("D3", 146.83f),
                Pair("G3", 196.0f), Pair("B3", 246.94f), Pair("E4", 329.63f)
            )
            val closestNote = guitarNotes.minByOrNull { abs(it.second - frequency) }
            return closestNote?.first ?: frequencyToNoteMapper.mapFrequencyToNote(frequency)
        }
    }

    class UkuleleMapper(private val frequencyToNoteMapper: FrequencyToNoteMapper) : InstrumentFrequencyMapper() {
        override fun mapFrequencyToNoteForInstrument(frequency: Float): String {
            return frequencyToNoteMapper.mapFrequencyToNote(frequency)
        }
    }

    class ViolinMapper(private val frequencyToNoteMapper: FrequencyToNoteMapper) : InstrumentFrequencyMapper() {
        override fun mapFrequencyToNoteForInstrument(frequency: Float): String {
            return frequencyToNoteMapper.mapFrequencyToNote(frequency)
        }
    }
}