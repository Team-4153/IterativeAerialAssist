/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team4153.systems;

import com.team4153.RobotMap;
import com.team4153.Sensors;
import edu.wpi.first.wpilibj.Solenoid;

/**
 *
 * @author 4153student
 */
public class Flippers implements Systems {

    Solenoid leftOpen, rightOpen, leftClose, rightClose;
    boolean open = false;

    /**
     *
     */
    public Flippers() {
        leftOpen = new Solenoid(RobotMap.LEFT_FLIPPER_OPEN);
        rightOpen = new Solenoid(RobotMap.RIGHT_FLIPPER_OPEN);
        leftClose = new Solenoid(RobotMap.LEFT_FLIPPER_CLOSE);
        rightClose = new Solenoid(RobotMap.RIGHT_FLIPPER_CLOSE);
    }

    /**
     *
     */
    public void execute() {
        FlipperThread flipperThread = new FlipperThread();
        flipperThread.start();
    }
    
    public void jitter(){
        (new JitterThread()).start();
    }

    protected class JitterThread extends Thread{
        public void run(){
            if(open){
                leftOpen.set(false);
                leftClose.set(true);
                rightOpen.set(false);
                rightClose.set(true);
                open = false;
            } else {
            leftOpen.set(true);
            leftClose.set(false);
            rightOpen.set(true);
            rightClose.set(false);
            open = true;
        }
        }
    }
    
    /**
     *
     */
    protected class FlipperThread extends Thread{

        /**
         *
         */
        public void run() {
        if (open) {
            while (!Sensors.getPhotoEye().get() && Sensors.getManipulatorJoystick().getRawButton(RobotMap.JSBUTTON_FLIPPERS)) {
                System.out.println("Waiting for photo eye");
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            if (Sensors.getManipulatorJoystick().getRawButton(RobotMap.JSBUTTON_FLIPPERS)) {
                leftOpen.set(false);
                leftClose.set(true);
                rightOpen.set(false);
                rightClose.set(true);
                open = false;
            }
        } else {
            System.out.println("Opening");
            leftOpen.set(true);
            leftClose.set(false);
            rightOpen.set(true);
            rightClose.set(false);
            open = true;
        }
        
    }
    }

}
