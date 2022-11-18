package DataProcess.IO;

/**
 * Stream of numerical data
 */
public interface DataStream {
    /**
     * Get the next series of numerical values
     * @return
     */
    double[] getNextRecord();
}
