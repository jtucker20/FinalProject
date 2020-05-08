package edu.mcdaniel.java2206.lab7_8.exceptions;


public class DowFileReaderException extends Exception {

    public DowFileReaderException(){
        super();
    }

    public DowFileReaderException(String msg){
        super(msg);
    }

    public DowFileReaderException(String msg, Throwable throwable){
        super(msg, throwable);
    }
}
