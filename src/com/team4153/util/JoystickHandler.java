/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team4153.util;

import com.team4153.RobotMap;
import com.team4153.Sensors;
import com.team4153.systems.Arm;
import com.team4153.systems.DistanceAngleTable;
import com.team4153.systems.Flippers;
import com.team4153.systems.GyroReset;
import com.team4153.systems.Jittering;
import com.team4153.systems.Shooter;
import com.team4153.systems.Winch;
import java.util.Vector;

/**
 *
 * @author 4153student
 */
public class JoystickHandler {
    
    /** Vector with all of the button handlers that control systems. */
    Vector buttons=new Vector();

    /**
     *
     * @param shooter
     * @param flippers
     */
    public JoystickHandler(Shooter shooter, Flippers flippers, Arm arm, Winch winch){
        //Add all ButtonHandlers here
        
        buttons.addElement(new ButtonHandler(Sensors.getDriverJoystick(),RobotMap.JSBUTTON_GYRO_RESET,new GyroReset(),true));
        buttons.addElement(new ButtonHandler(Sensors.getManipulatorJoystick(),RobotMap.JSBUTTON_FLIPPERS,flippers,true));
        buttons.addElement(new ButtonHandler(Sensors.getManipulatorJoystick(),RobotMap.JSBUTTON_TRIGGER,shooter,true));
        buttons.addElement(new ButtonHandler(Sensors.getManipulatorJoystick(),RobotMap.JSBUTTON_JITTER,new Jittering(flippers),false));
        buttons.addElement(new ButtonHandler(Sensors.getManipulatorJoystick(),RobotMap.JSBUTTON_AUTO_AIM,new DistanceAngleTable(arm),false));
        buttons.addElement(new ButtonHandler(Sensors.getManipulatorJoystick(),RobotMap.JSBUTTON_FORCE_FLIPPERS_TOGGLE,flippers,true));
        buttons.addElement(new ButtonHandler(Sensors.getManipulatorJoystick(),RobotMap.JSBUTTON_WINCH_HALF,winch,true));
        buttons.addElement(new ButtonHandler(Sensors.getManipulatorJoystick(),RobotMap.JSBUTTON_WINCH_FULL,winch,true));
    }
    
    /**
     *
     */
    public void execute(){
        for(int i=0;i<buttons.size();i++){
            ((ButtonHandler)(buttons.elementAt(i))).execute();
        }
    }
    
}
