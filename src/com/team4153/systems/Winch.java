package com.team4153.systems;

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
    public static final double WINCH_POWER = .3;
    
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
     *  Sets the amount of time (in milliseconds) the winch will be pulled back if executed.
     * 0 or less means go until limit switch is hit.
     */
    public void setPullBackTime(int pullBackTime){
        this.pullBackTime = pullBackTime;
    }

    public void execute(int buttonNumber) {
        WinchPuller puller = new WinchPuller(pullBackTime);
        puller.start();
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
                motor.set(WINCH_POWER);
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
