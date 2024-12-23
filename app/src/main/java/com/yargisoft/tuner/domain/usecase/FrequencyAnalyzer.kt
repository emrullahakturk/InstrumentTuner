package com.yargisoft.tuner.domain.usecase

import android.media.AudioRecord
import android.media.MediaRecorder
import android.media.AudioFormat
import javax.inject.Inject
import kotlin.math.sqrt

class FrequencyAnalyzer @Inject constructor() {

    private val sampleRate = 44100
    private val bufferSize = AudioRecord.getMinBufferSize(
        sampleRate,
        AudioFormat.CHANNEL_IN_MONO,
        AudioFormat.ENCODING_PCM_16BIT
    )
    private var audioRecord: AudioRecord? = null

    fun startRecording(onFrequencyDetected: (frequency: Float) -> Unit) {
        try {
            audioRecord = AudioRecord(
                MediaRecorder.AudioSource.MIC,
                sampleRate,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                bufferSize
            )

            if (audioRecord?.state != AudioRecord.STATE_INITIALIZED) {
                throw IllegalStateException("AudioRecord could not be initialized.")
            }

            val buffer = ShortArray(bufferSize)
            audioRecord?.startRecording()

            Thread {
                while (audioRecord?.recordingState == AudioRecord.RECORDSTATE_RECORDING) {
                    val read = audioRecord?.read(buffer, 0, buffer.size) ?: 0
                    if (read > 0) {
                        val frequency = calculateFrequency(buffer, sampleRate)
                        if (frequency > 50.0f) { // Gürültü filtresi
                            onFrequencyDetected(frequency)
                        }
                    }
                }
            }.start()
        } catch (e: Exception) {
            throw IllegalStateException("AudioRecord error: ${e.message}")
        }
    }

    fun stopRecording() {
        audioRecord?.stop()
        audioRecord?.release()
        audioRecord = null
    }

    private fun calculateFrequency(buffer: ShortArray, sampleRate: Int): Float {
        val n = buffer.size
        val paddedSize = 1 shl (32 - Integer.numberOfLeadingZeros(n - 1))

        val real = DoubleArray(paddedSize)
        val imag = DoubleArray(paddedSize)

        for (i in buffer.indices) {
            real[i] = buffer[i].toDouble()
        }

        for (i in buffer.size until paddedSize) {
            real[i] = 0.0
            imag[i] = 0.0
        }

        fft(real, imag)

        val magnitudes = DoubleArray(paddedSize / 2)
        for (i in magnitudes.indices) {
            magnitudes[i] = sqrt(real[i] * real[i] + imag[i] * imag[i])
        }

        val peakIndex = magnitudes.indices.maxByOrNull { magnitudes[it] } ?: 0
        return peakIndex * sampleRate.toFloat() / paddedSize
    }

    private fun fft(real: DoubleArray, imag: DoubleArray) {
        val n = real.size
        if (n == 0) return
        if (n and (n - 1) != 0) throw IllegalArgumentException("Array size is not a power of 2")

        val levels = 31 - Integer.numberOfLeadingZeros(n)
        val cosTable = DoubleArray(n / 2)
        val sinTable = DoubleArray(n / 2)

        for (i in 0 until n / 2) {
            cosTable[i] = kotlin.math.cos(2.0 * kotlin.math.PI * i / n)
            sinTable[i] = kotlin.math.sin(2.0 * kotlin.math.PI * i / n)
        }

        val bitReversedIndices = IntArray(n)
        for (i in 0 until n) {
            bitReversedIndices[i] = i.reverseBits(levels)
        }

        val tempReal = DoubleArray(n)
        val tempImag = DoubleArray(n)

        for (i in 0 until n) {
            tempReal[i] = real[bitReversedIndices[i]]
            tempImag[i] = imag[bitReversedIndices[i]]
        }

        for (i in real.indices) {
            real[i] = tempReal[i]
            imag[i] = tempImag[i]
        }

        var size = 2
        while (size <= n) {
            val halfSize = size / 2
            val tableStep = n / size
            for (i in 0 until n step size) {
                for (j in 0 until halfSize) {
                    val k = j * tableStep
                    val tReal = cosTable[k] * real[i + j + halfSize] - sinTable[k] * imag[i + j + halfSize]
                    val tImag = sinTable[k] * real[i + j + halfSize] + cosTable[k] * imag[i + j + halfSize]
                    real[i + j + halfSize] = real[i + j] - tReal
                    imag[i + j + halfSize] = imag[i + j] - tImag
                    real[i + j] += tReal
                    imag[i + j] += tImag
                }
            }
            size *= 2
        }
    }

    private fun Int.reverseBits(bitCount: Int): Int {
        var value = this
        var result = 0
        for (i in 0 until bitCount) {
            result = result shl 1 or (value and 1)
            value = value shr 1
        }
        return result
    }
}
