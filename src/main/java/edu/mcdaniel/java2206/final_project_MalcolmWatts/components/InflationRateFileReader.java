package edu.mcdaniel.java2206.final_project_MalcolmWatts.components;

import edu.mcdaniel.java2206.final_project_MalcolmWatts.exceptions.InflationRateFileReaderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.*;

/**
 * This class will read a file.  We will manipulate the data later.
 */
public class InflationRateFileReader {

    //=============================================================================================
    // Private Assets
    //=============================================================================================

    /**
     * This provides access to logging.
     */
    private final Logger log = LogManager.getLogger(InflationRateFileReader.class);

    /**
     * File that will hold the link to the Inflation Rate Data
     */
    private File irFile;

    /**
     * Field that will hold the values of interest
     */
    private List<Double> inflationRates;

    /**
     * Field that will hold the dates corresponding to the integer
     */
    private List<Date> inflationDates;


    //=============================================================================================
    // Constructor(s)
    //=============================================================================================

    /**
     * This No Argument constructor Will use the internal file.
     */
    public InflationRateFileReader() throws NullPointerException {
        // If this is called, we will use the internal file
        this.irFile = new File(
                this.getClass().getClassLoader().getResource("InfRate.csv").getFile()
        );
        //NOTE!  YOU MUST CATCH FOR A BAD INITIALIZATION OF THIS FILE!! IT WILL THROW A
        //Null pointer exception if the file isn't found!
    }

    /*
     * This one argument constructor will use the provided file.
     */
    public InflationRateFileReader(File file){
        this.irFile = file;  //Assumes this is not null...
    }


    //=============================================================================================
    // Major Methods
    //=============================================================================================

    /**
     * This major method initializes the file.
     */
    public void setUp() throws InflationRateFileReaderException {
        if(!validate()){
           throw new InflationRateFileReaderException("Invalid File Setup.");
        }

        //Here we setup/instantiate the Map as a hash map to hold the data.
        this.inflationRates = new ArrayList<>();
        this.inflationDates = new ArrayList<>();
    }

    /**
     * Major method to read in the data
     */
    public void read() throws IOException {
        if(!validate()){
            if(this.irFile == null){
                throw new IOException("The Interest Rate File is null!");
            }
            if(!this.irFile.canRead()){
                throw new IOException("The Interest Rate File cannot be read!");
            }
        }

        log.trace("File Validated. {}", this.irFile.getAbsolutePath());
        if(this.inflationRates == null){
            this.inflationRates = new ArrayList<>();
        }
        if(this.inflationDates == null){
            this.inflationDates = new ArrayList<>();
        }

        //Once we validate things are good, we try to read the lines of the file
        readLines();

    }

    /**
     * Line reader functionality
     */
    public void readLines() throws IOException {
        //This is a try with resources block.  Inside of it, you have auto-closeable things, like a buffered reader
        // You use this EVERY time there is a resource with auto-closeable abilities.

        try(FileReader fileReader = new FileReader(this.irFile); //Here we make the file reader
            BufferedReader reader = new BufferedReader(fileReader)){  //Here we make a buffered reader

            String line = "";  //This will hold the lines from the file reader
            int linePos = 0;  //This will hold the position the data was taken from.
            while((line = reader.readLine()) != null){  //This complicated logic takes a line from the reader
                // and puts it into line.  Then checks to see if the line was null.
                //The line reader will return a null when eof hits.

                //Lets say a requirement is that we skip lines we cannot read.
                try {
                    //Here we read a line into our data stream.
                    readAline(line, linePos); //This function is called for every line.
                } catch (InflationRateFileReaderException irfre){
                    log.error("Skipped a line! {}", linePos);
                    log.error(irfre);
                }
                //Here we increment to let us know we got to a new line.
                linePos++;
            }
        }
    }

    /**
     * Method to parse a single line
     */
    public void readAline(String line, int linePos) throws InflationRateFileReaderException {
        if(linePos < 0){
            throw new InflationRateFileReaderException("Bad Line Position: " + linePos);
        }
        if(linePos < 3){
            return;  // We don't want to read in the header lines!
        }
        String[] lineParts = line.split(","); // Here we split on commas as this file is comma
        // separated.

        //I am expecting that there will be 14 columns, All filled with data.
        if(lineParts.length == 14) {
            String yr  = lineParts[0]; //Since the year is at pos 0;
            String jan = clean(lineParts[1]);
            String feb = clean(lineParts[2]);
            String mar = clean(lineParts[3]);
            String apr = clean(lineParts[4]);
            String may = clean(lineParts[5]);
            String jun = clean(lineParts[6]);
            String jul = clean(lineParts[7]);
            String aug = clean(lineParts[8]);
            String sep = clean(lineParts[9]);
            String oct = clean(lineParts[10]);
            String nov = clean(lineParts[11]);
            String dec = clean(lineParts[12]);
//            String avg = clean(lineParts[13]); //Since the average is at pos 13.

            //Now to check we have values we are expecting!
            if(yr == null || yr.isBlank() || yr.isEmpty()){
                throw new InflationRateFileReaderException("Bad Data in line "
                        + linePos + " Line Value " + line);
            }

            int year = (Integer.parseInt(yr) - 1900);
            if(year < (2014-1900)){
                return;
            }
            //Here we set the dates
            Date dateJan = new Date(year, Calendar.JANUARY, 31);
            Date dateFeb = new Date(year, Calendar.FEBRUARY, 28);
            Date dateMar = new Date(year, Calendar.MARCH, 31);
            Date dateApr = new Date(year, Calendar.APRIL, 30);
            Date dateMay = new Date(year, Calendar.MAY, 31);
            Date dateJun = new Date(year, Calendar.JUNE, 30);
            Date dateJul = new Date(year, Calendar.JULY, 31);
            Date dateAug = new Date(year, Calendar.AUGUST, 31);
            Date dateSep = new Date(year, Calendar.SEPTEMBER, 30);
            Date dateOct = new Date(year, Calendar.OCTOBER, 31);
            Date dateNov = new Date(year, Calendar.NOVEMBER, 30);
            Date dateDec = new Date(year, Calendar.DECEMBER, 31);

            this.inflationDates.add(dateDec);
            this.inflationDates.add(dateNov);
            this.inflationDates.add(dateOct);
            this.inflationDates.add(dateSep);
            this.inflationDates.add(dateAug);
            this.inflationDates.add(dateJul);
            this.inflationDates.add(dateJun);
            this.inflationDates.add(dateMay);
            this.inflationDates.add(dateApr);
            this.inflationDates.add(dateMar);
            this.inflationDates.add(dateFeb);
            this.inflationDates.add(dateJan);


            //Here we set the double
            double valueDec = Double.parseDouble(dec);
            this.inflationRates.add(valueDec);

            double valueNov = Double.parseDouble(nov);
            this.inflationRates.add(valueNov);

            double valueOct = Double.parseDouble(oct);
            this.inflationRates.add(valueOct);

            double valueSep = Double.parseDouble(sep);
            this.inflationRates.add(valueSep);

            double valueAug = Double.parseDouble(aug);
            this.inflationRates.add(valueAug);

            double valueJul = Double.parseDouble(jul);
            this.inflationRates.add(valueJul);

            double valueJun = Double.parseDouble(jun);
            this.inflationRates.add(valueJun);

            double valueMay = Double.parseDouble(may);
            this.inflationRates.add(valueMay);

            double valueApr = Double.parseDouble(apr);
            this.inflationRates.add(valueApr);

            double valueMar = Double.parseDouble(mar);
            this.inflationRates.add(valueMar);

            double valueFeb = Double.parseDouble(feb);
            this.inflationRates.add(valueFeb);

            double valueJan = Double.parseDouble(jan);
            this.inflationRates.add(valueJan);

//            log.info("We had date: {}, and rate: {}", dateDec.toString(), valueDec);

        } else if(lineParts.length == 0 ) {
            throw new InflationRateFileReaderException("Couldn't read line " + linePos);
        } else {
            log.error("Line " + linePos + " was " + lineParts.length + " long and couldn't be read, it's being skipped");
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
        return this.irFile != null && this.irFile.canRead();
    }

    /**
     * Percent from string remover
     */
    private String clean(String input){
        return input.substring(0, input.indexOf('%'));
    }

    //=============================================================================================
    // Getters and Setters
    //=============================================================================================

    /**
     * Just to get the File name.
     */
    public String getFileName(){
        return this.irFile.getName();
    }

    /**
     * Just to get the file itself
     */
    public File getFile(){
        return this.irFile;
    }

    public List<Double> getInflationRates() {
        return inflationRates;
    }

    public void setInflationRates(List<Double> inflationRates) {
        this.inflationRates = inflationRates;
    }

    public List<Date> getInflationDates() {
        return inflationDates;
    }

    public void setInflationDates(List<Date> inflationDates) {
        this.inflationDates = inflationDates;
    }
}
