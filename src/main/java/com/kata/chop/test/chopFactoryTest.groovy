package com.kata.chop.test

import com.kata.chop.impl.chopFactory
import org.junit.Assert
import org.junit.Test

public class chopFactoryTest {
    @Test
    void testCorrectMatch(){
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
    void testExceptionOnEmptyArray(){
        assertErrorForValueAndList(chopFactory.emptyArrayException, 1, [])
    }

    @Test
    void testExceptionOnValueNotInList(){
        assertErrorForValueAndList(chopFactory.valueNotInArrayException, 4, [2,3,5,6])
    }

    @Test
    void testExceptionOnValueNotInList3(){
        assertErrorForValueAndList(chopFactory.valueNotInArrayException, 2, [0, 1])
    }
    @Test
    void testExceptionOnValueNotInList4(){
        assertErrorForValueAndList(chopFactory.valueNotInArrayException, 2, [3, 4])
    }

    static void assertForValueAndList(int integerPositionInList, int desiredInteger, List<Integer> integerArray){
        Assert.assertEquals(integerPositionInList, chopFactory.recursiveBinaryChop(desiredInteger, integerArray))
        Assert.assertEquals(integerPositionInList, chopFactory.blandBinaryChop(desiredInteger, integerArray))
        Assert.assertEquals(integerPositionInList, chopFactory.destructiveBinaryChop(desiredInteger, integerArray))
        //Assert.assertEquals(integerPositionInList, chopFactory.simpleBinaryChop(desiredInteger, integerArray))
        Assert.assertEquals(integerPositionInList, chopFactory.libraryBinaryChop(desiredInteger, new TreeSet<Integer>(integerArray)))
    }

    static void assertErrorForValueAndList(Class <Exception> desiredException, int desiredInteger, List<Integer> integerArray){
        //Tried ExpectedException but that will catch any number of exceptions after the expects(), effectively making
        //it fail just like @Test(expected=x.class). Back to try...catch... :-(
        //Didn't find good things in assertJ.
        //Cool stuff exists for java 8 tho.
        boolean thrown = false
        try {
            chopFactory.recursiveBinaryChop(desiredInteger, integerArray)
        }
        catch(Exception actualException){
            Assert.assertEquals(desiredException, actualException.class)
            thrown = true
        }
        Assert.assertTrue(thrown)
        thrown = false
        try {
            chopFactory.blandBinaryChop(desiredInteger, integerArray)
        }
        catch(Exception actualException){
            Assert.assertEquals(desiredException, actualException.class)
            thrown = true
        }
        Assert.assertTrue(thrown)
        thrown = false
        try {
            chopFactory.destructiveBinaryChop(desiredInteger, integerArray)
        }
        catch(Exception actualException){
            Assert.assertEquals(desiredException, actualException.class)
            thrown = true
        }
        Assert.assertTrue(thrown)
//        thrown = false
//        try {
//            //chopFactory.simpleBinaryChop(desiredInteger, integerArray)
//        }
//        catch(Exception actualException){
//            Assert.assertEquals(desiredException, actualException.class)
//            thrown = true
//        }
//        Assert.assertTrue(thrown)
        thrown = false
        try {
            System.out.println(chopFactory.libraryBinaryChop(desiredInteger, new TreeSet<Integer>(integerArray)));

        }
        catch(Exception actualException){
            Assert.assertEquals(desiredException, actualException.class)
            thrown = true
        }
        Assert.assertTrue(thrown)
    }

    static void assertErrorForValueAndListWithJ8(Class <Exception> desiredException, int desiredInteger, List<Integer> integerArray){

    }
}
