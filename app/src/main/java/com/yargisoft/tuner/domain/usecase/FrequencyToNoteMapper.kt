package com.yargisoft.tuner.domain.usecase

import kotlin.math.abs
import javax.inject.Inject

class FrequencyToNoteMapper @Inject constructor() {

    // Görseldeki frekans tablolarını bir map yapısında tanımlıyoruz
    private val notes = listOf(
        Pair("C0", 16.35f), Pair("C#0", 17.32f), Pair("D0", 18.35f), Pair("D#0", 19.45f), Pair("E0", 20.6f),
        Pair("F0", 21.83f), Pair("F#0", 23.12f), Pair("G0", 24.5f), Pair("G#0", 25.96f), Pair("A0", 27.5f),
        Pair("A#0", 29.14f), Pair("B0", 30.87f),

        Pair("C1", 32.7f), Pair("C#1", 34.65f), Pair("D1", 36.71f), Pair("D#1", 38.89f), Pair("E1", 41.2f),
        Pair("F1", 43.65f), Pair("F#1", 46.25f), Pair("G1", 49.0f), Pair("G#1", 51.91f), Pair("A1", 55.0f),
        Pair("A#1", 58.27f), Pair("B1", 61.74f),

        Pair("C2", 65.41f), Pair("C#2", 69.3f), Pair("D2", 73.42f), Pair("D#2", 77.78f), Pair("E2", 82.41f),
        Pair("F2", 87.31f), Pair("F#2", 92.5f), Pair("G2", 98.0f), Pair("G#2", 103.83f), Pair("A2", 110.0f),
        Pair("A#2", 116.54f), Pair("B2", 123.47f),

        Pair("C3", 130.81f), Pair("C#3", 138.59f), Pair("D3", 146.83f), Pair("D#3", 155.56f), Pair("E3", 164.81f),
        Pair("F3", 174.61f), Pair("F#3", 185.0f), Pair("G3", 196.0f), Pair("G#3", 207.65f), Pair("A3", 220.0f),
        Pair("A#3", 233.08f), Pair("B3", 246.94f),

        Pair("C4", 261.63f), Pair("C#4", 277.18f), Pair("D4", 293.66f), Pair("D#4", 311.13f), Pair("E4", 329.63f),
        Pair("F4", 349.23f), Pair("F#4", 369.99f), Pair("G4", 392.0f), Pair("G#4", 415.3f), Pair("A4", 440.0f),
        Pair("A#4", 466.16f), Pair("B4", 493.88f),

        Pair("C5", 523.25f), Pair("C#5", 554.37f), Pair("D5", 587.33f), Pair("D#5", 622.25f), Pair("E5", 659.26f),
        Pair("F5", 698.46f), Pair("F#5", 739.99f), Pair("G5", 783.99f), Pair("G#5", 830.61f), Pair("A5", 880.0f),
        Pair("A#5", 932.33f), Pair("B5", 987.77f),

        Pair("C6", 1046.5f), Pair("C#6", 1108.73f), Pair("D6", 1174.66f), Pair("D#6", 1244.51f), Pair("E6", 1318.51f),
        Pair("F6", 1396.91f), Pair("F#6", 1479.98f), Pair("G6", 1567.98f), Pair("G#6", 1661.22f), Pair("A6", 1760.0f),
        Pair("A#6", 1864.66f), Pair("B6", 1975.53f),

        Pair("C7", 2093.0f), Pair("C#7", 2217.46f), Pair("D7", 2349.32f), Pair("D#7", 2489.02f), Pair("E7", 2637.02f),
        Pair("F7", 2793.83f), Pair("F#7", 2959.96f), Pair("G7", 3135.96f), Pair("G#7", 3322.44f), Pair("A7", 3520.0f),
        Pair("A#7", 3729.31f), Pair("B7", 3951.07f),

        Pair("C8", 4186.01f), Pair("C#8", 4434.92f), Pair("D8", 4698.63f), Pair("D#8", 4978.03f), Pair("E8", 5274.04f),
        Pair("F8", 5587.65f), Pair("F#8", 5919.91f), Pair("G8", 6271.93f), Pair("G#8", 6644.88f), Pair("A8", 7040.0f),
        Pair("A#8", 7458.62f), Pair("B8", 7902.13f)
    )

    // Frekansı en yakın notaya eşleştirir
    fun mapFrequencyToNote(frequency: Float): String {
        val closestNote = notes.minByOrNull { abs(it.second - frequency) }
        return closestNote?.first ?: "Unknown"
    }
}
