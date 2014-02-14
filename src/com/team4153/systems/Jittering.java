/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team4153.systems;

/**
 *
 * @author Developer
 */
public class Jittering implements Systems {

    public static final int DELAY=50;
    Flippers flippers;
    
    public Jittering(Flippers flippers){
        this.flippers=flippers;
    }
    
    public void execute() {
        (new JitterThread(flippers)).start();
    }
    
    protected class JitterThread extends Thread{
        Flippers flippers;
        
        public JitterThread(Flippers flippers){
            this.flippers=flippers;
        }
        
        public void run(){
            flippers.jitter();
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    
}
