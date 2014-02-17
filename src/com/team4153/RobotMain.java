/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package com.team4153;

import com.team4153.systems.Chassis;
import com.team4153.systems.DashboardCommunication;
import com.team4153.systems.FilteredUltrasonic;
import com.team4153.systems.JoystickHandler;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotMain extends IterativeRobot {

    JoystickHandler joystick;
    Chassis chassis;
    
    DashboardCommunication dashboardComm;
    private final double FIRE_DISTANCE = 120;
    private final double MAX_AUTONOMOUS_SPEED = 0.5;

    public static final int OVERSHOOT_CORRECTION = 6;
    public static final int ULTRASONIC_DISPLACEMENT = 5;
    public static final double AUTONOMOUS_SLOWDOWN_AMOUNT = 0.4;
    public static final double AUTONOMOUS_SLOWDOWN_PERCENT = 1.35;

    int counter = 0;
    boolean withinFiringDistance = false;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        chassis = new Chassis();
        joystick = new JoystickHandler();
        
        
        dashboardComm = new DashboardCommunication(chassis);
        Sensors.getGyro();
    }

    public void autonomousInit() {
        counter = 0;
        Sensors.getGyro().reset();

    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

        SmartDashboard.putNumber("Ultrasonic not multiplies", Sensors.getUltrasonic().getVoltage());
        SmartDashboard.putNumber("Ultrasonic mulitplied", Sensors.getUltrasonicDistance());
        double distance = Sensors.getUltrasonicDistance();
        final double fireDistance = FIRE_DISTANCE + ULTRASONIC_DISPLACEMENT + OVERSHOOT_CORRECTION;
        if (!withinFiringDistance) {
            if (distance >= fireDistance) {
                if (distance >= fireDistance * AUTONOMOUS_SLOWDOWN_PERCENT) {
                    chassis.driveForward(MAX_AUTONOMOUS_SPEED);
                    System.out.println("Full Speed: " + distance);
                } else {
                    chassis.driveForward(MAX_AUTONOMOUS_SPEED * AUTONOMOUS_SLOWDOWN_AMOUNT);
                    System.out.println("Slow Down Speed: " + distance);
                }
                //SmartDashboard.putNumber("Counter", counter++);
                SmartDashboard.putNumber("Ultrasonic Running", distance);
                counter = 0;
            } else {
                withinFiringDistance = true;
                System.out.println("Stopped: " + distance);
                chassis.driveHalt();
                if (counter++ == 0) {
                    SmartDashboard.putNumber("Ultrasonic Stop", distance);
                }
                //SmartDashboard.putNumber("Counter", counter);
            }
        }
        
        if (withinFiringDistance) {
            chassis.turn(90);
        }

    }
    
    public void disabledInit(){
        Sensors.resetUltrasonicFilter();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        dashboardComm.execute();
        chassis.execute();
        joystick.execute();
        SmartDashboard.putNumber("Gyro", Sensors.getGyro().getAngle());
        SmartDashboard.putNumber("Ultrasonic not multiplies", Sensors.getUltrasonic().getVoltage());
        SmartDashboard.putNumber("Ultrasonic mulitplied", Sensors.getUltrasonicDistance());
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {

    }

}
