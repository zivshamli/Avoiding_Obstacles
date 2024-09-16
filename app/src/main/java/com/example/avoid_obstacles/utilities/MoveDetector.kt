package com.example.avoid_obstacles.utilities

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.example.avoid_obstacles.interfaces.Callback_TiltCallback

class MoveDetector(context: Context,private val tiltCallback: Callback_TiltCallback?) {
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) as Sensor
    private lateinit var sensorEventListener: SensorEventListener
    private var timestamp: Long = 0L



    init {
        initEventListener()
    }

    private fun initEventListener() {
        sensorEventListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                val x = event.values[0]

                calculateMove(x)
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
               //pass
            }
        }
    }

    private fun calculateMove(x: Float) {
        if (System.currentTimeMillis() - timestamp > 200) {
            timestamp = System.currentTimeMillis()
            if (x > 3.0) {
                if ( tiltCallback!= null) {
                    tiltCallback.tiltLeft()
                }
            } else if (x < -3.0) {
                if (tiltCallback != null) {
                    tiltCallback.tiltRight()
                }
            }
        }
    }
    fun stop() {
        sensorManager
            .unregisterListener(
                sensorEventListener,
                sensor
            )
    }

    fun start() {
        sensorManager
            .registerListener(
                sensorEventListener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
    }
}