package DataProcess.IO;

import java.io.FileNotFoundException;

/**
 * Numerical CSV stream
 */
public class CSVStream implements DataStream {
    private CSV csv; //The file

    /**
     * Open csv for stream and read header
     * @param filepath
     */
    public CSVStream (String filepath){
        try {
            csv = new CSV(filepath);
            //read header
            csv.getNextRecord();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the next line in the csv, converted to doubles
     * @return Values, or null if invalid or none left
     */
    @Override
    public double[] getNextRecord() {
        try {
            String[] data = csv.getNextRecord();
            if(data == null){return null;}
            //parse
            double[] numerical_data = new double[data.length];
            for (int i = 0; i < data.length; i++) {
                numerical_data[i]  = Double.parseDouble(data[i].trim());
            }
            return numerical_data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
