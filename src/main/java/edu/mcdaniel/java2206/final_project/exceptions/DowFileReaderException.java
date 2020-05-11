package edu.mcdaniel.java2206.final_project.exceptions;


public class DowFileReaderException extends Exception {
    public DowFileReaderException(String msg){
        super(msg);
    }

    public DowFileReaderException(String msg, Throwable throwable){
        super(msg, throwable);
    }

}
