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
    public static final double ARM_TOLERANCE = 0.07;
    /**
     * The angle difference when the motor starts slowing down
     */
    public static final double SLOW_DOWN_ANGLE = 10;

    /**
     *
     */
    public static final double ARM_MOTOR_MAX_POWER = 1;

    /**
     *
     */
    public static final double ARM_MOTION_RANGE = FORWARD_ARM_LIMIT+0*MIDDLE_ARM_LIMIT-BACK_ARM_LIMIT ;
    
    public static final int JITTER_DELAY=30;
    
    /**
     * The maximum power the drive will use.
     */
    public static final int DRIVE_POWER = 250;
    
    public static final double DISTANCE_THRESHHOLD = 26.0;
}