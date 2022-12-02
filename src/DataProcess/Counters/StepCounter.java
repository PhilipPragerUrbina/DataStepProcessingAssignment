package DataProcess.Counters;

import DataProcess.IO.DataStream;
import DataProcess.Math.Vector3;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Interface for step counter
 */
public interface StepCounter {
    /**
     * Count steps in a data sream
     * @param stream data
     * @return The step count
     */
    int countSteps(DataStream stream);

    /**
     * Get all possible data to graph for debugging
     * @return
     */
    ArrayList<Double>[] getRelevantData();

    /**
     * Get the indices in the sequential data where steps are detected for debugging/graphing
     * @return
     */
    ArrayList<Integer> getStepIndices();



}
