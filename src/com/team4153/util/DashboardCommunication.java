/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team4153.util;

import com.team4153.RobotMap;
import com.team4153.Sensors;
import com.team4153.systems.Chassis;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author 4153stucurrentDent
 */
public class DashboardCommunication {

    private final Chassis chassis;
    private boolean fieldControl;
    private boolean previousFieldControl;

    /**
     *
     * @param chassis
     */
    public DashboardCommunication(Chassis chassis) {
        this.chassis = chassis;
        SmartDashboard.putNumber("P:", chassis.getCurrentP());
        SmartDashboard.putNumber("I:", chassis.getCurrentI());
        SmartDashboard.putNumber("D:", chassis.getCurrentD());
        SmartDashboard.putBoolean("Field Control?", fieldControl);
    }

    /**
     *
     */
    public void execute() {
        double incomingP = SmartDashboard.getNumber("P:", chassis.getCurrentP());
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
        SmartDashboard.putNumber("Distance Ultrasonic 1", Sensors.getUltrasonicDistance());
        SmartDashboard.putNumber("Distance Ultrasonic 2", Sensors.getUltrasonicDistance2());
        SmartDashboard.putNumber("String Pot", Sensors.getStringPotAngle());
        SmartDashboard.putBoolean("Flippers Open:", Sensors.areFlippersOpen());
        SmartDashboard.putBoolean("Winch Limit", Sensors.getWinchLimitSwitch().get());
//        SmartDashboard.putBoolean("LimitSwitch 2", Sensors.getLimitSwitch2().get());
//        SmartDashboard.putBoolean("LimitSwitch 3", Sensors.getLimitSwitch3().get());
        SmartDashboard.putNumber("Gyro", Sensors.getGyro().getAngle());
        SmartDashboard.putBoolean("Photo Eye", Sensors.getPhotoEye().get());
        SmartDashboard.putBoolean("Auto Switch 1", Sensors.getAutoSwitch1().get());
        SmartDashboard.putBoolean("Auto Switch 2", Sensors.getAutoSwitch2().get());
        
    }

}
