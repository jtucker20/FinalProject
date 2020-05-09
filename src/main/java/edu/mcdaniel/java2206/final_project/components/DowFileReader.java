package edu.mcdaniel.java2206.final_project.components;

import edu.mcdaniel.java2206.final_project.exceptions.DowFileReaderException;
import edu.mcdaniel.java2206.final_project.exceptions.InflationRateFileReaderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * This class will read a file.  We will manipulate the data later.
 */
public class DowFileReader {

    //=============================================================================================
    // Private Assets
    //=============================================================================================

    /**
     * This provides access to logging.
     */
    private final Logger log = LogManager.getLogger(DowFileReader.class);

    /**
     * File that will hold the link to the Dow Data
     */
    private File dowFile;

    //TODO: YOU MUST PUT THE LISTS VARIABLES IN HERE!
    private List<Double> dowOpens;
    private List<Double> dowHighs;
    private List<Double> dowLows;
    private List<Double> dowCloses;
    private List<Date> dowDates;


    //=============================================================================================
    // Constructor(s)
    //=============================================================================================

    /**
     * This No Argument constructor Will use the internal file.
     */
    //DO NOT MODIFY
    public DowFileReader() throws NullPointerException {
        // If this is called, we will use the internal file
        this.dowFile = new File(
                this.getClass()
                        .getClassLoader()
                        .getResource("DJI_Dec_31_2014_to_Dec_31_2018.csv")
                        .getFile()
        );
        //NOTE!  YOU MUST CATCH FOR A BAD INITIALIZATION OF THIS FILE!! IT WILL THROW A
        //Null pointer exception if the file isn't found!
    }

    /*
     * This one argument constructor will use the provided file.
     */
    public DowFileReader(File file) {
        this.dowFile = file;  //Assumes this is not null...
    }


    //=============================================================================================
    // Major Methods
    //=============================================================================================

    /**
     * This major method initializes the file.
     */
    public void setUp() throws DowFileReaderException {
        //TODO, DO any setup you need.

        if (!validate()) {
            throw new DowFileReaderException("Invalid File Setup");
        }

        this.dowOpens = new ArrayList<>();
        this.dowHighs = new ArrayList<>();
        this.dowLows = new ArrayList<>();
        this.dowCloses = new ArrayList<>();
        this.dowDates = new ArrayList<>();
    }

    /**
     * Major method to read in the data
     */
    //DO NOT MODIFY.
    public void read() throws DowFileReaderException {
        // We try to read the lines of the file
        try {
            readLines();
        } catch (Exception ioe) {
            //If we get an exception of any type we need to stop execution and throw this information to the user.
//            throw new DowFileReaderException("Error parsing in the data!", ioe);
        }
    }

    /**
     * Line reader functionality
     */
    //DO NOT MODIFY THIS BEHAVIOR.
    public void readLines() throws DowFileReaderException, IOException {
        //This is a try with resources block.  Inside of it, you have auto-closeable things, like a buffered reader
        // You use this EVERY time there is a resource with auto-closeable abilities.
        try (FileReader fileReader = new FileReader(this.dowFile); //Here we make the file reader
             BufferedReader reader = new BufferedReader(fileReader)) {  //Here we make a buffered reader
            String line = "";  //This will hold the lines from the file reader
            int linePos = 0;  //This will hold the position the data was taken from.
            while ((line = reader.readLine()) != null) {  //This complicated logic takes a line from the reader
                // and puts it into line.  Then checks to see if the line was null.
                //The line reader will return a null when eof hits.
                readAline(line, linePos);
                linePos++;
            }
        } catch (IOException ioe) {
            String message = "Error Reading Lines Encountered.";
            log.error(message);
            log.error(ioe);
//            throw new DowFileReaderException(message, ioe);
        }
    }

    /**
     * Method to parse a single line
     */
    public void readAline(String line, int linePos) throws DowFileReaderException {
        //TODO: IMPLEMENT THIS!!!!
        if (linePos < 0) {
            throw new DowFileReaderException("Invalid Line Position" + linePos);
        }
        if (linePos < 3) {
            return;
        }

        String[] lineParts = line.split(",");
        String dx = lineParts[0];

        if (lineParts.length == 7) {

            Date date = new Date(
                    Integer.parseInt(dx.substring(0, 4)) - 1900,
                    Integer.parseInt(dx.substring(5, 7)) - 1,
                    Integer.parseInt(dx.substring(8, 10)));

            String open = lineParts[1];
            String high = lineParts[2];
            String low = lineParts[3];
            String close = lineParts[4];

            this.dowDates.add(date);
            System.out.print(open);

            double Open = Double.parseDouble(open);
            this.dowOpens.add(Open);

            double High = Double.parseDouble(high);
            this.dowHighs.add(High);

            double Low = Double.parseDouble(low);
            this.dowLows.add(Low);

            double Close = Double.parseDouble(close);
            this.dowCloses.add(Close);

        }
    }

    //=============================================================================================
    // Minor Methods(s)
    //=============================================================================================

    //TODO Put in minor methods

    public boolean validate() {
        return this.dowFile != null && this.dowFile.canRead();
    }

    private String cleanInput(String input) {
        return input.substring(0, 8);
    }

    //=============================================================================================
    // Getters and Setters
    //=============================================================================================

    /**
     * Just to get the File name.
     */
    public String getFileName() {
        return this.dowFile.getName();
    }

    /**
     * Just to get the file itself
     */
    public File getFile() {
        return this.dowFile;
    }

    //TODO IMPLEMENT THE GETTERS AND SETTERS!
    public List<Double> getDowOpens(){
        return this.dowOpens;

    }
    public void setDowOPens(List<Double> dowOpens){
        this.dowOpens = dowOpens;}

    public List<Double> getDowHighs(){
        return this.dowHighs;

    }
    public void setDowHighs(List<Double> dowHighs){
        this.dowHighs = dowHighs;}

    public List<Double> getDowLows(){
        return this.dowLows;
    }
    public void setDowLows(List<Double> dowLows){
        this.dowLows = dowLows;}

    public List<Double>getDowCloses(){
        return this.dowCloses;}

    }
    public void setDowCloses(List<Double> dowCloses){
        this.dowCloses = dowCloses;}

    public List<Date> getDowDates(){
        return this.dowDates;

    }
    public void setDowDates(List<Date> dowDates){
        this.dowDates = dowDates;}
}







