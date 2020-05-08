package edu.mcdaniel.java2206.lab7_8;

import edu.mcdaniel.java2206.lab7_8.components.CombinedFileWriter;
import edu.mcdaniel.java2206.lab7_8.components.Converter;
import edu.mcdaniel.java2206.lab7_8.components.DowFileReader;
import edu.mcdaniel.java2206.lab7_8.components.InflationRateFileReader;
import edu.mcdaniel.java2206.lab7_8.exceptions.DowFileReaderException;
import edu.mcdaniel.java2206.lab7_8.exceptions.FileWriterException;
import edu.mcdaniel.java2206.lab7_8.exceptions.InflationRateFileReaderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.*;

/**
 * This class is designed to start/wrap your class.
 */
@SpringBootApplication
public class Application {

    //=============================================================================================
    // Private Assets
    //=============================================================================================

    /**
     * The logging mechanism of the class.
     */
    private static Logger log = LogManager.getLogger(Application.class);


    //=============================================================================================
    // Constructor(s)
    //=============================================================================================

    /**
     * The constructor for the Spring Boot application
     */
    public Application(){
        //This constructor is empty as no additional information need be provided.
        //This is an implemented No Argument Constructor.
    }


    //=============================================================================================
    // Major Methods
    //=============================================================================================

    //No major methods to implement


    //=============================================================================================
    // PSVM
    //=============================================================================================

    /**
     * This method actually accomplishes the running of the code we are seeking to write
     * @param args the input from the command line.
     */
    public static void main(String[] args) {

        //===// Spring Application Hook //=======================================================//
        SpringApplication.run(Application.class, args);

        //===// User Defined Behavior //=========================================================//
        //Please start here and make the application work!
        Converter converter = new Converter();

        try {
            InflationRateFileReader inflationRateFileReader = new InflationRateFileReader();
            inflationRateFileReader.validate();

            inflationRateFileReader.setUp();

            inflationRateFileReader.read();

            Map<Integer, Double> inflationRates = inflationRateFileReader.getInflationRates();
            Map<Integer, Date> inflationDates = inflationRateFileReader.getInflationDates();

            converter.inputInflationData(inflationRates, inflationDates);

            Set<Map.Entry<Integer, Double>> entrySet = inflationRates.entrySet();
            for (Map.Entry<Integer, Double> entry : entrySet) {
                log.info("The position {} is associated with an average inflation rate of {}% for year {}.",
                        entry.getKey(), entry.getValue(),
                        inflationDates.get(entry.getKey()));

            }

        } catch (NullPointerException | InflationRateFileReaderException | IOException npe){
            log.error(npe);

        }

        try{
            DowFileReader dowFileReader = new DowFileReader();
            dowFileReader.validate();
            dowFileReader.setUp();
            dowFileReader.read();

            List<Double> opens = dowFileReader.getDowOpens();
            List<Double> highs = dowFileReader.getDowHighs();
            List<Double> lows = dowFileReader.getDowLows();
            List<Double> closes = dowFileReader.getDowClose();
            List<Date> dates = dowFileReader.getDowDates();

            converter.inputDowData(opens, highs, lows, closes, dates);

            Set<Map.Entry<Integer, Double>> entrySet = opens.entrySet();
            for (Map.Entry<Integer, Double> entry : entrySet) {
                log.info("The Open {}. The High {}. The Low {}. The Close {}. For {}",
                                entry.getValue(),
                                highs.get(entry.getKey()),
                                lows.get(entry.getKey()),
                                closes.get(entry.getKey()),
                                dates.get(entry.getKey()).toString()
                                );

            }
        } catch (DowFileReaderException dfre){
            log.error(dfre);
        }

        CombinedFileWriter combinedFileWriterInflation =
                new CombinedFileWriter("InflationData", "txt")
                        .withNamedFile().validate();
        CombinedFileWriter combinedFileWriterDow =
                new CombinedFileWriter("DowData", "txt")
                        .withNamedFile().validate();
        List<String> infoToWriteToInfFile = converter.getConvertedInflationInfo();
        List<String> infoToWriteToDowFile = converter.getConvertedDowInfo();

        boolean wrote = false;
        try {
             wrote = combinedFileWriterInflation.writeFileContents(infoToWriteToInfFile);
        } catch(IOException ioe){
            log.error(new FileWriterException("Failed to Write Inf File.", ioe));
        }

        try {
            wrote = wrote && combinedFileWriterDow.writeFileContents(infoToWriteToDowFile);
        } catch(IOException ioe){
            log.error(new FileWriterException("Failed to Write Dow File.", ioe));
        }

        if(wrote){
            log.info("SUCCESS!!!!!");
        }

    }


    //=============================================================================================
    // Minor Methods(s)
    //=============================================================================================

    //No minor methods made for this class


    //=============================================================================================
    // Getters and Setters
    //=============================================================================================

    //No private assets we want to expose in this class

}
