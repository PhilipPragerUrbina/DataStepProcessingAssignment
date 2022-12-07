package DataProcess;

import DataProcess.Counters.*;
import DataProcess.IO.CSVStream;
import DataProcess.IO.Tester;
import DataProcess.Math.DisplayUtils;


public class Main {

    public static void main(String[] args) {
        Tester tester = new Tester("Data/testFiles/blk3/" , DistanceCounter.class);
     //   System.out.println(tester);
        System.out.println("MSE error: " + tester.getMeanSquaredError());
        tester = new Tester("Data/testFiles/blk3/" , MagnitudeCounter.class);
        System.out.println("MSE error: " + tester.getMeanSquaredError());
        tester = new Tester("Data/testFiles/blk3/" , UpDownCounter.class);
        System.out.println("MSE error: " + tester.getMeanSquaredError());
        tester = new Tester("Data/testFiles/blk3/" , DirectionCounter.class);
        System.out.println("MSE error: " + tester.getMeanSquaredError());

        MagnitudeCounter counter = new MagnitudeCounter();
        counter.countSteps(new CSVStream("Data/testFiles/blk3/500 - Step - constant speed - aryav.csv"));
        DistanceCounter counter2 = new DistanceCounter();
        counter2.countSteps(new CSVStream("Data/testFiles/blk3/500 - Step - constant speed - aryav.csv"));
      //  DisplayUtils.plotSequentialData(counter.getStepIndices(),counter.getRelevantData());
        DisplayUtils.plotSequentialData(counter2.getStepIndices(),counter.getRelevantData());

    }

}
