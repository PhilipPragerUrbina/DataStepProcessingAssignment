package DataProcess.Counters;

import DataProcess.IO.DataStream;

import java.util.ArrayList;

/**
 *  Thomas's up down counter
  */
public class UpDownCounter implements StepCounter {

    private boolean isAbove = false;
    @Override
    public int countSteps(DataStream stream) {
        //get the upper bound, the max value
        double upper_bound = 0; // zero since absolute value
        while(true) {
            double[] values = stream.getNextRecord();
            if(values == null){break;}
            double mag = values[0] * values[0] + values[1] * values[1] + values[2] * values[2]; //repeats but whatever
            upper_bound = Math.max(upper_bound, Math.abs(mag));
        }
        upper_bound/=10; //this number is arbitrary, there has to be a better way to choose threshold


        stream.reset(); //start from beginning again


        int stepCount = 0;
        while(true) {
            double[] values = stream.getNextRecord();
            if(values == null){break;}
            double mag = values[0] * values[0] + values[1] * values[1] + values[2] * values[2];
            if (mag > upper_bound * upper_bound) {
                if (!isAbove) {
                    stepCount++;
                }
                isAbove = true;
            } else {
                isAbove = false;
            }
        }
        return stepCount;
    }


    @Override
    public ArrayList<Double>[] getRelevantData() {
        return new ArrayList[0];
    }

    @Override
    public ArrayList<Integer> getStepIndices() {
        return null;
    }
}
