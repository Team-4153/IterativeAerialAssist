/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team4153.systems;

import com.team4153.RobotConstants;
import com.team4153.RobotMap;
import com.team4153.Sensors;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author 4153student
 */
public class Arm implements Systems {

    private CANJaguar leftMotor;
    private CANJaguar rightMotor;
    private double desiredAngle;
    private boolean stopped = true;
    private double stoppedAt = 1.4;

    /**
     *
     */
    public Arm() {
        try {
            leftMotor = new CANJaguar(RobotMap.JAG_ARM_LEFT, CANJaguar.ControlMode.kPercentVbus);
            rightMotor = new CANJaguar(RobotMap.JAG_ARM_RIGHT, CANJaguar.ControlMode.kPercentVbus);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
//        desiredAngle = 90; //We start the game with the arm to the top
    }

    /**
     *
     * @param angle
     */
    public void moveArmTowardLocation(double angle) {
        desiredAngle = angle;
        if (Math.abs(Sensors.getStringPotAngle() - desiredAngle) >= RobotConstants.ARM_TOLERANCE) {
            System.out.println("Moving Arm to angle "
                    + (Sensors.getStringPotAngle()));
            moveArm(((desiredAngle - Sensors.getStringPotAngle())));
            System.out.println("Power: " + ((desiredAngle
                    - Sensors.getStringPotAngle())));
        } else {
            moveArm(0);
        }
    }

    /**
     *
     */
    public void execute(int buttonNumber) {
        Joystick joystick = Sensors.getManipulatorJoystick();
        double joystickAxis = joystick.getAxis(AxisType.kY) * 3 / 5;
        SmartDashboard.putNumber("Arm Angle: ", Sensors.getStringPotAngle());
        moveArm(joystickAxis);
        if(stopped){
            moveArmTowardLocation(stoppedAt);
        }
    }

    /**
     * The command to drive mecanum via joystick and gyro angle
     *
     * @param power The power to move the arm with
     *
     * @return Whether the arm was within the limits (moved successfully)
     */
    public boolean moveArm(double power) {
        if ((power > 0 && Sensors.getStringPotAngle() < RobotConstants.BACK_ARM_LIMIT)
                || (power < 0 && Sensors.getStringPotAngle() > RobotConstants.FORWARD_ARM_LIMIT)) {
            try {
                rightMotor.setX(power);
                leftMotor.setX(-power);
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
            stopped = false;
            return true;
        } else {
            if(!stopped){
                stopped = true;
                stoppedAt = Sensors.getStringPotAngle();
            }
            return false;
        }

    }

    /**
     *
     * @return
     */
    public double getDesiredAngle() {
        return desiredAngle;
    }

    /**
     *
     * @param angle
     */
    public void setDesiredAngle(double angle) {
        desiredAngle = angle;
    }

    /**
     * This thread should no longer be used - Calling multiple instances of it
     * may be causing bugs
     */
    protected class AutoArmThread extends Thread {

        /**
         *
         */
        public void run() {
            while (Math.abs(Sensors.getStringPotAngle() - desiredAngle) >= RobotConstants.ARM_TOLERANCE) {
                System.out.println("Moving Arm to angle "
                        + (Sensors.getStringPotAngle() - desiredAngle));
                moveArm(-((desiredAngle - Sensors.getStringPotAngle())));
                System.out.println("Power: " + ((desiredAngle
                        - Sensors.getStringPotAngle()) / (RobotConstants.ARM_MOTION_RANGE * 0.5)));
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            moveArm(0);

        }
    }

}
