package com.example.avoid_obstacles.utilities

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class SoundManager(private val context: Context) {
    private val executor: Executor = Executors.newSingleThreadExecutor()
    private var mediaPlayer: MediaPlayer? = null


    fun playSound(resId: Int) {
        executor.execute {
            mediaPlayer = MediaPlayer.create(context, resId).apply {
                isLooping = false
                setVolume(1.0f, 1.0f)
                start()

                // חיווי כשהצליל מתחיל
                Log.d("BackgroundSound", "Sound started")

                setOnCompletionListener {
                    // חיווי כשהצליל מסתיים
                    Log.d("BackgroundSound", "Sound completed")
                    release() // שחרור המדיה פלייר בסיום
                    mediaPlayer = null
                }
            }
        }
    }

    fun stopSound() {
        mediaPlayer?.let {
            executor.execute {
                if (it.isPlaying) {
                    it.stop()
                    Log.d("BackgroundSound", "Sound stopped")
                }
                it.release()
                mediaPlayer = null
            }
        }
    }



}








