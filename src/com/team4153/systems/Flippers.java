/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team4153.systems;

import com.team4153.RobotMap;
import edu.wpi.first.wpilibj.Solenoid;

/**
 *
 * @author 4153student
 */
public class Flippers implements Systems {
    
    Solenoid leftOpen, rightOpen, leftClose, rightClose;
    boolean open=false;
    
    public Flippers(){
        leftOpen=new Solenoid(RobotMap.LEFT_FLIPPER_OPEN);
        rightOpen=new Solenoid(RobotMap.RIGHT_FLIPPER_OPEN);
        leftClose=new Solenoid(RobotMap.LEFT_FLIPPER_CLOSE);
        rightClose=new Solenoid(RobotMap.RIGHT_FLIPPER_CLOSE);
    }
    
    public void execute (){
        
        if(open){
            leftOpen.set(false);
            leftClose.set(true);
            rightOpen.set(false);
            rightClose.set(true);
            open=false;
        }
        else{
            leftOpen.set(true);
            leftClose.set(false);
            rightOpen.set(true);
            rightClose.set(false);
            open=true;
        }
        
    }

}
