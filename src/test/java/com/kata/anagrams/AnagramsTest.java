package com.kata.anagrams;

import com.google.common.collect.Sets;
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
        Set<String> anagram = Sets.newHashSet("boaster", "boaters", "borates");
        Set<String> anagram2 = Sets.newHashSet("pates", "peats", "septa", "tapes",
                "spate", "paste", "tepas");
        Set<String> anagram3 = Sets.newHashSet("pictures", "cuprites", "crepitus",
                "piecrust");
        Set<String> anagram4 = Sets.newHashSet("punctilio", "unpolitic");
        Set<String> anagram5 = Sets.newHashSet("sunders", "undress");
        Set<String> anagram6 = Sets.newHashSet("fresher", "refresh");
        Set<String> anagram7 = Sets.newHashSet("inlets", "enlist", "listen", "silent");
        Set<String> anagram8 = Sets.newHashSet("kinship", "pinkish");
        Set<String> anagram9 = Sets.newHashSet("sinks", "skins");
        Set<String> anagram10 = Sets.newHashSet("knits", "stink");
        Set<String> anagram11 = Sets.newHashSet("sort", "rots");

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