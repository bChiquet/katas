package com.kata.anagrams;

import com.google.common.base.Throwables;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * Created by bec on 23/05/15.
 * Implements http://codekata.com/kata/kata06-anagrams/
 */
public class Anagrams {

    List<String> wordList;

    /**
     * Loads a list of words on which to work from :
     * @param filePath the path of the file containing the words.
     */
    public void on(String filePath) {
        try {
            wordList = Files.lines(Paths.get(filePath))
                    .map(String::trim)
                    .collect(toList());
        }
        catch (IOException e) {
            Throwables.propagate(e);
        }
    }

    /**
     * returns a word's letters in sorted order.
     * @param word the word
     * @return a String containing the word's letters in sorted order.
     */
    public static String getSortedString(String word){
        char[] wordChars = word.toCharArray();
        Arrays.sort(wordChars);
        return String.valueOf(wordChars);
    }

    /**
     * @return the number of anagram sets, excluding words that have no anagram.
     */
    public int getNumberOfAnagramSets() {
        Set<String> anagramSet = new TreeSet<>();
        Set<String> uniqueWordSet = new TreeSet<>();
        wordList.forEach(word -> {
            String sortedString = getSortedString(word);
            if(!uniqueWordSet.add(sortedString)) anagramSet.add(sortedString);
        });
        return anagramSet.size();
    }

    /**
     * @return the number of anagram sets, including words that have no anagram.
     */
    public int getNumberOfUniqueCharCompositions() {
        Set<String> anagramSet = new TreeSet<>();
        wordList.forEach(word -> anagramSet.add(getSortedString(word)));
        return anagramSet.size();
    }

    /**
     * Builds a mapping of (sorted strings : anagram sets) from the wordList
     * @return the map.
     */
    public Map<String, Set<String>> getAnagramSets() {
        Map<String, Set<String>> anagramMap = new TreeMap<>();
        wordList.forEach(word ->{
            String k = getSortedString(word);
            if (anagramMap.containsKey(k)) {
                anagramMap.get(k).add(word);
            }
            else{
                Set<String> l = new HashSet<>();
                l.add(word);
                anagramMap.put(k, l);
            }
        });
        return anagramMap;
    }

    //TODO find a way to binary search a string in a set of anagrams ?
    //    public List<Anagram> getAnagramSets2(){
    //        List<Anagram> anagramSet = new ArrayList<>();
    //        wordList.forEach(word ->{
    //            String sortedWord = getSortedString(word);
    //            if (Collections.binarySearch(anagramSet, sortedWord)) {
    //                anagramSet.
    //            }
    //            else {
    //                anagramSet.add(new Anagram(word));
    //            }
    //        });
    //        return anagramSet;
    //    }
}
