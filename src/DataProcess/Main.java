package DataProcess;

import DataProcess.IO.CSVStream;
import DataProcess.Math.Vector3;
import Plot.MathUtils;
import Plot.PlotWindow;
import Plot.ScatterPlot;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
	    // Read data
        CSVStream stream = new CSVStream("Data/steps/4-100-step-running.csv");


        int num_peaks = 0;
        double last_magnitude = 0;
        double last_last_magnitude = 0;
        ArrayList<Double> magnitudes = new ArrayList<Double>();
        ArrayList<Double> x_accel = new ArrayList<Double>();

        while (true){ //loop over data
            //get data
            double[] record = stream.getNextRecord();
            if(record == null ||record.length != 6 ){break;}
            Vector3 accel = new Vector3(record[0],record[1],record[2]);
            Vector3 gyro = new Vector3(record[3],record[4],record[5]);
           // System.out.println("Accel: " + accel + "   Gyro: " + gyro);

            double magnitude =accel.length();
            magnitudes.add(magnitude);
            x_accel.add(accel.x);

            if(last_magnitude > magnitude && last_magnitude > last_last_magnitude){
                num_peaks++;//is bigger than both neighbors
            }

            last_last_magnitude = last_magnitude;
            last_magnitude = magnitude;


        }
        System.out.println(num_peaks);

        //test plots
        ScatterPlot scatterPlot = new ScatterPlot(100, 100, 1500, 700);

        // create x-coordinates for the data
        double[] x = MathUtils.linspace(0, magnitudes .size()-1, magnitudes .size());
        List<Double> xCoords = MathUtils.toList(x);

        scatterPlot.set(ScatterPlot.Setting.show_axes, true);
        scatterPlot.set(ScatterPlot.Setting.show_border, true);
        scatterPlot.setTextSize(20);

        scatterPlot.plot(xCoords, magnitudes).fillColor("red").strokeWeight(2).strokeColor("red").style("-");
        scatterPlot.plot(xCoords, x_accel).fillColor("green").strokeWeight(2).strokeColor("green").style("-");

        PlotWindow window = PlotWindow.getWindowFor(scatterPlot, 800, 800);
        window.show();

    }
}
