package edu.mcdaniel.java2206.final_project.components;

import java.util.*;

public class Demo {
    public static final int MAX_RECURSIONS = 50;

    public String valueMaker(String input, int n){
        System.out.println(input);
        String output = input + "$";
        if(n < MAX_RECURSIONS){
            output = valueMaker(output, n + 1);
        }
        return output;
    }

    public static void main(String[] args) {
        System.out.println("End Result:" + (new Demo()).valueMaker("$", 0));

        Map<String, Integer> stringIntegerMap = new HashMap<>();
        stringIntegerMap.put("First", Integer.valueOf(1));
        stringIntegerMap.put("Second", Integer.valueOf(2));
        stringIntegerMap.put("Third", Integer.valueOf(3));
        stringIntegerMap.put("Fourth", Integer.valueOf(589));

        Set<Map.Entry<String, Integer>> entrySet = stringIntegerMap.entrySet();

        for(Map.Entry<String, Integer> entry : entrySet){
            System.out.println("Place: " + entry.getKey() + " is held by runner number "
                    + entry.getValue());
        }



        Map<String, Date> stringDatesMap = new HashMap<>();
        stringDatesMap.put("First", new Date());
        stringDatesMap.put("Second", new Date());
        stringDatesMap.put("Third", new Date());
        stringDatesMap.put("Fourth", new Date());

        Set<Map.Entry<String, Date>> dateEntrySet = stringDatesMap.entrySet();

        for(Map.Entry<String, Date> entry : dateEntrySet){
            System.out.println("For: " + entry.getKey() + " is held by date "
                    + entry.getValue());
        }

        Iterator<Map.Entry<String, Date>> iterator = stringDatesMap.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, Date> currValue = iterator.next();
            System.out.println("For position: " + currValue.getKey() +
                    " Has the date " + currValue.getValue());
        }


    }
}
