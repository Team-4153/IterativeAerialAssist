/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team4153.systems;

import com.team4153.RobotConstants;

/**
 *
 * @author Developer
 */
public class Jittering implements Systems {

    
    Flippers flippers;
    
    public Jittering(Flippers flippers){
        this.flippers=flippers;
    }
    
    public void execute(int buttonNumber) {
        (new JitterThread(flippers)).start();
    }
    
    protected class JitterThread extends Thread{
        Flippers flippers;
        
        public JitterThread(Flippers flippers){
            this.flippers=flippers;
        }
        
        public void run(){
            flippers.open();
            try {
                Thread.sleep(RobotConstants.JITTER_DELAY);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            flippers.close();
            try {
                Thread.sleep(RobotConstants.JITTER_DELAY);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    
}
