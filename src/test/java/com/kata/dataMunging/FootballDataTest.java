package com.kata.dataMunging;

import com.google.common.io.Resources;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FootballDataTest {
    FootballData footballData;

    @Before
    public void setUp() throws Exception{
        footballData = new FootballData();
    }

    @Test
    public void should_return_team_with_lowest_goals_spread() throws Exception {
        // Given
        footballData.processFootballData(Resources.getResource("football.dat").getPath());

        // When
        String teamName = footballData.getTeamWithSmallestScoredTakenDifference();

        // Then
        assertThat(teamName).isEqualTo("Aston_Villa");
    }

}