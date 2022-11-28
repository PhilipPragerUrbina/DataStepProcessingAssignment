package DataProcess.IO;

import java.util.ArrayList;

public class Tester {
    private String directory;
    ArrayList<Integer> step_counts;
    ArrayList<Integer> expected_step_counts;
    ArrayList<String> files;

    public Tester(String directory){
        this.directory = directory;
    }

    public double getTotalError(){
        return 0;
    }

    public ArrayList<Integer> getExpected(){
        return expected_step_counts;
    }
    public ArrayList<String> getNames(){
        return files;
    }
    public ArrayList<Integer> getPredicted(){
        return step_counts;
    }
    public ArrayList<Integer> getError(){
        return new ArrayList<>();
    }

}
