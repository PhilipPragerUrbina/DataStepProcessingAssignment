package DataProcess.Counters;

import DataProcess.IO.DataStream;
import DataProcess.Math.DisplayUtils;
import DataProcess.Math.Vector3;

import java.util.ArrayList;

/**
 * Counter that factors in acceleration direction and rotation
 */
public class DirectionCounter implements StepCounter {


    @Override public int countSteps(DataStream stream) {
        int num_steps = 0;

        ArrayList<Double> rot_buffer = new ArrayList<>(); //keep track of past rotation
        final int MINIMA_WIDTH = 4; //how wide to check for dips

        while (true) { //iterate over data
            double[] record = stream.getNextRecord();   //get data
            if (record == null || record.length != 6) {break;} //check if loop should continue

            //get vectors
            Vector3 accel = new Vector3(record[0], record[1], record[2]);
            Vector3 gyro = new Vector3(record[3], record[4], record[5]);

            Vector3 direction = accel.normalized(); //get direction

            //get weighted average of rotation using direction
            double rotation = weightedAverage(new double[]{gyro.x, gyro.y, gyro.z}, new double[]{direction.x, direction.y, direction.z});
            rot_buffer.add(rotation);

            if(rot_buffer.size() == MINIMA_WIDTH * 2 + 1){
                if(checkMinima(rot_buffer, MINIMA_WIDTH)){
                    num_steps++;
                };

                for (int i = 0; i < MINIMA_WIDTH; i++) { //move forward
                    rot_buffer.remove(0);
               }
            }



        }
        return num_steps;
    }


    private boolean checkMinima(ArrayList<Double> rot_buffer, int MINIMA_WIDTH){
        int center = rot_buffer.size() - MINIMA_WIDTH - 1;
        double center_value = rot_buffer.get(center);
        //check one side
        for (int i = 1; i <= center; i++) {
            //should be decreasing
            if(rot_buffer.get(i) >= rot_buffer.get(i-1) ) { //this one is greater than last
                return false; //is increasing
            }
        }
            //check other side
            for (int i = rot_buffer.size()-2; i >= center; i--) {
                //should be decreasing
                if (rot_buffer.get(i) >= rot_buffer.get(i + 1)) { //this one is greater than last
                    return false; //is increasing
                }
            }
            return true;
    }


    @Override
    public ArrayList<Double>[] getRelevantData() {
        return new ArrayList[]{};
    }

    @Override
    public ArrayList<Integer> getStepIndices() {
        return new ArrayList<Integer>();
    }

    /**
     * Get a weighted average
     * @param values  Actual values
     * @param  weight weights between 0 and 1
     * @return The average or zero if non-matching lengths
     */
    private double weightedAverage(double[] values, double[] weight){
        if(values.length != weight.length){
            return 0; //not correct length
        }
        double average = 0;
        for (int i = 0; i < values.length; i++) {
            average+= values[i]*weight[i];
        }
        return average;
    }
}
