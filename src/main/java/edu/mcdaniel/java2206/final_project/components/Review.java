package edu.mcdaniel.java2206.final_project.components;

import org.springframework.boot.json.JsonParseException;

import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.*;

public class Review  {

    //Assets
    private String socialSecurityNumber;
    private PublicKey authorityKey;
    private String compoundMessage;

    //Constructors
    //Compiler MAKES this constructor:
    public Review(){
        this("Not Given");
    }

    public Review(String value){
        this(value, "Not Given");
    }

    public Review(String value, String message){
        //COMPILER INSERTS!
        super();
        if(value == null || message==null){
            throw new RuntimeException("Invalid setup.");
        }
        this.compoundMessage = value + " with the message " + message;
    }

//    public Review(PublicKey authorityKey, String socialSecurityNumber){
//        this.socialSecurityNumber = socialSecurityNumber;
//        this.authorityKey = authorityKey;
//    }

    //Major Methods

    public static void main(String[] args) {

//        Review review = new Review();
//        ReviewCentral centralReview = new ReviewCentral();
//
//        System.out.println(review.getCompoundMessage());
//        System.out.println(centralReview.getCompoundValue());
//        System.out.println((centralReview.getCompoundMessage()));

//        Review review = new Review();
//
//        try {
//            review.printMessage(null);
//        } catch (Exception e){
//            System.out.println(e);
//        } catch (JsonParseException jpe){
//            System.out.println(jpe.getMessage());
//        }

//        Random random = new Random();
//        String[] arrayString = new String[5023];
//        System.out.println("Length of Array: " + arrayString.length);
//        for(int i = 0; i <= arrayString.length; i++){
//            System.out.println("Line Number: " + i);
//            arrayString[i] = "Num: " + random.nextInt(100);
//        }





    }

    //Minor methods
    private String printMessage(String value) throws Exception{
        System.out.println(value);
        return (value.length() > 0) ? value.length() + "" : "Invalid";
    }

    //Getters and Setters

//    //Secure retrieval of SSN
//    public String getSocialSecurityNumber(X509Certificate certificate){
//        try {
//            certificate.checkValidity();
//            certificate.verify(authorityKey);
//            return socialSecurityNumber;
//
//        } catch (Exception e){
//            System.out.println(e);
//            return "INVALID";
//        }
//    }


    public String getCompoundMessage() {
        return compoundMessage;
    }

    public void setCompoundMessage(String compoundMessage) {
        this.compoundMessage = compoundMessage;
    }
}

class ReviewCentral extends Review {
    private String compoundValue;
    public ReviewCentral(){
        super("Default Value", "Default Message");
        compoundValue = super.getCompoundMessage();
        compoundValue += " From the Central Review Object.";
    }

    public String getCompoundValue(){
        return compoundValue;
    }
}




interface Face {
    String getFaceDescription();
}

interface HappyFace extends Face {
    String getSmileDescription();
//    String getFaceDescription();
}

interface Grin extends Face {
    double getSmileSize();
}

interface GiddyFace extends HappyFace, Grin {
    double getNumberOfHoursSpentSmilingThusFar();
}

abstract class Person implements GiddyFace {

    public abstract String getNumberOfHands();

    @Override
    public String getFaceDescription() {
        return null;
    }

    @Override
    public String getSmileDescription() {
        return null;
    }

    @Override
    public double getSmileSize() {
        return 0;
    }

    @Override
    public double getNumberOfHoursSpentSmilingThusFar() {
        return 0;
    }
}

class TallPerson extends Person{

    @Override
    public String getNumberOfHands() {
        return "Two Hand Luke.";
    }
}

class Runner{
    public static void main(String[] args) {

        TallPerson tallPerson = new TallPerson();
        Person person = new TallPerson();
//        Person person1 = new Person();
//        Person person2 = new Person() {
//            @Override
//            public String getNumberOfHands() {
//                return "null";
//            }
//        };


    }
}




class SymbolPrinter {
    private String symbol;
    private static final int MAX_PRINTED_SYMBOLS = 100;
    private int desiredPrintedSymbols;

    public SymbolPrinter(String symbol, int desSym){
        if(symbol == null || symbol.length() > 1 || symbol.isBlank()){
            this.symbol = "$";
        } else {
            this.symbol = symbol;
        }
        if(desSym > 0 && desSym < MAX_PRINTED_SYMBOLS) {
            this.desiredPrintedSymbols = desSym;
        } else {
            this.desiredPrintedSymbols = MAX_PRINTED_SYMBOLS;
        }
    }

    private String valueMaker(String input, int n){
        String output = input + this.symbol;
        if(n < MAX_PRINTED_SYMBOLS && n < this.desiredPrintedSymbols){
            System.out.println(output);
            output = valueMaker(output, n + 1);
        }
        return output;
    }

    public void printOut(){
        System.out.println(this.symbol);
        String output = valueMaker(this.symbol, 1);
        System.out.println(output);
    }
}

class SymbolPrinterTester{
    public static void main(String[] args) {
        SymbolPrinter printer = new SymbolPrinter("%", 10);
        printer.printOut();
    }
}



interface StackExampleAbstractDataTypeInterface<E> extends List<E>{

    boolean empty();
    E peek();
    E pop();
    E push(E item);
    int search(Object o);
}

class StackExampleAbstractDataType<E> implements StackExampleAbstractDataTypeInterface<E> {
    @Override
    public boolean empty() {
        return false;
    }

    @Override
    public E peek() {
        return null;
    }

    @Override
    public E pop() {
        return null;
    }

    @Override
    public E push(E item) {
        return null;
    }

    @Override
    public int search(Object o) {
        return 0;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(E e) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public void add(int index, E element) {

    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }
}

class StackExampleRunner{
    public static void main(String[] args) {

        StackExampleAbstractDataType<String> stackOfStrings = new StackExampleAbstractDataType<>();
        stackOfStrings.push("HI");
        String value = stackOfStrings.pop();

    }
}


