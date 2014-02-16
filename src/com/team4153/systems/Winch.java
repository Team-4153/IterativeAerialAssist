package com.team4153.systems;

import com.team4153.RobotConstants;
import com.team4153.RobotMap;
import com.team4153.Sensors;
import edu.wpi.first.wpilibj.Victor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 4153student
 */
public class Winch implements Systems{
    /**
     * .15 is too low, .3 is pretty fast
     */
    
    
    Victor motor;
    
    /**
     * The amount of time (in milliseconds) the winch will be pulled back if executed.
     * 0 or less means go until limit switch is hit.
     */
    private int pullBackTime = 0;
    
    public Winch(){
        motor = new Victor(RobotMap.VICTOR_CHANNEL);
        motor.setExpiration(2.0);
    }
    
    /**
     * pulls back the winch all the way until the limit is hit
     */
    public void pullBackWinch() {
        pullBackWinch(0);

    }

    /**
     * pulls back the winch until the specified time or the limit is hit.
     *
     * @param time in milliseconds
     */
    public void pullBackWinch(int time) {
//        setPullBackTime(time);
        execute(-1);
    }
    
    /**
     *  Sets the amount of time (in milliseconds) the winch will be pulled back if executed.
     * 0 or less means go until limit switch is hit.
     */
    public void setPullBackTime(int pullBackTime){
        this.pullBackTime = pullBackTime;
    }

    public void execute(int buttonNumber) {
//        if(buttonNumber>0){
//            if(buttonNumber==RobotMap.JSBUTTON_WINCH_HALF){
//                setPullBackTime(RobotConstants.WINCH_HALF_TIME);
//            }else{
////                if (buttonNumber==RobotMap.JSBUTTON_WINCH_FULL){
////                    setPullBackTime(0);
////                }
//            }
//        }
//        if(buttonNumber != RobotMap.JSBUTTON_WINCH_HALF){
        WinchPuller puller = new WinchPuller(pullBackTime);
        puller.start();
//        }
    }
    
    /**
     * Thread that pulls the winch back until the time elapses or the winch
     * limit switch is hit. A 0 or negative number means to go until the switch
     * is hit.
     */
    protected class WinchPuller extends Thread {

        int maxTime;
        long startTime;

        protected WinchPuller(int time) {
            startTime = System.currentTimeMillis();
            maxTime = time;
        }

        public void run() {
            while (!Sensors.getWinchLimitSwitch().get()
                    // If maxTime is less than 0 this part will always return true ->
                    && (System.currentTimeMillis() - startTime < maxTime || maxTime <= 0)) {
                motor.set(RobotConstants.WINCH_POWER);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            motor.set(0);
        }
    }
    
}
