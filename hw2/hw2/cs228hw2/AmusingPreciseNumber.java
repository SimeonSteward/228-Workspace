package cs228hw2;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import static java.lang.Character.isWhitespace;

/**
 * @author Simeon Steward
 * A infinatly precisc number which can do addition, subtraction, absolute value, neg
 */
public class AmusingPreciseNumber {
    private ArrayList<Integer> integers;
    private ArrayList<Integer> decimals;
    private boolean isPositive;

    /**
     * Constructor that makes amusing precise number out of an integer
     * @param numb to be copied
     */
    public AmusingPreciseNumber( int numb ){
        integers = new ArrayList<>();
        decimals = new ArrayList<>();
        String numbAsString = ((Integer) numb).toString();
        int i = setPositive(numbAsString);

        for (; i < numbAsString.length(); i++) {
            integers.add(charToInt(numbAsString.charAt(i)));
        }
        trim(this);
    }

    /**
     * Constructor which takes a string and converts it into a number
     * @param numb the number to be converted
     */
    public AmusingPreciseNumber( String numb ){
        integers=new ArrayList<>();
        decimals = new ArrayList<>();
        int i = setPositive(numb);

        for (; i < numb.length(); i++) {
            if(numb.charAt(i)=='.'){
                i++;
                break;
            }

            integers.add(charToInt(numb.charAt(i)));
        }
        for (; i < numb.length(); i++) {
            decimals.add(charToInt(numb.charAt(i)));
        }
        trim(this);
    }

    /**
     * Constructor that takes a reader and uses it to create a number
     * @param r the reader that will be used
     * @throws IOException
     */
    public AmusingPreciseNumber( Reader r ) throws IOException {
        StringBuilder builder = new StringBuilder();
        while(r.ready()){
            int num = r.read();
            char cur = (char) num;

            if(num==-1){
                break;
            } else if(!isWhitespace(cur)){
                builder.append(cur);
            }
        }
        r.close();
        String s = builder.toString();

        //I tried to use the string constructer but java didn't like it
        integers=new ArrayList<>();
        decimals = new ArrayList<>();
        int i = setPositive(s);

        for (; i < s.length(); i++) {
            if(s.charAt(i)=='.'){
                i++;
                break;
            }

            integers.add(charToInt(s.charAt(i)));
        }
        for (; i < s.length(); i++) {
            decimals.add(charToInt(s.charAt(i)));
        }
        trim(this);
    }

    /**
     * Creates a deep copy of numb
     * @param numb to be copied
     */
    public AmusingPreciseNumber( AmusingPreciseNumber numb ){
        integers = new ArrayList<>();
        decimals = new ArrayList<>();
        isPositive = numb.isPositive;
        for (int i = 0; i < numb.decimals.size(); i++) {
            decimals.add(numb.decimals.get(i));
        }
        for (int i = 0; i <numb.integers.size(); i++) {
            integers.add(numb.integers.get(i));
        }

    }

    /**
     * Checks whether the string can be used to create a number
     * @param numb the number to be checked
     * @return whether the string can be used to create a number
     */
    public static boolean isNumber(String numb){
        try{
            new AmusingPreciseNumber(numb);
        }catch (RuntimeException e){
            return false;
        }

        return true;
    }

    /**
     * Adds the number numb to itself
     * @param numb the number being added
     */
    public void add( AmusingPreciseNumber numb ){
        ArrayList<Integer> newIntegers = new ArrayList<>();
        ArrayList<Integer> newDecimals = new ArrayList<>();
        AmusingPreciseNumber mostPrecise = mostPrecise(numb,this);
        AmusingPreciseNumber leastPrecise = leastPrecise(numb,this);
        AmusingPreciseNumber largest = greatestMagnitude(numb,this);
        AmusingPreciseNumber smallest = smallestMagnitude(numb,this);
        //Same sign
        if(numb.isPositive==isPositive){
            int carryOver = 0;
            for(int i = mostPrecise.decimals.size()-1; i >= 0; i--){
                int toAdd = getVal(mostPrecise.decimals,i)+getVal(leastPrecise.decimals,i)+carryOver;
                //check whether carrying the one is appropriate
                carryOver=0;
                if(toAdd>9){
                    carryOver=1;
                    toAdd -= 10;
                }
                newDecimals.add(0,toAdd);
            }

            int offset = largest.integers.size()-smallest.integers.size();
            for (int i = largest.integers.size()-1; i >= 0; i--) {
                int toAdd = getVal(largest.integers,i)+getVal(smallest.integers,i-offset)+carryOver;

                carryOver=0;
                if(toAdd>9){
                    carryOver=1;
                    toAdd -= 10;
                }
                newIntegers.add(0,toAdd);
            }
            newIntegers.add(0,carryOver);

            //determine which number is most precise
            //copy over those precise values over until you get to the point where the second value is just as precise
            //start adding those two values carrying over an extra number if needed
        }
        //Opposite Signs
        else{
            isPositive = largest.isPositive;
            int carryOver = 0;
            for(int i = mostPrecise.decimals.size()-1; i >= 0; i--){
                int toAdd = getVal(largest.decimals,i)-getVal(smallest.decimals,i)+carryOver;
                //check whether carrying the one is appropriate
                carryOver=0;
                if(toAdd<0){
                    carryOver = -1;
                    toAdd += 10;
                }
                newDecimals.add(0,toAdd);
            }

            int offset = largest.integers.size()-smallest.integers.size();
            for (int i = largest.integers.size()-1; i >= 0; i--) {
                int toAdd = getVal(largest.integers,i)-getVal(smallest.integers,i-offset)+carryOver;

                carryOver=0;
                if(toAdd<0){
                    carryOver = -1;
                    toAdd += 10;
                }
                newIntegers.add(0,toAdd);
            }
            newIntegers.add(0,carryOver);
        }
        decimals = newDecimals;
        integers = newIntegers;
        trim(this);
    }

    /**
     * Compares two given numbers and returns the one with the least significant figures
     * @param numb1 the first number to compare
     * @param numb2 the second number to compare
     * @return the least precise number of the two parameters, numb2 if they are the same
     */
    private AmusingPreciseNumber leastPrecise(AmusingPreciseNumber numb1, AmusingPreciseNumber numb2) {
        if(numb1.decimals.size() < numb2.decimals.size()) {
            return numb1;
        }
        else{
            return numb2;
        }
    }

    /**
     * Compares two given numbers and returns the one with the most significant figures
     * @param numb1 the first number to compare
     * @param numb2 the second number to compare
     * @return the most precise number of the two parameters, numb1 if they are the same
     */
    private AmusingPreciseNumber mostPrecise(AmusingPreciseNumber numb1, AmusingPreciseNumber numb2) {
        if(numb1.decimals.size() < numb2.decimals.size()){
            return numb2;
        }
        else{
            return numb1;
        }
    }

    /**
     * Compares two given numbers and returns the one with the most digits
     * @param numb1 the first number to compare
     * @param numb2 the second number to compare
     * @return the number with the most digits of the two parameters, numb2 if they are the same
     */
    private AmusingPreciseNumber greatestMagnitude(AmusingPreciseNumber numb1, AmusingPreciseNumber numb2) {
        if(numb1.integers.size() > numb2.integers.size()){
            return numb1;
        }
        else if(numb1.integers.size() < numb2.integers.size()){
            return numb2;
        }else{
            //cycle through the integers
            for (int i = numb1.integers.size()-1; i > 0; i--) {
                if(numb1.integers.get(i) > numb2.integers.get(i)) {
                    return numb1;
                }
                if(numb1.integers.get(i) < numb2.integers.get(i)) {
                    return numb2;
                }
            }

            AmusingPreciseNumber leastPrecise = leastPrecise(numb1, numb2);
            for (int i = 0; i <leastPrecise.decimals.size(); i++) {
                if (numb1.decimals.get(i) > numb2.decimals.get(i)) {
                    return numb1;
                }
                if (numb1.decimals.get(i) < numb2.decimals.get(i)) {
                    return numb2;
                }
            }
            return mostPrecise(numb1, numb2);
        }
    }

    /**
     * Compares two given numbers and returns the one with the least digits
     * @param numb1 the first number to compare
     * @param numb2 the second number to compare
     * @return the number with the least digits of the two parameters, numb2 if they are the same
     */
    private AmusingPreciseNumber smallestMagnitude(AmusingPreciseNumber numb1, AmusingPreciseNumber numb2) {
        if(greatestMagnitude(numb1,numb2) == numb1){
            return numb2;
        }else {
            return numb1;
        }
    }

    /**
     * Subtracts numb from this number
     * @param numb the number to be subtracted
     */
    public void subtract( AmusingPreciseNumber numb ){
        add(negate(numb));
    }

    /**
     * Negates the number
     */
    public void negate(){
        isPositive = !isPositive;
        trim(this);
    }

    /**
     * Takes the absolute value of the number
     */
    public void abs(){
        isPositive = true;
    }

    /**
     * Static method which adds the two numbers and outputs the result in a new number
     * @param numb1 the 1st number to be added
     * @param numb2 the 2nd number to be added
     * @return the result
     */
    public static AmusingPreciseNumber add(AmusingPreciseNumber numb1,AmusingPreciseNumber numb2){
        AmusingPreciseNumber retVal = new AmusingPreciseNumber(numb1);
        retVal.add(numb2);
        return retVal;
    }

    /**
     * Static method which subtracts the two numbers and outputs the result in a new number
     * @param numb1 the number to be subtracted from
     * @param numb2 the number that is subtracted from the 1st number
     * @return the result
     */
    public static AmusingPreciseNumber subtract(AmusingPreciseNumber numb1,AmusingPreciseNumber numb2){
        AmusingPreciseNumber retVal = new AmusingPreciseNumber(numb1);
        retVal.subtract(numb2);
        return retVal;
    }

    /**
     * Creates a deep copy of numb and then negates it
     * @param numb the number to be negated
     * @return the negated number
     */
    public static AmusingPreciseNumber negate(AmusingPreciseNumber numb){
        AmusingPreciseNumber retVal = new AmusingPreciseNumber(numb);
        retVal.negate();
        return retVal;
    }

    /**
     * Creates a deep copy of numb and then take the absolute value of it
     * @param numb the number to be absolute valued
     * @return the absolute value of numb
     */
    public static AmusingPreciseNumber abs(AmusingPreciseNumber numb){
        AmusingPreciseNumber retVal = new AmusingPreciseNumber(numb);
        retVal.abs();
        return retVal;
    }

    /**
     * Converts the char into an int if it is the char value of an int
     * @param ch the char
     * @return the int
     */
    private static int charToInt(char ch){
        if((int)ch<48||ch>57){
            throw new RuntimeException();
        }
        return ((int) ch)-48;
    }

    /**
     * Converts the char into an int if it is the char value of an int
     * @param num the char
     * @return the int
     */
    private static char intToChar(int num){
        if((int)num<0||num>10){
            throw new RuntimeException();
        }
        return (char) (num+48);
    }

    /**
     * Checks weather the string starts with a '-' or a '+' and sets isPositive accordingly. If no '-' or '+', sets isPositive to true.
     * @param numbAsString the number in string format
     * @return the index where the numbers start
     */
    private int setPositive(String numbAsString){
        if(numbAsString.charAt(0)=='-') {
            isPositive = false;
            return 1;
        }
        else if(numbAsString.charAt(0)=='+'){
            isPositive = true;
            return 1;
        }
        else{
            isPositive = true;
            return 0;
        }
    }

    /**
     * Checks whether this AmusingPreciseNumber is the same as num
     * @param num the number to check with
     * @return whether this AmusingPreciseNumber is the same as num
     */
    public boolean equals(AmusingPreciseNumber num) {
        return (isPositive==num.isPositive&&integers==num.integers&&decimals==num.decimals);
    }

    @Override
    public String toString(){
        ArrayList<Character> characters = new ArrayList<>();
        if(!isPositive){
            characters.add('-');
        }
        if(integers.size()==0){
            characters.add('0');
        }
        for (Integer integer : integers) {
            characters.add(intToChar(integer));
        }
        if(decimals.size()>0){
            characters.add('.');
            for (Integer decimal : decimals) {
                characters.add(intToChar(decimal));
            }
        }
        StringBuilder builder = new StringBuilder(characters.size());
        for(Character character: characters){
            builder.append(character);
        }
        return builder.toString();
    }

    /**
     * Gets the value at index of arr, return 0 if out of bounds.
     * @param arr the array that will be examined
     * @param index the index to be examined
     * @return the value at index of arr, return 0 if out of bounds
     */
    private int getVal(ArrayList<Integer> arr, int index){
        if(index<0 || index>=arr.size()){
            return 0;
        }
        return arr.get(index);
    }

    /**
     * Trims the array so that there are no extra 0s at the beginning or end of the array.
     * 0 is represented by empty integers and decimals lists(0 is represented as positive)
     * @param numb the number to be trimmed
     */
    private static void trim(AmusingPreciseNumber numb){
        //Trims 0s from front of numb.Integers
        while (0<numb.integers.size()&&numb.integers.get(0)==0){
            numb.integers.remove(0);
        }

        //Trims 0s from end of numb.Decimals
        int i = numb.decimals.size()-1;
        while (numb.decimals.size()>0&&numb.decimals.get(i)==0){
            numb.decimals.remove(i--);
        }

        //0 is represented by integers and decimals being empty, is positive is true
        if(numb.decimals.size()==0&&numb.integers.size()==0){
            numb.isPositive = true;
        }
    }
}
