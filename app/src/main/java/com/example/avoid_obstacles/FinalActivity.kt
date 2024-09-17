package com.example.avoid_obstacles

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout

import androidx.appcompat.app.AppCompatActivity
import com.example.avoid_obstacles.fragments.HighScoreFragment
import com.example.avoid_obstacles.fragments.MapFragment
import com.example.avoid_obstacles.interfaces.Callback_HighScoreCallback
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class FinalActivity : AppCompatActivity() {

    private lateinit var main_FRAME_list: FrameLayout
    private lateinit var main_FRAME_map: FrameLayout
    private lateinit var  highScoreFragment: HighScoreFragment
    private lateinit var  mapFragment: MapFragment
    private lateinit var  btn_home: ExtendedFloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final)
        findViews()
        initViews()
    }

    private fun initViews() {
        highScoreFragment = HighScoreFragment()

        supportFragmentManager.beginTransaction()
            .add(R.id.main_FRAME_list, highScoreFragment)
            .commit()

        highScoreFragment.highScoreCallback=object : Callback_HighScoreCallback {
            override fun getLocation(lat: Double, lon: Double) {
               mapFragment.changeLocation(lat,lon)
            }


        }


        mapFragment = MapFragment()

        supportFragmentManager.beginTransaction()
            .add(R.id.main_FRAME_map, mapFragment)
            .commit()


        btn_home.setOnClickListener { v: View -> changeActivitytoMenu() }


    }

    private fun findViews() {

        main_FRAME_list = findViewById(R.id.main_FRAME_list)

        main_FRAME_map = findViewById(R.id.main_FRAME_map)

        btn_home = findViewById(R.id.final_BTN_home)


    }

    private fun changeActivitytoMenu() {
        val intent = Intent(this, MenuActivity::class.java)
        val b = Bundle()
        intent.putExtras(b)
        startActivity(intent)
        finish()
    }

}