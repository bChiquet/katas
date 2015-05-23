package com.kata.bloomFilter;

import com.google.common.base.Throwables;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.BitSet;
import java.util.Random;

/**
 * Created by bec on 20/05/15.
 * Implements http://codekata.com/kata/kata05-bloom-filters/
 */
public class BloomFilter {
    //The bit array
    private BitSet filter;
    //The size of the bloom filter.
    private int filterSize;
    //The number of bits that are set to 1 for each word.
    private int numberOfHashPerWord;
    private int numberOfWordsStored;
    private int expectedNumberOfElements;

    /**
     * Exception thrown when there is no filter size value that fits the couple
     * (expectedElementNumber, acceptableFalsePositiveRate).
     */
    public class FilterCantBuildException extends Exception {}

    /**
     * Constructs a simple bloom filter.
     * The filter will automatically determine the bitmap size and the number of keys per word from :
     * @param expectedNumberOfElements The number of words the bloom filter is expected to contain
     * @param acceptableFalsePositiveRate The acceptable error rate when the filter is full.
     * The calculation is explained here : http://pages.cs.wisc.edu/~cao/papers/summary-cache/node8.html
     */
    BloomFilter(int expectedNumberOfElements, double acceptableFalsePositiveRate) throws FilterCantBuildException {
        int multiplier=0;
        for (int i=2;i+1<Integer.MAX_VALUE/expectedNumberOfElements;i++){
            if (Math.pow(0.6185, (double)i) < acceptableFalsePositiveRate){
                multiplier = i+1;
                break;
            }
        }
        if (multiplier==0) {throw new FilterCantBuildException();}
        numberOfHashPerWord = (int)(multiplier * Math.log(2));
        filterSize = expectedNumberOfElements * multiplier;
        filter = new BitSet(filterSize);
        numberOfWordsStored = 0;
        this.expectedNumberOfElements = expectedNumberOfElements;
    }

    /**
     * adds all the words contained in a file in the bloom filter.
     * The file format should be 1 word per line.
     * @param filePath containing the words to be added
     * @return false if one or more words hasn't been added because of lack of space, true otherwise.
     * The words will be added to the filter anyway
     */
    public boolean addWordsFromFile(String filePath) {
        try {
            return Files.lines(Paths.get(filePath))
                    .map(this::addWord)
                    .reduce((result, result2) -> result && result2)
                    .get();
        }
        catch (IOException e) {
            Throwables.propagate(e);
            return false;
        }
    }

    /**
     * Adds a word's hash in the bloom filter.
     * The word is trimmed before adding.
     * @param word to be added
     * @return false if the word hasn't been added because the filter can't contain more words, true otherwise
     * The word will be added to the filter anyway.
     */
    public boolean addWord(String word) {
        filter.or(hash(word.trim()));
        numberOfWordsStored++;
        return numberOfWordsStored <= expectedNumberOfElements;
    }

    /**
     * checks if a word's hash is contained in the bloom filter.
     * @param word to be tested
     * @return true if the word's hash bits are all in the filter, false otherwise
     */
    public boolean contains(String word) {
        BitSet hashOrFilter = hash(word);
        hashOrFilter.or(filter);
        return hashOrFilter.cardinality() == filter.cardinality();
    }

    /**
     * @return the number of words that can be stored before the filter is full
     */
    public int spaceLeft(){
        return expectedNumberOfElements - numberOfWordsStored;
    }

    private BitSet hash(String word){
        //This hash method sucks ; deal with it.
        Random rand = new Random(word.hashCode());
        BitSet hash = new BitSet(filterSize);
        for (int i=0; i< numberOfHashPerWord;i++) {hash.set(rand.nextInt(filterSize), true);}
        return hash;
    }
}
