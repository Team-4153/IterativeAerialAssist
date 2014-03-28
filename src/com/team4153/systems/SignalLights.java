/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team4153.systems;

import com.team4153.RobotConstants;
import com.team4153.RobotMap;
import com.team4153.Sensors;
import edu.wpi.first.wpilibj.Relay;


/**
 *
 * @author 4153student
 */
public class SignalLights implements Systems {
 
    private Relay signalLights;
    
    public SignalLights(){
         signalLights = new Relay(RobotMap.LIGHT_CHANNEL);
    }
    
    public void execute(int button) {
   if (Sensors.getSemifilteredUltrasonic()>90 && Sensors.getSemifilteredUltrasonic()<145){
       signalLights.set(Relay.Value.kForward);
       System.out.println("Changed");
   }
   else
       signalLights.set(Relay.Value.kOff);
    }
}

