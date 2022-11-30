package DataProcess.Counters;

import DataProcess.IO.DataStream;
import DataProcess.Math.Vector3;

import java.util.ArrayList;

//simple magnitude step counter
public class MagnitudeCounter implements StepCounter {
    ArrayList<Integer> step_indices;
    ArrayList<Double> magnitudes;

    @Override public int countSteps(DataStream stream) {
        step_indices = new ArrayList<>();
        magnitudes = new ArrayList<>();

        int num_peaks = 0;
        double last_magnitude = 0;
        double last_last_magnitude = 0;
        int idx = 0;
        while (true) { //loop over data
            //get data
            double[] record = stream.getNextRecord();
            if (record == null || record.length != 6) {break;}
            Vector3 accel = new Vector3(record[0], record[1], record[2]);
            //Vector3 gyro = new Vector3(record[3], record[4], record[5]);

            //calculate magnitude
            double magnitude = accel.length();
            magnitudes.add(magnitude);

            //test for peak
            if (last_magnitude > magnitude && last_magnitude > last_last_magnitude) {
                num_peaks++;//is bigger than both neighbors
                step_indices.add(idx);
            }

            last_last_magnitude = last_magnitude;
            last_magnitude = magnitude;
             idx++;
        }
        return num_peaks;
    }

    @Override
    public ArrayList<Double>[] getRelevantData() {
        return new ArrayList[]{magnitudes};
    }

    @Override
    public ArrayList<Integer> getStepIndices() {
        return step_indices;
    }
}
