/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team4153.systems;

import com.team4153.RobotMap;
import com.team4153.Sensors;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;

/**
 *
 * @author 4153student
 */
public class Shooter extends Thread implements Systems {

    public static final int DELAY=5;
    
    /**
     * shooter solenoid open
     */
    Solenoid open;
    /**
     * shooter solenoid close
     */
    Solenoid close;
    

    /**
     * The length of time (in milliseconds) the winch will be pulled back; 0 or
     * less means go until limit.
     */
//    private int defaultWinchPullbackTime = 1000;

//    private boolean pullBackByDefault = false;

    private Flippers flippers;//To open flippers when shoot
//    private Winch winch;

    /**
     *
     */
    public Shooter(Flippers flippers, Winch winch) {
        open = new Solenoid(RobotMap.WINCH_LOCK);
        close = new Solenoid(RobotMap.WINCH_RELEASE);
        this.flippers = flippers;
//        this.winch = winch;
        open.set(false);
        close.set(true);
    }

    /**
     * Sets the amount of time the winch pulls back after firing (0 or less
     * means until the limit is hit.
     *
     * @param time in milliseconds.
     */
//    public void setDefaultWinchPullbackTime(int time) {
//        defaultWinchPullbackTime = time;
//    }

    

    /**
     * Fires the shooter.
     */
    public void execute(int buttonNumber) {
        ShooterThread shooterThread = new ShooterThread(flippers);
        shooterThread.start();
    }

    /**
     *
     */
    protected class ShooterThread extends Thread {

       
       Flippers flippers;
        public ShooterThread(Flippers flippers) {
            this.flippers = flippers;
        }

        /**
         *
         */
        public void run() {
            open.set(true);
            close.set(false);
           try {
               Thread.sleep(DELAY);
           } catch (InterruptedException ex) {
               ex.printStackTrace();
           }
            flippers.open();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            //TODO: Add shooting code (motor, etc.) here, take out sleep statement
            open.set(false);
            close.set(true);

            
//            if (pullBackByDefault) {
//                winch.pullBackWinch(defaultWinchPullbackTime);
//            }
        }

    }

    
}
