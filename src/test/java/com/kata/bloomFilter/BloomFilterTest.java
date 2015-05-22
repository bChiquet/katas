package com.kata.bloomFilter;

import com.google.common.io.Resources;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by bec on 21/05/15.
 */
public class BloomFilterTest {

    BloomFilter bloomFilterTest;

    @Before
    public void setUp(){
        bloomFilterTest = new BloomFilter(16*100000);
    }

    @Test
    public void should_process_word_list(){
        bloomFilterTest.processWordFile(Resources.getResource("wordlist").getPath());
        assertTrue(bloomFilterTest.isPresent("artisan"));
    }



}