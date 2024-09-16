package com.example.avoid_obstacles.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.avoid_obstacles.R
import com.example.avoid_obstacles.models.ScoreList
import com.example.avoid_obstacles.utilities.Constants
import com.example.avoid_obstacles.utilities.SharedPreferencesManagerV3
import com.google.android.material.textview.MaterialTextView
import com.google.gson.Gson


class HighScoreFragment : Fragment() {





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v=inflater.inflate(R.layout.fragment_high_score, container, false)

        return v
    }






}