package com.kata.bloomFilter;

import com.google.common.io.Resources;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by bec on 21/05/15.
 * tests for class BloomFilter
 */
public class BloomFilterTest {

    static final double ACCEPTABLE_FALSE_POSITIVE_FREQUENCY = 0.1;
    BloomFilter bloomFilter;

    @Before
    public void setUp() throws Exception {
        int numberOfWords = 140000;
        double falsePositiveRate = 0.0001;
        bloomFilter = new BloomFilter(numberOfWords, falsePositiveRate);
    }

    @Test(expected = BloomFilter.FilterCantBuildException.class)
    public void should_throw_with_unrealistic_filter_build_size() throws Exception{
        new BloomFilter(Integer.MAX_VALUE/3, 0.0001);
    }

    @Test
    public void should_return_false_when_too_many_words_are_added(){
        bloomFilter.addWordsFromFile(Resources.getResource("wordlist").getPath());

        boolean bulkWordAddCheck =
                bloomFilter.addWordsFromFile(Resources.getResource("wordlist").getPath());
        boolean wordAddCheck = bloomFilter.addWord("rot");

        assertTrue(!bulkWordAddCheck);
        assertTrue(!wordAddCheck);
    }

    @Test
    public void should_determine_if_word_hash_exists_in_bloomfilter(){
        bloomFilter.addWord("art");
        assertTrue(bloomFilter.contains("art"));
        assertTrue(!bloomFilter.contains("arts"));
        assertTrue(!bloomFilter.contains("ars"));
    }

    @Test
    public void should_process_word_list(){
        bloomFilter.addWordsFromFile(Resources.getResource("wordlist").getPath());
        assertTrue(bloomFilter.contains("artisan"));
        assertEquals(281, bloomFilter.spaceLeft());
    }

    @Test
    public void should_add_word_to_bitset(){
        bloomFilter.addWord("craftsman");
        assertTrue(bloomFilter.contains("craftsman"));
        assertEquals(139999, bloomFilter.spaceLeft());
    }

    @Test
    public void should_have_acceptable_false_positive_frequeny() {
        bloomFilter.addWordsFromFile(Resources.getResource("wordlist").getPath());
        //TODO generate a couple thousand imaginary words not in wordlist and check if
        // bloomfilter says they exist. Frequency should be inferior to :
        System.out.println(ACCEPTABLE_FALSE_POSITIVE_FREQUENCY);
    }
}