/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team4153.util;

import com.team4153.RobotConstants;
import com.team4153.RobotMap;
import com.team4153.Sensors;
import com.team4153.systems.Chassis;
import com.team4153.systems.DistanceAngleTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author 4153stucurrentDent
 */
public class DashboardCommunication {

    private final Chassis chassis;
    private final DistanceAngleTable table;
    private boolean fieldControl;
    private boolean previousFieldControl;

    /**
     *
     * @param chassis
     */
    public DashboardCommunication(Chassis chassis, DistanceAngleTable table) {
        this.chassis = chassis;
        this.table=table;
        /*SmartDashboard.putNumber("P:", chassis.getCurrentP());
        SmartDashboard.putNumber("I:", chassis.getCurrentI());
        SmartDashboard.putNumber("D:", chassis.getCurrentD());
        SmartDashboard.putBoolean("Field Control?", fieldControl);
        SmartDashboard.putNumber("Joystick X", Sensors.getDriverJoystick().getX());
        SmartDashboard.putNumber("Joystick Y", Sensors.getDriverJoystick().getY());
        SmartDashboard.putNumber("Joystick Twist", Sensors.getDriverJoystick().getTwist());*/


    }

    /**
     *
     */
    public void execute() {
        /*double incomingP = SmartDashboard.getNumber("P:", chassis.getCurrentP());
        double incomingI = SmartDashboard.getNumber("I:", chassis.getCurrentI());
        double incomingD = SmartDashboard.getNumber("D:", chassis.getCurrentD());
        fieldControl = SmartDashboard.getBoolean("Field Control?", true);
        if (incomingP != chassis.getCurrentP() || incomingI != chassis.getCurrentI() || incomingD != chassis.getCurrentD()) {
            chassis.setPID(incomingP, incomingI, incomingD);
        }
        if (previousFieldControl != fieldControl) {
            chassis.setFieldControl(fieldControl);
            previousFieldControl = fieldControl;
        }
        
        SmartDashboard.putBoolean("Winch Limit", Sensors.getWinchLimitSwitch().get());
        SmartDashboard.putNumber("Distance Ultrasonic: ", Sensors.getUltrasonicDistance());
        SmartDashboard.putNumber("Distance Ultrasonic filtered: ", Sensors.getFilteredUltrasonicDistance());
        SmartDashboard.putNumber("String Pot", Sensors.getStringPotAngle());
//        SmartDashboard.putBoolean("Flippers Open:", Sensors.areFlippersOpen());
        SmartDashboard.putBoolean("Winch Limit", Sensors.getWinchLimitSwitch().get());
//        SmartDashboard.putBoolean("LimitSwitch 2", Sensors.getLimitSwitch2().get());
//        SmartDashboard.putBoolean("LimitSwitch 3", Sensors.getLimitSwitch3().get());
        SmartDashboard.putNumber("Gyro", Sensors.getGyro().getAngle());
        SmartDashboard.putBoolean("Photo Eye", Sensors.getPhotoEyeLeft().get());
        SmartDashboard.putBoolean("Photo Eye 2", Sensors.getPhotoEyeRight().get());
        //SmartDashboard.putBoolean("Auto Switch 1", Sensors.getAutoSwitch1().get());
        SmartDashboard.putBoolean("Auto Switch 2", Sensors.getAutoSwitch2().get());
        SmartDashboard.putBoolean("Auto Switch 3", Sensors.getAutoSwitch3().get());
        SmartDashboard.putBoolean("Backing Switch", Sensors.getBackingSwitch().get());*/
        
        SmartDashboard.putNumber("Arm Angle", getAngle(Sensors.getStringPotAngle()));
        SmartDashboard.putNumber("Distance", Sensors.getSemifilteredUltrasonic());
        boolean inDistance=Sensors.getSemifilteredUltrasonic()>RobotConstants.DISTANCES[0] 
                && Sensors.getSemifilteredUltrasonic()<RobotConstants.DISTANCES[RobotConstants.DISTANCES.length-1];
        SmartDashboard.putBoolean("In Range?",inDistance);
        SmartDashboard.putBoolean("Winch Back?", Sensors.isWinchBack());
        SmartDashboard.putBoolean("Arm in Position", armInPosition());
    }

    public boolean armInPosition(){
        double angle=0.18+DistanceAngleTable.calculateAngle(Sensors.getSemifilteredUltrasonic());
        return Math.abs(angle-Sensors.getStringPotAngle())<RobotConstants.ARM_TOLERANCE;
    }
    
    public double getAngle(double voltage){
        return voltage; //TODO:Calculate angle from RotPot value
    }
}
