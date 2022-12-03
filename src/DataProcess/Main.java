package DataProcess;

import DataProcess.Counters.MagnitudeCounter;
import DataProcess.Counters.*;
import DataProcess.IO.CSVStream;
import DataProcess.IO.Tester;
import DataProcess.Math.DisplayUtils;


public class Main {

    public static void main(String[] args) {
//        Tester tester = new Tester("Data/testFiles/blk3/" , DCTCounter.class);
//        System.out.println(tester);
//        System.out.println("MSE error: " + tester.getMeanSquaredError());


        // Read data
        CSVStream stream = new CSVStream("Data/steps/4-100-step-running.csv");
        StepCounter counter = new DCTCounter(50);
        System.out.println(counter.countSteps(stream));
        DisplayUtils.plotSequentialData(counter.getRelevantData());
    }

}
