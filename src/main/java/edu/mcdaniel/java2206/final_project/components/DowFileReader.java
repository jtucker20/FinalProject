package edu.mcdaniel.java2206.final_project.components;

import edu.mcdaniel.java2206.final_project.exceptions.DowFileReaderException;
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

    /**
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
     * LinkedLists are used for fast list-building since the reader des not do much else with them...
     * ...but considering the Grapher does loop through the lists provided to it,...
     * I'm not sure whether ArrayLists<> should be used instead
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
        if(linePos == 0){
            return;
        }
        String[] entries = line.split(","); /*csv file, so each element is delimited by commas...*/

        if(entries.length >= 5){                  /*...requires that entries for date, open, high, low, and close are populated in each line...*/
            for(String entry : entries){
                if(entry.isBlank()) {
                    missingData(linePos, line);
                    return;                      /*...and skips otherwise*/
                    //throw new DowFileReaderException("Missing data in line " + linePos + " Line Value " + line);
                }
            }

                                                /*Declaring this counting variable outside of the scope of the try-catch statements...*/
            int i = 0;                          /* allows error message to provide... */
            try{                                /*...the exact entry that produced the error */
                dowDates.add(getDate(entries[i])); /*the code assumes relevant data to be in entries[0]...*/
                dowOpens.add(Double.parseDouble(entries[++i])); /*...entries[1]...*/
                dowHighs.add(Double.parseDouble(entries[++i])); /*...entries[2]...*/
                dowLows.add(Double.parseDouble(entries[++i]));  /*...entries[3]...*/
                dowCloses.add(Double.parseDouble(entries[++i]));/*...and entries[4], as is the case in the provided csv */
            }
            catch(Exception e){
                throw new DowFileReaderException("Bad data in " + entryPos(i) + " entry of line " + linePos + ": " + line);
            }                                   /*there aren't any entries in the dow file that would require this code, */
                                                /*but it seems like sensible to......trap for Format Exceptions*/

        }
        else {
            missingData(linePos, line);
        }
    }

    //=============================================================================================
    // Minor Methods(s)
    //=============================================================================================

    private boolean validate(){
        return this.dowFile != null && this.dowFile.canRead();
    }

    /**
     * entryPos() method converts index value to ordinal adjective (for clarity) when reporting the location of bad data
     */
    private String entryPos(int i){
        Map<Integer, String> entryPosMap = new HashMap<>();
        entryPosMap.put(0, "first");
        entryPosMap.put(1, "second");
        entryPosMap.put(2, "third");
        entryPosMap.put(3, "fourth");
        entryPosMap.put(4, "fifth");
        System.out.println(i);
        return entryPosMap.get(i);
    }

    /**
     * missingDate() logs an error message when it has to skip an underpopulated line in the .csv
     */
    private void missingData(int linePos, String line){
        log.error("Missing data in line " + linePos + " Line Value " + line);
    }

    /**
     * getDate() receives a string containing date from readALine() and returns a Date object
     */
    private Date getDate(String dateEntry) throws NumberFormatException {
        String[] dateValues = dateEntry.split("-");  /*...and is in yyyy-mm-dd format, as is the case with the given .csv */

        int year = Integer.parseInt(dateValues[0]) - 1900;  /*Date class requires that 1900 be subtracted from the year... */
        int month = Integer.parseInt(dateValues[1]) - 1;    /*...and that the month value range from 0 to 11...*/
        int day = Integer.parseInt(dateValues[2]);
        return new Date(year, month, day);                  /*...when initializing a Date object with integers*/
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

    /**
     * gets list of Dow open values
     */
    public List<Double> getDowOpens(){
        return this.dowOpens;
    }

    /**
     * allows client program to set Dow open values list, for whatever reason
     */
    public void setDowOpens(List<Double> opens){
        this.dowOpens = opens;
    }

    /**
     * gets list of Dow high values
     */
    public List<Double> getDowHighs(){
        return this.dowHighs;
    }

    /**
     * allows client program to set Dow high values list, for whatever reason
     */
    public void setDowHighs(List<Double> highs){
        this.dowHighs = highs;
    }

    /**
     * gets list of Dow low values
     */
    public List<Double> getDowLows(){
        return this.dowLows;
    }

    /**
     * allows client program to set Dow low values list, for whatever reason
     */
    public void setDowLows(List<Double> lows){
        this.dowLows = lows;
    }

    /**
     * gets list of Dow close values
     */
    public List<Double> getDowCloses(){
        return this.dowLows;
    }

    /**
     * allows client program to set Dow close values, for whatever reason
     */
    public void setDowCloses(List<Double> closes){
        this.dowCloses = closes;
    }

    /**
     * gets list of Dow dates
     */
    public List<Date> getDowDates(){
        return this.dowDates;
    }

    /**
     * allows client program to set Dow dates, for whatever reason
     */
    public void setDowDates(List<Date> dates){
        this.dowDates = dates;
    }
}
