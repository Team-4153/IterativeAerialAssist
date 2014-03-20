package com.team4153.systems;

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
    private CANJaguar rightFront;
    private CANJaguar rightRear;
    private CANJaguar leftFront;
    private CANJaguar leftRear;
    double twist = 0;
    double x = 0;
    double y = 0; 
    double throttle = 0;

    /**  The control mode needs to be set in the constructor for the speed mode to work:
     *  http://www.chiefdelphi.com/forums/showthread.php?t=89721
     * 
     * Setting the "changeControlMode" after the constructor does not seem to work.
     * 
     */
    public Chassis() {
        try {
            ControlMode mode = CANJaguar.ControlMode.kPercentVbus;
            rightFront = new CANJaguar(RobotMap.JAG_RIGHT_FRONT_MOTOR, mode );
            configSpeedControl(rightFront,true,.3,.005,0);
//            configSpeedControl(rightFront,false);
            rightRear = new CANJaguar(RobotMap.JAG_RIGHT_REAR_MOTOR, mode );
            configSpeedControl(rightRear,true,.3,.005,0);
//            configSpeedControl(rightRear,false);
            leftFront = new CANJaguar(RobotMap.JAG_LEFT_FRONT_MOTOR, mode );
            configSpeedControl(leftFront,true,.3,.005,0);
//            configSpeedControl(leftFront,true);
            leftRear = new CANJaguar(RobotMap.JAG_LEFT_REAR_MOTOR, mode );
            configSpeedControl(leftRear,true,.3,.005,0);
//            configSpeedControl(leftRear,false);

        } catch (CANTimeoutException ex) {
            System.out.println("Chassis constructor CANTimeoutException: ");
            ex.printStackTrace();
            //System.exit(-1);
        }
        
        drive = new RobotDrive(leftFront, leftRear, rightFront, rightRear);
        drive.setInvertedMotor(MotorType.kRearRight, true);//
        drive.setInvertedMotor(MotorType.kFrontRight, true);
        //drive.setMaxOutput(300);//TODO: Fix the magic numbers
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
private void configSpeedControl(CANJaguar jag,boolean PIDpositive,double P, double I, double D) throws CANTimeoutException {
        final int CPR = 360;
        final double ENCODER_FINAL_POS = 0;
        final double VOLTAGE_RAMP = 40;
        jag.changeControlMode(CANJaguar.ControlMode.kPercentVbus);
        jag.setSpeedReference(CANJaguar.SpeedReference.kNone);
//        jag.enableControl();
        jag.configMaxOutputVoltage(10);//ToDo: 
        // PIDs may be required.  Values here:
        //  http://www.chiefdelphi.com/forums/showthread.php?t=91384
        // and here:
        // http://www.chiefdelphi.com/forums/showthread.php?t=89721
        // neither seem correct.
        jag.setPID(0.4, .005, 0);
        if (PIDpositive) {
            jag.setPID(P, I, D);
        }else{
            jag.setPID(-P, -I, -D);
        }
        jag.setSpeedReference(CANJaguar.SpeedReference.kQuadEncoder);
        jag.configEncoderCodesPerRev(CPR);
//        jag.setVoltageRampRate(VOLTAGE_RAMP);
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
        
//        twist = (twist*0.9 + stick.getTwist()*0.25);
//        x = x*0.9 + stick.getX()*0.1;
//        y = y*0.9 + stick.getY()*0.1;
        twist = -stick.getTwist() * 0.5;
        x = -stick.getX();
        y = -stick.getY();
        throttle = (stick.getRawAxis(RobotMap.JSAXIS_THROTTLE) - 1.0) / -2.0;
     //   System.out.println("Throttle: " + throttle);
        SmartDashboard.putString("Throttle", "" + throttle);
        
        // tolerances to keep the robot from jittering
        double jitterTolerance = 0.05;
        if(Math.abs(x) < jitterTolerance ){
            x = 0;
        } else {
             x = (Math.abs(x) - jitterTolerance) * getSign(x);
        }
        if(Math.abs(y) < jitterTolerance ){
            y = 0;
        } else {
            y = (Math.abs(y) - jitterTolerance) * getSign(y);
        }
        if(Math.abs(twist) < jitterTolerance ){
            twist = 0;
        }
        
        // limit drive
//        drive.setMaxOutput(1000*throttle);
        System.out.println("gyro: " + heading);
        
        
        System.out.println("X " + x );
        System.out.println("Y " + y);
        
      drive.mecanumDrive_Cartesian(x, y, twist, -heading*fieldControl);
        try {
            System.out.println("encoder: " + rightFront.getSpeed());
            System.out.println("jag out set: " + rightFront.getX());
//            System.out.println("jag output: " + rightFront.getOutputVoltage());
//            System.out.println("jag faults: " + rightFront.getFaults());
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }
    
    public void turn(double direction){
        drive.mecanumDrive_Polar(0, 0, -direction);
    }
   
    private int getSign(double val){
        if (val<0.0) {
            return -1;
        } else if (val > 0.0) {
            return 1;
        }
        return 0;
    }
    
    
    public void setPID(double P, double I, double D){
        try {
            configSpeedControl(rightFront,true,P,I,D);
            configSpeedControl(rightRear,true,P,I,D);
            configSpeedControl(leftFront,true,P,I,D);
            configSpeedControl(leftRear,true,P,I,D);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }
    
    public void setFieldControl(boolean fieldControl){
        if(fieldControl){
            this.fieldControl = 1;
        }else{
            this.fieldControl = 0;
        }
    }

    
    /**
     * Makes the robot chassis
     */
    public void driveForward(double input){
//        drive.setMaxOutput(maxOutput);
        drive.mecanumDrive_Cartesian(0,input,0,Sensors.getGyro().getAngle());
        try {
            SmartDashboard.putNumber("Jag Speed", rightFront.getSpeed());
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }
    
    //to test rotation in autonomous.  
    /*public void driveInCircles(){
        Sensors.getGyro().reset();
        drive.setMaxOutput(50);
        drive.mecanumDrive_Cartesian(1,1,0,0);
    }*/
    
    /**
     * Stop the robot chassis from moving
     */
    public void driveHalt() {
        this.drive.mecanumDrive_Cartesian(0,0,0,0);
        System.out.println("** driveHalt");
    }

    public void execute() {
        mecanumDrive(Sensors.getJoystick(), Sensors.getGyro().getAngle());
    }
}
