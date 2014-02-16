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
public class DistanceAngleTable extends Thread implements Systems {

    Arm arm;

    public static final double distances[] = {100, 120, 130, 140,500};
    public static final double angles[] = {2.2, 2.0, 1.925, 1.85,1.6};

    public DistanceAngleTable(Arm arm) {
        this.arm = arm;

        //TODO: Put actual values using "put Value()", have to be in order
    }

//    private void putValue(Double distance, Double angle) {
//        distances.addElement(distance);
//        angles.addElement(angle);
//    }
    public void execute(int buttonNumber) {
        double angle = calculateAngle(Sensors.getUltrasonicDistance());
        // need to check for error here...
//        System.out.println("Target Angle: " + angle);
        if(angle != -1.0){
            arm.moveArmTowardLocation(angle);
        }
    }

    private double calculateAngle(double distance) {
        int distanceIndex = 0;
        try {
            while (getDistanceAt(distanceIndex) < distance) {
                distanceIndex++;
            }
            // you can extrapolate on either end of the angles and distance
            // whether you should is another question.
//            System.out.println("distance in method: " + distance);
//            System.out.println("distance index: " + distanceIndex);
            if (distanceIndex > 0 && distanceIndex < distances.length) {

                double ratio = (getAngleAt(distanceIndex) - getAngleAt(distanceIndex - 1)) / (getDistanceAt(distanceIndex) - getDistanceAt(distanceIndex - 1));
                double angle = getAngleAt(distanceIndex - 1) + ratio * (distance - getDistanceAt(distanceIndex - 1));
                return angle;

            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    private double getDistanceAt(int index) {
        // potential to throw "indexoutofboundsexception" which is not caught.
        return distances[index];
    }

    private double getAngleAt(int index) {
        return angles[index];
    }

//    private void doubleTest() {
//        Vector v = new Vector();
//        v.addElement(new Double(45));
//        Double d1 = new Double(13);
//        Double d2 = new Double(18);
//        if (d1.doubleValue() < d2.doubleValue()) {
//            ///...
//        }
//    }
}
