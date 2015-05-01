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
    public int movingViewBinaryChop(int integerLookedFor, int[] integerArray)
            throws EmptyArrayException, ValueNotInArrayException {
        if (integerArray.length == 0){
            throw new EmptyArrayException();
        }
        int lookingAt = integerArray.length/2;
        int moveView = integerArray.length/2;
        for (int i=0;i<=Math.log10(integerArray.length)/Math.log10(2);i++) {
            //Edge case : list of size = 2, integer above top list value.
            if (lookingAt == integerArray.length){break;}
            moveView = (int)Math.ceil(moveView/2.0);
            if (integerArray[lookingAt] == integerLookedFor){
                return lookingAt;
            }
            else if (integerArray[lookingAt] < integerLookedFor){
                lookingAt += moveView;
            }
            else {
                lookingAt -= moveView;
            }
        }
        throw new ValueNotInArrayException();
    }

    //Day five implementation : use a SortedSet.
    public int libraryBinaryChop(int integerLookedFor, SortedSet<Integer> integerSet)
            throws EmptyArrayException, ValueNotInArrayException {
        if(integerSet.size()==0){throw new EmptyArrayException();}
        if (integerSet.contains(integerLookedFor)) {
            return integerSet.headSet(integerLookedFor).size();
        }
        else {
            throw new ValueNotInArrayException();
        }
    }
}
