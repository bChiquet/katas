package com.kata.secretSanta;

import com.google.common.io.Resources;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by bec on 15/06/15.
 */
public class SecretSantaTest {
    SecretSanta santa;

    @Before
    public void setup(){
        santa = new SecretSanta();
    }

    @Test
    public void should_return_santa_list(){
        System.out.println(
            santa.on(Resources.getResource("namesList").getPath()).drawSanta());
    }

    @Test
    public void should_not_have_family_santa(){
        //Before
        santa.on(Resources.getResource("namesList").getPath());
        //When
        List<String> santaList = santa.drawSanta();
        //Then
        String previousPerson = santaList.get(santaList.size()-1);
        System.out.println(santaList);
        for (String person : santaList) {
            assertNotEquals(person.split("\\s+")[1], previousPerson.split("\\s+")[1]);
            previousPerson = person;
        }
    }
}