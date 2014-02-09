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
    /**.15 is too low, .3 is pretty  fast*/
    public static final double WINCH_POWER = .3;
    /**shooter solenoid open */
    Solenoid open;
    /**shooter solenoid close*/
    Solenoid close;
    Victor motor;

    /**
     *
     */
    public Shooter() {
        open = new Solenoid(RobotMap.WINCH_LATCH);
        close = new Solenoid(RobotMap.WINCH_UNLATCH);
        motor = new Victor(RobotMap.VICTOR_CHANNEL);
        motor.setExpiration(2.0);
    }

    /**
     *
     */
    public void execute() {
        ShooterThread shooterThread = new ShooterThread();
        shooterThread.start();
    }

    /**
     *
     */
    protected class ShooterThread extends Thread {

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

            while (Sensors.getWinchLimitSwitch().get()) {
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
