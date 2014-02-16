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
    double distanceHistory[] = {0,0,0,0,0};
    int setupCounter = 0;
    
    public double getFilteredDistance(){
        double newDistance = Sensors.getUltrasonicDistance();
        if(setupCounter < distanceHistory.length){
            distanceHistory[setupCounter] = newDistance;
            setupCounter++;
            return newDistance;
        } else {
            if(Math.abs(newDistance-getAverage()) < RobotConstants.DISTANCE_THRESHHOLD){
                for(int i = 1; i <distanceHistory.length; i++){
                    distanceHistory[i-1] = distanceHistory[i];
                }
                distanceHistory[distanceHistory.length] = newDistance;
                return newDistance;
            }else{
                System.out.println("Discarded value: " + newDistance);
            }
        }
        return getAverage();
    }
    public double getAverage(){
        double total = 0;
        for(int i = 0; i < distanceHistory.length; i++){
            total += distanceHistory[i];
        }
        return total/distanceHistory.length;
    }
}
