package com.kata.bloomFilter;

import com.google.common.base.Throwables;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.BitSet;
import java.util.PrimitiveIterator.OfInt;

/**
 * Created by bec on 20/05/15.
 * Implements http://codekata.com/kata/kata05-bloom-filters/
 */
public class BloomFilter {

    static final int KEY = 7;
    static final int MULT = 61;

    BitSet filter;

    BloomFilter(int bitSize){
        filter = new BitSet(bitSize);
    }

    public void processWordFile(String filePath) {
        try {
            Files.lines(Paths.get(filePath)).forEach(this::addWordToFilter);
        } catch (IOException e) {
            Throwables.propagate(e);
        }

    }

    public void addWordToFilter(String word) {
        filter.and(getHash(word));
    }

    public boolean isPresent(String word) {
        //Todo fix this method
        BitSet wordXorFilter = getHash(word);
        wordXorFilter.xor(filter);
        return wordXorFilter.cardinality() == filter.cardinality() - getHash(word).cardinality();
    }

    private BitSet getHash(String word){
        long[] hash = new long[] {hashMethod1(word), hashMethod2(word)};
        return BitSet.valueOf(hash);
    }

    private long hashMethod1(String word) {
        return 0;
    }

    private long hashMethod2(String word) {
        OfInt wordCharIterator = word.chars().iterator();
        int hash = KEY;
        while (wordCharIterator.hasNext()) {
            hash *= MULT;
            hash += wordCharIterator.nextInt();
        }
        return hash;
    }
}
