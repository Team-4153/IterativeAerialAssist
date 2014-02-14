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

    /**
     * .15 is too low, .3 is pretty fast
     */
    public static final double WINCH_POWER = .3;
    /**
     * shooter solenoid open
     */
    Solenoid open;
    /**
     * shooter solenoid close
     */
    Solenoid close;
    Victor motor;

    /**
     * The length of time (in milliseconds) the winch will be pulled back; 0 or less means go
     * until limit.
     */
    private int defaultWinchPullbackTime = 0;

    Flippers flippers;//To open flippers when shoot

    /**
     *
     */
    public Shooter(Flippers flippers) {
        open = new Solenoid(RobotMap.WINCH_LATCH);
        close = new Solenoid(RobotMap.WINCH_UNLATCH);
        motor = new Victor(RobotMap.VICTOR_CHANNEL);
        motor.setExpiration(2.0);
        this.flippers = flippers;
    }

    /**
     * Sets the amount of time the winch pulls back after firing (0 or less means
     * until the limit is hit.
     * 
     * @param time in milliseconds.
     */
    public void setDefaultWinchPullbackTime(int time) {
        defaultWinchPullbackTime = time;
    }
    
    /**
     * pulls back the winch all the way until the limit is hit
     */
    public void pullBackWinch() {
        WinchPuller puller = new WinchPuller(0);
        puller.start();

    }

    /**
     * pulls back the winch until the specified time or the limit is hit.
     * @param time in milliseconds
     */
    public void pullBackWinch(int time) {
        WinchPuller puller = new WinchPuller(time);
        puller.start();
    }

    /**
     * Fires the shooter.
     */
    public void execute() {
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
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            //TODO: Add shooting code (motor, etc.) here, take out sleep statement
            open.set(false);
            close.set(true);

            if (Sensors.getleftFlipper().get()) {
                flippers.execute();
            }
            WinchPuller puller = new WinchPuller(defaultWinchPullbackTime);
            puller.start();
        }

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
            while (Sensors.getWinchLimitSwitch().get()
                    // If maxTime is less than 0 this part will always return true
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
