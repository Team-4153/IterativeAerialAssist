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
    Thread jitterThread;
    
    public Jittering(Flippers flippers){
        this.flippers=flippers;
    }
    
    public void execute(int buttonNumber) {
        if(jitterThread == null){
            jitterThread = new JitterThread(flippers);
            jitterThread.start();
        }
    }
    
    protected class JitterThread extends Thread{
        Flippers flippers;
        
        public JitterThread(Flippers flippers){
            this.flippers = flippers;
        }
        
        public void run(){
            
            try {
                flippers.open();
                try {
                    Thread.sleep(RobotConstants.JITTER_OPEN_DELAY);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                flippers.close();
                try {
                    Thread.sleep(RobotConstants.JITTER_CLOSE_DELAY);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            } finally {
                jitterThread = null;
            }
        }
    }
    
}
