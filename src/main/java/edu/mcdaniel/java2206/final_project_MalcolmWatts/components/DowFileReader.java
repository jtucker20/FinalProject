package edu.mcdaniel.java2206.final_project_MalcolmWatts.components;

import edu.mcdaniel.java2206.final_project_MalcolmWatts.exceptions.DowFileReaderException;
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
    //ENCAPSULATION
    private File dowFile;
    private List<Double> dowOpens;
    private List<Double> dowCloses;
    private List<Double> dowHighs;
    private List<Double> dowLows;
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
        try{
            readLines();
        } catch (Exception ioe){
            //If we get an exception of any type we need to stop execution and throw this information to the user.
//            throw new DowFileReaderException("Error parsing in the data!", ioe);
        }
    }

    /**
     * Line reader functionality
     */
    //DO NOT MODIFY THIS BEHAVIOR.
    public void readLines() throws DowFileReaderException {
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
        } catch(IOException ioe){
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
        //SETUP
        String[] lineSection = line.split(", ");
        String n = lineSection[0];

        //IF BLOCK
        if (lineSection.length >= 5) {

            //PARSE DATE
            Date dowDate = new Date(
                    Integer.parseInt(n.substring(0,4))-1900,
                    Integer.parseInt(n.substring(5,7))-1,
                    Integer.parseInt(n.substring(8,9))-1
            );

            //POSITION OF EACH STRING IN THE ARRAY
            String open = lineSection[1];
            String high = lineSection[2];
            String low = lineSection[3];
            String close = lineSection[4];

            //PARSE
            this.dowDates.add(dowDate);
            double dataOpen = Double.parseDouble(open);
            this.dowOpens.add(dataOpen);
            double dataHigh = Double.parseDouble(high);
            this.dowHighs.add(dataHigh);
            double dataLows = Double.parseDouble(low);
            this.dowLows.add(dataLows);
            double dataClose = Double.parseDouble(close);
            this.dowCloses.add(dataClose);
            System.exit(0);
        }

        //EXIT
        //else{
          //  System.exit(0);
        //}

    }


    //=============================================================================================
    // Minor Methods(s)
    //=============================================================================================



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
    //GETTERS
    public File getFile(){
        return this.dowFile;
    }
    public List<Double> getDowOpens(){
        return this.dowOpens;
    }
    public List<Double> getDowHighs(){
        return this.dowHighs;
    }
    public List<Double> getDowLows(){
        return this.dowLows;
    }
    public List<Double> getDowCloses(){
        return this.dowCloses;
    }
    public List<Date> getDowDates(){
        return this.dowDates;
    }

    //SETTERS
    public void setDowOpens(List<Double> dowOpens){
        this.dowOpens = dowOpens;
    }
    public void setDowHighs(List<Double> dowHighs){
        this.dowHighs = dowHighs;
    }
    public void setDowLows(List<Double> dowLows){
        this.dowLows = dowLows;
    }
    public void setDowCloses(List<Double> dowCloses){
        this.dowCloses = dowCloses;
    }
    public void setDowDates(List<Date> dowDates){
        this.dowDates = dowDates;
    }

}
