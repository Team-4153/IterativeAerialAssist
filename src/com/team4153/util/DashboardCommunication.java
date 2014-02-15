/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team4153.util;

import com.team4153.systems.Chassis;
import edu.wpi.first.wpilibj.Sendable;
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
        SmartDashboard.putNumber("Gyro", 180);
    }

}
