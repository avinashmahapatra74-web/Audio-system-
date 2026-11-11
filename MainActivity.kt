package com.example.audiosystem

import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.switchmaterial.SwitchMaterial

class MainActivity : AppCompatActivity() {

    private var audioProcessor: StudioAudioProcessor? = null
    private lateinit var switchSoundEffect: SwitchMaterial
    private lateinit var seekBass: SeekBar
    private lateinit var seekTreble: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        switchSoundEffect = findViewById(R.id.switchSoundEffect)
        seekBass = findViewById(R.id.seekBass)
        seekTreble = findViewById(R.id.seekTreble)

        // Session 0 global system output target karta hai
        audioProcessor = StudioAudioProcessor(0)

        switchSoundEffect.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                audioProcessor?.initializeAudioEngine()
            } else {
                audioProcessor?.release()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        audioProcessor?.release()
    }
}
