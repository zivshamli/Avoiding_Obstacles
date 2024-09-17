package com.example.avoid_obstacles.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.avoid_obstacles.adapter.ScoreAdapter
import com.example.avoid_obstacles.databinding.FragmentHighScoreBinding
import com.example.avoid_obstacles.interfaces.Callback_HighScoreCallback
import com.example.avoid_obstacles.interfaces.Callback_ScoreCallback
import com.example.avoid_obstacles.models.Score

import com.example.avoid_obstacles.utilities.SharedPreferencesManagerV3



class HighScoreFragment : Fragment() {

    private lateinit var binding : FragmentHighScoreBinding
    var highScoreCallback: Callback_HighScoreCallback?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding=FragmentHighScoreBinding.inflate(inflater,container,false)
        val view=binding.root
        val scoreList=SharedPreferencesManagerV3.getInstance().getScoreListFromSP()

        val scoreAdapter=ScoreAdapter(scoreList.scoresArrayList)
        scoreAdapter.callback_ScoreCallback=object : Callback_ScoreCallback {
            override fun scoreClicked(score: Score, position: Int) {
                highScoreCallback?.getLocation(score.lat,score.lon)
            }

        }
        binding.mainRVScoresList.adapter=scoreAdapter

        val linearLayoutManager=LinearLayoutManager(this.context)
        linearLayoutManager.orientation= RecyclerView.VERTICAL
        binding.mainRVScoresList.layoutManager=linearLayoutManager

        return view

    }

}






