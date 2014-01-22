package com.team4153.systems;

import com.team4153.RobotMap;
import com.team4153.Sensors;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Chassis subsystem for mecanum drive
 */
public class Chassis implements Systems {

    private RobotDrive drive;
    /**
     * Jaguar CAN bus motor controllers.
     */
    private CANJaguar rightFront;
    private CANJaguar rightRear;
    private CANJaguar leftFront;
    private CANJaguar leftRear;

    /**  The control mode needs to be set in the constructor for the speed mode to work:
     *  http://www.chiefdelphi.com/forums/showthread.php?t=89721
     * 
     * Setting the "changeControlMode" after the constructor does not seem to work.
     * 
     */
    public Chassis() {
        try {
            System.out.println("Chassis Construtor started");
            rightFront = new CANJaguar(RobotMap.JAG_RIGHT_FRONT_MOTOR, CANJaguar.ControlMode.kSpeed);
            configSpeedControl(rightFront,false,.3,.005,0);
            System.out.println("JAG Right Front works, " + RobotMap.JAG_RIGHT_FRONT_MOTOR);
            rightRear = new CANJaguar(RobotMap.JAG_RIGHT_REAR_MOTOR, CANJaguar.ControlMode.kSpeed);
            configSpeedControl(rightRear,false,.3,.005,0);
            System.out.println("JAG Right Back works, " + RobotMap.JAG_RIGHT_REAR_MOTOR);
            leftFront = new CANJaguar(RobotMap.JAG_LEFT_FRONT_MOTOR, CANJaguar.ControlMode.kSpeed);
            configSpeedControl(leftFront,true,.3,.005,0);
            System.out.println("JAG Left Front works, " + RobotMap.JAG_LEFT_FRONT_MOTOR);
            leftRear = new CANJaguar(RobotMap.JAG_LEFT_REAR_MOTOR, CANJaguar.ControlMode.kSpeed);
            configSpeedControl(leftRear,false,.3,.005,0);
            System.out.println("JAG Left Back works, " + RobotMap.JAG_LEFT_REAR_MOTOR);

        } catch (CANTimeoutException ex) {
            System.out.println("Chassis constructor CANTimeoutException: ");
            ex.printStackTrace();
            //System.exit(-1);
        }
        
        drive = new RobotDrive(leftFront, leftRear, rightFront, rightRear);
        drive.setInvertedMotor(MotorType.kRearRight, true);//
        drive.setInvertedMotor(MotorType.kFrontRight, true);
        drive.setMaxOutput(200);//TODO: Fix the magic numbers
        drive.setSafetyEnabled(false);
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private void configSpeedControl(CANJaguar jag, boolean pidPositive, double P, double I, double D) throws CANTimeoutException {
        final int CPR = 360;
        final double ENCODER_FINAL_POS = 0;
        final double VOLTAGE_RAMP = 40;
//        jag.changeControlMode(CANJaguar.ControlMode.kPercentVbus);
//        jag.setSpeedReference(CANJaguar.SpeedReference.kNone);
//        jag.enableControl();
//        jag.configMaxOutputVoltage(10);//ToDo: 
        // PIDs may be required.  Values here:
        //  http://www.chiefdelphi.com/forums/showthread.php?t=91384
        // and here:
        // http://www.chiefdelphi.com/forums/showthread.php?t=89721
        // neither seem correct.
        if(pidPositive){
            jag.setPID(P, I, D);
        }
        else{
            jag.setPID(-P, -I, -D);
        }
        
        jag.setSpeedReference(CANJaguar.SpeedReference.kQuadEncoder);
        jag.configEncoderCodesPerRev(CPR);
//        jag.setVoltageRampRate(VOLTAGE_RAMP);
        jag.enableControl();

    }


    /**
     * The command to drive mecanum via joystick and gyro angle
     *
     * @param stick The driver's joystick (usually via OI)
     * @param heading The gyro angle/heading
     */
    public void mecanumDrive(Joystick stick, double heading) {
        double twist, x, y, throttle;
        twist = stick.getTwist() * 0.5;
        x = stick.getX();
        y = stick.getY();
        throttle = (stick.getRawAxis(RobotMap.JSAXIS_THROTTLE) - 1) / -2;
        System.out.println("Throttle: " + throttle);
        SmartDashboard.putString("Throttle", "" + throttle);
        if(Math.abs(x) < 0.02){
            x = 0;
        }
        if(Math.abs(y) < 0.02){
            y = 0;
        }
        if(Math.abs(twist) < 0.02){
            twist = 0;
        }
        drive.setMaxOutput(1000*throttle);
        System.out.println("gyro: " + heading);
        this.drive.mecanumDrive_Cartesian(x, y, twist, heading);
    }
    
    public void setPID(double P, double I, double D){
        try {
            configSpeedControl(rightFront,false,P,I,D);
            configSpeedControl(rightRear,false,P,I,D);
            configSpeedControl(leftFront,true,P,I,D);
            configSpeedControl(leftRear,false,P,I,D);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Stop the robot chassis from moving
     */
    public void driveHalt() {
        System.out.println("** driveHalt");
        this.drive.mecanumDrive_Polar(0, 0, 0);
    }

    public void execute() {
        mecanumDrive(Sensors.getJoystick(), Sensors.getGyro().getAngle());
    }
}
