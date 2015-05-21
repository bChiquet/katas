package com.kata.bloomFilter;

import com.google.common.base.Throwables;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.BitSet;

/**
 * Created by bec on 20/05/15.
 * Implements http://codekata.com/kata/kata05-bloom-filters/
 */
public class BloomFilter {

    BitSet filter;

    BloomFilter(int bitSize){
        filter = new BitSet(bitSize);
        System.out.println(filter.size());
    }

    public void processWordFile(String filePath) {
        try {
            Files.lines(Paths.get(filePath)).forEach(this::addWordToFilter);
        } catch (IOException e) {
            Throwables.propagate(e);
        }

    }

    private void addWordToFilter(String word) {
        //TODO create multiple hash values for each word
        //TODO for each hash value, AND it with the bitmap
    }


}
