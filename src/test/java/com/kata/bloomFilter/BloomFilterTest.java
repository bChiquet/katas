package com.kata.bloomFilter;

import com.google.common.io.Resources;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.NavigableSet;
import java.util.TreeSet;

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
        double falsePositiveRate = 0.001;
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
    public void should_have_acceptable_false_positive_frequeny() throws IOException {
        bloomFilter.addWordsFromFile(Resources.getResource("wordlist").getPath());
        double measuredFalsePositiveFrequency = 0;
        int NUMBER_OF_TESTS = 5000;
        int SIZE_OF_TEST_STRING = 6;

        //Create a dict containing the words added to the filter
        NavigableSet<String> wordSet = new TreeSet<>();
        Files.lines(Paths.get(Resources.getResource("wordlist").getPath()))
                .map(String::trim)
                .forEach(wordSet::add);

        //Test random words
        String randomWord;
        for (int i=1;i<NUMBER_OF_TESTS;i++){
            randomWord = RandomStringUtils.randomAlphabetic(SIZE_OF_TEST_STRING);
            if (wordSet.contains(randomWord)) --i;
            else {
                wordSet.add(randomWord);
                if (bloomFilter.contains(randomWord)) {
                    measuredFalsePositiveFrequency += (double)1/NUMBER_OF_TESTS;
                }
            }
        }
        assertTrue(measuredFalsePositiveFrequency < ACCEPTABLE_FALSE_POSITIVE_FREQUENCY);
    }
}