package com.example.avoid_obstacles.logic

import com.example.avoid_obstacles.utilities.Constants
import kotlin.random.Random

class GameManager(private val lifeCount:Int=3) {
    private var obstaclesArr =IntArray(Constants.COLS*Constants.ROWS){0}
    private var randFlag :Int=0
    private var randLane :Int=0
    private var crush:Int=0
    private var score_game:Int=0

    fun isGameLost():Boolean{
        return crush==lifeCount
    }

    fun playerMovement(direction:Int,current_lane:Int):Int{
        if(direction==Constants.LEFT){
            if(current_lane+direction>=0){
                return current_lane+direction
            }
            return current_lane
        }
        else if(direction==Constants.RIGHT){
            if(current_lane+direction<Constants.COLS){
                return current_lane+direction
            }
            return current_lane
        }// IF WE ADD MORE DIRECTIONS WE NEED TO ADD THEM HERE

            return current_lane
        

    }
    fun obstaclesMovement(){
        // The last row
        for(i in (Constants.ROWS-1)*Constants.COLS until Constants.ROWS*Constants.COLS){
            obstaclesArr[i]=0
        }
        // To promote the enemies that have already been created

        for(i in Constants.ROWS-1 downTo 1)
            for (j in 0 until Constants.COLS){

                obstaclesArr[(i)*Constants.COLS+j]=obstaclesArr[(i-1)*Constants.COLS+j]
                obstaclesArr[(i-1)*Constants.COLS+j]=0

            }
        // To init more enemies
        randFlag=Random.nextInt(1,3)
        if(randFlag==1)
        {
            randLane=Random.nextInt(0,Constants.COLS)
            obstaclesArr[randLane]=1

        }
        else if(randFlag==2)
        {
            randLane=Random.nextInt(0,Constants.COLS)
            obstaclesArr[randLane]=2
        }

    }
    fun getObstaclesArr():IntArray{
        return obstaclesArr
    }

    fun getCrush():Int{
        return crush
    }

    fun getScore():Int{
        return score_game
    }

    fun checkCollision(current_lane:Int):Int{
        if(obstaclesArr[(Constants.ROWS-1)*Constants.COLS+current_lane]==1) {
            crush++
            return 1
        }
        else if(obstaclesArr[(Constants.ROWS-1)*Constants.COLS+current_lane]==2) {
            score_game+=50
            return 2
        }
        return 0

    }

}