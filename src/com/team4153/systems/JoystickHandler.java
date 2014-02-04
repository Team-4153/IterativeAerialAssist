/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team4153.systems;

import com.team4153.RobotMap;
import com.team4153.Sensors;
import edu.wpi.first.wpilibj.Joystick;
import java.util.Vector;

/**
 *
 * @author 4153student
 */
public class JoystickHandler implements Systems {
    
    Vector buttons=new Vector();
    Flippers flippers=new Flippers();
            
    public JoystickHandler(Shooter shooter){
        //Add all ButtonHandlers here
        
        buttons.addElement(new ButtonHandler(Sensors.getDriverJoystick(),RobotMap.JSBUTTON_GYRO_RESET,new GyroReset(),true));
        buttons.addElement(new ButtonHandler(Sensors.getManipulatorJoystick(),RobotMap.JSBUTTON_FLIPPERS,flippers,true));
        buttons.addElement(new ButtonHandler(Sensors.getManipulatorJoystick(),RobotMap.JSBUTTON_TRIGGER,shooter,true));
    }
    
    public void execute(){
        for(int i=0;i<buttons.size();i++){
            ((ButtonHandler)(buttons.elementAt(i))).execute();
        }
    }
    
}
