package sk.tuke.gamestudio.game.core;

public class Player {
    private String name;
    private int score;

    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void addToScore(int points){
        this.score += points;
    }

    public void resetScore(){
        this.score=0;
    }

}
