/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package com.team4153;

import com.team4153.systems.Chassis;
import com.team4153.systems.DashboardCommunication;
import com.team4153.systems.JoystickHandler;
import com.team4153.systems.Shooter;
import com.team4153.systems.Vision;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotMain extends IterativeRobot {
    public static double FIRE_DISTANCE = 30;

    JoystickHandler joystick;
    Chassis chassis;
    DashboardCommunication dashboardComm;
    Vision vision;
    Compressor compressor;
    Shooter shooter;

    long autoStartTime = -1;
    boolean autoHot = false;
    boolean autoTarget = false;
    boolean autoShoot = false;
    boolean autoDriveDone = false;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        chassis = new Chassis();
        shooter = new Shooter();
        joystick = new JoystickHandler(shooter);
        dashboardComm = new DashboardCommunication(chassis);
        vision = new Vision();
        startCompressor();
        Sensors.getGyro().getAngle();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {//Needs Testing
//        startCompressor();
        if (autoStartTime == -1) {
            autoStartTime = System.currentTimeMillis();
            System.out.println("Drive Start");
        }

        
        if (Sensors.getUltrasonicDistance() > FIRE_DISTANCE) {//TODO:Replace with distance sensor
            //TODO:include chassis
        }
        else if(!autoDriveDone){
            System.out.println("Drive Stop");
             autoDriveDone = true;
        }

        if(autoDriveDone){
            vision.execute();
            if(vision.isTarget()&&vision.isHot()&&!autoShoot){
                shooter.execute();
                autoShoot=true;
            }
        }
        
        if(System.currentTimeMillis()-autoStartTime>9000&&!autoShoot){
            shooter.execute();
            autoShoot=true;
        }
    }

    public void autonomousInit(){
        resetAuto();
    }
    
    public void resetAuto() {
        autoStartTime = -1;
        autoHot = false;
        autoTarget = false;
        autoShoot = false;
        autoDriveDone = false;
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
//        startCompressor();
        dashboardComm.execute();
        chassis.execute();
        joystick.execute();
        SmartDashboard.putNumber("Distance", Sensors.getUltrasonicDistance());
        SmartDashboard.putNumber("Rot Pot", Sensors.getRotPotAngle());
        SmartDashboard.putBoolean("Flippers Open:", Sensors.areFlippersOpen());
        vision.execute();
        SmartDashboard.putNumber("Vision Distance",vision.getDistance());
        SmartDashboard.putBoolean("hot", vision.isHot());
        SmartDashboard.putBoolean("target", vision.isTarget());
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {

    }

    public void startCompressor() {
        if (compressor == null) {
            compressor = new Compressor(RobotMap.PRESSURE_SWITCH, RobotMap.COMPRESSOR_CHANNEL);
            compressor.start();
        }
    }
}
