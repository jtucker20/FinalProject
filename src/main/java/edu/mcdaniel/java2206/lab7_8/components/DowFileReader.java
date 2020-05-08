package edu.mcdaniel.java2206.lab7_8.components;

import edu.mcdaniel.java2206.lab7_8.exceptions.DowFileReaderException;
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

    /**
     * Field that will hold the values of Dow Opens
     */
    private List<Double> dowOpens;

    /**
     * Field that will hold the values of Dow highs
     */
    private List<Double> dowHighs;

    /**
     * Field that will hold the values of Dow lows
     */
    private List<Double> dowLows;

    /**
     * Field that will hold the values of Dow closes
     */
    private List<Double> dowClose;

    /**
     * Field that will hold the dates corresponding to the integer
     */
    private List<Date> dowDates;


    //=============================================================================================
    // Constructor(s)
    //=============================================================================================

    /**
     * This No Argument constructor Will use the internal file.
     */
    public DowFileReader() throws NullPointerException {
        // If this is called, we will use the internal file
        this.dowFile = new File(
                this.getClass().getClassLoader().getResource("DJI.csv").getFile()
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
           throw new DowFileReaderException("Invalid File Setup.");
        }

        //Here we setup/instantiate the Map as a hash map to hold the data.
        this.dowOpens = new ArrayList<Double>();
        this.dowHighs = new ArrayList<Double>();
        this.dowLows  = new ArrayList<Double>();
        this.dowClose = new ArrayList<Double>();

        this.dowDates = new ArrayList<Date>();
    }

    /**
     * Major method to read in the data
     */
    public void read() throws DowFileReaderException {
        //TODO: FINISH THIS CHECK!!!
        if(!validate() || this.dowOpens == null || this.dowHighs == null
            || this.dowLows == null || this.dowClose == null || this.dowDates == null){
            //We validate that all parts are actually active
            throw new DowFileReaderException("Invalid Setup!");
        }

        //Once we validate things are good, we try to read the lines of the file
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
        if (linePos < 0) {
            throw new DowFileReaderException("Bad Line Position: " + linePos);
        }
        if (linePos < 2) {
            return;  // We don't want to read in the header lines!
        }
        String[] lineParts = line.split(","); // Here we split on commas as this file is comma
        // separated.

        //I am expecting that there will be 14 columns, All filled with data.
        if (lineParts.length == 7) {
            String date = lineParts[0]; //Since the year is at pos 0;
            String open = lineParts[1]; //Since the average is at pos 1;
            String high = lineParts[2]; //Since the average is at pos 2;
            String low = lineParts[3]; //Since the average is at pos 3;
            String close = lineParts[4];//Since the average is at pos 4;

            //Now to check we have values we are expecting!
            if (date == null || date.isBlank() || date.isEmpty() || open == null || open.isBlank() || open.isEmpty()
                    || high == null || high.isBlank() || high.isEmpty() || low == null || low.isBlank() || low.isEmpty()
                    || close == null || close.isBlank() || close.isEmpty()) {
                throw new DowFileReaderException("Bad Data in line " + linePos + " Line Value " + line);

            }

            //Date Parsing
            String year = date.substring(0, 4);
            String month = date.substring(5,7);
            String day = date.substring(8);
            int yr = Integer.parseInt(year) - 1900;
            int mn = Integer.parseInt(month) - 1;
            int dy = Integer.parseInt(day);
            Date dt = new Date(yr, mn, dy);
            this.dowDates.add(linePos, dt);

            //Open
            double opn = Double.parseDouble(open);
            this.dowOpens.add(linePos, opn);

            //High
            double hgh = Double.parseDouble(high);
            this.dowHighs.add(linePos, hgh);

            //Low
            double lw = Double.parseDouble(low);
            this.dowLows.add(linePos, lw);

            //Close
            double cls = Double.parseDouble(close);
            this.dowClose.add(linePos, cls);
        }
    }

    //=============================================================================================
    // Minor Methods(s)
    //=============================================================================================

    /**
     * validation method.
     * @return true if valid.
     */
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

    public List<Double> getDowOpens() {
        return dowOpens;
    }

    public void setDowOpens(List<Double> dowOpens) {
        this.dowOpens = dowOpens;
    }

    public List<Double> getDowHighs() {
        return dowHighs;
    }

    public void setDowHighs(List<Double> dowHighs) { this.dowHighs = dowHighs; }

    public List<Double> getDowLows() {
        return dowLows;
    }

    public void setDowLows(List<Double> dowLows) {
        this.dowLows = dowLows;
    }

    public List<Double> getDowClose() {
        return dowClose;
    }

    public void setDowClose(List<Double> dowClose) {
        this.dowClose = dowClose;
    }

    public List<Date> getDowDates() {
        return dowDates;
    }

    public void setDowDates(List<Date> dowDates) {
        this.dowDates = dowDates;
    }
}