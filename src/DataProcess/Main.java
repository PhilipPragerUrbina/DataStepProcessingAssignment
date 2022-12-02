package DataProcess;

import DataProcess.Counters.DirectionCounter;
import DataProcess.Counters.MagnitudeCounter;
import DataProcess.Counters.StepCounter;
import DataProcess.Counters.UpDownCounter;
import DataProcess.IO.CSVStream;
import DataProcess.IO.Tester;
import DataProcess.Math.DisplayUtils;


public class Main {

    public static void main(String[] args) {

        Tester tester = new Tester("Data/testFiles/blk3/" , MagnitudeCounter.class);
      //  System.out.println("magnitude  error: " + tester.getMeanSquaredError());
         //tester = new Tester("Data/testFiles/blk3/" , UpDownCounter.class);
   //     System.out.println("up down error: " + tester.getMeanSquaredError());
        tester = new Tester("Data/testFiles/blk3/" , DirectionCounter.class);
        System.out.println(tester);
        System.out.println("dir error: " + tester.getMeanSquaredError());
    }

}
