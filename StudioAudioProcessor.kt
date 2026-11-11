package com.example.audiosystem

import android.media.audiofx.DynamicsProcessing
import android.media.audiofx.DynamicsProcessing.*

class StudioAudioProcessor(private val audioSessionId: Int) {
    private var dp: DynamicsProcessing? = null

    fun initializeAudioEngine() {
        val channelCount = 2
        val preEqBandCount = 3
        val preEq = Eq(true, true, preEqBandCount)
        
        // 60Hz Punchy Sub-Bass (+5.5 dB)
        preEq.setBand(0, EqBand(true, 60.0f, 5.5f))
        // 1000Hz Mids (Flat 0 dB)
        preEq.setBand(1, EqBand(true, 1000.0f, 0.0f))
        // 12000Hz High Treble (+3.0 dB)
        preEq.setBand(2, EqBand(true, 12000.0f, 3.0f))

        val builder = Config.Builder(
            Config.VARIANT_FAVOR_FREQUENCY_RESOLUTION,
            channelCount,
            true, preEqBandCount,
            false, 0,
            false, 0,
            true
        )

        val config = builder.build()
        for (ch in 0 until channelCount) {
            config.setPreEqForChannel(ch, preEq)
        }

        dp = DynamicsProcessing(0, audioSessionId, config).apply {
            enabled = true
        }
    }

    fun release() {
        dp?.enabled = false
        dp?.release()
        dp = null
    }
}
