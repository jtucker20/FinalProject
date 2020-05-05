package edu.mcdaniel.java2206.final_project.components;

import java.awt.*;
import java.util.Random;

public class LambdaTester {

    interface MessagePrinter {
        void messagePrinting(String part1, String part2);
    }

    interface SomeRandomType {
        void doSomething();
    }

    public static void main(String[] args) {

        SomeRandomType type = () -> {
            System.out.println("We are inside of a Lambda");
        };

        type.doSomething();

        //_________________________________________________________________________

        SomeRandomType bobbin = () -> {
            Random random = new Random();

            final String ANSI_RESET = "\u001B[0m";
            final String ANSI_BLACK = "\u001B[30m";
            final String ANSI_RED = "\u001B[31m";
            final String ANSI_GREEN = "\u001B[32m";
            final String ANSI_YELLOW = "\u001B[33m";
            final String ANSI_BLUE = "\u001B[34m";
            final String ANSI_PURPLE = "\u001B[35m";
            final String ANSI_CYAN = "\u001B[36m";
            final String ANSI_WHITE = "\u001B[37m";

            int times = random.nextInt(500);

            String message1 = ANSI_CYAN + "The bobbin found itself wound ";
            String message2 = ANSI_BLUE + times;
            String message3 = ANSI_PURPLE + " times round by the ";
            String message4 = ANSI_RED + "red " + ANSI_GREEN + "thread." + ANSI_RESET;

            System.out.println(message1 + message2 + message3 + message4);
        };

        bobbin.doSomething();

        //_________________________________________________________________________


        String[] values = {"Hello", "Hi", "Hola"};

        MessagePrinter messagePrinterType1 = (msgPart1, msgPart2) -> {
            String message = msgPart1 + " is a way of saying " + msgPart2;
            System.out.println(message);
        };

        messagePrinterType1.messagePrinting(values[0], values[1]);

        //_________________________________________________________________________

        MessagePrinter issuePrinter = (message, issue) -> {
            String result = "We have the following message: " + message;
            result = result + "\nAnd this occurred because of this issue: " + issue;

            System.out.println(result);
        };

        issuePrinter.messagePrinting("Go fly a kite!", "It is sunny outside!");







    }


}
