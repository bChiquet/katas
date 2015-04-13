package com.kata.chop.impl;

import java.util.SortedSet;
import java.util.List;

/**
 * Kata implementing binary chop
 * http://codekata.com/kata/kata02-karate-chop/
 **/
public class chopFactory {
    public static class valueNotInArrayException extends Exception{}
    public static class emptyArrayException extends Exception{}
    public static class listNotOrderedException extends Exception{} //TODO find what to do with this

    // Day one : the classic recursive function.
    public static int recursiveBinaryChop(int integerLookedFor, List<Integer> integerList)
            throws emptyArrayException, valueNotInArrayException {
        if (integerList.size()==0){
            throw new emptyArrayException();
        }
        if (integerList.size() == 1 && !integerList.get(0).equals(integerLookedFor)){
            throw new valueNotInArrayException();
        }
        if (integerList.get(0).equals(integerLookedFor)){
            return 0;
        }
        if (integerLookedFor < integerList.get(integerList.size()/2)){
            return recursiveBinaryChop(integerLookedFor, integerList.subList(0, integerList.size()/2));
        }
        else {
            return recursiveBinaryChop(integerLookedFor, integerList.subList(integerList.size()/2, integerList.size()))
                    + (integerList.size()/2);
        }
    }

    // Day two, not convinced by this one. Code's too complicated.
    // Apparently integer rounding is fucked up.
    // Added new tests because previous set wouldn't catch all fish.
    public static int blandBinaryChop(int integerLookedFor, List<Integer> integerList)
            throws emptyArrayException, valueNotInArrayException {
        if (integerList.size()==0){
            throw new emptyArrayException();
        }
        int lookingAt;
        int inferiorBound = 0;
        int superiorBound = integerList.size()-1;
        while (inferiorBound <= superiorBound){
            lookingAt = inferiorBound+(superiorBound-inferiorBound)/2;
            if (integerList.get(lookingAt) == integerLookedFor){
                return lookingAt;
            }
            else if (integerList.get(lookingAt) > integerLookedFor){
                superiorBound=lookingAt-1;
            }
            else {
                inferiorBound=lookingAt+1;
            }
        }
        throw new valueNotInArrayException();
    }

    // Day three : Maybe we should use aspect-oriented magic to avoid duplicating the list size
    // checking code in all of our chop functions...
    // We could add a method to check if the list is ordered, but since this basically is O(n) for an O(log(n)) method...
    public static void ckeckIfListIsChoppable(List<Integer> integerList)
            throws emptyArrayException {
        if (integerList.size()==0){
            throw new emptyArrayException();
        }
    }
    public static void ckeckIfSetIsChoppable(SortedSet<Integer> integerSet)
            throws emptyArrayException {
        if (integerSet.size()==0){
            throw new emptyArrayException();
        }
    }

    // Day three implementation : For this day we gradually get rid of whatever in the list is not relevant to us.
    // Not sure that's a very good idea performance wise, but it's quite easy to understand.
    // TO-DONE : check what ressource subList consumes. http://java-performance.info/arraylist-performance/
    public static int destructiveBinaryChop(int integerLookedFor, List<Integer> integerList)
            throws emptyArrayException, valueNotInArrayException {
        ckeckIfListIsChoppable(integerList);
        int finalPos =0;
        int lookingAt;
        while (integerList.size()>1){
            lookingAt = integerList.size()/2;
            if (integerLookedFor >= integerList.get(lookingAt)){
                finalPos += lookingAt;
                integerList = integerList.subList(lookingAt, integerList.size());
            }
            else integerList = integerList.subList(0, lookingAt);
        }
        if (integerList.get(0) == integerLookedFor) {
            return finalPos;
        }
        else throw new valueNotInArrayException();
    }

    //Day four implementation
    //TODO make it work
    public static int simpleBinaryChop(int integerLookedFor, List<Integer> integerList)
            throws emptyArrayException, valueNotInArrayException {
        ckeckIfListIsChoppable(integerList);
        int listSize = integerList.size();
        int lookingAt = listSize/2;
        for(int iterationNumber= 1; iterationNumber <Math.log(listSize)/Math.log(2) ; iterationNumber++){
            if (integerLookedFor == integerList.get(lookingAt)){
                return lookingAt;
            }
            else if (integerLookedFor > integerList.get(lookingAt)){

                lookingAt +=lookingAt/2;
            }
            else {
                lookingAt-= 1;
            }
        }
        throw new valueNotInArrayException();
    }

    //Day five implementation : Or we could use an object that is designed for that use, although chopping now barely makes sense.
    public static int libraryBinaryChop(int integerLookedFor, SortedSet<Integer> integerSet)
            throws emptyArrayException, valueNotInArrayException{
        ckeckIfSetIsChoppable(integerSet);
        if (integerSet.contains(integerLookedFor)) {
            return integerSet.headSet(integerLookedFor).size();
        }
        else {
            throw new valueNotInArrayException();
        }
    }
}
