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
    private double joystickArmLocation = RobotConstants.MIDDLE_ARM_LIMIT;
//    private boolean stopped = true;
//    private double stoppedAt;

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
//        new RuntimeException("Arm Moved").printStackTrace();
        joystickArmLocation = angle;
        double offset = joystickArmLocation - Sensors.getStringPotAngle();
        if (Math.abs(offset) >= getTolerance()) {
            moveArm(Math.sqrt(Math.abs(offset)) * Chassis.getSign(offset));
            System.out.println("Angle: " + joystickArmLocation);
//            System.out.println("offset: " + offset);
//            System.out.println("Power: " + (Math.sqrt(Math.abs(offset)) * Chassis.getSign(offset)));
        } else {
            moveArm(0);
//            stoppedAt = desiredAngle;
        }
    }

    /**
     *
     */
    public void execute(int buttonNumber) {
        if (buttonNumber == RobotMap.JSBUTTON_GO_TO_PICKUP) {
            joystickArmLocation = RobotConstants.PICKUP_POSITION;
            return;
        }
        if (buttonNumber == RobotMap.JSBUTTON_AUTO_AIM) {
            autoAimArmLocation();
            return;
        }
        Joystick joystick = Sensors.getManipulatorJoystick();
        double joystickAxis = joystick.getAxis(AxisType.kY) * 3 / 5;
        if (Math.abs(joystickAxis) < 0.1) {
            joystickAxis = 0;
        }
        if ((joystickAxis > 0 && joystickArmLocation < RobotConstants.BACK_ARM_LIMIT)
                || (joystickAxis < 0 && joystickArmLocation > RobotConstants.FORWARD_ARM_LIMIT)) {
            joystickArmLocation += joystickAxis * RobotConstants.PROPORTIONAL_ARM_JOYSTICK_MULTIPLIER;
        }
        SmartDashboard.putNumber("Arm Angle: ", Sensors.getStringPotAngle());
        moveArmTowardLocation(joystickArmLocation);
    }

    public void autoAimArmLocation() {
        double newAngle = DistanceAngleTable.calculateAngle(Sensors.getSemifilteredUltrasonic());
        if((newAngle < RobotConstants.BACK_ARM_LIMIT) || (newAngle > RobotConstants.FORWARD_ARM_LIMIT)){
            joystickArmLocation = newAngle;
        }
    }

    public static double getTolerance() {
        if (Math.abs(Sensors.getManipulatorJoystick().getAxis(AxisType.kY) * 3 / 5) < 0.1) {
            return RobotConstants.ARM_TOLERANCE;
        } else {
            return 0;
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
//            stopped = false;
            return true;
        } else {
            try {
                rightMotor.setX(0);
                leftMotor.setX(0);
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
//            if(!stopped){
//                stopped = true;
//                stoppedAt = Sensors.getStringPotAngle();
//            }
            return false;
        }

    }

    /**
     *
     * @return
     */
    public double getDesiredAngle() {
        return joystickArmLocation;
    }

    /**
     *
     * @param angle
     */
    public void setDesiredAngle(double angle) {
        joystickArmLocation = angle;
    }
}
