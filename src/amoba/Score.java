package amoba;

public class Score {
	
    private int score;
    private Coordinate coordinate;

    public Score(int score, Coordinate coordinate) {
        this.score = score;
        this.coordinate = coordinate;
    }
    
    public int getScore() {
    	return score;
    }
    
    public Coordinate getCoordinate() {
    	return coordinate;
    }
}
