/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team4153;

/**
 *
 * @author 4153student
 */
public class RobotConstants {
    public static final double FORWARD_ARM_LIMIT = 1.35;
    public static final double MIDDLE_ARM_LIMIT = 2.28;
    public static final double BACK_ARM_LIMIT = 2.9;
    public static final double WINCH_POWER = -0.5;
    public static final int WINCH_HALF_TIME = 1000;
    /**
     * The arm tolerance in the + and - direction.
     */
    public static final double ARM_TOLERANCE = 0.06;
    /**
     * The angle difference when the motor starts slowing down
     */
    public static final double SLOW_DOWN_ANGLE = 10;

    /**
     *
     */
    public static final double ARM_MOTOR_MAX_POWER = 1;
    
    public static final double ARM_POSITION_HOLD_POWER = 0.1;

    /**
     *
     */
    public static final double ARM_MOTION_RANGE = FORWARD_ARM_LIMIT+0*MIDDLE_ARM_LIMIT-BACK_ARM_LIMIT ;
    
    public static final int JITTER_OPEN_DELAY = 30;
    public static final int JITTER_CLOSE_DELAY = 50;
    
    /**
     * The maximum power the drive will use.f
     */
    public static final int DRIVE_POWER = 350;
    
    public static final double DISTANCE_THRESHHOLD = 50.0;
    
    public static final double RANGE_FINDER_MUlTIPLIER = 0.0124;
    
    public static final double RANGE_FINDER_MUlTIPLIER2 = 0.098;

    public static final double PICKUP_POSITION=1.4;
    public static final double AUTONOMOUS_SLOWDOWN_AMOUNT = 0.4;
    public static final int ULTRASONIC_DISPLACEMENT = 5;
    public static final int OVERSHOOT_CORRECTION = 6;
    public static final double AUTONOMOUS_SLOWDOWN_PERCENT = 1.35;
    public static final int EXCEPTION_STOP_TIME = 5000;
    public static final int EXCEPTION_FIRE_TIME = 9000;
    public final static double BASE_FIRE_DISTANCE = 120;
    public final static double MAX_AUTONOMOUS_SPEED = 1;
    public final static double DROPPING_ANGLE = 2;
    /**
     * The ideal angle to place the arm at to fire at the fire distance
     */
    public static double SHOOTING_ANGLE = 1.95;
    public static final double INITIAL_P = 0.3;
    public static final double INITIAL_I = 0.005;
    public static final double INITIAL_D = 0;
    public static final double[] ANGLES = {1.96, 1.95, 1.945, 1.94, 1.7};
    public static final double[] DISTANCES = {84, 96, 108, 120, 500};
    public static final int LR_SCORE_LIMIT = 50;
    public static final int ASPECT_RATIO_LIMIT = 55;
    public static final double VIEW_ANGLE = 49; //Axis M1013
    //final double VIEW_ANGLE = 41.7;		//Axis 206 camera
    //final double VIEW_ANGLE = 37.4;  //Axis M1011 camera
    public static final int VERTICAL_SCORE_LIMIT = 50;
    //Maximum number of particles to process
    public static final int MAX_PARTICLES = 8;
    //Score limits used for hot target determination
    public static final int TAPE_WIDTH_LIMIT = 50;
    //Camera constants used for distance calculation
    public static final int Y_IMAGE_RES = 480; //X Image resolution in pixels, should be 120, 240 or 480
    //Minimum area of particles to be considered
    public static final int AREA_MINIMUM = 150;
    //Score limits used for target identification
    public static final int RECTANGULARITY_LIMIT = 40;
    public static final int MAX_IMAGES = 1000;
    /**
     * The time the image storer waits between storing images.
     */
    public static final int IMAGE_DELAY = 1000;
    public static final double STOP_DISTANCE = RobotConstants.BASE_FIRE_DISTANCE + RobotConstants.ULTRASONIC_DISPLACEMENT + RobotConstants.OVERSHOOT_CORRECTION;
    
    /**
     * The number of old values the ultrasonic stores to average and compare new values to.
     */
    public static final int ULTRASONIC_FILTER_HISTORY_LENGTH = 5;
    /**
     * The maximum amount an ultrasonic value can vary from the average without being discarded.
     */
    public static final double ULTRASONIC_FILTER_DISTANCE_THRESHHOLD = 20;
    /**
     * The maximum number of ultrasonic values that will be discarded before the filter resets.
     */
    public static final int MAX_ULTRASONIC_FILTER_DISCARD = 5;
}
