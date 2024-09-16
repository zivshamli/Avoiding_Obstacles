package com.example.avoid_obstacles

import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.avoid_obstacles.fragments.HighScoreFragment
import com.example.avoid_obstacles.fragments.MapFragment

class FinalActivity : AppCompatActivity() {

    private lateinit var main_FRAME_list: FrameLayout
    private lateinit var main_FRAME_map: FrameLayout
    private lateinit var  highScoreFragment: HighScoreFragment
    private lateinit var  mapFragment: MapFragment

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



        mapFragment = MapFragment()

        supportFragmentManager.beginTransaction()
            .add(R.id.main_FRAME_map, mapFragment)
            .commit()
    }

    private fun findViews() {

        main_FRAME_list = findViewById(R.id.main_FRAME_list)

        main_FRAME_map = findViewById(R.id.main_FRAME_map)



    }


}