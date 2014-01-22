/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team4153.systems;

import com.team4153.Sensors;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 *
 * @author 4153student
 */
public class ButtonHandler implements Systems {
    
    private boolean pressedPreviously=false;
    private boolean runJustOnce; //Sets if the System should be executed just once if the Button is pressed or always
    private int buttonNumber;
    private Systems runSystem;
    
    public ButtonHandler(int joystickButton){
        this.buttonNumber = joystickButton;
    }
    
    public ButtonHandler(int joystickButton, Systems runSystem, boolean runJustOnce){
        this(joystickButton);
        this.runSystem=runSystem;
        this.runJustOnce=runJustOnce;
    }
    
    public void execute(){
        if(Sensors.getJoystick().getRawButton(buttonNumber)){
            if(!pressedPreviously||!runJustOnce){
                runSystem.execute();
            }
            pressedPreviously=true;
        }
        else{
            pressedPreviously=false;
        }
    }
    
}
