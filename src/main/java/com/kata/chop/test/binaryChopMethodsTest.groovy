package com.kata.chop.test

import com.kata.chop.impl.binaryChopMethods
import org.junit.Assert
import org.junit.Test

import java.util.concurrent.Callable

public class binaryChopMethodsTest {
    @Test
    void testCorrectMatch1(){
        assertForValueAndList(3, 5, [1,2,3,5,8,13,21])
    }
    @Test
    void testCorrectMatch2(){
        assertForValueAndList(1, 2, [1,2,3,4])
    }
    @Test
    void testCorrectMatch3(){
        assertForValueAndList(0, 1, [1,2,3,5,8,13,21])
    }
    @Test
    void testCorrectMatch4(){
        assertForValueAndList(6, 21, [1,2,3,5,8,13,21])
    }
    @Test
    void testCorrectMatch5(){
        assertForValueAndList(7, 25, [1,2,3,4,6,9,12,25])
    }
    @Test
    void testCorrectMatch6(){
        assertForValueAndList(3, 4, [1,2,3,4,6,9,12,25,30,35,40,50,100])
    }
    @Test
    void testCorrectMatch7(){
        assertForValueAndList(0, 1, [1,2])
    }
    @Test
    void testCorrectMatch8(){
        assertForValueAndList(1, 2, [1,2])
    }

    @Test
    void testExceptionOnEmptyArray(){
        agnosticAssertErrorForValueAndList(binaryChopMethods.emptyArrayException, 1, [])
    }

    @Test
    void testExceptionOnValueNotInList1(){
        agnosticAssertErrorForValueAndList(binaryChopMethods.valueNotInArrayException, 4, [2,3,5,6])
    }
    @Test
    void testExceptionOnValueNotInList2(){
        agnosticAssertErrorForValueAndList(binaryChopMethods.valueNotInArrayException, 101, [1,2,3,4,6,9,12,25,30,35,40,50,100])
    }
    @Test
    void testExceptionOnValueNotInList3(){
        agnosticAssertErrorForValueAndList(binaryChopMethods.valueNotInArrayException, 2, [0, 1])
    }
    @Test
    void testExceptionOnValueNotInList4(){
        agnosticAssertErrorForValueAndList(binaryChopMethods.valueNotInArrayException, 0, [1, 2])
    }
    @Test
    void testExceptionOnValueNotInList5(){
        agnosticAssertErrorForValueAndList(binaryChopMethods.valueNotInArrayException, 2, [3, 4])
    }
    @Test
    void testExceptionOnValueNotInList6(){
        agnosticAssertErrorForValueAndList(binaryChopMethods.valueNotInArrayException, 2, [1])
    }

    static void assertForValueAndList(int integerPositionInList, int desiredInteger, List<Integer> integerArray){
        Assert.assertEquals(integerPositionInList, binaryChopMethods.recursiveBinaryChop(desiredInteger, integerArray))
        Assert.assertEquals(integerPositionInList, binaryChopMethods.blandBinaryChop(desiredInteger, integerArray))
        Assert.assertEquals(integerPositionInList, binaryChopMethods.destructiveBinaryChop(desiredInteger, integerArray))
        Assert.assertEquals(integerPositionInList, binaryChopMethods.simpleBinaryChop(desiredInteger, integerArray))
        Assert.assertEquals(integerPositionInList, binaryChopMethods.libraryBinaryChop(desiredInteger, new TreeSet<Integer>(integerArray)))
    }

    //Creating a switch between assertErrorForValueAndListWithoutStyle and assertErrorForValueAndList_NowWithGroovyFunctionPointer
    static void agnosticAssertErrorForValueAndList(Class <Exception> desiredException, int desiredInteger, List<Integer> integerArray){
        //assertErrorForValueAndListWithoutStyle(desiredException, desiredInteger, integerArray)
        assertErrorForValueAndList_NowWithGroovyFunctionPointer(desiredException, desiredInteger, integerArray)
    }


    static void assertErrorForValueAndListWithoutStyle(Class <Exception> desiredException, int desiredInteger, List<Integer> integerArray){
        //Tried ExpectedException but that will catch any number of exceptions after the expects(), effectively making
        //it fail just like @Test(expected=x.class). Back to try...catch... :-(
        //Didn't find good things in assertJ.
        //Cool stuff exists for that in the Catch-exception lib.
        boolean thrown = false
        try {
            binaryChopMethods.recursiveBinaryChop(desiredInteger, integerArray)
        }
        catch(Exception actualException){
            Assert.assertEquals(desiredException, actualException.class)
            thrown = true
        }
        Assert.assertTrue(thrown)
        thrown = false
        try {
            binaryChopMethods.blandBinaryChop(desiredInteger, integerArray)
        }
        catch(Exception actualException){
            Assert.assertEquals(desiredException, actualException.class)
            thrown = true
        }
        Assert.assertTrue(thrown)
        thrown = false
        try {
            binaryChopMethods.destructiveBinaryChop(desiredInteger, integerArray)
        }
        catch(Exception actualException){
            Assert.assertEquals(desiredException, actualException.class)
            thrown = true
        }
        Assert.assertTrue(thrown)
        thrown = false
        try {
            binaryChopMethods.simpleBinaryChop(desiredInteger, integerArray)
        }
        catch(Exception actualException){
            Assert.assertEquals(desiredException, actualException.class)
            thrown = true
        }
        Assert.assertTrue(thrown)
        thrown = false
        try {
            binaryChopMethods.libraryBinaryChop(desiredInteger, new TreeSet<Integer>(integerArray))

        }
        catch(Exception actualException){
            Assert.assertEquals(desiredException, actualException.class)
            thrown = true
        }
        Assert.assertTrue(thrown)
    }

    //Because fuck java, right ?
    //We could also do this with Java8 and currying, but groovy syntax is clearer.
    static void assertErrorForValueAndList_NowWithGroovyFunctionPointer(Class <Exception> desiredException, int desiredInteger, List<Integer> integerArray){
        wrapsChopAndCatchesException desiredException, {binaryChopMethods.recursiveBinaryChop(desiredInteger, integerArray)}
        wrapsChopAndCatchesException desiredException, {binaryChopMethods.blandBinaryChop(desiredInteger, integerArray)}
        wrapsChopAndCatchesException desiredException, {binaryChopMethods.destructiveBinaryChop(desiredInteger, integerArray)}
        wrapsChopAndCatchesException desiredException, {binaryChopMethods.simpleBinaryChop(desiredInteger, integerArray)}
        wrapsChopAndCatchesException desiredException, {binaryChopMethods.libraryBinaryChop(desiredInteger, new TreeSet<Integer>(integerArray))}

    }

    static wrapsChopAndCatchesException(desiredException, actualOutput){
        try {
            actualOutput()
        }
        catch(Exception actualException){
            Assert.assertEquals desiredException, actualException.class
        }
    }
}