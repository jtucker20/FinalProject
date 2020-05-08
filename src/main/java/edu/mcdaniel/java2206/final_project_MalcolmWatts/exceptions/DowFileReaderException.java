package edu.mcdaniel.java2206.final_project_MalcolmWatts.exceptions;
//IMPORTS
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class DowFileReaderException extends Exception {
    public DowFileReaderException(String message){
        super(message);
    }

    public DowFileReaderException(String msg, Throwable throwable){
        super(msg, throwable);
        Logger logger = LogManager.getLogger(DowFileReaderException.class);
        logger.error("The program failed to: {}", msg);
        logger.error(throwable);
    }
}