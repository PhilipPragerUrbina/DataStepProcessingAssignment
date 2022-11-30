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
     * Get all possible data to graph
     * @return
     */
    ArrayList<Double>[] getRelevantData();

    /**
     * Get the indices in the sequntial data where steps are detected
     * @return
     */
    ArrayList<Integer> getStepIndices();



}
