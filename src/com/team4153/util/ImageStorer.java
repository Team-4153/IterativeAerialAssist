/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team4153.util;

import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.MonoImage;
import edu.wpi.first.wpilibj.image.NIVisionException;

/**
 *
 * @author 4153student
 */
public class ImageStorer extends Thread {

    public static final int delay = 1000;

    AxisCamera camera;
    int imageNum = 0;
    
    public static final int MAX_IMAGES = 1000;

    public ImageStorer(AxisCamera camera) {
        this.camera = camera;
    }

    public void run() {
        
        /*int folderID = new Random().nextInt();
        try {
           FileConnection filecon = (FileConnection) Connector.open("file:///ImageSet"+folderID+"/empty/",Connector.WRITE);
           filecon.create();
           filecon.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }*/
        
        while (true) {
            try {
                ColorImage image = null;
                try {
                    Thread.sleep(delay);
                    image = camera.getImage();     // comment if using stored images
//          s      ColorImage image;                           // next 2 lines read image from flash on cRIO
//                image = new RGBImage("/testImage.jpg");		// get the sample image from the cRIO flash
                    MonoImage thresholdImage = image.getIntensityPlane();   // keep only green objects
                    thresholdImage.write("/Images/image"+imageNum+".bmp");
                    //image.write("/stopaction/image" + imageNum + ".bmp");
                    imageNum++;
                    if(imageNum>MAX_IMAGES){
                        image.free();
                        break;
                    }
                } catch (AxisCameraException ex) {
                    ex.printStackTrace();
                } catch (NIVisionException ex) {
                    ex.printStackTrace();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                finally{
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
