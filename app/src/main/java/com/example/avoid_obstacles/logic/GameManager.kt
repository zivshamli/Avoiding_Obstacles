package com.example.avoid_obstacles.logic

import com.example.avoid_obstacles.utilities.Constants
import kotlin.random.Random

class GameManager(private val lifeCount:Int=3) {
    private var enemiesArr =IntArray(Constants.COLS*Constants.ROWS){0}
    private var randFlag :Int=0
    private var randLane :Int=0
    private var crush:Int=0

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
    fun enemyMovement(){
        // The last row
        for(i in (Constants.ROWS-1)*Constants.COLS until Constants.ROWS*Constants.COLS){
            enemiesArr[i]=0
        }
        // To promote the enemies that have already been created

        for(i in Constants.ROWS-1 downTo 1)
            for (j in 0 until Constants.COLS){

                enemiesArr[(i)*Constants.COLS+j]=enemiesArr[(i-1)*Constants.COLS+j]
                enemiesArr[(i-1)*Constants.COLS+j]=0

            }
        // To init more enemies
        randFlag=Random.nextInt(1,3)
        if(randFlag==1)
        {
            randLane=Random.nextInt(0,Constants.COLS)
            enemiesArr[randLane]=1

        }

    }
    fun getEnemiesArr():IntArray{
        return enemiesArr
    }

    fun getCrush():Int{
        return crush
    }

    fun checkCollision(current_lane:Int):Boolean{
        if(enemiesArr[(Constants.ROWS-1)*Constants.COLS+current_lane]==1) {
            crush++
            return true
        }
        return false

    }

}