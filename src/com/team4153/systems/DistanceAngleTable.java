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

    public static final double distances[] = {100, 120, 130, 140};
    public static final double angles[] = {68, 55, 48, 45};

    public DistanceAngleTable(Arm arm) {
        this.arm = arm;

        //TODO: Put actual values using "put Value()", have to be in order
    }

//    private void putValue(Double distance, Double angle) {
//        distances.addElement(distance);
//        angles.addElement(angle);
//    }
    public void execute() {
        double angle = calculateAngle(Sensors.getUltrasonicDistance());
        // need to check for error here...
        arm.moveArmToLocation(angle);
    }

    private double calculateAngle(double distance) {
        int bigger = 0;
        while (getDistanceAt(bigger) < distance) {
            bigger++;
        }
        // you can extrapolate on either end of the angles and distance
        // whether you should is another question.
        if (bigger > 0 && bigger < distances.length) {
            double ratio = (getAngleAt(bigger) - getAngleAt(bigger - 1)) / (getDistanceAt(bigger) - getDistanceAt(bigger - 1));
            double angle = getAngleAt(bigger - 1) + ratio * (distance - getDistanceAt(bigger - 1));
            return angle;
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