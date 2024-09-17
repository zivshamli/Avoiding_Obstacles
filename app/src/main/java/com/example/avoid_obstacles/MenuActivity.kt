package com.example.avoid_obstacles

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.avoid_obstacles.utilities.Constants
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textview.MaterialTextView

class MenuActivity : AppCompatActivity() {

    private lateinit var main_speed_LBL: MaterialTextView
    private lateinit var main_sensor_LBL: MaterialTextView
    private lateinit var main_play_BTN: MaterialButton
    private lateinit var main_record_table_BTN: MaterialButton
    private lateinit var main_speed_Switch: SwitchMaterial
    private lateinit var main_sensor_Switch: SwitchMaterial

    var speed_Flag: Boolean = false
    var sensor_Flag: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        // Request location permissions
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
            Constants.LOCATION_PERMISSION_REQUEST_CODE
        )
        findViews()
        initViews()

    }

    private fun initViews() {
        main_speed_LBL.text=getString(R.string.normal)
        main_sensor_LBL.text=getString(R.string.buttons)
        main_speed_Switch.setOnCheckedChangeListener { _, isChecked -> speedChoice(isChecked) }
        main_sensor_Switch.setOnCheckedChangeListener { _, isChecked -> sensorChoice(isChecked) }


            main_record_table_BTN.setOnClickListener { changeActivityToRecordTable()}



        main_play_BTN.setOnClickListener {view :View ->
            if (sensor_Flag) {
                speed_Flag=false
            }
            changeActivity()
        }
    }

    private fun changeActivityToRecordTable() {
        val intent = Intent(this, FinalActivity::class.java)
        val b = Bundle()
        intent.putExtras(b)
        startActivity(intent)
        finish()
    }

    private fun changeActivity() {
        val intent = Intent(this, MainActivity::class.java)
        val b = Bundle()
       b.putBoolean(Constants.SPEED_KEY,speed_Flag)
       b.putBoolean(Constants.SENSOR_KEY,sensor_Flag)
        intent.putExtras(b)
        startActivity(intent)
        finish()
    }

    private fun sensorChoice(isChecked: Boolean) {
        if (isChecked){
            main_sensor_LBL.text = getString(R.string.sensors)
            sensor_Flag = true
            main_speed_LBL.visibility=View.INVISIBLE
            main_speed_Switch.visibility=View.INVISIBLE
        }else{
            main_sensor_LBL.text = getString(R.string.buttons)
            sensor_Flag = false
            main_speed_LBL.visibility=View.VISIBLE
            main_speed_Switch.visibility=View.VISIBLE
        }


    }

    fun findViews() {
            main_speed_LBL = findViewById(R.id.main_speed_LBL)
            main_sensor_LBL = findViewById(R.id.main_sensor_LBL)
            main_play_BTN = findViewById(R.id.main_start_BTN)
            main_speed_Switch = findViewById(R.id.main_speed_Switch)
            main_sensor_Switch = findViewById(R.id.main_sensor_Switch)
            main_record_table_BTN = findViewById(R.id.menu_BTN_record_table)

        }


     fun speedChoice(isChecked: Boolean) {
        if (isChecked) {
            main_speed_LBL.text = getString(R.string.fast)
            speed_Flag = true
        } else {
            main_speed_LBL.text = getString(R.string.normal)
            speed_Flag = false
        }
    }
    // Handle the result of the permission request
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.LOCATION_PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // Permissions granted, proceed with location access
            } else {
                // Permissions denied, show an error message or handle accordingly
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}