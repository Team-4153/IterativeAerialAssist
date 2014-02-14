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
import java.util.Vector;
 *
 * @author 4153student
 */
public class JoystickHandler implements Systems {
    
    Vector buttons=new Vector();
    
    public JoystickHandler(){
        //Add all ButtonHandlers here
        
        buttons.addElement(new ButtonHandler(RobotMap.JSBUTTON_GYRO_RESET,new GyroReset(),false));
    }
    
    public void execute(){
        for(int i=0;i<buttons.size();i++){
            ((ButtonHandler)(buttons.elementAt(i))).execute();
        }
    }
    
}
