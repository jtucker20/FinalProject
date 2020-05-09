package edu.mcdaniel.java2206.final_project.exceptions;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class DowFileReaderException extends Exception {
    //TODO: MAKE THIS ACTUALLY WORK!

    public DowFileReaderException(String message){
        super (message);
    }
    public DowFileReaderException(String message, Throwable throwable){
        super(message, throwable);
        Logger logger = LogManager.getLogger(DowFileReaderException.class);
        logger.error("The program failed to: {}", message);
        logger.error(throwable);
    }

}
