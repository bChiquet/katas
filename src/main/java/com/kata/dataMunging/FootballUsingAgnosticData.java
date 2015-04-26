package com.kata.dataMunging;

import java.util.List;

/**
 * Kata implementing football calculations with generic reader
 * codekata.com/kata/kata04-data-munging/
 **/
public class FootballUsingAgnosticData {
    public static final int FOOTBALL_TEAM_FIELD = 2;
    public static final int GOALS_SCORED_FIELD = 7;
    public static final int GOALS_TAKEN_FIELD = 9;

    private class TeamScoreInfo implements GenericData {
        public String teamName;
        public int goalsScored;
        public int goalsTaken;



        public TeamScoreInfo withValues(String[] dataValues) {
            this.teamName = dataValues[FOOTBALL_TEAM_FIELD];
            this.goalsScored = Integer.parseInt(dataValues[GOALS_SCORED_FIELD]);
            this.goalsTaken =  Integer.parseInt(dataValues[GOALS_TAKEN_FIELD]);
            return this;
        }
    }

    AgnosticData footballData;

    FootballUsingAgnosticData(String filePath){
        footballData = new AgnosticData(filePath, TeamScoreInfo.class);
    }

    String getTeamWithSmallestScoredTakenDifference(){
        String smallestScoredTakenDifferenceTeam="";
        int smallestScoredTakelDifference = Integer.MAX_VALUE;
        for (TeamScoreInfo team : (List<TeamScoreInfo>)footballData.agnosticDataPointsList){
            //System.out.println(team.teamName + " " + String.valueOf(Math.abs(team.goalsTaken - team.goalsScored)));
            if(Math.abs(team.goalsTaken - team.goalsScored) < smallestScoredTakelDifference) {
                smallestScoredTakenDifferenceTeam = team.teamName;
                smallestScoredTakelDifference = Math.abs(team.goalsTaken - team.goalsScored);
            }
        }
        return smallestScoredTakenDifferenceTeam;
    }
}
