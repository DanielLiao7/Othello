public class OthelloAI {

    private Othello othello;
    private int[][] fakeBoard;

    public OthelloAI(Othello othello){
        this.othello = othello;
        fakeBoard = new int[8][8];
    }

    public void play(){
        if(!othello.isGameEnded()){
            int score = Integer.MIN_VALUE;
            int[] bestPlay = new int[2];
            bestPlay[0] = -1;
            bestPlay[1] = -1;

            for(int row = 0; row < othello.getBoard().length; row++) {
                for (int col = 0; col < othello.getBoard()[0].length; col++) {
                    if(othello.legalMove(row, col)){
                        int[][] out = othello.placeFakeTile(row, col, othello.getBoard(), othello.getCurrentTurn());
                        for(int i = 0; i < out.length; i++) {
                            for (int j = 0; j < out[0].length; j++) {
                                fakeBoard[i][j] = out[i][j];
                            }
                        }
                        int thisScore = 0;
                        for(int i = 0; i < fakeBoard.length; i++){
                            for(int j = 0; j < fakeBoard[0].length; j++) {
                                if(fakeBoard[i][j] == othello.getCurrentTurn() &&
                                        (i == 0 && j == 0
                                                || i == 0 && j == 7
                                                || i == 7 && j == 0
                                                || i == 7 && j == 7)){
                                    thisScore += 10;
                                }
                                else if(fakeBoard[i][j] == othello.getCurrentTurn() &&
                                        (i == 0 || i == 7 || j == 0 || j == 7)){
                                    thisScore += 3;
                                }
                                else if(fakeBoard[i][j] == othello.getCurrentTurn()){
                                    thisScore++;
                                }
                            }
                        }

                        thisScore -= nextMoveScore();

                        if(thisScore > score){
                            score = thisScore;
                            bestPlay[0] = row;
                            bestPlay[1] = col;
                        }
                    }
                }
            }
            if(bestPlay[0] != -1 || bestPlay[1] != -1){
                othello.placeTile(bestPlay[0], bestPlay[1]);
            }
        }
    }

    private int nextMoveScore(){
        int score = 0;
        int nextTurn = 0;
        if(othello.getCurrentTurn() == 1){
            nextTurn = 2;
        }
        else{
            nextTurn = 1;
        }
        int[][] nextBoard = new int[8][8];

        for(int row = 0; row < fakeBoard.length; row++) {
            for (int col = 0; col < fakeBoard[0].length; col++) {
                if (othello.legalMove(row, col, fakeBoard, nextTurn)) {
                    int[][] out = othello.placeFakeTile(row, col, fakeBoard, nextTurn);
                    for(int i = 0; i < out.length; i++) {
                        for (int j = 0; j < out[0].length; j++) {
                            nextBoard[i][j] = out[i][j];
                        }
                    }
                    int thisScore = 0;
                    for (int i = 0; i < nextBoard.length; i++) {
                        for (int j = 0; j < nextBoard[0].length; j++) {
                            if (nextBoard[i][j] == nextTurn &&
                                    (i == 0 && j == 0
                                            || i == 0 && j == 7
                                            || i == 7 && j == 0
                                            || i == 7 && j == 7)) {
                                thisScore += 10;
                            } else if (nextBoard[i][j] == nextTurn &&
                                    (i == 0 || i == 7 || j == 0 || j == 7)) {
                                thisScore += 3;
                            } else if (nextBoard[i][j] == nextTurn) {
                                thisScore++;
                            }
                        }
                    }

                    if (thisScore > score) {
                        score = thisScore;
                    }
                }
            }
        }
        return score;
    }
}
