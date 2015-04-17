package com.kata.chop.impl;

import java.util.SortedSet;
import java.util.List;

/**
 * Kata implementing binary chop
 * http://codekata.com/kata/kata02-karate-chop/
 **/
public class binaryChopMethods {
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
        int middleOfList = integerList.size()/2;
        if (integerLookedFor < integerList.get(middleOfList)){
            return recursiveBinaryChop(integerLookedFor, integerList.subList(0, middleOfList));
        }
        else {
            return recursiveBinaryChop(integerLookedFor, integerList.subList(middleOfList, integerList.size()))
                    + middleOfList;
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
    // We could check if our list is ordered, but since that's O(n), for an O(log(n)) operation it sounds dumb.
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

    //Day four implementation.
    // We expect O(log(list size)) comparisons so if we don't find after that many steps we just assume it's not there.
    //Not very readable.
    // Also Java rounds ints in a strange fashion. We should never implicitly expect Java to round ints correctly.
    public static int simpleBinaryChop(int integerLookedFor, List<Integer> integerList)
            throws emptyArrayException, valueNotInArrayException {
        ckeckIfListIsChoppable(integerList);
        int lookingAt = integerList.size()/2;
        int shiftView = integerList.size()/2;
        for (int i=0;i<=Math.log10(integerList.size())/Math.log10(2);i++) {
            //Edge case : list of size = 2, integer above top list value.
            if (lookingAt==integerList.size()){break;}
            shiftView = (int)Math.ceil(shiftView/2.0);
            if (integerList.get(lookingAt) == integerLookedFor){
                return lookingAt;
            }
            else if (integerList.get(lookingAt) < integerLookedFor){
                lookingAt += shiftView;
            }
            else {
                lookingAt -= shiftView;
            }
        }
        throw new valueNotInArrayException();
    }

    //Day five implementation : Why not just use an object that is designed for that use ?
    // Although chopping now barely makes sense, since sortedSets abstract the underlying array.
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
