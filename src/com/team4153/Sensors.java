package com.team4153;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public abstract class Sensors {

    private static Joystick joystick;
    private static Button triggerButton;
    private static Button gyroReset;
    private static Button flipperButton;
    private static Gyro gyro;
    private static AnalogChannel ultrasonic;
    public  static AnalogChannel rotPot;
    public  static DigitalInput leftFlipper;
    public static final double VOLTAGE_FACTOR=0.0098;
    
    static {
        getGyro();
    }
    
    public Sensors() {
        
    }
    
    public static Joystick getJoystick() {
        if(joystick == null){
            joystick = new Joystick(RobotMap.JOYSTICK_PORT);
        }
        return joystick;
    }

//    public static Button getTriggerButton() {
//        if(triggerButton == null){
//            triggerButton = new JoystickButton(joystick, RobotMap.JSBUTTON_TRIGGER);
//        }
//        return triggerButton;
//    }
//    
//    public static Button getGyroResetButton() {
//        if(gyroReset == null){
//            gyroReset = new JoystickButton(joystick,RobotMap.JSBUTTON_GYRO_RESET);
//        }
//        System.out.println(gyroReset);
//        return gyroReset;
//    }
    
//    public static Button getFlippersButton(){
//        if(flipperButton == null){
//            flipperButton = new JoystickButton(joystick,RobotMap.JSBUTTON_FLIPPERS);
//        }
//        return flipperButton;
//    }
    
    public static Gyro getGyro() {
        if(gyro == null){
            gyro = new Gyro(RobotMap.GYRO_CHANNEL);
        }
        return gyro;
    }
    
    public static AnalogChannel getUltrasonic() {
        if (ultrasonic== null) {
            ultrasonic = new AnalogChannel (RobotMap.ULTRASONIC_CHANNEL);
        }
        return ultrasonic;
    }
    
    public static double getUltrasonicDistance(){
        return getUltrasonic().getVoltage()/VOLTAGE_FACTOR;
    }
    
    public static AnalogChannel getRotPot(){
        if (rotPot== null) {
            rotPot = new AnalogChannel (RobotMap.ROT_POT_CHANNEL);
        }
        return rotPot;
    }
    
    public static double getRotPotAngle(){
        return getRotPot().getVoltage();
    }
    
    public static DigitalInput getleftFlipper(){
        if (leftFlipper== null) {
            leftFlipper = new DigitalInput (RobotMap.MRS_LEFT);
        }
        return leftFlipper;
    }
    
    public static boolean areFlippersOpen(){
        return !getleftFlipper().get();
    }
}
