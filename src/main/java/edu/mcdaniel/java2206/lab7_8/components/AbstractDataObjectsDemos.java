package edu.mcdaniel.java2206.lab7_8.components;

import org.json.*;

import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AbstractDataObjectsDemos {


    private static String json =
            "{\n" +
            "    \"glossary\": {\n" +
            "        \"title\": \"example glossary\",\n" +
            "\t\t\"GlossDiv\": {\n" +
            "            \"title\": \"S\",\n" +
            "\t\t\t\"GlossList\": {\n" +
            "                \"GlossEntry\": {\n" +
            "                    \"ID\": \"SGML\",\n" +
            "\t\t\t\t\t\"SortAs\": \"SGML\",\n" +
            "\t\t\t\t\t\"GlossTerm\": \"Standard Generalized Markup Language\",\n" +
            "\t\t\t\t\t\"Acronym\": \"SGML\",\n" +
            "\t\t\t\t\t\"Abbrev\": \"ISO 8879:1986\",\n" +
            "\t\t\t\t\t\"GlossDef\": {\n" +
            "                        \"para\": \"A meta-markup language, used to create markup languages such as DocBook.\",\n" +
            "\t\t\t\t\t\t\"GlossSeeAlso\": [\"GML\", \"XML\"]\n" +
            "                    },\n" +
            "\t\t\t\t\t\"GlossSee\": \"markup\"\n" +
            "                }\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "}";

    public static void main(String[] args) throws IOException {


        String value = "Hello World";
        char[] hwArray = value.toCharArray();

        for(int i = 0; i < hwArray.length; i++){
            System.out.print(hwArray[i]);
        }

        System.out.println();

        JSONObject jsonObject = new JSONObject(json);

        System.out.println(jsonObject.getJSONObject("glossary").get("title"));

        List<String> listOfStrings = new ArrayList<>();

        listOfStrings.add("String one");
        listOfStrings.add("String two");

        listOfStrings.add(0, "String 3");

        listOfStrings.add(0,null);

        for(String v : listOfStrings){
            System.out.println(v);
        }

        boolean contains = listOfStrings.contains("String 3");
        if(contains){
            System.out.println("Found it!");
        } else {
            System.out.println("Didn't find it!");
        }

        String zeroListItem = listOfStrings.get(0);  // Should be null
        System.out.println(zeroListItem);

        String fourthListItem = listOfStrings.get(3); // Should be "String two";
        System.out.println(fourthListItem);

        int indexOfStringOne = listOfStrings.indexOf("String two");
        System.out.println(indexOfStringOne);

        List<String> subList = listOfStrings.subList(1,3);

        Object[] arrayOfObjects = listOfStrings.toArray();
        String[] arrayOfStrings = listOfStrings.toArray(new String[4]);

        System.out.println("_--------------------_");
        System.out.println(arrayOfStrings[0]);
        System.out.println(arrayOfStrings[1]);
        System.out.println(arrayOfStrings[2]);
        System.out.println(arrayOfStrings[3]);

        System.out.println("----------------------");

        for(String v : subList){
            System.out.println(v);
        }


        System.out.println("+++++++++++++++++++++++");
        List<String[]> stringArraysList = new ArrayList<>();
        String[] arrayOne = {"Hi", "Howdy"};
        String[] arrayTwo = {"Bye", "Good Bye"};
        String[] arrayThree = {"No", "NOOOO"};

        stringArraysList.add(arrayOne);
        stringArraysList.add(arrayTwo);
        stringArraysList.add(arrayThree);

        for(String[] array : stringArraysList){
            for(String val : array){
                System.out.print(val + " " );
            }
            System.out.println("\n");
        }

        Object[] objArray = stringArraysList.toArray();
        String[][] matrix = {(String[]) objArray[0], (String[])objArray[1], (String[])objArray[2]};

        System.out.println(matrix[2][1]);

        System.out.println(stringArraysList.get(2)[1]);


        List<List<String>> listOfLists = new ArrayList<>();
        List<String> subList1 = new ArrayList<>();
        subList1.add("Boogers and Fries");
        subList1.add("Chips and eyeballs");

        List<String> subList2 = new ArrayList<>();
        subList2.add("Fire and Brimstone");
        subList2.add("Jokes and Blokes");

        listOfLists.add(subList1);
        listOfLists.add(subList2);

        for(List<String> sList : listOfLists){
            for(String s : sList){
                System.out.println(s);
            }
        }

        List<String> stringList = listOfLists.get(0);

        List<File> fileList = new ArrayList<>();
        List<String> nonArrayList = new LinkedList<>();


        File file1 = new File(AbstractDataObjectsDemos.class.getClassLoader()
                .getResource("InfRate.csv").getFile());
        File file2 = new File(AbstractDataObjectsDemos.class.getClassLoader()
                .getResource("DJI.csv").getFile());

        fileList.add(file1);
        fileList.add(file2);
        try {
            for (File f : fileList) {
                if (f.exists()) {
                    String name = f.getCanonicalPath() + "_copy";
                    File newFile = new File(name);
                    newFile.createNewFile();

                    FileReader fileReader = new FileReader(f);
                    BufferedReader reader = new BufferedReader(fileReader);
                    FileWriter fileWriter = new FileWriter(newFile);
                    String lines;
                    while ((lines = reader.readLine()) != null) {
                        fileWriter.write(lines);
                        fileWriter.write("\n");
                    }
                    fileWriter.close();
                    reader.close();
                    fileReader.close();


                }
            }
        } catch (Exception e){
            System.out.println(e.getCause());

        }
    }
}
