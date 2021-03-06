package com.team4153.systems;

import com.team4153.RobotConstants;
import com.team4153.RobotMap;
import com.team4153.Sensors;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.CANJaguar.ControlMode;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Chassis subsystem for mecanum drive
 */
public class Chassis implements Systems {

    private int fieldControl = 1;
    private RobotDrive drive;
    /**
     * Jaguar CAN bus motor controllers.
     */
    public CANJaguar rightFront;
    private CANJaguar rightRear;
    private CANJaguar leftFront;
    private CANJaguar leftRear;
    private double currentP;
    private double currentI;
    private double currentD;
    private boolean useEncoders=true;

    /**
     * The control mode needs to be set in the constructor for the speed mode to
     * work: http://www.chiefdelphi.com/forums/showthread.php?t=89721
     *
     * Setting the "changeControlMode" after the constructor does not seem to
     * work.
     *
     */
    public Chassis() {
        currentP = RobotConstants.INITIAL_P;
        currentI = RobotConstants.INITIAL_I;
        currentD = RobotConstants.INITIAL_D;
        try {
            ControlMode mode = CANJaguar.ControlMode.kSpeed;
            rightFront = new CANJaguar(RobotMap.JAG_RIGHT_FRONT_MOTOR, mode);
            rightRear = new CANJaguar(RobotMap.JAG_RIGHT_REAR_MOTOR, mode);
            leftFront = new CANJaguar(RobotMap.JAG_LEFT_FRONT_MOTOR, mode);
            leftRear = new CANJaguar(RobotMap.JAG_LEFT_REAR_MOTOR, mode);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }

        initJags();

        drive = new RobotDrive(leftFront, leftRear, rightFront, rightRear);
        drive.setInvertedMotor(MotorType.kRearRight, true);//
        drive.setInvertedMotor(MotorType.kFrontRight, true);
        drive.setMaxOutput(RobotConstants.DRIVE_POWER);
        drive.setSafetyEnabled(false);
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
//    private void configSpeedControl(CANJaguar jag, boolean pidPositive, double P, double I, double D) throws CANTimeoutException {
//        final int CPR = 360;
//        final double ENCODER_FINAL_POS = 0;
//        final double VOLTAGE_RAMP = 40;
//        
//        if (jag.getControlMode() == CANJaguar.ControlMode.kPercentVbus) {
//            return; //don't add stuffif in kPercentVbus
//        }
////        jag.changeControlMode(CANJaguar.ControlMode.kPercentVbus);
////        jag.setSpeedReference(CANJaguar.SpeedReference.kNone);
////        jag.enableControl();
////        jag.configMaxOutputVoltage(10);//ToDo: 
//        // PIDs may be required.  Values here:
//        //  http://www.chiefdelphi.com/forums/showthread.php?t=91384
//        // and here:
//        // http://www.chiefdelphi.com/forums/showthread.php?t=89721
//        // neither seem correct.
//        if(pidPositive){
//            jag.setPID(P, I, D);
//        }        else{
//            jag.setPID(-P, -I, -D);
//        }
//        
//        jag.setSpeedReference(CANJaguar.SpeedReference.kQuadEncoder);
//        jag.configEncoderCodesPerRev(CPR);
////        jag.setVoltageRampRate(VOLTAGE_RAMP);
//        jag.enableControl();
//
//    }
    private void configSpeedControl(CANJaguar jag, boolean PIDpositive, double P, double I, double D) throws CANTimeoutException {
        final int CPR = 360;
        final double ENCODER_FINAL_POS = 0;
        final double VOLTAGE_RAMP = 6;
        if (PIDpositive) {
            jag.setPID(P, I, D);
        } else {
            jag.setPID(-P, -I, -D);
        }
        
        jag.changeControlMode(ControlMode.kSpeed);
        jag.setSpeedReference(CANJaguar.SpeedReference.kQuadEncoder);
        jag.configEncoderCodesPerRev(CPR);
        jag.setVoltageRampRate(VOLTAGE_RAMP);
        jag.enableControl();

//        System.out.println("Control Mode = " + jag.getControlMode());
    }

   
    
    /**
     * The command to drive mecanum via joystick and gyro angle
     *
     * @param stick The driver's joystick (usually via OI)
     * @param heading The gyro angle/heading
     */
    public void mecanumDrive(Joystick stick, double heading) {
        double twist, x, y, throttle;

        twist = stick.getTwist() * 0.7;//Maybe change, perviously .5
        x = stick.getX();
        y = stick.getY();
        throttle = (stick.getRawAxis(RobotMap.JSAXIS_THROTTLE) - 1.0) / -2.0;
        //   System.out.println("Throttle: " + throttle);
        SmartDashboard.putString("Throttle", "" + throttle);

        // tolerances to keep the robot from jittering
        double jitterTolerance = 0.05;
        if (Math.abs(x) < jitterTolerance) {
            x = 0;
        } else {
            x = (Math.abs(x) - jitterTolerance) * getSign(x);
        }
        if (Math.abs(y) < jitterTolerance) {
            y = 0;
        } else {
            y = (Math.abs(y) - jitterTolerance) * getSign(y);
        }
        if (Math.abs(twist) < jitterTolerance) {
            twist = 0;
        }

        // limit drive
//        drive.setMaxOutput(300);
//        System.out.println("gyro: " + heading);
//        System.out.println("X " + x );
//        System.out.println("Y " + y);
        if(stick.getRawButton(RobotMap.JSBUTTON_DRIVE_STRAIGHT)){
            if(stick.getRawButton(RobotMap.JSBUTTON_STOP_IN_RANGE)){
                if (Sensors.getSemifilteredUltrasonic()>RobotConstants.DISTANCES[0] && Sensors.getSemifilteredUltrasonic()<RobotConstants.DISTANCES[RobotConstants.DISTANCES.length-1]){
                    drive.mecanumDrive_Cartesian(0, 0, 0, 0);
                }else{
                    drive.mecanumDrive_Cartesian(0, y, twist, 0);
                }
            }else{
                drive.mecanumDrive_Cartesian(0, y, twist, 0);
            }
        }else if(stick.getRawButton(RobotMap.JSBUTTON_STOP_IN_RANGE)){
            if (Sensors.getSemifilteredUltrasonic()>RobotConstants.DISTANCES[0] && Sensors.getSemifilteredUltrasonic()<RobotConstants.DISTANCES[RobotConstants.DISTANCES.length-1]){
                    drive.mecanumDrive_Cartesian(x, 0, 0, 0);
            }else{
                drive.mecanumDrive_Cartesian(x, y, twist, heading * fieldControl);
            }
        }else{
            drive.mecanumDrive_Cartesian(x, y, twist, heading * fieldControl);     
        }
        
//        try {
//            System.out.println("encoder: " + rightFront.getSpeed());
//            System.out.println("jag out set rr: " + rightRear.getX() + " rf: " + rightFront.getX() + 
//                    " lr: "+ leftRear.getX() + " lf: "+ leftFront.getX());
//            System.out.println("jag output: rr: " + rightRear.getOutputVoltage()+ " rf: " + rightFront.getOutputVoltage()+ 
//                    " lr: "+ leftRear.getOutputVoltage()+ " lf: "+ leftFront.getOutputVoltage());
//            System.out.println("rr faults: " + rightRear.getFaults() + "rf faults: " + rightFront.getFaults() + "lr faults: "+ leftRear.getFaults() + "lf faults: "+ leftFront.getFaults());
//            System.out.println("I: " + currentI);
//            System.out.println("D: " + currentD);
//            System.out.println("jag faults: " + rightFront.getFaults());
//        } catch (CANTimeoutException ex) {
//            ex.printStackTrace();
//        }
    }

    public void disableEncoder(CANJaguar jag){
        try {
            jag.changeControlMode(ControlMode.kPercentVbus);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }
    
    
    public void disableEncoders(){
        disableEncoder(rightFront);
        disableEncoder(leftFront);
        disableEncoder(rightRear);
        disableEncoder(leftRear);
        useEncoders=false;
    }
    
    /**
     * Initializes the jag speed controls
     */
    public void initJags() {
        try {
            configSpeedControl(rightFront, true, currentP, currentI, currentD);
//            configSpeedControl(rightFront,false);

            configSpeedControl(rightRear, true, currentP, currentI, currentD);
//            configSpeedControl(rightRear,false);

            configSpeedControl(leftFront, true, currentP, currentI, currentD);
//            configSpeedControl(leftFront,true);

            configSpeedControl(leftRear, true, currentP, currentI, currentD);
//            configSpeedControl(leftRear,false);

        } catch (CANTimeoutException ex) {
            System.out.println("Chassis constructor CANTimeoutException: ");
            ex.printStackTrace();
            //System.exit(-1);
        }
        useEncoders=true;
    }

    public void turn(double direction) {
//        drive.mecanumDrive_Cartesian(0, 0, direction - Sensors.getGyro().getAngle(), Sensors.getGyro().getAngle());
          drive.mecanumDrive_Polar(0, 0, direction);
    }

    public void driveForward(double input) {
        drive.mecanumDrive_Cartesian(0, -input, 0, Sensors.getGyro().getAngle());
    }

    public static int getSign(double val) {
        if (val < 0.0) {
            return -1;
        } else if (val > 0.0) {
            return 1;
        }
        return 0;
    }

    /**
     *
     * @param nextP
     * @param nextI
     * @param nextD
     */
    public void setPID(double nextP, double nextI, double nextD) {
        currentP = nextP;
        currentI = nextP;
        currentD = nextP;
        try {
            configSpeedControl(rightFront, false, nextP, nextI, nextD);
            configSpeedControl(rightRear, false, nextP, nextI, nextD);
            configSpeedControl(leftFront, true, nextP, nextI, nextD);
            configSpeedControl(leftRear, false, nextP, nextI, nextD);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }

    /**
     *
     * @param fieldControl
     */
    public void setFieldControl(boolean fieldControl) {
        if (fieldControl) {
            this.fieldControl = 1;
        } else {
            this.fieldControl = 0;
        }
    }

    /**
     *
     * @return
     */
    public double getCurrentP() {
        return currentP;
    }

    /**
     *
     * @param currentP
     */
    public void setCurrentP(double currentP) {
        this.currentP = currentP;
    }

    /**
     *
     * @return
     */
    public double getCurrentI() {
        return currentI;
    }

    /**
     *
     * @param currentI
     */
    public void setCurrentI(double currentI) {
        this.currentI = currentI;
    }

    /**
     *
     * @return
     */
    public double getCurrentD() {
        return currentD;
    }

    /**
     *
     * @param currentD
     */
    public void setCurrentD(double currentD) {
        this.currentD = currentD;
    }

    /**
     * Stop the robot chassis from moving
     */
    public void driveHalt() {
        System.out.println("** driveHalt");
        this.drive.mecanumDrive_Polar(0, 0, 0);
    }

    /**
     *
     */
    public void execute(int buttonNumber) {
        if (buttonNumber < 0) {
            mecanumDrive(Sensors.getDriverJoystick(), Sensors.getGyro().getAngle());
            
        }
        if(buttonNumber==RobotMap.JSBUTTON_DISABLE_ENCODERS){
            if(useEncoders)
                disableEncoders();
            else
                initJags();
        }
        
        if (Sensors.getDriverJoystick().getRawButton(RobotMap.JSBUTTON_JAG_RESET)) {
            initJags();
        }
    }
}
