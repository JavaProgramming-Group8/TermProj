package shootingspaceship;

public class ScoreSystem {
    private int totalScore;
    
    public ScoreSystem() {
        totalScore = 0;
    }

    public void addScore(int point) {
    	totalScore += point;
    }

    public int getScore() {
        return totalScore;
    }
}