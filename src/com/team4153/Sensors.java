package com.team4153;

import com.team4153.systems.FilteredUltrasonic;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public abstract class Sensors {

    /**
     * joystick used by the driver
     */
    private static Joystick driverJoystick;
    /**
     * joystick used for the ball manipulation
     */
    private static Joystick manipulatorJoystick;

    //private static Button triggerButton;
    //private static Button gyroReset;
    //private static Button flipperButton;
    private static Gyro gyro;
    private static AnalogChannel ultrasonic;
    private static AnalogChannel ultrasonic2;
    private static AnalogChannel stringPot;
    private static DigitalInput leftFlipper;
    private static FilteredUltrasonic filteredUltrasonic;

    /**
     * The limit switch the winch hits to stop the winch motor
     */
    private static DigitalInput winchLimitSwitch;

    /**
     * Currently unknown limit switch 2
     */
    private static DigitalInput limitSwitch2;//TODO:Rename
    /**
     * Currently unknown limit switch 3
     */
    private static DigitalInput limitSwitch3;//TODO:Rename
    
    private static DigitalInput autoSwitch1;
    private static DigitalInput autoSwitch2;

    private static DigitalInput photoEye;

    /**
     * Switch to indicate robot needs initial setup (arm within competition
     * bounds, compressor maybe on, etc.).
     */
    private static DigitalInput initSwitch;


    /**
     * The rotational potentiometer on the arm - goes from 0 to max volts
     */
    public static final double ROT_POT_MAX_VOLTS = 5;

    static {
        getGyro();
    }

    /**
     *
     */
    public Sensors() {

    }

    /**
     * Returns the driver's joystick if it exists. If it does not, initializes
     * the driver's joystick then returns it.
     *
     * @return The driver's joystick
     */
    public static Joystick getDriverJoystick() {
        if (driverJoystick == null) {
            driverJoystick = new Joystick(RobotMap.DRIVER_JOYSTICK_PORT);
        }
        return driverJoystick;
    }

    /**
     * Returns the manipulators's joystick if it exists. If it does not,
     * initializes the manipulators's joystick then returns it.
     *
     * @return The manipulators's joystick
     */
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
    
    /**
     * Returns the gyro if it exists. If it does not, initializes the gyro then
     * returns it.
     *
     * @return The gyro
     */
    public static Gyro getGyro() {
        if (gyro == null) {
            gyro = new Gyro(RobotMap.GYRO_CHANNEL);
        }
        return gyro;
    }

    /**
     * Returns the ultrasonic sensor if it exists. If it does not, initializes
     * the ultrasonic sensor then returns.
     *
     * @return The ultrasonic sensor
     */
    public static AnalogChannel getUltrasonic() {
        if (ultrasonic == null) {
            ultrasonic = new AnalogChannel(RobotMap.ULTRASONIC_CHANNEL);
        }
        return ultrasonic;
    }

    
     public static AnalogChannel getUltrasonic2() {
        if (ultrasonic2 == null) {
            ultrasonic2 = new AnalogChannel(RobotMap.ULTRASONIC2_CHANNEL);
        }
        return ultrasonic2;
    }
    /**
     *
     * @return The distance according to the ultrasonic sensor (in inches)
     */
    public static double getUltrasonicDistance() {
        return getUltrasonic().getVoltage() / RobotConstants.RANGE_FINDER_MUlTIPLIER;
    }
    
    public static double getUltrasonicDistance2() {
        return getUltrasonic().getVoltage() / RobotConstants.RANGE_FINDER_MUlTIPLIER2;
    }
    
    public static double getFilteredUltrasonicDistance(){
        if (filteredUltrasonic == null) {
            filteredUltrasonic = new FilteredUltrasonic();
        }
        return filteredUltrasonic.getFilteredDistance();
    }

    /**
     * Returns the arm's rotational potentiometer if it exists. If it does not,
     * initializes the arm's rotational potentiometer then returns.
     *
     * @return The arm's rotational potentiometer
     */
    public static AnalogChannel getStringPot() {
        if (stringPot == null) {
            stringPot = new AnalogChannel(RobotMap.STRING_POT_CHANNEL);
        }
        return stringPot;
    }

    /**
     * @return The rotation of the arm according to the arm's rotational
     * potentiometer (in a value between 0 and ROT_POT_MAX_VOLTS).
     */
    public static double getStringPotAngle() {
        // return getStringPot().getVoltage()/ROT_POT_MAX_VOLTS*360;
        return getStringPot().getVoltage();
    }

    /**
     * Returns the arm's left flipper if it exists. If it does not, initializes
     * the arm's left flipper then returns.
     *
     * @return The arm's left flipper
     */
    public static DigitalInput getleftFlipper() {
        if (leftFlipper == null) {
            leftFlipper = new DigitalInput(RobotMap.MRS_LEFT);
        }
        
        return leftFlipper;
    }

    /**
     *
     * @return
     */
    public static boolean areFlippersOpen() {
        return !getleftFlipper().get();
    }

    /**
     * Returns the winch limit switch if it exists. If it does not, initializes
     * the winch limit switch then returns it. NOTE: The input is true if the
     * switch is open.
     *
     * @return The winch limit switch
     */
    public static DigitalInput getWinchLimitSwitch() {
        if (winchLimitSwitch == null) {
            winchLimitSwitch = new DigitalInput(RobotMap.WINCH_LIMIT_SWITCH);
        }
        return winchLimitSwitch;
    }
    
    public static DigitalInput getAutoSwitch1() {
        if (autoSwitch1 == null) {
            autoSwitch1 = new DigitalInput(RobotMap.AUTO_SWITCH1);
        }
        return autoSwitch1;
    }
    
    public static DigitalInput getAutoSwitch2() {
        if (autoSwitch2 == null) {
            autoSwitch2 = new DigitalInput(RobotMap.AUTO_SWITCH2);
        }
        return autoSwitch2;
    }

    //TODO: Rename

    /**
     *
     * @return
     */
        public static DigitalInput getLimitSwitch2() {
        if (limitSwitch2 == null) {
            limitSwitch2 = new DigitalInput(RobotMap.LIMIT_SWITCH_2);
        }
        return limitSwitch2;
    }

    //TODO: Rename

    /**
     *
     * @return
     */
        public static DigitalInput getLimitSwitch3() {
        if (limitSwitch3 == null) {
            limitSwitch3 = new DigitalInput(RobotMap.LIMIT_SWITCH_3);
        }
        return limitSwitch3;
    }

    /**
     * Returns the photo eye if it exists. If it does not, initializes the photo
     * eye then returns it.
     *
     * @return The photo eye
     */
    public static DigitalInput getPhotoEye() {
        if (photoEye == null) {
            photoEye = new DigitalInput(RobotMap.PHOTO_EYE);
        }
        return photoEye;
    }

   

}
