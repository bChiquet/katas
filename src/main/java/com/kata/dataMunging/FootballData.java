package com.kata.dataMunging;

import com.google.common.base.Throwables;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Kata implementing football calculations
 * codekata.com/kata/kata04-data-munging/
 * Basically a copy of WeatherData with name refactoring and minor changes
 **/
public class FootballData {
    public static final int FOOTBALL_TEAM_FIELD = 2;
    public static final int GOALS_SCORED_FIELD = 7;
    public static final int GOALS_TAKEN_FIELD = 9;

    private List<TeamScoreInfo> footballTeamsInfoList;

    private class TeamScoreInfo {
        public final String teamName;
        public final int goalsScored;
        public final int goalsTaken;

        TeamScoreInfo(String teamName, int goalsScored, int goalsTaken){
            this.teamName = teamName;
            this.goalsScored = goalsScored;
            this.goalsTaken = goalsTaken;
        }
    }

    void processFootballData(String filePath) {
        try {
            footballTeamsInfoList = footballInfoLineConsumer(Files.lines(Paths.get(filePath)));
        }
        catch(IOException exception){
            Throwables.propagate(exception);
        }
    }

    List<TeamScoreInfo> footballInfoLineConsumer(Stream<String> stream) {
        return getRawStream(stream)
                .map(strings -> new TeamScoreInfo(
                        strings[FOOTBALL_TEAM_FIELD],
                        Integer.parseInt(strings[GOALS_SCORED_FIELD]),
                        Integer.parseInt(strings[GOALS_TAKEN_FIELD])))
                .collect(toList());
    }

    private Stream<String[]> getRawStream(Stream<String> stream) {
        return stream
                // skip header
                .skip(1)
                // skip separation line
                .filter(line -> !line.contains("-----"))
                .map(s -> s.split("\\s+"));
    }

    String getTeamWithSmallestScoredTakenDifference(){
        return footballTeamsInfoList.stream()
                .reduce((teamScoreInfo, teamScoreInfo2) -> {
                    if (Math.abs(teamScoreInfo.goalsTaken - teamScoreInfo.goalsScored)
                            < Math.abs(teamScoreInfo2.goalsTaken - teamScoreInfo2.goalsScored))
                        return teamScoreInfo;

                    return teamScoreInfo2;
                })
                .get()
                .teamName;
    }

}
