package com.kata.dataMunging;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Kata implementing football calculations
 * codekata.com/kata/kata04-data-munging/
 * Basically a copy of WeatherData with name refactoring and minor changes
 **/
public class FootballData {
    public static final int FOOTBALL_TEAM_FIELD = 2;
    public static final int GOALS_SCORED_FIELD = 7;
    public static final int GOALS_TAKEN_FIELD = 9;

    private class TeamScoreInfo {
        public String teamName;
        public int goalsScored;
        public int goalsTaken;

        TeamScoreInfo(String teamName, int goalsScored, int goalsTaken){
            this.teamName = teamName;
            this.goalsScored = goalsScored;
            this.goalsTaken = goalsTaken;
        }
    }
    List<TeamScoreInfo> footballTeamsInfoList = new ArrayList<>();

    FootballData(String filePath) {
        try {
            Stream<String> lines = Files.lines(Paths.get(filePath));
            lines.forEach(this::footballInfoLineConsumer);
        }
        catch(IOException exception){
            System.out.println("IOException");
            //TODO Find a way to handle exception properly
        }
    }

    void footballInfoLineConsumer(String footballInfoLine) {
        String[] footballData = footballInfoLine.split("\\s+");
        try {
            footballTeamsInfoList.add(
                    new TeamScoreInfo(
                            footballData[FOOTBALL_TEAM_FIELD],
                            Integer.parseInt(footballData[GOALS_SCORED_FIELD]),
                            Integer.parseInt(footballData[GOALS_TAKEN_FIELD]))
            );
        }
        catch (ArrayIndexOutOfBoundsException | NumberFormatException ignored){}
    }

    String getTeamWithSmallestScoredTakenDifference(){
        String smallestScoredTakenDifferenceTeam="";
        int smallestScoredTakelDifference = Integer.MAX_VALUE;
        for (TeamScoreInfo team : footballTeamsInfoList){
            //System.out.println(team.teamName + " " + String.valueOf(Math.abs(team.goalsTaken - team.goalsScored)));
            if(Math.abs(team.goalsTaken - team.goalsScored) < smallestScoredTakelDifference) {
                smallestScoredTakenDifferenceTeam = team.teamName;
                smallestScoredTakelDifference = Math.abs(team.goalsTaken - team.goalsScored);
            }
        }
        return smallestScoredTakenDifferenceTeam;
    }

}
