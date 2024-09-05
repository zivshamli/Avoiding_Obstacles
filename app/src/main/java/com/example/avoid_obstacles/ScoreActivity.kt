package com.example.avoid_obstacles

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.avoid_obstacles.utilities.Constants
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView


class ScoreActivity : AppCompatActivity() {

  private lateinit var  score_LBL_result: MaterialTextView
   private lateinit var main_BTN_replay: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
        findViews()
        initViews()
    }

    private fun initViews() {
        val bundle: Bundle? = intent.extras
        val message: String? = bundle?.getString(Constants.STATUS_KEY)
        score_LBL_result.text= buildString {
            append(message)
        }
        main_BTN_replay.setOnClickListener { view: View -> changeActivity() }
    }

    private fun findViews() {
        score_LBL_result = findViewById(R.id.score_LBL_result)
        main_BTN_replay = findViewById(R.id.main_BTN_replay)
    }
    private fun changeActivity(){
        val intent = Intent(this, MainActivity::class.java)
        val b = Bundle()
        intent.putExtras(b)
        startActivity(intent)
        finish()
    }

}