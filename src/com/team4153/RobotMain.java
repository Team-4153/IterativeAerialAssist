/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package com.team4153;

import com.team4153.systems.Arm;
import com.team4153.systems.Chassis;
import com.team4153.systems.DashboardCommunication;
import com.team4153.systems.DistanceAngleTable;
import com.team4153.systems.Flippers;
import com.team4153.systems.ImageStorer;
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

    /**
     * The ideal angle to place the arm at to fire at the fire distance
     */
    public static double SHOOTING_ANGLE = 2.5;

    JoystickHandler joystick;
    Chassis chassis;
    DashboardCommunication dashboardComm;
    Vision vision;
    Compressor compressor;
    Shooter shooter;
    Flippers flippers;
    Arm arm;
    DistanceAngleTable angleTable;
    ImageStorer storer;

    private static long autoStartTime = -1;
    boolean autoHot = false;
    boolean autoTarget = false;
    boolean autoShot = false;

    private final double FIRE_DISTANCE = 120;
    private final double MAX_AUTONOMOUS_SPEED = 200;

    public static final int OVERSHOOT_CORRECTION = 6;
    public static final int ULTRASONIC_DISPLACEMENT = 5;
    public static final double AUTONOMOUS_SLOWDOWN_AMOUNT = 0.4;
    public static final double AUTONOMOUS_SLOWDOWN_PERCENT = 1.35;
    public static final int EXCEPTION_FIRE_TIME = 9000;
    public static final int EXCEPTION_STOP_TIME = 5000;

    int counter = 0;
    boolean withinFiringDistance = false;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        chassis = new Chassis();
        arm = new Arm();
        flippers = new Flippers();
        shooter = new Shooter(flippers);
        joystick = new JoystickHandler(shooter, flippers, arm);
        dashboardComm = new DashboardCommunication(chassis);
        angleTable = new DistanceAngleTable(arm);
        vision = new Vision();
        storer = new ImageStorer(vision.getCamera());
        storer.start();
        startCompressor();
        Sensors.getGyro().getAngle();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        /*if (autoStartTime == -1) {
         autoStartTime = System.currentTimeMillis();
         System.out.println("Drive Start");
         }

        
        
         if (Sensors.getUltrasonicDistance() > FIRE_DISTANCE) {
         chassis.driveForward();
         }
         else if(!autoDriveDone){
         chassis.driveHalt();
         System.out.println("Drive Stop");
         autoDriveDone = true;
         }*/

        //note this line both performs the lookup and moves the arm.
        angleTable.execute();

        SmartDashboard.putNumber("Ultrasonic not multiplies", Sensors.getUltrasonic().getVoltage());
        SmartDashboard.putNumber("Ultrasonic mulitplied", Sensors.getUltrasonicDistance());
        double distance = Sensors.getUltrasonicDistance();
        final double fireDistance = FIRE_DISTANCE + ULTRASONIC_DISPLACEMENT + OVERSHOOT_CORRECTION;
        
        // if the robot is already withing the firing distance we do not want to
        // run any of the movement code - this 'if' makes sure of that
        if (!withinFiringDistance) {
            
            // if the robot has run longer than the exception stop time it sets up
            // as if it were within range - there is probably something wrong with
            // the ultrasonic
            if (distance >= fireDistance && getMatchTime() < EXCEPTION_STOP_TIME) {
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

        /*if (withinFiringDistance) {
         chassis.turn(90);
         }*/
        
        // this will run when the arm is in position and we are in range
        if (withinFiringDistance && Math.abs(SHOOTING_ANGLE - arm.getDesiredAngle()) < Arm.TOLERANCE) {
            vision.execute();
            SmartDashboard.putBoolean("Target: ", vision.isTarget());
            SmartDashboard.putBoolean("Hot: ", vision.isHot());
            if (vision.isTarget() && vision.isHot() && !autoShot) {
                shooter.execute();
                autoShot = true;
            }
        }

        if (getMatchTime() > EXCEPTION_FIRE_TIME && !autoShot) {
            shooter.execute();
            autoShot = true;
        }
    }

    /**
     * Run once at the beginning of autonomous mode - resets and moves arm to
     * shooting angle.
     */
    public void autonomousInit() {
        resetAuto();
        autoStartTime = System.currentTimeMillis();
        angleTable.execute();
    }

    /**
     * Resets variables used in autonomous.
     */
    public void resetAuto() {
        autoStartTime = -1;
        autoHot = false;
        autoTarget = false;
        autoShot = false;
        withinFiringDistance = false;
        counter = 0;
    }
    
    public static long getMatchTime (){
        return System.currentTimeMillis() - autoStartTime;
    }

    /**
     * This function is called periodically during operator control.
     */
    public void teleopPeriodic() {
//        startCompressor();
        dashboardComm.execute();
        chassis.execute();
        arm.execute();
        joystick.execute();
        SmartDashboard.putNumber("Distance", Sensors.getUltrasonicDistance());
        SmartDashboard.putNumber("Rot Pot", Sensors.getRotPotAngle());
        SmartDashboard.putBoolean("Flippers Open:", Sensors.areFlippersOpen());
        SmartDashboard.putBoolean("LimitSwitch 1", Sensors.getWinchLimitSwitch().get());
        SmartDashboard.putBoolean("LimitSwitch 2", Sensors.getLimitSwitch2().get());
        SmartDashboard.putBoolean("LimitSwitch 3", Sensors.getLimitSwitch3().get());
        SmartDashboard.putNumber("Gyro", Sensors.getGyro().getAngle());
        SmartDashboard.putBoolean("Photo Eye", Sensors.getPhotoEye().get());
    }

    /**
     * This function is called periodically during test mode.
     */
    public void testPeriodic() {

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
