package amoba;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GameBoard {

	private List<Coordinate> coordinates;
	public List<Score> faErtekek;
	private int[][] gameBoard;
	private int score;
	private int J;
	private int K;

	public GameBoard(int J, int K) {
		this.J = J;
		this.K = K;
		faErtekek = new ArrayList<>();
		gameBoard = new int[K][K];
		for (int i = 0; i < K; i++) {
			for (int j = 0; j < K; j++) {
				gameBoard[0][0] = 0;
			}
		}
	}

	public int kiertekel() {
		score = 0;
		int dbX;
		int dbO;

		// sor teszt
		for (int i = 0; i < K; ++i) {
			dbX = 0;
			dbO = 0;
			for (int j = 0; j < K; ++j) {
				if (gameBoard[i][j] == 1) {
					dbX++;
				} else {
					dbO++;
				}

			}
			score += ertekel(dbX, dbO);
		}
		System.out.println("score1 = "  + score);

		// oszlop teszt
		for (int j = 0; j < K; ++j) {
			dbX = 0;
			dbO = 0;
			for (int i = 0; i < K; ++i) {
				if (gameBoard[i][j] == 1) {
					dbX++;
				} else {
					dbO++;
				}
			}
			score += ertekel(dbX, dbO);
		}
		System.out.println("score2 = "  + score);

		dbX = 0;
		dbO = 0;
		// foatlo teszt
		for (int i = 0; i < K; ++i) {
			if (gameBoard[i][i] == 1) {
				dbX++;
			} else if (gameBoard[i][i] == 2) {
				dbO++;
			}
		}

		score += ertekel(dbX, dbO);
		System.out.println("score3 = "  + score);

		
		dbX = 0;
		dbO = 0;
		// mellekatlo teszt
		for (int i = 0; i < K; i++) {
			if (gameBoard[i][K - i - 1] == 1) {
				dbX++;
			} else if (gameBoard[i][K - i - 1] == 2) {
				dbO++;
			}
		}

		score += ertekel(dbX, dbO);
		System.out.println("score4 = "  + score);

		
		return score;
	}

	private int ertekel(int dbX, int dbO) {
		int erteke;
		if (dbX == 3) {
			erteke = 100;
		} else if (dbX == 2 && dbO == 0) {
			erteke = 10;
		} else if (dbX == 1 && dbO == 0) {
			erteke = 1;
		} else if (dbO == 3) {
			erteke = -100;
		} else if (dbO == 2 && dbX == 0) {
			erteke = -10;
		} else if (dbO == 1 && dbX == 0) {
			erteke = -1;
		} else {
			erteke = 0;
		}
		return erteke;
	}
	
int uptoDepth = -1;
    
    public int alphaBetaMinimax(int alpha, int beta, int depth, int turn){
        
        if(beta <= alpha) {
        	System.out.println("Pruning at depth = "+depth);
        	if(turn == 1) { 
        		return Integer.MAX_VALUE; 
        	} else { 
        		return Integer.MIN_VALUE; 
        	}
        }
        
        if(ki_nyert() != 0)
        	return kiertekel();
        
        List<Coordinate> pointsAvailable = getAvailableStates();
        
        if(pointsAvailable.isEmpty()) return 0;
        
        if(depth==0) {
        	faErtekek.clear(); 
        }
        
        int maxValue = Integer.MIN_VALUE, minValue = Integer.MAX_VALUE;
        
        for(int i=0;i<pointsAvailable.size(); ++i){
            Coordinate coordinate = pointsAvailable.get(i);
            
            int currentScore = 0;
            
            if(turn == 1){
                beszur(coordinate, 1); 
                currentScore = alphaBetaMinimax(alpha, beta, depth+1, 2);
                maxValue = Math.max(maxValue, currentScore); 
                
                //Set alpha
                alpha = Math.max(currentScore, alpha);
                
                if(depth == 0) {
                    faErtekek.add(new Score(currentScore, coordinate));
                }
            }else if(turn == 2){
                beszur(coordinate, 2);
                currentScore = alphaBetaMinimax(alpha, beta, depth+1, 1); 
                minValue = Math.min(minValue, currentScore);
                
                //Set beta
                beta = Math.min(currentScore, beta);
            }
            //reset board
            gameBoard[coordinate.getX()][coordinate.getY()] = 0; 
            
            //If a pruning has been done, don't evaluate the rest of the sibling states
            if(currentScore == Integer.MAX_VALUE || currentScore == Integer.MIN_VALUE) break;
        }
        return turn == 1 ? maxValue : minValue;
    }  
	
	public List<Coordinate> getAvailableStates() {
        coordinates = new ArrayList<>();
        for (int i = 0; i < K; ++i) {
            for (int j = 0; j < K; ++j) {
                if (gameBoard[i][j] == 0) {
                    coordinates.add(new Coordinate(i, j));
                }
            }
        }
        return coordinates;
    }

	

	public void beszur(Coordinate coordinate, int player) {
		gameBoard[coordinate.getX()][coordinate.getY()] = player;
	}

	public Coordinate legjobbLepes() {
		int MAX = Integer.MIN_VALUE;
		int best = -1;

		for (int i = 0; i < faErtekek.size(); ++i) {
			if (MAX < faErtekek.get(i).getScore()) {
				MAX = faErtekek.get(i).getScore();
				best = i;
			}
		}

		return faErtekek.get(best).getCoordinate();
	}

	public void nullazTabla() {
		for (int i = 0; i < K; ++i) {
			for (int j = 0; j < K; ++j) {
				gameBoard[i][j] = 0;
			}
		}
	}

	public void kiir() {
		for (int i = 0; i < K; ++i) {
			for (int j = 0; j < K; ++j) {
				System.out.print(gameBoard[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public int ki_nyert() {
		int elsoPont;
		int masodikPont;

		// sor teszt
		for (int i = 0; i < K; i++) {
			elsoPont = 0;
			masodikPont = 0;
			for (int j = 0; j < K; j++) {
				if (gameBoard[i][j] == 1) {
					++elsoPont;
					masodikPont = 0;
					if (elsoPont == J) {
						return 1;
					}
				} else if (gameBoard[i][j] == 2) {
					++masodikPont;
					elsoPont = 0;
					if (masodikPont == J) {
						return 2;
					}
				} else {
					elsoPont = 0;
					masodikPont = 0;
				}
			}
		}

		// oszlop teszt
		for (int i = 0; i < K; i++) {
			elsoPont = 0;
			masodikPont = 0;
			for (int j = 0; j < K; j++) {
				if (gameBoard[j][i] == 1) {
					++elsoPont;
					masodikPont = 0;
					if (elsoPont == J) {
						return 1;
					}
				} else if (gameBoard[j][i] == 2) {
					++masodikPont;
					elsoPont = 0;
					if (masodikPont == J) {
						return 2;
					}
				} else {
					elsoPont = 0;
					masodikPont = 0;
				}
			}
		}

		elsoPont = 0;
		masodikPont = 0;
		// foatlo teszt
		for (int i = 0; i < K; i++) {
			if (gameBoard[i][i] == 1) {
				++elsoPont;
				masodikPont = 0;
				if (elsoPont == J) {
					return 1;
				}
			} else if (gameBoard[i][i] == 2) {
				++masodikPont;
				elsoPont = 0;
				if (masodikPont == J) {
					return 2;
				}
			} else {
				elsoPont = 0;
				masodikPont = 0;
			}
		}

		elsoPont = 0;
		masodikPont = 0;
		// mellekatlo teszt
		for (int i = 0; i < K; i++) {
			if (gameBoard[i][K - i - 1] == 1) {
				++elsoPont;
				masodikPont = 0;
				if (elsoPont == J) {
					return 1;
				}
			} else if (gameBoard[i][K - i - 1] == 2) {
				++masodikPont;
				elsoPont = 0;
				if (masodikPont == J) {
					return 2;
				}
			} else {
				elsoPont = 0;
				masodikPont = 0;
			}
		}

		return 0;
	}
}