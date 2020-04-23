package edu.mcdaniel.java2206.final_project.exceptions;

import java.io.IOException;

public class FileWriterException extends RuntimeException {

    public FileWriterException(String s, IOException ioe) {
        super(s, ioe);
    }
}
