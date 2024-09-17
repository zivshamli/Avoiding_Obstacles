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
import com.example.avoid_obstacles.interfaces.Callback_SpeedCallback
import com.example.avoid_obstacles.interfaces.Callback_TiltCallback
import com.example.avoid_obstacles.logic.GameManager
import com.example.avoid_obstacles.utilities.Constants
import com.example.avoid_obstacles.utilities.MoveDetector
import com.example.avoid_obstacles.utilities.SharedPreferencesManagerV3
import com.example.avoid_obstacles.utilities.SoundManager
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView

class MainActivity : AppCompatActivity() {

    private lateinit var main_FAB_left : ExtendedFloatingActionButton
    private lateinit var main_FAB_right : ExtendedFloatingActionButton
    private  lateinit var  main_Player_IMGS: Array<ShapeableImageView>
    private lateinit var  enemy_IMGS: Array<ShapeableImageView>
    private lateinit var  coin_IMGS: Array<ShapeableImageView>
    private  lateinit var  gameManager: GameManager
    private lateinit var main_IMG_hearts: Array<ShapeableImageView>
    private lateinit var main_score_LBL: MaterialTextView
    private var current_lane:Int=1
    private  val handler: Handler = Handler(Looper.getMainLooper())
    private  var isEnd:Boolean=false
    private var timerOn:Boolean = false
    private var speed_Flag:Boolean=false
    private var sensor_Flag:Boolean=false
    private var speed:Long=Constants.REGULAR //DELAY
    private lateinit var moveDetector: MoveDetector
    val soundManager: SoundManager = SoundManager(this)


    val runnable: Runnable = object : Runnable {
        override fun run() {
            odometer()
            obstaclesGamePlay()

            if (isEnd==false) {
                refreshUI()
            }
            handler.postDelayed(this,   speed)
        }
    }

    private fun odometer() {
        gameManager.odometer()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_main)
                findViews()
                gameManager =GameManager(main_IMG_hearts.size)
                initViews()
            }

            private fun initViews() {
                getDataFromFormerActivity()
                speedChoice()
                startTimer()
                if (sensor_Flag){
                   enableSensorsAndDisableButtons()
                }else{
                    enableButtonsAndDisableSensors()
                }



            }

    private fun enableButtonsAndDisableSensors() {
        main_FAB_right.visibility=View.VISIBLE
        main_FAB_left.visibility=View.VISIBLE
        main_FAB_left.setOnClickListener { view: View? -> playerMove(Constants.LEFT) }
        main_FAB_right.setOnClickListener { view: View? -> playerMove(Constants.RIGHT) }
    }

    private fun enableSensorsAndDisableButtons() {
        main_FAB_left.visibility=View.INVISIBLE
        main_FAB_right.visibility=View.INVISIBLE
        initMoveDetector()
    }

    private fun initMoveDetector() {
        moveDetector=MoveDetector(this,object : Callback_TiltCallback {
            override fun tiltLeft() {
                playerMove(Constants.LEFT)
            }


            override fun tiltRight() {
               playerMove(Constants.RIGHT)
            }
        }, object : Callback_SpeedCallback {
            override fun fastSpeed() {
               speed=Constants.FAST
            }

            override fun regularSpeed() {
                speed=Constants.REGULAR
            }
        } )
    }

    private fun speedChoice() {
        if (speed_Flag){
            speed=Constants.FAST
        }else{
            speed=Constants.REGULAR
        }
    }

    private fun getDataFromFormerActivity() {
        val bundle: Bundle? = intent.extras
        val speed: Boolean? = bundle?.getBoolean(Constants.SPEED_KEY)
        if (speed != null) {
            speed_Flag=speed
        }

        val sensor: Boolean? = bundle?.getBoolean(Constants.SENSOR_KEY)
        if (sensor != null) {
            sensor_Flag=sensor
        }
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
                coin_IMGS= arrayOf(
                    findViewById(R.id.main_Coin_IMG_0),
                    findViewById(R.id.main_Coin_IMG_1),
                    findViewById(R.id.main_Coin_IMG_2),
                    findViewById(R.id.main_Coin_IMG_3),
                    findViewById(R.id.main_Coin_IMG_4),
                    findViewById(R.id.main_Coin_IMG_5),
                    findViewById(R.id.main_Coin_IMG_6),
                    findViewById(R.id.main_Coin_IMG_7),
                    findViewById(R.id.main_Coin_IMG_8),
                    findViewById(R.id.main_Coin_IMG_9),
                    findViewById(R.id.main_Coin_IMG_10),
                    findViewById(R.id.main_Coin_IMG_11),
                    findViewById(R.id.main_Coin_IMG_12),
                    findViewById(R.id.main_Coin_IMG_13),
                    findViewById(R.id.main_Coin_IMG_14),
                    findViewById(R.id.main_Coin_IMG_15),
                    findViewById(R.id.main_Coin_IMG_16),
                    findViewById(R.id.main_Coin_IMG_17),
                    findViewById(R.id.main_Coin_IMG_18),
                    findViewById(R.id.main_Coin_IMG_19),
                    findViewById(R.id.main_Coin_IMG_20),
                )
                main_score_LBL=findViewById(R.id.main_score_LBL)
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
                for(i in 0 until coin_IMGS.size) {
                    if (gameManager.getObstaclesArr()[i] == 2) {
                        coin_IMGS[i].visibility = View.VISIBLE
                    }else{
                        coin_IMGS[i].visibility=View.INVISIBLE
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
        soundManager.playSound(R.raw.police_siren)
    }


    fun checkCollision() {
        if (gameManager.checkCollision(current_lane)==1) {
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
            //display score
            main_score_LBL.text= buildString {
                append("Score: ")
                append(gameManager.getScore())
            }
            checkCollision()
        }
    }
    private fun changeActivity(message: String){
        val intent = Intent(this, ScoreActivity::class.java)
        val b = Bundle()
        b.putString(Constants.STATUS_KEY,message)
        b.putInt(Constants.SCORE_KEY,gameManager.getScore())
        intent.putExtras(b)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTimer()
        soundManager.stopSound()
        if (sensor_Flag){
            moveDetector.stop()
        }
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
        if (sensor_Flag){
            moveDetector.stop()
        }
        soundManager.stopSound()
    }

    override fun onResume() {
        super.onResume()
        startTimer()
        if (sensor_Flag){
            moveDetector.start()
        }

    }
}

