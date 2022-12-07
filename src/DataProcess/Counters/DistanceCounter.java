package DataProcess.Counters;

import DataProcess.IO.DataStream;
import DataProcess.Math.Vector3;

import java.util.ArrayList;

/**
 * What is a step? Steps are so arbitrary. Do you need to move for it to be a step? What about a lunge? What about a jump. Is that uppy downy pattern of acceleration a step?
 * Not to mention that some peoples "constant speed walking" data looks like they had their phone be run over by a car.
 * The average length of a human step is 2.1-2.5 feet according to google. That's a step.
 *  we have ACCELERATION(g or m/s^2), we have TIME(50 milliseconds). That means we have DISTANCE!
 * Now if we just used this distance to calculate displacement, we would not count circles
 * But if we used total distance, we would have the phones extra back and forth movement.
 * But if we use displacement, and reset it when the displacement is greater than a step(and count the step) it solves the above problems
 * Unfortunately, we don't know the exact timestamps or units, making it similarly accurate to the other methods we tried
 */
public class DistanceCounter implements StepCounter {
    private static boolean IS_G = false; //what unit is acceleration
    private static Vector3 TIME = new Vector3(50.0 / 1000.0); //TIme in seconds, 50 millisecond to seconds
    private static double STEP = 0.73152; //2.4 feet in meters. The average length of human step.

    ArrayList<Integer> step_indices = new ArrayList<>();
    @Override public int countSteps(DataStream stream) {
        int num_steps = 0;

        Vector3 displacement = new Vector3(); //total displacement in meters
        Vector3 velocity = new Vector3(); //current velocity in m/s
        int idx = 0;
        while (true) { //iterate over data
            double[] record = stream.getNextRecord();   //get data
            if (record == null || record.length != 6) {
                break;
            } //check if loop should continue

            //ignore y gravity
            Vector3 accel = new Vector3(record[0],0, record[2]); //acceleration in m/s^2

            if(IS_G){
                //convert to m/s^2
                accel = accel.multiply(new Vector3(9.81));
            }

            //get and add velocity
            velocity = velocity.add(accel.multiply(TIME)); //v = u +at

            //get and add displacement
            displacement = displacement.add(velocity.multiply(TIME)); //d=vt


            //calculate distance
            if(displacement.length() >= STEP){
                num_steps++;//took a step
                displacement = new Vector3(); //reset displacement
                velocity = new Vector3(); //reset velocity to avoid drift
                step_indices.add(idx);
            }
            idx++;
        }
        return num_steps; //sometimes really accurate, sometimes not
    }
    @Override
    public ArrayList<Double>[] getRelevantData() {
        return new ArrayList[]{};
    }

    @Override
    public ArrayList<Integer> getStepIndices() {
        return step_indices;
    }
}
