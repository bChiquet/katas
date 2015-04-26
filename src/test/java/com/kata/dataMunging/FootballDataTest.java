package com.kata.dataMunging;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FootballDataTest {
    public static final String PATH_TO_DATA = "C:\\Users\\Py\\IdeaProjects\\katas\\src\\test\\ressources\\football.dat";
    FootballData footballData;

    @Before
    public void setUp() throws Exception{
        footballData = new FootballData(PATH_TO_DATA);
    }

    @Test
    public void should_return_team_with_lowest_goals_spread() throws Exception{
        Assert.assertEquals("Aston_Villa", footballData.getTeamWithSmallestScoredTakenDifference());
    }

}