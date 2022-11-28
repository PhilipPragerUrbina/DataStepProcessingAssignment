package DataProcess.Math;

import Plot.MathUtils;
import Plot.PlotWindow;
import Plot.ScatterPlot;

import java.util.ArrayList;
import java.util.List;

/**
 * Utilities for Visualizing Data
 * Uses cool plotting library from Dober
 */
public class DisplayUtils {

    //Colors for plotting
    private final static String[] colors = {"red", "green", "blue", "yellow", "black"};
    /**
     * Plot numerical data, that is in sequential order
     * @param values 1 or more arrays of data to plot
     */
    public static void plotSequentialData(ArrayList<Double>... values ){
        //min and maxes of all the added arrays
        ArrayList<Double> max_values_y = new ArrayList<>();
        ArrayList<Double> min_values_y = new ArrayList<>();
        ArrayList<Double> lengths = new ArrayList<>(); //lengths of all arrays

        //get array's information
        for (ArrayList<Double> arr: values) {
            max_values_y.add(getMax(arr));
            min_values_y.add(getMin(arr));
            lengths.add((double) arr.size() );
        }
        //todo add points(in document from last class)

        //Create scatter plot
        ScatterPlot plot = new ScatterPlot(0, (int)getMin(min_values_y), (int)getMax(lengths), (int)getMax(max_values_y));
        plot.set(ScatterPlot.Setting.show_axes, true);
        plot.set(ScatterPlot.Setting.show_border, true);
        plot.setTextSize(20);

        int color = 0; //color id
        for (ArrayList<Double> arr: values) {
            //create x coords and plot each dataset
            plot.plot(getSequentialNumbers(arr.size()), arr).strokeWeight(2).strokeColor(colors[color]).style("-");
            color = (color+1)%colors.length; //get next color id
        }

        // Show actual window
        PlotWindow window = PlotWindow.getWindowFor(plot, (int)(plot.getDataViewMaxX() - plot.getDataViewMinX()), (int)(plot.getDataViewMaxY() - plot.getDataViewMinY()));
        window.show();

    }

    /**
     * Get a list of values increasing by 1, useful for x coordinates
     * @param length How long the list should be
     * @return The list of doubles, starting at 0
     */
    public static ArrayList<Double> getSequentialNumbers(int length){
     return (ArrayList<Double>) MathUtils.toList(MathUtils.linspace(0, length-1, length));
    }

    /**
     * Get the maximum value in the array
     * @param array
     * @returnThe maximum value
     */
    public static double getMax(ArrayList<Double> array){
        double max = array.get(0);
        for (Double d: array ) {
            if(d > max){max = d;}
        }
        return max;
    }

    /**
     * Get the minimum value in the array
     * @param array
     * @returnThe minimum value
     */
    public static double getMin(ArrayList<Double> array){
        double min = array.get(0);
        for (Double d: array ) {
            if(d < min){min = d;}
        }
        return min;
    }

    /**
     * Split vector3 array into 3 double arrays
     * @param data The vector array
     * @return Array containing one arraylist for each axis, xyz
     */
    public static ArrayList<Double>[] splitAxis(ArrayList<Vector3> data) {
        ArrayList<Double>[] out = new ArrayList[3];
        for (int i = 0; i < out.length; i++){out[i] = new ArrayList<>();} //create arrays
        for (Vector3 v: data) { //iterate through data
            out[0].add(v.x);out[1].add(v.y);out[2].add(v.z);
        }
        return out;
    }
}
