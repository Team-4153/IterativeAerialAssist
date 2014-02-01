/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team4153.systems;

import com.team4153.RobotMap;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;

/**
 *
 * @author 4153student
 */
public class Shooter implements Systems {
    
    Solenoid open;
    Solenoid close;
    Victor motor;
    
    public Shooter(){
        open=new Solenoid(RobotMap.WINCH_LATCH);
        close=new Solenoid(RobotMap.WINCH_UNLATCH);
        motor=new Victor(RobotMap.VICTOR_CHANNEL);
        motor.setExpiration(2.0);
    }
    
    public void execute(){
        open.set(true);
        close.set(false);
       
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        
        //TODO: Add shooting code (motor, etc.) here, take out sleep statement
        open.set(false);
        close.set(true);
        
        motor.set(-1);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        motor.set(0);
    }
}
