/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team4153.systems;

import com.team4153.RobotConstants;
import com.team4153.Sensors;

/**
 *
 * @author 4153student
 */
public class FilteredUltrasonic {
    public static final int HISTORY_LENGTH = 5;
    double distanceHistory[] = new double[HISTORY_LENGTH];
    int setupCounter = 0;
    int discardCount = 0;
    public static final int MAX_DISCARD = 5;
    public static final double DISTANCE_THRESHHOLD = 20;
    
    public double getFilteredDistance(){
        double newDistance = Sensors.getUltrasonicDistance();
        if(setupCounter < distanceHistory.length){
            if(newDistance < RobotConstants.MAX_RELEVANT_ULTRASONIC_VALUE){
                distanceHistory[setupCounter] = newDistance;
                setupCounter++;
            }
            return newDistance;
        } else {
            if(newDistance < RobotConstants.MAX_RELEVANT_ULTRASONIC_VALUE){
                if(Math.abs(newDistance-getAverage()) < DISTANCE_THRESHHOLD){
                    for(int i = 1; i <distanceHistory.length; i++){
                        distanceHistory[i-1] = distanceHistory[i];
                    }
                    distanceHistory[distanceHistory.length-1] = newDistance;
                    discardCount = 0;
                    return newDistance;
                }else{
                    discardCount++;
                    if(discardCount>MAX_DISCARD){
                        Sensors.resetUltrasonicFilter();
                    }
                }
            }
        }
        return getAverage();
    }
    /**
    * Returns the value of the ultrasonic unless it is above the max relevant, then
    * returns average.
    */
    public double getSemifilteredUltrasonic(){
        double distance = Sensors.getUltrasonicDistance();
        if(distance < RobotConstants.MAX_RELEVANT_ULTRASONIC_VALUE){
            return distance;
        }else{
            return getAverage();
        }
    }
    
    public double getAverage(){
        double total = 0;
        for(int i = 0; i < distanceHistory.length; i++){
            total += distanceHistory[i];
        }
        return total/distanceHistory.length;
    }
}
