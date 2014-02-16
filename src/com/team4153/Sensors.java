package com.team4153;

import com.team4153.systems.FilteredUltrasonic;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public abstract class Sensors {

    private static Joystick joystick;
    private static Button triggerButton;
    private static Button gyroReset;
    private static Gyro gyro;
    private static AnalogChannel ultrasonic;
    private static FilteredUltrasonic filteredUltrasonic;
    private static final double RANGE_FINDER_MUlTIPLIER = .0124;

    public Sensors() {

    }
    
    public static void resetUltrasonicFilter(){
        filteredUltrasonic = null;
    }

    public static Joystick getJoystick() {
        if (joystick == null) {
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
    public static Gyro getGyro() {
        if (gyro == null) {
            gyro = new Gyro(RobotMap.GYRO_CHANNEL);
        }
        return gyro;
    }

    public static AnalogChannel getUltrasonic() {
        if (ultrasonic == null) {
            ultrasonic = new AnalogChannel(5);
        }
        return ultrasonic;
    }

    public static double getUltrasonicDistance() {
        return getUltrasonic().getVoltage() / RANGE_FINDER_MUlTIPLIER;
    }

    public static double getFilteredUltrasonicDistance() {
        if (filteredUltrasonic == null) {
            filteredUltrasonic = new FilteredUltrasonic();
        }
        return filteredUltrasonic.getFilteredDistance();

    }

}
