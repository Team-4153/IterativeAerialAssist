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

    private static Joystick driverJoystick;
    private static Joystick manipulatorJoystick;
    private static Button triggerButton;
    private static Button gyroReset;
    private static Button flipperButton;
    private static Gyro gyro;
    private static AnalogChannel ultrasonic;
    private static AnalogChannel rotPot;
    private static DigitalInput leftFlipper;
    private static DigitalInput limitSwitch1;//TODO:Rename
    private static DigitalInput limitSwitch2;
    private static DigitalInput limitSwitch3;
    private static final double RANGE_FINDER_MUlTIPLIER = 0.0098;
    public static final double ROT_POT_MAX_VOLTS=5;

    static {
        getGyro();
    }

    public Sensors() {

    }

    public static Joystick getDriverJoystick() {
        if (driverJoystick == null) {
            driverJoystick = new Joystick(RobotMap.DRIVER_JOYSTICK_PORT);
        }
        return driverJoystick;
    }
    
    public static Joystick getManipulatorJoystick() {
        if (manipulatorJoystick == null) {
            manipulatorJoystick = new Joystick(RobotMap.MANIPULATOR_JOYSTICK_PORT);
        }
        return manipulatorJoystick;
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
        if (gyro == null) {
            gyro = new Gyro(RobotMap.GYRO_CHANNEL);
        }
        return gyro;
    }

    public static AnalogChannel getUltrasonic() {
        if (ultrasonic == null) {
            ultrasonic = new AnalogChannel(RobotMap.ULTRASONIC_CHANNEL);
        }
        return ultrasonic;
    }

    public static double getUltrasonicDistance() {
        return getUltrasonic().getVoltage() / RANGE_FINDER_MUlTIPLIER;
    }

    public static AnalogChannel getRotPot() {
        if (rotPot == null) {
            rotPot = new AnalogChannel(RobotMap.ROT_POT_CHANNEL);
        }
        return rotPot;
    }

    public static double getRotPotAngle() {
        return getRotPot().getVoltage()/ROT_POT_MAX_VOLTS*360;
    }

    public static DigitalInput getleftFlipper() {
        if (leftFlipper == null) {
            leftFlipper = new DigitalInput(RobotMap.MRS_LEFT);
        }
        return leftFlipper;
    }

    public static boolean areFlippersOpen() {
        return !getleftFlipper().get();
    }

    //Returns true if the switch is OPEN!!
    public static DigitalInput getLimitSwitch1() {
        if (limitSwitch1 == null) {
            limitSwitch1 = new DigitalInput(RobotMap.LIMIT_SWITCH_1);
        }
        return limitSwitch1;
    }

    public static DigitalInput getLimitSwitch2() {
        if (limitSwitch2 == null) {
            limitSwitch2 = new DigitalInput(RobotMap.LIMIT_SWITCH_2);
        }
        return limitSwitch2;
    }

    public static DigitalInput getLimitSwitch3() {
        if (limitSwitch3 == null) {
            limitSwitch3 = new DigitalInput(RobotMap.LIMIT_SWITCH_3);
        }
        return limitSwitch3;
    }

}
