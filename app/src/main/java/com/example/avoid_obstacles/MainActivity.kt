package com.example.avoid_obstacles

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.avoid_obstacles.logic.GameManager
import com.example.avoid_obstacles.utilities.Constants
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.imageview.ShapeableImageView

class MainActivity : AppCompatActivity() {

    private lateinit var main_FAB_left : ExtendedFloatingActionButton
    private lateinit var main_FAB_right : ExtendedFloatingActionButton
    private  lateinit var  main_Player_IMGS: Array<ShapeableImageView>
    private lateinit var  enemy_IMGS: Array<ShapeableImageView>
    private  lateinit var  gameManager: GameManager
    private lateinit var main_IMG_hearts: Array<ShapeableImageView>
    private var current_lane:Int=1
    private  val handler: Handler = Handler(Looper.getMainLooper())
    private  var isEnd:Boolean=false
    private var timerOn:Boolean = false

    val runnable: Runnable = object : Runnable {
        override fun run() {
            obstaclesGamePlay()
            if (isEnd==false) {
                refreshUI()
            }
            handler.postDelayed(this,   Constants.DELAY)
        }
    }
            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_main)
                findViews()
                gameManager =GameManager(main_IMG_hearts.size)
                initViews()
            }

            private fun initViews() {
                startTimer()
                main_FAB_left.setOnClickListener { view: View? -> playerMove(Constants.LEFT) }
                main_FAB_right.setOnClickListener { view: View? -> playerMove(Constants.RIGHT) }
            }


            private fun findViews() {
                main_FAB_left = findViewById(R.id.main_FAB_left)

                main_FAB_right = findViewById(R.id.main_FAB_right)

                main_Player_IMGS = arrayOf(
                    findViewById(R.id.main_Player_IMG_0),
                    findViewById(R.id.main_Player_IMG_1),
                    findViewById(R.id.main_Player_IMG_2)
                )

                enemy_IMGS = arrayOf(
                    findViewById(R.id.main_Police_IMG_0),
                    findViewById(R.id.main_Police_IMG_1),
                    findViewById(R.id.main_Police_IMG_2),
                    findViewById(R.id.main_Police_IMG_3),
                    findViewById(R.id.main_Police_IMG_4),
                    findViewById(R.id.main_Police_IMG_5),
                    findViewById(R.id.main_Police_IMG_6),
                    findViewById(R.id.main_Police_IMG_7),
                    findViewById(R.id.main_Police_IMG_8),
                    findViewById(R.id.main_Police_IMG_9),
                    findViewById(R.id.main_Police_IMG_10),
                    findViewById(R.id.main_Police_IMG_11),
                    findViewById(R.id.main_Police_IMG_12),
                    findViewById(R.id.main_Police_IMG_13),
                    findViewById(R.id.main_Police_IMG_14),
                    findViewById(R.id.main_Police_IMG_15),
                    findViewById(R.id.main_Police_IMG_16),
                    findViewById(R.id.main_Police_IMG_17),
                    findViewById(R.id.main_Police_IMG_18),
                    findViewById(R.id.main_Police_IMG_19),
                    findViewById(R.id.main_Police_IMG_20),
                )
                main_IMG_hearts= arrayOf(
                    findViewById(R.id.main_IMG_heart0),
                    findViewById(R.id.main_IMG_heart1),
                    findViewById(R.id.main_IMG_heart2)
                )
            }

            private fun playerMove(direction: Int) {
                main_Player_IMGS[current_lane].visibility = View.INVISIBLE
                current_lane = gameManager.playerMovement(direction, current_lane)
                main_Player_IMGS[current_lane].visibility = View.VISIBLE
                refreshUI()
            }

            fun obstaclesMove() {

                for (i in 0 until enemy_IMGS.size) {
                    if (gameManager.getObstaclesArr()[i] == 1) {
                        enemy_IMGS[i].visibility = View.VISIBLE
                    } else {
                        enemy_IMGS[i].visibility = View.INVISIBLE
                    }
                }

            }
            fun obstaclesGamePlay()
            {
                gameManager.obstaclesMovement()
                obstaclesMove()
            }
             private fun toast(text: String) {
                Toast
                .makeText(
                    this,
                        text,
                         Toast.LENGTH_SHORT
                    ).show()
             }
    private fun vibrate() {
        val vibrator: Vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                this.getSystemService(VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            this.getSystemService(VIBRATOR_SERVICE) as Vibrator
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {



            val oneShotVibrationEffect = VibrationEffect.createOneShot(
                500,
                VibrationEffect.DEFAULT_AMPLITUDE
            )
            vibrator.vibrate(oneShotVibrationEffect)
        } else {
            vibrator.vibrate(500)
        }
    }
    fun toastAndVibrate(text: String)
    {
        toast(text)
        vibrate()
    }


    fun checkCollision() {
        if (gameManager.checkCollision(current_lane)==true) {
            toastAndVibrate(Constants.CRUSHTEXT)
            if(gameManager.getCrush() != 0){
                main_IMG_hearts[main_IMG_hearts.size-gameManager.getCrush()].visibility=View.INVISIBLE
            }
        }
    }
   fun refreshUI()
    {
        if(gameManager.isGameLost()){
            isEnd=true
            stopTimer()
            changeActivity("Game Over😭")
        }
        else{
            checkCollision()
        }
    }
    private fun changeActivity(message: String){
        val intent = Intent(this, ScoreActivity::class.java)
        val b = Bundle()
        b.putString(Constants.STATUS_KEY,message)
        intent.putExtras(b)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTimer()
    }
    fun startTimer()
    {
        if (!timerOn) {
            timerOn = true
            handler.postDelayed(runnable, 0)
        }
    }
    fun stopTimer(){
        timerOn = false
        handler.removeCallbacks(runnable)
    }



    override fun onPause() {
        super.onPause()
        stopTimer()
    }

    override fun onResume() {
        super.onResume()
        startTimer()
    }
}

