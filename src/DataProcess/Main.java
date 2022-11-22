package DataProcess;

import DataProcess.IO.CSVStream;
import DataProcess.Math.DisplayUtils;
import DataProcess.Math.Vector3;


import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {
        // Read data
        CSVStream stream = new CSVStream("Data/steps/4-100-step-running.csv");


        int num_peaks = 0;
        double last_magnitude = 0;
        double last_last_magnitude = 0;
        ArrayList<Double> magnitudes = new ArrayList<Double>();
        ArrayList<Vector3> accelerations = new ArrayList<Vector3>();

        while (true) { //loop over data
            //get data
            double[] record = stream.getNextRecord();
            if (record == null || record.length != 6) {
                break;
            }
            Vector3 accel = new Vector3(record[0], record[1], record[2]);
            Vector3 gyro = new Vector3(record[3], record[4], record[5]);
            // System.out.println("Accel: " + accel + "   Gyro: " + gyro);

            double magnitude = accel.length();
            magnitudes.add(magnitude);
            accelerations.add(accel.x);

            if (last_magnitude > magnitude && last_magnitude > last_last_magnitude) {
                num_peaks++;//is bigger than both neighbors
            }

            last_last_magnitude = last_magnitude;
            last_magnitude = magnitude;


        }
        System.out.println(num_peaks);
        DisplayUtils.plotSequentialData(magnitudes,DisplayUtils.splitAxis(accelerations));
    }

}
