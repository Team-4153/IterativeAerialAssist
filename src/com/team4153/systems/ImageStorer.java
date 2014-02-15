/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team4153.systems;

import com.sun.squawk.microedition.io.FileConnection;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.Image;
import edu.wpi.first.wpilibj.image.MonoImage;
import edu.wpi.first.wpilibj.image.NIVisionException;
import java.io.IOException;
import javax.microedition.io.Connector;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 *
 * @author 4153student
 */
public class ImageStorer extends Thread {

    public static final int delay = 10000;

    AxisCamera camera;
    int imageNum = 0;

    public ImageStorer(AxisCamera camera) {
        this.camera = camera;
    }

    public void run() {
        
        int folderID = this.hashCode();
        try {
           FileConnection filecon = (FileConnection) Connector.open("file:///ImageSet"+folderID);
           if(!filecon.exists()) {
               filecon.create();
           }
           filecon.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        while (true) {
            try {
                ColorImage image = null;
                try {
                    image = camera.getImage();     // comment if using stored images
//                ColorImage image;                           // next 2 lines read image from flash on cRIO
//                image = new RGBImage("/testImage.jpg");		// get the sample image from the cRIO flash
                    MonoImage thresholdImage = image.getBluePlane();   // keep only green objects
                    thresholdImage.write("/ImageSet"+folderID+"/image"+imageNum+".bmp");
                    //image.write("/stopaction/image" + imageNum + ".bmp");
                    imageNum++;
                    image.free();
                    Thread.sleep(delay);
                } catch (AxisCameraException ex) {
                    image.free();
                    ex.printStackTrace();
                } catch (NIVisionException ex) {
                    image.free();
                    ex.printStackTrace();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                    image.free();
                }
            } catch (NIVisionException ex) {
                ex.printStackTrace();
            } catch (NullPointerException ex){
                System.out.println("No images to store yet");
            }
        }
    }
}
