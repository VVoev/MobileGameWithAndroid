package me.icytower.UltimateCop.Db;

public class Scores {

    private int score;
    private String playerInitials;

    public Scores() {

    }

    public Scores(String playerInitials,int score) {
        this.playerInitials = playerInitials;
        this.score = score;
    }

    public int getScore() {return score;}

    public void setScore(int score) {this.score = score;}

    public String getPlayerInitials() {return playerInitials;}

    public void setPlayerInitials(String playerInitials) {this.playerInitials = playerInitials;}
}
