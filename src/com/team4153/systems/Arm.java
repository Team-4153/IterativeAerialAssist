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

/**
 *
 * @author 4153student
 */
public class Arm implements Systems {

    public static final double DEGREE = 1;//The Degree the arm changed if the joystick is pushed forward
    public static final double TOLERANCE = 2.5;//The tolerance in the + and - direction
    public static final double SLOW_DOWN_ANGLE = 10;//The angle difference when the motor starts slowing down
    public static final double MOTOR_MAX_POWER = 1;

    private CANJaguar leftMotor;
    private CANJaguar rightMotor;
    private double desiredAngle;

    public Arm() {
        try {
            leftMotor = new CANJaguar(RobotMap.JAG_ARM_LEFT, CANJaguar.ControlMode.kPercentVbus);
            rightMotor = new CANJaguar(RobotMap.JAG_ARM_RIGHT, CANJaguar.ControlMode.kPercentVbus);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
        desiredAngle = 90; //We start the game with the arm to the top
    }

    public void execute() {
        Joystick joystick = Sensors.getManipulatorJoystick();
        double joystickAxis = joystick.getAxis(AxisType.kY);
        if (Math.abs(joystickAxis) > 0.2) {
            desiredAngle += joystickAxis * DEGREE;
            if (desiredAngle < 0) {
                desiredAngle = 0;
            }
            if (desiredAngle > 120) {
                desiredAngle = 120;
            }
        }

        if (Math.abs(desiredAngle - Sensors.getRotPotAngle()) > TOLERANCE) {
            double power = MOTOR_MAX_POWER * (desiredAngle - Sensors.getRotPotAngle()) / SLOW_DOWN_ANGLE;
            try {
                rightMotor.setX(power);
                leftMotor.setX(power);
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public double getDesiredAngle(){
        return desiredAngle;
    }
    
    public void setDesiredAngle(double angle){
        desiredAngle=angle;
    }

}
