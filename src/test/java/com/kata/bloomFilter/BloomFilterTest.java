package com.kata.bloomFilter;

import com.google.common.io.Resources;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by bec on 21/05/15.
 */
public class BloomFilterTest {

    static final double ACCEPTABLE_FALSE_POSITIVE_FREQUENCY = 0.1;
    BloomFilter bloomFilter;

    @Before
    public void setUp(){
        bloomFilter = new BloomFilter(16*100000);
    }

    @Test
    public void should_determine_if_word_hash_exists_in_bloomfilter(){
        bloomFilter.addWordToFilter("art");
        assertTrue(bloomFilter.isPresent("art"));
        assertTrue(!bloomFilter.isPresent("arts"));
        assertTrue(!bloomFilter.isPresent("ars"));
    }

    @Test
    public void should_process_word_list(){
        bloomFilter.processWordFile(Resources.getResource("wordlist").getPath());
        assertTrue(bloomFilter.isPresent("artisan"));
    }

    @Test
    public void should_add_word_to_bitset(){
        bloomFilter.addWordToFilter("craftsman");
        assertTrue(bloomFilter.isPresent("craftsman"));
    }

    @Test
    public void should_have_low_false_positive_frequeny() {
        bloomFilter.processWordFile(Resources.getResource("wordlist").getPath());
        //TODO generate a couple thousand imaginary words not in wordlist and check if
        // bloomfilter says they exist. Frequency should be inferior to :
        System.out.println(ACCEPTABLE_FALSE_POSITIVE_FREQUENCY);
    }



}