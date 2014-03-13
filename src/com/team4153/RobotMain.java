/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package com.team4153;

import com.team4153.systems.Arm;
import com.team4153.systems.Chassis;
import com.team4153.systems.DistanceAngleTable;
import com.team4153.systems.Flippers;
import com.team4153.util.ImageStorer;
import com.team4153.util.JoystickHandler;
import com.team4153.systems.Shooter;
import com.team4153.systems.Vision;
import com.team4153.systems.Winch;
import com.team4153.util.DashboardCommunication;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
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
    Vision vision;
    Compressor compressor;
    Shooter shooter;
    Flippers flippers;
    Arm arm;
    Winch winch;
    DistanceAngleTable angleTable;
    ImageStorer storer;
    Autonomous autonomous;

    boolean imageTaken=false;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        
        chassis = new Chassis();
        arm = new Arm();
        flippers = new Flippers();
        winch = new Winch();
        shooter = new Shooter(flippers, winch);
        angleTable = new DistanceAngleTable(arm);
        joystick = new JoystickHandler(shooter, flippers, arm, winch, chassis, angleTable);
        dashboardComm = new DashboardCommunication(chassis);
        vision = new Vision();
        storer = new ImageStorer(vision.getCamera());
        autonomous = new Autonomous(chassis, shooter, arm, flippers, angleTable);
        //storer.start();
        startCompressor();
        Sensors.getGyro().getAngle();
        dashboardComm.execute();
        flippers.close();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        if(!imageTaken){
            vision.saveImage();
            imageTaken=true;
        }
        dashboardComm.execute();
        //boolean as1=Sensors.getAutoSwitch1().get();
        boolean as2=Sensors.getAutoSwitch2().get();
        
            autonomous.shootHighGoal();
       
        
    }

    /**
     * Run once at the beginning of autonomous mode - resets and moves arm to
     * shooting angle.
     */
    public void autonomousInit() {
        autonomous.init();
        dashboardComm.execute();
    }

    /**
     * Resets variables used in autonomous.
     */
    

    /**
     * This function is called periodically during operator control.
     */
    public void teleopPeriodic() {
//        startCompressor();
        dashboardComm.execute();
        chassis.execute(-1);
        arm.execute(-1);
        joystick.execute();
        
    }

    /**
     * This function is called periodically during test mode.
     */
    public void testPeriodic() {

    }
    
    public void disabledInit() {
        Sensors.resetUltrasonicFilter();
        flippers.close();
    }

    /**
     * Initializes the compressor and starts the method that will run the
     * compressor.
     */
    public void startCompressor() {
        if (compressor == null) {
            compressor = new Compressor(RobotMap.PRESSURE_SWITCH, RobotMap.COMPRESSOR_CHANNEL);
            compressor.start();
        }
    }
}
