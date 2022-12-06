package DataProcess.Counters;

import DataProcess.IO.DataStream;
import DataProcess.Math.*;


import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class DCTCounter implements StepCounter{
    private final int chunkSize;

    public DCTCounter(int chunkSize){
        this.chunkSize = chunkSize;
    }

    @Override
    public int countSteps(DataStream stream) {
        return 0;
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

