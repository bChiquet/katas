package com.kata.chop;

import java.util.Arrays;
import java.util.SortedSet;

/**
 * Kata implementing binary chop
 * http://codekata.com/kata/kata02-karate-chop/
 **/
public class BinaryChopMethods {
    public static class ValueNotInArrayException extends Exception{}
    public static class EmptyArrayException extends Exception{}
    // As a binary search is O(log(n)) and checking the order is O(n), this check can't be done.
    public static class ListNotOrderedException extends Exception{}

    // Day one : the classic recursive function.
    public int recursiveBinaryChop(int integerLookedFor, int[] integerArray)
            throws EmptyArrayException, ValueNotInArrayException {
        if (integerArray.length == 0){
            throw new EmptyArrayException();
        }
        if (integerArray.length == 1 && integerArray[0] != integerLookedFor){
            throw new ValueNotInArrayException();
        }
        int middleOfArray = integerArray.length/2;
        if (integerArray[middleOfArray] == integerLookedFor){
            return middleOfArray;
        }
        if (integerLookedFor < integerArray[middleOfArray]){
            return recursiveBinaryChop(integerLookedFor, Arrays.copyOfRange(integerArray, 0, middleOfArray));
        }
        else {
            return recursiveBinaryChop(integerLookedFor, Arrays.copyOfRange(integerArray , middleOfArray +1, integerArray.length))
                    + middleOfArray+1;
        }
    }

    // Day two : Moving the boundaries of potential search
    public int boundaryBinaryChop(int integerLookedFor, int[] integerArray)
            throws EmptyArrayException, ValueNotInArrayException {
        if (integerArray.length==0){
            throw new EmptyArrayException();
        }
        int lookingAt;
        int inferiorBound = 0;
        int superiorBound = integerArray.length-1;
        while (inferiorBound <= superiorBound){
            lookingAt = inferiorBound+(superiorBound-inferiorBound)/2;
            if (integerArray[lookingAt] == integerLookedFor){
                return lookingAt;
            }
            else if (integerArray[lookingAt] > integerLookedFor){
                superiorBound=lookingAt-1;
            }
            else {
                inferiorBound=lookingAt+1;
            }
        }
        throw new ValueNotInArrayException();
    }

    // Day three : reducing the size of the array we're looking at.
    /**
     * Searches for an integer in an array and returns its position.
     * @param integerLookedFor the integer we're looking for
     * @param integerArray the array to parse
     * @return the position of the researched integer in the array
     * @throws EmptyArrayException if the array is empty
     * @throws ValueNotInArrayException if the value isn't in the array
     */
    public int arrayReductionBinaryChop(int integerLookedFor, int[] integerArray)
            throws EmptyArrayException, ValueNotInArrayException {
        if (integerArray.length == 0){
            throw new EmptyArrayException();
        }
        int finalPos = 0;
        int lookingAt;
        while (integerArray.length>1){
            lookingAt = integerArray.length/2;
            if (integerLookedFor >= integerArray[lookingAt]){
                finalPos += lookingAt;
                integerArray = Arrays.copyOfRange(integerArray, lookingAt, integerArray.length);
            }
            else integerArray = Arrays.copyOfRange(integerArray, 0, lookingAt);
        }
        if (integerArray[0] == integerLookedFor) {
            return finalPos;
        }
        else throw new ValueNotInArrayException();
    }

    //Day four : directly calculating the position to look for in the array.
    /**
     * Searches for an integer in an array and returns its position.
     * @param integerLookedFor the integer we're looking for
     * @param integerArray the array to parse
     * @return the position of the researched integer in the array
     * @throws EmptyArrayException if the array is empty
     * @throws ValueNotInArrayException if the value isn't in the array
     */
    public int movingViewBinaryChop(int integerLookedFor, int[] integerArray)
            throws EmptyArrayException, ValueNotInArrayException {
        //Check if there's something in the array.
        if (integerArray.length == 0) {throw new EmptyArrayException();}
        //Look for the integer in the array :
        int lookingAt = integerArray.length/2;
        int stepSize = integerArray.length/2;
        double iterationsNeeded = Math.log10(integerArray.length) / Math.log10(2);
        for (int i=0; i <= iterationsNeeded ; i++) {
            //Edge case : list of size = 2, integer above top list value.
            if (lookingAt == integerArray.length){break;}
            stepSize = (int)Math.ceil(stepSize/2.0);
            if (integerArray[lookingAt] == integerLookedFor){
                return lookingAt;
            }
            else if (integerArray[lookingAt] < integerLookedFor){
                lookingAt += stepSize;
            }
            else {
                lookingAt -= stepSize;
            }
        }
        //If nothing is found, throw :
        throw new ValueNotInArrayException();
    }

    //Day five implementation : use the library method implemented in Arrays.
    /**
     * Searches for an integer in an array and returns its position.
     * @param integerLookedFor the integer we're looking for
     * @param integerArray the array to parse
     * @return the position of the researched integer in the array
     * @throws EmptyArrayException if the array is empty
     * @throws ValueNotInArrayException if the value isn't in the array
     */
    public int libraryBinaryChop(int integerLookedFor, int[] integerArray)
            throws EmptyArrayException, ValueNotInArrayException {
        //Check if there's something in the array
        if(integerArray.length==0){throw new EmptyArrayException();}
        //Look for the integer in the array
        int result = Arrays.binarySearch(integerArray, integerLookedFor);
        //If nothing is found, throw :
        if (result <0) {throw new ValueNotInArrayException();}
        return result;
    }
}
