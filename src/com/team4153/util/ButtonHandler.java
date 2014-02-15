/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team4153.util;

import com.team4153.systems.Systems;
import edu.wpi.first.wpilibj.Joystick;

/**
 *
 * @author 4153student
 */
public class ButtonHandler {
    
    private boolean pressedPreviously=false;
    private boolean runJustOnce; //Sets if the System should be executed just once if the Button is pressed or always
    private int buttonNumber;
    private Systems runSystem;
    private Joystick joystick;
    
    /**
     *
     * @param joystick
     * @param joystickButton
     */
    public ButtonHandler(Joystick joystick, int joystickButton){
        this.buttonNumber = joystickButton;
        this.joystick=joystick;
    }
    
    /**
     *
     * @param joystick
     * @param joystickButton
     * @param runSystem
     * @param runJustOnce
     */
    public ButtonHandler(Joystick joystick,int joystickButton, Systems runSystem, boolean runJustOnce){
        this(joystick,joystickButton);
        this.runSystem=runSystem;
        this.runJustOnce=runJustOnce;
    }
    
    /**
     *
     */
    public void execute(){
        if(joystick.getRawButton(buttonNumber)){
            if(!pressedPreviously||!runJustOnce){
                runSystem.execute(buttonNumber);
            }
            pressedPreviously=true;
        }
        else{
            pressedPreviously=false;
        }
    }
    
}
