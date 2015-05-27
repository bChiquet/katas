package com.kata.anagrams;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bec on 25/05/15.
 * Object wraping one set of anagrams.
 */
public class Anagram implements Comparable<Anagram>{
    //TODO actually worthless as long as Anagrams.getAnagramSets2 isn't figured out.
    String sortedString;
    List<String> anagrams;

    public Anagram(String word) {
        sortedString = Anagrams.getSortedString(word);
        anagrams = new ArrayList<>();
        anagrams.add(word);
    }

    public void add(String word) {
        anagrams.add(word);
    }

    @Override
    public int compareTo(Anagram a) {
        return sortedString.compareTo(a.sortedString);
    }
}
