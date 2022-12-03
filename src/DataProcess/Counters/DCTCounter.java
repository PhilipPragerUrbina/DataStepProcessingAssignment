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
    public int countSteps(DataStream stream){
        int numSteps =0;
        boringLoop:
        while (true) { //loop over data
            //get data
            ArrayList<double[]> stuff = new ArrayList<>();
            int i = 0;
            do{
                stuff.add(stream.getNextRecord());
                if(stuff.get(i).equals(null) || stuff.get(i).length != 6){
                    double[][] temp = new double[i][6];
                    for(int j = 0; j < i; j ++){
                        temp[j] = stuff.get(j);
                    }
                    numSteps+=guessNextSteps(temp);
                    break boringLoop;
                }
                i++;
            }while(i < chunkSize);
            double[][] temp = new double[i][6];
            for(int j = 0; j < i; j ++){
                temp[j] = stuff.get(j);
            }
            numSteps+=guessNextSteps(temp);
        }
        return (numSteps);
    }
    private int guessNextSteps(double[][] allData){
        double[][] tranformed = DiscreteCosineTransform(allData);
        return guessSteps(chooseGreatestThings(tranformed));
    }
    public int[] chooseGreatestThings(double[][] data){
        //for each dimension of these, we just read it;
        double[] greatest = new double[data[0].length];
        int[] out = new int[data[0].length];
        Arrays.fill(greatest, Double.MIN_VALUE);
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                if(data[i][j] > out[j]){
                    greatest[j] = data[i][j];
                    out[j] = i;
                }
            }
        }
        return out;
    }
    public int guessSteps(int[] majorFreqs) {
        int out = 0;
        for (int i = 0; i < majorFreqs.length; i++) {
            out+=majorFreqs[i];
        }
        return out/ majorFreqs.length;
    }
    public double[][] DiscreteCosineTransform(double[][] multiDim ){
        int i = multiDim.length;
        int w = multiDim[0].length;
        double[][] out = new double[i][multiDim[0].length];
        double thing = 2*Math.PI/i;
        for (int j = 0; j < i; j++) {
            for (int k = 0; k < i; k++) {
                for (int l = 0; l < w; l++) {
                    out[j][l]+= Math.cos(j*k*thing)*multiDim[k][l];
                }
            }
        }
        return out;
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

