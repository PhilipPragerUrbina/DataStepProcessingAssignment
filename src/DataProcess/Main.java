package DataProcess;

import DataProcess.IO.CSVStream;
import DataProcess.Math.DisplayUtils;


public class Main {

    public static void main(String[] args) {
        // Read data
        CSVStream stream = new CSVStream("Data/steps/4-100-step-running.csv");
        StepCounter counter = new MagnitudeCounter();
        System.out.println(counter.countSteps(stream));
        DisplayUtils.plotSequentialData(counter.getRelevantData());
    }

}
