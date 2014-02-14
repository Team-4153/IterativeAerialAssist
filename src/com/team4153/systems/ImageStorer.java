/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team4153.systems;

import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.NIVisionException;

/**
 *
 * @author 4153student
 */
public class ImageStorer extends Thread {

    public static final int delay=5000;
    
    AxisCamera camera;
    int imageNum=0;
    
    public ImageStorer(){
        camera=AxisCamera.getInstance("10.41.53.11");
    }
    
    public void run(){
        while(true){
            try {
                camera.getImage().write("Image "+imageNum);
                imageNum++;
                Thread.sleep(delay);
            } catch (AxisCameraException ex) {
                ex.printStackTrace();
            } catch (NIVisionException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
