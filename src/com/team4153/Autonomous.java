/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team4153;

import com.team4153.systems.Arm;
import com.team4153.systems.Chassis;
import com.team4153.systems.DistanceAngleTable;
import com.team4153.systems.Flippers;
import com.team4153.systems.Shooter;
import com.team4153.systems.Vision;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Developer
 */
public class Autonomous {

    Chassis chassis;
    DistanceAngleTable angleTable;
    Vision vision;
    Shooter shooter;
    Arm arm;
    Flippers flippers;

    private static long autoStartTime = -1;
    boolean autoHot = false;
    boolean autoTarget = false;
    boolean autoShot = false;

    int counter = 0;
    boolean withinFiringDistance = false;
    boolean withinSlowdownDistance = false;

    public Autonomous(Chassis chassis, Shooter shooter, Arm arm, Flippers flippers) {
        this.chassis = chassis;
        this.shooter = shooter;
        this.arm = arm;
        this.flippers=flippers;
        vision = new Vision();
        angleTable = new DistanceAngleTable(arm);
    }
    public void shootHighGoal() {
        try {
            System.out.println("Arbitrary Drive value: " + chassis.rightFront.getX());
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }

        //note this line both performs the lookup and moves the arm.
        angleTable.execute(-1);

        double distance = Sensors.getFilteredUltrasonicDistance();
        

        // if the robot is already withing the firing distance we do not want to
        // run any of the movement code - this 'if' makes sure of that
        if (!withinFiringDistance) {
            System.out.println("distance " + distance);
            // if the robot has run longer than the exception stop time it sets up
            // as if it were within range - there is probably something wrong with
            // the ultrasonic
            if (distance >= RobotConstants.STOP_DISTANCE && getMatchTime() < RobotConstants.EXCEPTION_STOP_TIME) {
                if (distance >= RobotConstants.STOP_DISTANCE * RobotConstants.AUTONOMOUS_SLOWDOWN_PERCENT && !withinSlowdownDistance) {
                    chassis.driveForward(RobotConstants.MAX_AUTONOMOUS_SPEED);
                    System.out.println("Full Speed: " + distance);
                } else {
                    chassis.driveForward(RobotConstants.MAX_AUTONOMOUS_SPEED * RobotConstants.AUTONOMOUS_SLOWDOWN_AMOUNT);
                    System.out.println("Slow Down Speed: " + distance);
                    withinSlowdownDistance = true;
                }
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
        if (!autoTarget) {
            vision.execute(-1);
            if (vision.isTarget()) {
                autoTarget = true;
                autoHot = vision.isHot();
                System.out.println("Target found, Hot: " + autoHot);
            }
        }

        // this will run when the arm is in position and we are in range
        if (withinFiringDistance && Math.abs(RobotConstants.SHOOTING_ANGLE - arm.getDesiredAngle()) < RobotConstants.ARM_TOLERANCE) {
            /*vision.execute(-1);
             SmartDashboard.putBoolean("Target: ", vision.isTarget());
             SmartDashboard.putBoolean("Hot: ", vision.isHot());
             if (vision.isTarget() && vision.isHot() && !autoShot) {
             shooter.execute(-1);
             autoShot = true;
             }*/

            if (!autoShot && autoTarget && (autoHot && getMatchTime() < 5000 || !autoHot)) {
                shooter.execute(-1);
                autoShot = true;
            }

        }

        if (getMatchTime() > RobotConstants.EXCEPTION_FIRE_TIME && !autoShot) {
            shooter.execute(-1);
            autoShot = true;
        }
    }

    public void dropInLowGoal() {
        double distance=Sensors.getFilteredUltrasonicDistance();
        arm.moveArmTowardLocation(RobotConstants.DROPPING_ANGLE);
        if(distance>RobotConstants.ULTRASONIC_DISPLACEMENT){
            chassis.driveForward(RobotConstants.MAX_AUTONOMOUS_SPEED);
        }
        else{
            if(!autoShot){
                flippers.execute(-1);
            }
        }
    }

    public void resetAuto() {
        autoStartTime = -1;
        autoHot = false;
        autoTarget = false;
        autoShot = false;
        withinFiringDistance = false;
        counter = 0;

    }

    public static long getMatchTime() {
        return System.currentTimeMillis() - autoStartTime;
    }

    public void init() {
        resetAuto();
        autoStartTime = System.currentTimeMillis();
        angleTable.execute(-1);
    }

}
