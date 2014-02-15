/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team4153.systems;

/**
 *
 * @author 4153student
 */
public interface Systems {
    
    /**
     * Execute the system functions as if activated by the given button number
     * (negative means no button).
     * 
     * @param buttonNumber The button that activated this system.
     */
    public void execute(int buttonNumber);
    
}
