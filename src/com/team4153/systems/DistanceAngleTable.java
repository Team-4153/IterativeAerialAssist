/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team4153.systems;

import com.team4153.Sensors;
import java.util.Vector;

/**
 *
 * @author 4153student
 */
public class DistanceAngleTable extends Thread implements Systems {

    Arm arm;

    Vector distances;
    Vector angles;

    public DistanceAngleTable(Arm arm) {
        distances = new Vector();
        angles = new Vector();
        this.arm = arm;

        //TODO: Put actual values using "put Value()", have to be in order
    }

    private void putValue(Double distance, Double angle) {
        distances.addElement(distance);
        angles.addElement(angle);
    }

    public void execute() {
        double angle = calculateAngle(Sensors.getUltrasonicDistance());
        arm.moveArmToLocation(angle);
    }

    private double calculateAngle(double distance) {
        int bigger = 0;
        while (getDistanceAt(bigger) < distance) {
            bigger++;
        }
        if (bigger > 0 && bigger < distances.size()) {
            double ratio = (getAngleAt(bigger) - getAngleAt(bigger - 1)) / (getDistanceAt(bigger) - getDistanceAt(bigger - 1));
            double angle = getAngleAt(bigger - 1) + ratio * (distance - getDistanceAt(bigger - 1));
            return angle;
        }
        return -1;
    }

    private double getDistanceAt(int index) {
        return Double.parseDouble(("" + (Double) distances.elementAt(index)));
    }

    private double getAngleAt(int index) {
        return Double.parseDouble(("" + (Double) angles.elementAt(index)));
    }
}
