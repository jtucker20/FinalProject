package edu.mcdaniel.java2206.final_project_MalcolmWatts;

import java.util.*;

public class ListExercises {

    private int i = 0;

    public ListExercises(int i){
        this.i = i;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListExercises)) return false;
        ListExercises that = (ListExercises) o;
        return i == that.i;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i);
    }

    public static void main(String[] args) {
        List<String> listOfGroceries = new LinkedList<>();
//        listOfGroceries.add("Salt");
        listOfGroceries.add("A Dozen Eggs");
        listOfGroceries.add("Bread");
        listOfGroceries.add("Milk");
        listOfGroceries.add("Baking Soda");


        // At Store one, they found a dozen eggs and milk.
        listOfGroceries.remove("A Dozen Eggs");
        listOfGroceries.remove("Milk");

        //The person left store one and went to walmart.  Walmart had Bread.
        listOfGroceries.remove("Bread");

        // PRint out the remaining groceries.
        for(String s : listOfGroceries){
            System.out.println("Grocery remaining: " + s);
        }

        // A call comes in to ask for pecans and pens.
        listOfGroceries.add("Pecans");
        listOfGroceries.add("Pens");

        System.out.println("Now The Remaining Groceries are: ");
        for(String s : listOfGroceries){
            System.out.println(s);
        }

        //Suppose Target has Pens and Baking Soda
        listOfGroceries.remove("Pens");
        listOfGroceries.remove("Baking Soda");

        for(String s : listOfGroceries){
            System.out.println("What's left: " + s);
        }

        //Suppose 7/11 has a bag of stale pecans.
        listOfGroceries.remove("Pecans");

        System.out.println(listOfGroceries.size());
        for(String s : listOfGroceries){
            System.out.println("ANYTHING LEFT?: " + s);
        }


//
//        List<String> element = new ArrayList<>();
//        element.add("A");
//        element.add("B");
//        element.add("C");
//        element.add("B");
//        element.add("A");
//
//        for(int i = 0; i < 5; i++){
//            for(int j = 0; j < 5; j++){
//                if(i == j){
//                    continue;
//                }
//                if(element.get(i).equals(element.get(j))){
//                    System.out.println("Element i = " + i + " is equal to Element j = " + j);
//                }
//            }
//        }



        System.out.println("MAPS!______________________________");

        Map<String, Integer> stringIntegerMap = new HashMap<>();
        stringIntegerMap.put("Hi", 2);
        stringIntegerMap.put("Bye", 343924302);
        stringIntegerMap.put("Middle", ((343924300 / 2) + 2));

        System.out.println("The beginning number is: " + stringIntegerMap.get("Hi"));
        System.out.println("The middle number is: " + stringIntegerMap.get("Middle"));
        System.out.println("The end number is: " + stringIntegerMap.get("Bye"));


        ListExercises exercises1 = new ListExercises(0);
        ListExercises exercises2 = new ListExercises(1);
        ListExercises exercises3 = new ListExercises(2);
        Map<ListExercises, Integer> listExercisesIntegerMap = new HashMap<>();
        listExercisesIntegerMap.put(exercises1, 1);
        listExercisesIntegerMap.put(exercises2, 2);
        listExercisesIntegerMap.put(exercises3, 4);

        ListExercises key1 = new ListExercises(4);
        ListExercises key2 = new ListExercises(1);

        if (listExercisesIntegerMap.containsKey(key2)) {

            int value = listExercisesIntegerMap.get(key2);
            System.out.println("The value is: " + value);
        } else {
            System.out.println("The value wasn't found.");
        }


    }
}
