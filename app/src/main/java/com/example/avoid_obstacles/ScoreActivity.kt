package com.example.avoid_obstacles

import android.Manifest

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.EditText

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

import com.example.avoid_obstacles.models.Score
import com.example.avoid_obstacles.models.ScoreList
import com.example.avoid_obstacles.utilities.Constants
import com.example.avoid_obstacles.utilities.SharedPreferencesManagerV3
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.google.gson.Gson


class ScoreActivity : AppCompatActivity() {

  private lateinit var  score_LBL_result: MaterialTextView
   private lateinit var main_BTN_record_table: MaterialButton
   private lateinit var main_BTN_replay: MaterialButton
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var score: Int = 0
    private var userName: String = ""
     private lateinit var input:EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        findViews()
        initViews()
        showNameInputDialog(score)
    }

    private fun initViews() {
        val bundle: Bundle? = intent.extras
        val finalscore: Int? = bundle?.getInt(Constants.SCORE_KEY)
        if (finalscore != null) {
            score = finalscore
        }
        score_LBL_result.text= buildString {
            append("Score: ")
            append(score.toString())
        }
        main_BTN_record_table.setOnClickListener { view: View -> changeActivity() }
        main_BTN_replay.setOnClickListener { view: View -> changeActivitytoMenu() }
    }

    private fun changeActivitytoMenu() {
        val intent = Intent(this, MenuActivity::class.java)
        val b = Bundle()
        intent.putExtras(b)
        startActivity(intent)
        finish()
    }

    private fun findViews() {
        score_LBL_result = findViewById(R.id.score_LBL_result)
        main_BTN_record_table = findViewById(R.id.main_BTN_record_table)
        main_BTN_replay = findViewById(R.id.main_BTN_replay)
    }
    private fun changeActivity(){
        val intent = Intent(this, FinalActivity::class.java)
        val b = Bundle()
        intent.putExtras(b)
        startActivity(intent)
        finish()
    }
    private fun showNameInputDialog(score: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("insert your name")

        input = EditText(this)
        builder.setView(input)
        builder.setPositiveButton("OK") { dialog, id -> onClick(score) }
        builder.setNegativeButton("Cancel") { dialog, id -> dialog.cancel() }



        builder.show()

    }


    @Override
    private fun onClick(score: Int) {
        userName = input.text.toString()
        // Check for location permissions
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            score_LBL_result.text= buildString {
                append("Missing Permissions")
            }
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location?->
            if (location != null) {
                val lat = location.latitude
                val long = location.longitude
                val managerV3=SharedPreferencesManagerV3.getInstance()
                val gson = Gson()
                val scorelistFromSP=managerV3.getString(Constants.SCORELIST_KEY,"")
                var scorelist=gson.fromJson(scorelistFromSP,ScoreList::class.java)
                if (scorelist==null){
                    scorelist=ScoreList()
                }
                scorelist.addScore(Score( userName,score,lat,long))
                scorelist.scoresArrayList.sort()
                val scorelistString=gson.toJson(scorelist)
                managerV3.putString(Constants.SCORELIST_KEY,scorelistString)

            }


        }
    }




}