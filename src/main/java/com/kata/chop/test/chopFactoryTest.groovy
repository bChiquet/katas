package com.kata.chop.test

import com.kata.chop.impl.chopFactory
import org.junit.Assert
import org.junit.Test

class chopFactoryTest {

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


    @Test (expected = chopFactory.valueNotInArrayException.class)
    void testExceptionOnValueNotInList(){
        assertForValueAndList(2, 4, [2,3,5,6])
    }

    @Test(expected = chopFactory.emptyArrayException.class)
    void testExceptionOnValueNotInList2(){
        assertForValueAndList(2, 1, [])
    }

    @Test(expected = chopFactory.valueNotInArrayException.class)
    void testExceptionOnValueNotInList3(){
        assertForValueAndList(2, 2, [0, 1])
    }
    @Test(expected = chopFactory.valueNotInArrayException.class)
    void testExceptionOnValueNotInList4(){
        assertForValueAndList(2, 2, [3, 4])
    }

    // TODO find a way to enrich unexpected exceptions.
    static void assertForValueAndList(int integerPositionInList, int desiredInteger, List<Integer> integerArray){
        Assert.assertEquals("recursiveBinaryChop",integerPositionInList, chopFactory.recursiveBinaryChop(desiredInteger, integerArray))
        Assert.assertEquals("blandBinaryChop",integerPositionInList, chopFactory.blandBinaryChop(desiredInteger, integerArray))
        Assert.assertEquals("destructiveBinaryChop", integerPositionInList, chopFactory.destructiveBinaryChop(desiredInteger, integerArray))
    }
}
