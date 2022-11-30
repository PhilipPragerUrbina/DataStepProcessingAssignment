package DataProcess.IO;

import DataProcess.Counters.StepCounter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 *  Test a step counter on a directory of files
 */
public class Tester {
    private String directory; //The directory
    private ArrayList<Integer> step_counts = new ArrayList<>(); //The predicted step counts
    private ArrayList<Integer> expected_step_counts = new ArrayList<>(); //The actual step counts
    private ArrayList<String> files = new ArrayList<>(); //The file names

    /**
     * Create a new tester scenario. Filenames should contain actual step number somewhere
     * @param directory Where test files are
     * @param counter_class What counters to use
     * @warning Counts all digits in file name, don't have extra numbers
     */
    public Tester(String directory, Class<? extends StepCounter> counter_class){
        this.directory = directory;

        ArrayList<Path> paths = getPaths();

        for (Path path : paths) {
            int actual_num_steps = extractNumSteps(path); //get the actual step number from path

            //create stream and counter
            DataStream stream = new CSVStream(path.toString());
            StepCounter counter = null;
            try {
                counter = counter_class.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                e.printStackTrace();
            }

            //get prediction
            int prediction = counter.countSteps(stream);

            //add values
            step_counts.add(prediction);
            expected_step_counts.add(actual_num_steps);
            files.add(path.toString());
        }
    }

    /**
     * Create a printable reperentation
     * @return A formatted table of values
     */
    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < files.size(); i++) {
            str+=  files.get(i) + "\tactual: " + step_counts.get(i) + "\t Expected: " + expected_step_counts.get(i) +
                    "\t Error:" + (expected_step_counts.get(i)- step_counts.get(i)) + "\n";
        }
        return str;
    }


    /**
     * Get the total error
     * @return expected-predicted
     */
    public int getTotalError(){
        int total_error = 0;
        for (int i = 0; i < files.size(); i++) {
            total_error+=   (expected_step_counts.get(i)- step_counts.get(i));
        }
        return total_error;
    }

    /**
     * Get the mean squared error
     * @return error
     */
    public double getMeanSquaredError(){
        double total_error = 0;
        for (int i = 0; i < files.size(); i++) {
            total_error+=  Math.pow (expected_step_counts.get(i)- step_counts.get(i),2);
        }
        return total_error/ (double) files.size();
    }



    /**
     * Get the average error
     * @return Average error
     */
    public double getAverageError(){
        return (double)getTotalError() / (double)files.size();
    }




    /**
     * Get expected values from all the filenames
     * @return List of values
     */
    public ArrayList<Integer> getExpected(){
        return expected_step_counts;
    }

    /**
     * Get the filenames
     * @return The list of filenames/paths
     */
    public ArrayList<String> getNames(){
        return files;
    }

    /**
     * Get the predicted values
     * @return List of values
     */
    public ArrayList<Integer> getPredicted(){
        return step_counts;
    }

    /**
     * @taken_from The Dober Himself
     * extract the number of steps from a path
     * @param path The file path
     * @return Num steps
     */
    private static int extractNumSteps(Path path) {
        String filename = path.getFileName().toString();
        filename = filename.replaceAll("[^\\d]","");
        int steps;
        try {
            steps = Integer.parseInt(filename.trim());
        } catch (Exception e) {
            System.err.println("Error extracting # of steps from filename: " + filename);
            return -1;
        }
        return steps;
    }

    /**
     * @based_on the Doberman
     * get the paths from the directory
     * @return The list of paths
     */
    private  ArrayList<Path> getPaths() {
        ArrayList<Path> paths = new ArrayList<>();
        Path workDir = Paths.get(directory);
        if (!Files.notExists(workDir)) {
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(workDir)) {
                for (Path p : directoryStream) {
                    paths.add(p);
                }
                return paths;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

}
