 package com.team4153;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static final int LEFT_MOTOR = 1;
    // public static final int RIGHT_MOTOR = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static final int RANGE_FINDER_PORT = 1;
    // public static final int RANGE_FINDER_MODULE = 2;
    
        
    public static final int DRIVER_JOYSTICK_PORT = 2;
    public static final int MANIPULATOR_JOYSTICK_PORT = 1;
    public static final int JSBUTTON_TRIGGER = 1;
    public static final int JSBUTTON_GYRO_RESET = 2;
    public static final int JSBUTTON_FLIPPERS=3;
    public static final int JSBUTTON_JAG_RESET=10;
    public static final int JSBUTTON_AUTO_AIM=9;
    public static final int JSBUTTON_FORCE_FLIPPERS_TOGGLE=8;
    public static final int JSAXIS_THROTTLE = 4;
    public static final int JSAXIS_ARM=1;
    public static final int JSBUTTON_JITTER=9;
    
    public static final int JAG_LEFT_FRONT_MOTOR = 10;
    public static final int JAG_LEFT_REAR_MOTOR = 4;
    public static final int JAG_RIGHT_FRONT_MOTOR = 12;
    public static final int JAG_RIGHT_REAR_MOTOR = 14;
    public static final int VICTOR_CHANNEL=1;
    public static final int JAG_ARM_LEFT=13;
    public static final int JAG_ARM_RIGHT=11;
    
    public static final int GYRO_CHANNEL = 1;
    public static final int ULTRASONIC_CHANNEL = 2;
    public static final int ROT_POT_CHANNEL=3;

    public static final int COMPRESSOR_CHANNEL=1;
    public static final int PRESSURE_SWITCH=14;
    
    public static final int LEFT_FLIPPER_OPEN = 1;
    public static final int LEFT_FLIPPER_CLOSE =2;
    public static final int RIGHT_FLIPPER_OPEN = 5;
    public static final int RIGHT_FLIPPER_CLOSE =6;
    public static final int WINCH_LATCH =3;
    public static final int WINCH_UNLATCH = 4;
    
    public static final int MRS_LEFT=10;
    
    
    public static final int WINCH_LIMIT_SWITCH = 11;
    public static final int LIMIT_SWITCH_2 = 12;
    public static final int LIMIT_SWITCH_3 = 13;
    public static final int INIT_SWITCH = 1;
    
    public static final int PHOTO_EYE=8;
    
}
