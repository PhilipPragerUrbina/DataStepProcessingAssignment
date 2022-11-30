package DataProcess.Counters;

import DataProcess.IO.DataStream;
import DataProcess.Math.Vector3;

import java.util.ArrayList;
import java.util.Vector;

public class UpDownCounter implements StepCounter {
    private static final int upperBoundSquared = 400;
    private boolean isAbove = false;
    @Override
    public int countSteps(DataStream stream) {
        int stepCount = 0;
        while(true) {

            double[] values = stream.getNextRecord();
            if(values == null){break;}
            double mag = values[0] * values[0] + values[1] * values[1] + values[2] * values[2];
            if (mag > upperBoundSquared) {
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
