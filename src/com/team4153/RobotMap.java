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
    
    // ********** NOTES!! *************
    // Do not use autoswitch 1 or channel 2
        
    public static final int DRIVER_JOYSTICK_PORT = 2;
    public static final int MANIPULATOR_JOYSTICK_PORT = 1;
    
    public static final int JSBUTTON_GYRO_RESET = 2;
    public static final int JSBUTTON_JAG_RESET=10;
    public static final int JSAXIS_THROTTLE = 4;
    public static final int JSBUTTON_DISABLE_ENCODERS=11;
    public static final int JSBUTTON_DRIVE_STRAIGHT=9;
    public static final int JSBUTTON_STOP_IN_RANGE=3;
    
    
    public static final int JSBUTTON_TRIGGER = 1;
    public static final int JSBUTTON_FLIPPERS_GRAB=3;
    public static final int JSBUTTON_FLIPPERS_CATCH = 8;
    public static final int JSBUTTON_AUTO_AIM=4;
    public static final int JSBUTTON_AUTO_AIM_UP=11;
    public static final int JSBUTTON_AUTO_AIM_DOWN=10;
    public static final int JSBUTTON_FORCE_FLIPPERS_TOGGLE=5;
    public static final int JSBUTTON_WINCH_HALF=6;
    public static final int JSBUTTON_WINCH_FULL=7;
    public static final int JSBUTTON_JITTER=9;
    public static final int JSBUTTON_GO_TO_PICKUP = 2;
    public static final int JSBUTTON_GO_TO_TRUSS = 6;
    public static final int JSAXIS_ARM=1;
    
    public static final int JAG_LEFT_FRONT_MOTOR = 10;
    public static final int JAG_LEFT_REAR_MOTOR = 4;
    public static final int JAG_RIGHT_FRONT_MOTOR = 12;
    public static final int JAG_RIGHT_REAR_MOTOR = 14;
    public static final int VICTOR_CHANNEL=1;
    public static final int JAG_ARM_LEFT=13;
    public static final int JAG_ARM_RIGHT=11;
    
    public static final int GYRO_CHANNEL = 1;
    public static final int ULTRASONIC_CHANNEL = 5;
    public static final int ULTRASONIC2_CHANNEL = 7;
    public static final int STRING_POT_CHANNEL=3;

    public static final int COMPRESSOR_CHANNEL=1;
    public static final int PRESSURE_SWITCH=14;
    
    public static final int LEFT_GRAB_OPEN = 1;
    public static final int LEFT_GRAB_CLOSE = 2;
    public static final int RIGHT_GRAB_OPEN = 5;
    public static final int RIGHT_GRAB_CLOSE =6;
    public static final int WINCH_LOCK =3;
    public static final int WINCH_RELEASE = 4;
    
//    public static final int MRS_LEFT=10;
    
    
    public static final int WINCH_LIMIT_SWITCH = 11;
    public static final int LIMIT_SWITCH_2 = 12;
    public static final int LIMIT_SWITCH_3 = 13;
    public static final int AUTO_SWITCH_3 = 4;
    public static final int AUTO_SWITCH2 = 3;
//    public static final int AUTO_SWITCH1 = 12;
    public static final int BACKING_SWITCH = 1;

   
    
    public static final int PHOTO_EYE_LEFT_CLOSE = 10;
    public static final int PHOTO_EYE_LEFT_FAR = 9;
    public static final int PHOTO_EYE_RIGHT_CLOSE = 8; //TODO: Fix the numbers
    public static final int PHOTO_EYE_RIGHT_FAR = 6;
    
}
