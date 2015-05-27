package com.kata.anagrams;

import com.google.common.io.Resources;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by bec on 23/05/15.
 *  Tests for class Anagrams.
 */
public class AnagramsTest {

    Anagrams anagrams;

    @Before
    public void setUp() throws IOException {
        anagrams = new Anagrams();
    }

    @Test
    public void should_return_number_of_unique_character_compositions_in_word_list() {
        //Before
        anagrams.on(Resources.getResource("smallAnagramList").getPath());
        //When
        int uniqueComp = anagrams.getNumberOfUniqueCharCompositions();
        //Then
        assertThat(uniqueComp)
                .isEqualTo(11);
    }

    @Test
    public void should_return_number_of_anagram_sets_in_word_list(){
        //before
        anagrams.on(Resources.getResource("wordlist").getPath());
        //When
        int anagramNumber = anagrams.getNumberOfAnagramSets();
        //Then
        assertThat(anagramNumber)
                .isEqualTo(8381);
    }

    @Test
    public void should_return_anagram_sets(){
        //Using data : //TODO redo test with Groovy & see how many lines it takes
        Set<String> anagram = new HashSet<>();
        anagram.add("boaster"); anagram.add("boaters"); anagram.add("borates");
        Set<String> anagram2 = new HashSet<>();
        anagram2.add("pates"); anagram2.add("peats"); anagram2.add("septa"); anagram2.add("tapes");
        anagram2.add("spate"); anagram2.add("paste"); anagram2.add("tepas");
        Set<String> anagram3 = new HashSet<>();
        anagram3.add("pictures"); anagram3.add("cuprites"); anagram3.add("crepitus"); anagram3.add("piecrust");
        Set<String> anagram4 = new HashSet<>();
        anagram4.add("punctilio"); anagram4.add("unpolitic");
        Set<String> anagram5 = new HashSet<>();
        anagram5.add("sunders"); anagram5.add("undress");
        Set<String> anagram6 = new HashSet<>();
        anagram6.add("fresher"); anagram6.add("refresh");
        Set<String> anagram7 = new HashSet<>();
        anagram7.add("inlets"); anagram7.add("enlist"); anagram7.add("listen"); anagram7.add("silent");
        Set<String> anagram8 = new HashSet<>();
        anagram8.add("kinship"); anagram8.add("pinkish");
        Set<String> anagram9 = new HashSet<>();
        anagram9.add("sinks"); anagram9.add("skins");
        Set<String> anagram10 = new HashSet<>();
        anagram10.add("knits"); anagram10.add("stink");
        Set<String> anagram11 = new HashSet<>();
        anagram11.add("sort"); anagram11.add("rots");

        //before
        anagrams.on(Resources.getResource("smallAnagramList").getPath());
        //when
        Map<String, Set<String>> anagramSet = anagrams.getAnagramSets();
        //Then
        assertThat(anagramSet)
                .hasSize(11)
                .containsValues(anagram, anagram2, anagram3, anagram4, anagram5, anagram6,
                                anagram7, anagram8, anagram9, anagram10, anagram11);
    }
}