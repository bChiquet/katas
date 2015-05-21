package com.kata.bloomFilter;

import com.google.common.io.Resources;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by bec on 21/05/15.
 */
public class BloomFilterTest {

    BloomFilter bloomFilterTest;

    @Before
    public void setUp(){
        bloomFilterTest = new BloomFilter(1<<30);
    }

    @Test
    public void should_process_word_list(){
        bloomFilterTest.processWordFile(Resources.getResource("wordlist").getPath());
    }

    @Test
    public void should_match_existing_word(){
        bloomFilterTest.processWordFile(Resources.getResource("wordlist").getPath());
    }



}