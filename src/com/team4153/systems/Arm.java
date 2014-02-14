/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team4153.systems;

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

    /**
     *
     */
    public static final double UPPER_LIMIT = 4;

    /**
     *
     */
    public static final double LOWER_LIMIT = 1;

    /**
     *
     */
    public static final double TOLERANCE = 1;//The tolerance in the + and - direction

    /**
     *
     */
    public static final double SLOW_DOWN_ANGLE = 10;//The angle difference when the motor starts slowing down

    /**
     *
     */
    public static final double MOTOR_MAX_POWER = 1;

    /**
     *
     */
    public static final double MOTION_RANGE = UPPER_LIMIT-LOWER_LIMIT;
    private CANJaguar leftMotor;
    private CANJaguar rightMotor;
    private double desiredAngle;

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
    public void moveArmToLocation(double angle){
        desiredAngle = angle;
        AutoArmThread autoArmThread = new AutoArmThread();
        autoArmThread.start();
    }

    /**
     *
     */
    public void execute() {
        Joystick joystick = Sensors.getManipulatorJoystick();
        double joystickAxis = joystick.getAxis(AxisType.kY)*3/5;
        SmartDashboard.putNumber("Arm Angle: ", Sensors.getRotPotAngle());
        moveArm(joystickAxis);
    }
    
    /**
     * The command to drive mecanum via joystick and gyro angle
     *
     * @param power The power to move the arm with
     * 
     * @return Whether the arm was within the limits (moved successfully)
     */
    public boolean moveArm(double power){
        if((power > 0 && Sensors.getRotPotAngle() < UPPER_LIMIT ) || 
                (power < 0 && Sensors.getRotPotAngle() > LOWER_LIMIT) ){
            try {
                rightMotor.setX(power);
                leftMotor.setX(-power);
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
            return true;
        } else {
            try {
                rightMotor.setX(0);
                leftMotor.setX(0);
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
            return false;
        }
        
    }
    
    /**
     *
     * @return
     */
    public double getDesiredAngle(){
        return desiredAngle;
    }
    
    /**
     *
     * @param angle
     */
    public void setDesiredAngle(double angle){
        desiredAngle=angle;
    }
    
    
    /**
     *
     */
    protected class AutoArmThread extends Thread{

        /**
         *
         */
        public void run(){
            while (Math.abs(Sensors.getRotPotAngle()-desiredAngle) >= TOLERANCE){
                System.out.println("Moving Arm to angle " +
                        (Sensors.getRotPotAngle()-desiredAngle));
                boolean success = moveArm(( (  desiredAngle-
                        Sensors.getRotPotAngle()  )/(MOTION_RANGE*0.5) ));
                System.out.println("Power: " +( (  desiredAngle-
                     Sensors.getRotPotAngle()  )/(MOTION_RANGE*0.5) ));
                
            }
            moveArm(0);
            
        }
    }

}
