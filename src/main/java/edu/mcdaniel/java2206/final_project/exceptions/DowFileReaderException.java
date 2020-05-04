package edu.mcdaniel.java2206.final_project.exceptions;


import edu.mcdaniel.java2206.final_project.components.DowFileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DowFileReaderException extends Exception {
    //TODO: MAKE THIS ACTUALLY WORK!

    /* Implementing the class with two constructors in which one with a String argument,
        and the other with a String and an Exception argument. Each constructor calls the correct super method
     */

    public DowFileReaderException() {
        super();
    }
    public DowFileReaderException(String message) {
        super (message);
    }

    public DowFileReaderException(String msg, Throwable throwable){
        super(msg, throwable);
        Logger logger = LogManager.getLogger(DowFileReader.class);
        logger.error("The program failed to: {}", msg);
        logger.error(throwable);
    }

}
