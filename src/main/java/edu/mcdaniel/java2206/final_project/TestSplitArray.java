package edu.mcdaniel.java2206.final_project;

public class TestSplitArray {
    public static void main(String[] args) {

        String line = "A,B,C,D,E,F";
        String[] entries = line.split(",");
        System.out.println(entries.length);

        line = "A,B,C,,E,F";
        entries = line.split(",");
        System.out.println(entries.length);


        line = "A,B,C,D,E,F,";
        entries = line.split(",");
        System.out.println(entries.length);

        line = "A,B,C,D,E,F, ";
        entries = line.split(",");
        System.out.println(entries.length);

        line = "A,B,C,D,E,F," + null;
        entries = line.split(",");
        System.out.println(entries.length);
    }
}
