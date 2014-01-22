/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team4153.systems;

import com.team4153.Sensors;

/**
 *
 * @author 4153student
 */
public class GyroReset implements Systems {
    
    public void execute(){
        Sensors.getGyro().reset();
    }
    
}
