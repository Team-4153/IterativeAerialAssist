/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team4153.systems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author 4153student
 */
public class DashboardPID implements Systems{
    private double P, I, D, previousP, previousI, previousD;
    private Chassis chassis;
    
    public DashboardPID(Chassis chassis){
        this.chassis = chassis;
        SmartDashboard.putNumber("P:", 0);
        SmartDashboard.putNumber("I:", 0);
        SmartDashboard.putNumber("D:", 0);
    }
    
    public void execute() {
        P = SmartDashboard.getNumber("P:", 0);
        I = SmartDashboard.getNumber("I:", 0);
        D = SmartDashboard.getNumber("D:", 0);
        if(previousP!=P||previousI!=I||previousD!=D){
            chassis.setPID(P, I, D);
        }
        previousP = P;
        previousI = I;
        previousD = D;
    }
    
}
