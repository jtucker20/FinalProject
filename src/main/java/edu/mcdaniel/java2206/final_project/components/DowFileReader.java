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
    public DowFileReader(File file){
        this.dowFile = file;  //Assumes this is not null...
    }


    //=============================================================================================
    // Major Methods
    //=============================================================================================

    /**
     * This major method initializes the file.
     */
    public void setUp() throws DowFileReaderException {
        if(!validate()){
            throw new DowFileReaderException("Invalid File Setup or Bad Permissions");
        }
        this.dowOpens = new LinkedList<>();
        this.dowHighs = new LinkedList<>();
        this.dowLows = new LinkedList<>();
        this.dowCloses = new LinkedList<>();

        this.dowDates = new LinkedList<>();
    }

    /**
     * Major method to read in the data
     */
    //DO NOT MODIFY.
    public void read() throws DowFileReaderException {
        // We try to read the lines of the file
        try{
            readLines();
        } catch (Exception ioe){
            //If we get an exception of any type we need to stop execution and throw this information to the user.
            throw new DowFileReaderException("Error parsing in the data!", ioe);
        }
    }

    /**
     * Line reader functionality
     */
    //DO NOT MODIFY THIS BEHAVIOR.
    public void readLines() throws DowFileReaderException, IOException {
        //This is a try with resources block.  Inside of it, you have auto-closeable things, like a buffered reader
        // You use this EVERY time there is a resource with auto-closeable abilities.
        try(FileReader fileReader = new FileReader(this.dowFile); //Here we make the file reader
            BufferedReader reader = new BufferedReader(fileReader)){  //Here we make a buffered reader
            String line = "";  //This will hold the lines from the file reader
            int linePos = 0;  //This will hold the position the data was taken from.
            while((line = reader.readLine()) != null){  //This complicated logic takes a line from the reader
                // and puts it into line.  Then checks to see if the line was null.
                //The line reader will return a null when eof hits.
                readAline(line, linePos);
                linePos++;
            }
        }
    }

    /**
     * Method to parse a single line
     */
    public void readAline(String line, int linePos) throws DowFileReaderException {
        //TODO: IMPLMENT THIS!!!!
    }

    //=============================================================================================
    // Minor Methods(s)
    //=============================================================================================

    public boolean validate(){
        return this.dowFile != null && this.dowFile.canRead();
    }

    //=============================================================================================
    // Getters and Setters
    //=============================================================================================

    /**
     * Just to get the File name.
     */
    public String getFileName(){
        return this.dowFile.getName();
    }

    /**
     * Just to get the file itself
     */
    public File getFile(){
        return this.dowFile;
    }

    public List<Double> getDowOpens(){
        return this.dowOpens;
    }
    public void setDowOpens(List<Double> opens){
        this.dowOpens = opens;
    }

    public List<Double> getDowHighs(){
        return this.dowHighs;
    }
    public void setDowHighs(List<Double> highs){
        this.dowHighs = highs;
    }

    public List<Double> getDowLows(){
        return this.dowLows;
    }
    public void setDowLows(List<Double> lows){
        this.dowLows = lows;
    }

    public List<Double> getDowCloses(){
        return this.dowLows;
    }
    public void setDowCloses(List<Double> closes){
        this.dowCloses = closes;
    }

    public List<Date> getDowDates(){
        return this.dowDates;
    }
    public void setDowDates(List<Date> dates){
        this.dowDates = dates;
    }
}
