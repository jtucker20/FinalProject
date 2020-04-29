package edu.mcdaniel.java2206.final_project.exceptions;


public class DowFileReaderException extends Exception {
    public DowFileReaderException(String message){
        super(message);
    }
    public DowFileReaderException(String message, Exception e){
        super(message, e); //
    }

}
