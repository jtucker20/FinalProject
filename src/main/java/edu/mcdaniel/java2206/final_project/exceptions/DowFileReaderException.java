package edu.mcdaniel.java2206.final_project.exceptions;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DowFileReaderException extends Exception {
    //TODO: MAKE THIS ACTUALLY WORK!

    //As instructed, this calls super and a string called message
    public DowFileReaderException(String message){
        super(message);
    }

    //Again, as per instruction, thi9s again calls super, this time for
    //both a message and a throwable object
    public DowFileReaderException(String msg, Throwable throwable){
        super(msg, throwable);
        Logger logger = LogManager.getLogger(DowFileReaderException.class);
        logger.error("The program failed to: {}", msg);
        logger.error(throwable);
    }
}


