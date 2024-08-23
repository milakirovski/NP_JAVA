package kolok2.zad29_FootballTable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

class TeamStats implements Comparable<TeamStats>{
    String name;
    int wins;
    int losses;
    int ties;
    int matchesPlayed;
    int goalsGiven;
    int goalsReceived;
    int totalTeamPoints;


    public TeamStats(String name, int wins, int losses, int ties, int matchesPlayed, int goalsGiven, int goalsReceived,
                     int totalTeamPoints) {
        this.name = name;
        this.wins = wins;
        this.losses = losses;
        this.ties = ties;
        this.matchesPlayed = matchesPlayed;
        this.goalsGiven = goalsGiven;
        this.goalsReceived = goalsReceived;
        this.totalTeamPoints = totalTeamPoints;
    }

    public static TeamStats teamStats(String teamName, List<Game> games){
        int goalsGiven = 0;
        int goalsReceived = 0;
        int wins = 0;
        int ties = 0;
        int loses = 0;
        int matchesPlayed = games.size();
        int totalTeamPoints = 0 ;

        for (Game game : games){
            if(game.homeTeam.equals(teamName)){
                //edna presmetka
                if(game.homeGoals > game.awayGoals){
                    ++wins;
                }else if(game.homeGoals == game.awayGoals){
                    ++ties;
                }else{
                    ++loses;
                }

                goalsGiven += game.homeGoals;
                goalsReceived += game.awayGoals;

            }else{
                //stranski team presmetka

                if(game.awayGoals > game.homeGoals){
                    ++wins;
                }else if(game.homeGoals == game.awayGoals){
                    ++ties;
                }else{
                    ++loses;
                }

                goalsGiven += game.awayGoals;
                goalsReceived += game.homeGoals;
            }
        }

        totalTeamPoints =  wins * 3 + ties;

        return new TeamStats(teamName,wins,loses,ties,matchesPlayed,goalsGiven,goalsReceived,totalTeamPoints);

    }

    public String getName() {
        return name;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getTies() {
        return ties;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public int getGoalsGiven() {
        return goalsGiven;
    }

    public int getGoalsReceived() {
        return goalsReceived;
    }

    public int getTotalTeamPoints() {
        return totalTeamPoints;
    }

    public int getGoalDifference(){
        return getGoalsGiven() - getGoalsReceived();
    }

    @Override
    public int compareTo(TeamStats o) {
        Comparator<TeamStats> comparator = Comparator.comparingInt(TeamStats::getTotalTeamPoints)
                .thenComparingInt(TeamStats::getGoalDifference).reversed()
                .thenComparing(TeamStats::getName);

        return comparator.compare(this,o);
    }

    @Override
    public String toString() {
        //сите броеви се печатат со 5 места порамнети во десно
        //Team                   P    W    D    L  PTS
        return String.format("%5d%5d%5d%5d%5d",
                matchesPlayed,
                wins,
                ties,
                losses,
                totalTeamPoints);
    }
}

class Game{
    String homeTeam;
    String awayTeam;
    int homeGoals;
    int awayGoals;

    public Game(String homeTeam, String awayTeam, int homeGoals, int awayGoals) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
    }

}

class FootballTable{
    Map<String, List<Game>> teamsMap;
    Map<String, TeamStats> teamStatsMap;

    public FootballTable() {
        this.teamsMap = new HashMap<>();
        this.teamStatsMap = new HashMap<>();
    }

    public void addGame(String homeTeam, String awayTeam, int homeGoals, int awayGoals) {
        Game newGame = new Game(homeTeam, awayTeam, homeGoals, awayGoals);

        teamsMap.putIfAbsent(homeTeam,new ArrayList<>());
        teamsMap.get(homeTeam).add(newGame);

        teamsMap.putIfAbsent(awayTeam,new ArrayList<>());
        teamsMap.get(awayTeam).add(newGame);

    }

    public void statsPerTeam(){

        Set<String> teamNames = teamsMap.keySet();

        teamNames.forEach(team -> {
            List<Game> gamesPerTeam = teamsMap.get(team);
            TeamStats teamStats = TeamStats.teamStats(team,gamesPerTeam);
            teamStatsMap.putIfAbsent(team,teamStats);
        });
    }

    public void printTable() {
        statsPerTeam();

        teamStatsMap = teamStatsMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())  // Uses the compareTo method in TeamStats
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,  // Handle potential duplicates
                        LinkedHashMap::new  // Maintain the sorted order
                ));

        //1. Liverpool          9    8    0    1   24
        int i = 0;

       for (Map.Entry entry : teamStatsMap.entrySet()){
           ++i;
           if(i < 10){
               System.out.printf(" %d. %-15s%s\n", i, entry.getKey(),entry.getValue());
           }else{
               System.out.printf("%d. %-15s%s\n", i, entry.getKey(),entry.getValue());
           }
       }
    }
}


public class FootballTableTest {
    public static void main(String[] args) throws IOException {
        FootballTable table = new FootballTable();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.lines()
                .map(line -> line.split(";"))
                .forEach(parts -> table.addGame(parts[0], parts[1],
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3])));
        reader.close();
        System.out.println("=== TABLE ===");
        System.out.printf("%-19s%5s%5s%5s%5s%5s\n", "Team", "P", "W", "D", "L", "PTS");
        table.printTable();
    }
}

// Your code here

