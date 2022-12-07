package DataProcess.Counters;

import DataProcess.IO.DataStream;
import DataProcess.Math.Vector3;

import java.util.ArrayList;

public class FFTCounter implements StepCounter{
    private final int chunkSize = 500;
    //500 and 0.89 is 21000
    private static final double noiseConsumer = 0.89;
    @Override
    public int countSteps(DataStream stream) {
        int total = 0;
        outerLoop:
        while (true) { //loop over data
            //get data
            ArrayList<double[]> tempStorage = new ArrayList<>();
            for (int i = 0; i < chunkSize; i++) {
                double[] record = stream.getNextRecord();
                if (record == null || record.length != 6) {break outerLoop;}
                tempStorage.add(record);
            }

            complex[] data = new complex[tempStorage.size()];
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < data.length; j++) {
                    data[j] = new complex(tempStorage.get(j)[i], 0.0);
                }
                data = DFT(data);
                total+=chooseImportantMagnitude(data);
            }
        }
        return total/6;
    }

    @Override
    public ArrayList<Double>[] getRelevantData() {
        return new ArrayList[0];
    }

    @Override
    public ArrayList<Integer> getStepIndices() {
        return null;
    }
    private static int chooseImportantMagnitude(complex[] transformed){
        double greatest = 0;
        int out = 0;
        for (int i = 0; i < transformed.length; i++) {
            double temp = transformed[i].abs();
            if(temp > greatest){
                out = i;
                greatest = temp;
            }
        }
        return out;
    }
    private static complex[] DFT(complex[] data){
        int N = data.length;
        complex[] out = new complex[(int)(noiseConsumer*N)];
        for (int i = 0; i < out.length; i++) {
            out[i] = new complex(0.0,0.0);
        }
        double factor = Math.PI*2/N;
        for (int i = 0; i < out.length; i++) {
            for (int j = 0; j < N; j++) {
                out[i].add( complex.multiply(data[j], exp(factor*j*i)));
            }
        }
        return out;
    }
    private static complex exp(double in){
        return new complex(Math.cos(in), Math.sin(in));
    }
}
class complex{
    private double r, i;
    complex(double real, double imaginary){
        this.r = real;
        this.i = imaginary;
    }
    double abs(){
        return Math.sqrt(i*i + r*r);
    }
    double getReal(){return this.r;}
    double getImaginary(){return this.i;}
    double getR(){return this.r;}
    double getI(){return this.i;}
    static complex multiply(complex a, complex b){
        return new complex(a.getR()*b.getR() - a.getI()*b.getI(), a.getR()*b.getI() + a.getI()*b.getR());
    }
    void multiply(complex b){
        double tempR = this.r;
        this.r = tempR*b.getR() - this.i*b.getI();
        this.i = tempR*b.getI() + this.i*b.getR();
    }
    void add(complex a){
        this.r+=a.getR();
        this.i+=a.getI();
    }
    static complex add(complex a, complex b){
        return new complex(a.getR() + b.getR(), a.getI() + b.getI());
    }
}
