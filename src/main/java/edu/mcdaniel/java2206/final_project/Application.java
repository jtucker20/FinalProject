package edu.mcdaniel.java2206.final_project;

import edu.mcdaniel.java2206.final_project.components.*;
import edu.mcdaniel.java2206.final_project.exceptions.DowFileReaderException;
import edu.mcdaniel.java2206.final_project.exceptions.FileWriterException;
import edu.mcdaniel.java2206.final_project.exceptions.InflationRateFileReaderException;
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
        DowFileReader dowFileReader;
        List<Double> highs = null;
        List<Date> dates = null;
        try{
            dowFileReader = new DowFileReader();
            dowFileReader.setUp();
            dowFileReader.read();
            highs = dowFileReader.getDowHighs();
            dates = dowFileReader.getDowDates();
        } catch (DowFileReaderException dfre){
            log.error(dfre);
            throw new RuntimeException("Failed to run dow Reader", dfre);
        }

        InflationRateFileReader inflationRateFileReader;
        List<Double> rates = null;
        List<Date> infDates = null;
        try{
            inflationRateFileReader = new InflationRateFileReader();
            inflationRateFileReader.setUp();
            inflationRateFileReader.read();

            rates    = inflationRateFileReader.getInflationRates();
            infDates = inflationRateFileReader.getInflationDates();
        } catch (IOException | InflationRateFileReaderException irfre){
            log.error(irfre);
            throw new RuntimeException("Failed to run inflation rate Reader", irfre);
        }
        Grapher grapher = new Grapher(highs, rates, dates, infDates);
        grapher.convert();
        String graph = grapher.drawLineGraph();

        System.out.println(graph);
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
