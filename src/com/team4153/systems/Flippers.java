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
        leftOpen = new Solenoid(RobotMap.LEFT_GRAB_OPEN);
        rightOpen = new Solenoid(RobotMap.RIGHT_GRAB_OPEN);
        leftClose = new Solenoid(RobotMap.LEFT_GRAB_CLOSE);
        rightClose = new Solenoid(RobotMap.RIGHT_GRAB_CLOSE);
    }

    /**
     *
     */
    public void execute(int buttonNumber) {
        FlipperThread flipperThread = new FlipperThread(buttonNumber);
        flipperThread.start();
    }

    public void toggle() {
        if (open) {
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

    public void open() {
        leftOpen.set(true);
        leftClose.set(false);
        rightOpen.set(true);
        rightClose.set(false);
        open = true;
    }

    public void close() {
        leftOpen.set(false);
        leftClose.set(true);
        rightOpen.set(false);
        rightClose.set(true);
        open = false;
    }

    /**
     *
     */
    protected class FlipperThread extends Thread {

        int buttonNumber;

        protected FlipperThread(int buttonNumber) {
            this.buttonNumber = buttonNumber;
        }

        /**
         *
         */
        public void run() {
            if (open) {
                if (buttonNumber == RobotMap.JSBUTTON_FLIPPERS_GRAB) {
                    while (!Sensors.getPhotoEyeRight().get() && !Sensors.getPhotoEyeLeft().get() && Sensors.getManipulatorJoystick().getRawButton(RobotMap.JSBUTTON_FLIPPERS_GRAB)) {
                        System.out.println("Waiting for photo eye");
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                    if (Sensors.getManipulatorJoystick().getRawButton(RobotMap.JSBUTTON_FLIPPERS_GRAB)) {
                        leftOpen.set(false);
                        leftClose.set(true);
                        rightOpen.set(false);
                        rightClose.set(true);
                        open = false;
                    }
                }else if (buttonNumber == RobotMap.JSBUTTON_FLIPPERS_CATCH) {
                    while (!Sensors.getPhotoEyeLeftFar().get() && !Sensors.getPhotoEyeRightFar().get() && Sensors.getManipulatorJoystick().getRawButton(RobotMap.JSBUTTON_FLIPPERS_CATCH)) {
                        System.out.println("Waiting for photo eye");
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }

                    if (Sensors.getManipulatorJoystick().getRawButton(RobotMap.JSBUTTON_FLIPPERS_CATCH)) {
                        leftOpen.set(false);
                        leftClose.set(true);
                        rightOpen.set(false);
                        rightClose.set(true);
                        open = false;
                    }
                } else {
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
