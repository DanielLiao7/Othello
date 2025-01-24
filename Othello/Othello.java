/* 
    Daniel Liao, 2022
*/

public class Othello {

    private int[][] board; //0 = empty; 1 = black; 2 = white
    private int[][] prevBoard;
    private int currentTurn = 1;
    private int prevTurn = 1;
    private int numBlackPieces;
    private int numWhitePieces;
    private boolean gameEnded = false;

    public Othello(){
        //initialize board
        board = new int[8][8];
        board[3][3] = 2;
        board[3][4] = 1;
        board[4][3] = 1;
        board[4][4] = 2;

        prevBoard = new int[8][8];
        prevBoard[3][3] = 2;
        prevBoard[3][4] = 1;
        prevBoard[4][3] = 1;
        prevBoard[4][4] = 2;

        Window window = new Window(this, 640, 540);
    }

    public boolean legalMove(int row, int col){
        if(board[row][col] != 0) {
            return false;
        }

        //Check if there is a matching piece in all 8 directions
        //Up
        for(int check = row + 1; check < board.length; check++){
            if(board[check][col] == currentTurn){
                //Break if a matching piece is found directly next to the piece
                if(check == row + 1){
                    break;
                }
                return true;
            }
            //Break if there is a gap
            else if(board[check][col] == 0){
                break;
            }
        }

        //Down
        for(int check = row - 1; check >= 0; check--){
            if(board[check][col] == currentTurn){
                if(check == row - 1){
                    break;
                }
                return true;
            }
            else if(board[check][col] == 0){
                break;
            }
        }

        //Right
        for(int check = col + 1; check < board[0].length; check++){
            if(board[row][check] == currentTurn){
                if(check == col + 1){
                    break;
                }
                return true;
            }
            else if(board[row][check] == 0){
                break;
            }
        }

        //Left
        for(int check = col - 1; check >= 0; check--){
            if(board[row][check] == currentTurn){
                if(check == col -1){
                    break;
                }
                return true;
            }
            else if(board[row][check] == 0){
                break;
            }
        }

        //Top Right
        for(int check = 1; (row - check >= 0 && col + check < board.length); check++){
            if(board[row - check][col + check] == currentTurn){
                if(check == 1){
                    break;
                }
                return true;
            }
            else if(board[row - check][col + check] == 0){
                break;
            }
        }

        //Bottom Right
        for(int check = 1; (row + check < board.length && col + check < board.length); check++){
            if(board[row + check][col + check] == currentTurn){
                if(check == 1){
                    break;
                }
                return true;
            }
            else if(board[row + check][col + check] == 0){
                break;
            }
        }

        //Top Left
        for(int check = 1; (row - check >= 0 && col - check >= 0); check++){
            if(board[row - check][col - check] == currentTurn){
                if(check == 1){
                    break;
                }
                return true;
            }
            else if(board[row - check][col - check] == 0){
                break;
            }
        }

        //Bottom Left
        for(int check = 1; (row + check < board.length && col - check >= 0); check++){
            if(board[row + check][col - check] == currentTurn){
                if(check == 1){
                    break;
                }
                return true;
            }
            else if(board[row + check][col - check] == 0){
                break;
            }
        }

        return false;
    }

    //THIS METHOD IS FOR THE AI
    public boolean legalMove(int row, int col, int[][] board, int currentTurn){
        if(board[row][col] != 0) {
            return false;
        }

        //Check if there is a matching piece in all 8 directions
        //Up
        for(int check = row + 1; check < board.length; check++){
            if(board[check][col] == currentTurn){
                //Break if a matching piece is found directly next to the piece
                if(check == row + 1){
                    break;
                }
                return true;
            }
            //Break if there is a gap
            else if(board[check][col] == 0){
                break;
            }
        }

        //Down
        for(int check = row - 1; check >= 0; check--){
            if(board[check][col] == currentTurn){
                if(check == row - 1){
                    break;
                }
                return true;
            }
            else if(board[check][col] == 0){
                break;
            }
        }

        //Right
        for(int check = col + 1; check < board[0].length; check++){
            if(board[row][check] == currentTurn){
                if(check == col + 1){
                    break;
                }
                return true;
            }
            else if(board[row][check] == 0){
                break;
            }
        }

        //Left
        for(int check = col - 1; check >= 0; check--){
            if(board[row][check] == currentTurn){
                if(check == col -1){
                    break;
                }
                return true;
            }
            else if(board[row][check] == 0){
                break;
            }
        }

        //Top Right
        for(int check = 1; (row - check >= 0 && col + check < board.length); check++){
            if(board[row - check][col + check] == currentTurn){
                if(check == 1){
                    break;
                }
                return true;
            }
            else if(board[row - check][col + check] == 0){
                break;
            }
        }

        //Bottom Right
        for(int check = 1; (row + check < board.length && col + check < board.length); check++){
            if(board[row + check][col + check] == currentTurn){
                if(check == 1){
                    break;
                }
                return true;
            }
            else if(board[row + check][col + check] == 0){
                break;
            }
        }

        //Top Left
        for(int check = 1; (row - check >= 0 && col - check >= 0); check++){
            if(board[row - check][col - check] == currentTurn){
                if(check == 1){
                    break;
                }
                return true;
            }
            else if(board[row - check][col - check] == 0){
                break;
            }
        }

        //Bottom Left
        for(int check = 1; (row + check < board.length && col - check >= 0); check++){
            if(board[row + check][col - check] == currentTurn){
                if(check == 1){
                    break;
                }
                return true;
            }
            else if(board[row + check][col - check] == 0){
                break;
            }
        }

        return false;
    }

    public void placeTile(int row, int col){
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                prevBoard[i][j] = board[i][j];
            }
        }

        board[row][col] = currentTurn;

        //Up
        //1st loop looks for the first matching piece
        for(int check = row - 1; check >= 0; check--){
            if(board[check][col] == board[row][col]){
                //2nd loop runs through all pieces btw the original and the found matching piece
                for(int found = row - 1; found >= check; found--){
                    board[found][col] = board[row][col];
                }
                break;
            }
            //Break if there is a gap
            else if(board[check][col] == 0){
                break;
            }
        }

        //Down
        for(int check = row + 1; check < board.length; check++){
            if(board[check][col] == board[row][col]){
                for(int found = row + 1; found < check; found++){
                    board[found][col] = board[row][col];
                }
                break;
            }
            else if(board[check][col] == 0){
                break;
            }
        }

        //Right
        for(int check = col + 1; check < board[0].length; check++){
            if(board[row][check] == board[row][col]){
                for(int found = col + 1; found < check; found++){
                    board[row][found] = board[row][col];
                }
                break;
            }
            else if(board[row][check] == 0){
                break;
            }
        }

        //Left
        for(int check = col - 1; check >= 0; check--){
            if(board[row][check] == board[row][col]){
                for(int found = col - 1; found >= check; found--){
                    board[row][found] = board[row][col];
                }
                break;
            }
            else if(board[row][check] == 0){
                break;
            }
        }

        //Top Right
        for(int check = 1; (row - check >= 0 && col + check < board.length); check++){
            if(board[row - check][col + check] == board[row][col]){
                for(int found = 1; found < check; found++){
                    board[row - found][col + found] = board[row][col];
                }
                break;
            }
            else if(board[row - check][col + check] == 0){
                break;
            }
        }

        //Bottom Right
        for(int check = 1; (row + check < board.length && col + check < board.length); check++){
            if(board[row + check][col + check] == board[row][col]){
                for(int found = 1; found < check; found++){
                    board[row + found][col + found] = board[row][col];
                }
                break;
            }
            else if(board[row + check][col + check] == 0){
                break;
            }
        }

        //Top Left
        for(int check = 1; (row - check >= 0 && col - check >= 0); check++){
            if(board[row - check][col - check] == board[row][col]){
                for(int found = 1; found < check; found++){
                    board[row - found][col - found] = board[row][col];
                }
                break;
            }
            else if(board[row - check][col - check] == 0){
                break;
            }
        }

        //Bottom Left
        for(int check = 1; (row + check < board.length && col - check >= 0); check++){
            if(board[row + check][col - check] == board[row][col]){
                for(int found = 1; found < check; found++){
                    board[row + found][col - found] = board[row][col];
                }
                break;
            }
            else if(board[row + check][col - check] == 0){
                break;
            }
        }
    }

    //THIS METHOD IS FOR THE AI
    public int[][] placeFakeTile(int row, int col, int[][] board, int turn){
        int[][] out = new int[8][8];
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++) {
                out[i][j] = board[i][j];
            }
        }

        out[row][col] = turn;

        //Up
        //1st loop looks for the first matching piece
        for(int check = row - 1; check >= 0; check--){
            if(out[check][col] == out[row][col]){
                //2nd loop runs through all pieces btw the original and the found matching piece
                for(int found = row - 1; found >= check; found--){
                    out[found][col] = out[row][col];
                }
                break;
            }
            //Break if there is a gap
            else if(out[check][col] == 0){
                break;
            }
        }

        //Down
        for(int check = row + 1; check < out.length; check++){
            if(out[check][col] == out[row][col]){
                for(int found = row + 1; found < check; found++){
                    out[found][col] = out[row][col];
                }
                break;
            }
            else if(out[check][col] == 0){
                break;
            }
        }

        //Right
        for(int check = col + 1; check < out[0].length; check++){
            if(out[row][check] == out[row][col]){
                for(int found = col + 1; found < check; found++){
                    out[row][found] = out[row][col];
                }
                break;
            }
            else if(out[row][check] == 0){
                break;
            }
        }

        //Left
        for(int check = col - 1; check >= 0; check--){
            if(out[row][check] == out[row][col]){
                for(int found = col - 1; found >= check; found--){
                    out[row][found] = out[row][col];
                }
                break;
            }
            else if(out[row][check] == 0){
                break;
            }
        }

        //Top Right
        for(int check = 1; (row - check >= 0 && col + check < out.length); check++){
            if(out[row - check][col + check] == out[row][col]){
                for(int found = 1; found < check; found++){
                    out[row - found][col + found] = out[row][col];
                }
                break;
            }
            else if(out[row - check][col + check] == 0){
                break;
            }
        }

        //Bottom Right
        for(int check = 1; (row + check < out.length && col + check < out.length); check++){
            if(out[row + check][col + check] == out[row][col]){
                for(int found = 1; found < check; found++){
                    out[row + found][col + found] = out[row][col];
                }
                break;
            }
            else if(out[row + check][col + check] == 0){
                break;
            }
        }

        //Top Left
        for(int check = 1; (row - check >= 0 && col - check >= 0); check++){
            if(out[row - check][col - check] == out[row][col]){
                for(int found = 1; found < check; found++){
                    out[row - found][col - found] = out[row][col];
                }
                break;
            }
            else if(out[row - check][col - check] == 0){
                break;
            }
        }

        //Bottom Left
        for(int check = 1; (row + check < out.length && col - check >= 0); check++){
            if(out[row + check][col - check] == out[row][col]){
                for(int found = 1; found < check; found++){
                    out[row + found][col - found] = out[row][col];
                }
                break;
            }
            else if(out[row + check][col - check] == 0){
                break;
            }
        }

        return out;
    }

    public void updateScore(){
        numBlackPieces = 0;
        numWhitePieces = 0;

        for(int[] row : board){
            for(int col : row){
                if(col == 1){
                    numBlackPieces++;
                }
                else if(col == 2){
                    numWhitePieces++;
                }
            }
        }
    }

    public void resetBoard(){
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                prevBoard[i][j] = board[i][j];
            }
        }
        prevTurn = currentTurn;

        board = new int[8][8];
        board[3][3] = 2;
        board[3][4] = 1;
        board[4][3] = 1;
        board[4][4] = 2;

        currentTurn = 1;

        gameEnded = false;

        updateScore();
    }

    public void changeTurns(){
        prevTurn = currentTurn;

        if(currentTurn == 1){
            currentTurn = 2;
        }
        else{
            currentTurn = 1;
        }
    }

    public void undoMove(){
        for(int row = 0; row < board.length; row++){
            for(int col = 0; col < board[0].length; col++){
                board[row][col] = prevBoard[row][col];
            }
        }
        currentTurn = prevTurn;
        updateScore();
    }

    public int getNumLegalMoves(){
        int count = 0;
        for(int row = 0; row < board.length; row++){
            for(int col = 0; col < board[0].length; col++) {
                if(legalMove(row, col)){
                    count++;
                }
            }
        }
        return count;
    }

    public static void main(String[] args){
        Othello o = new Othello();
    }

    public int[][] getBoard(){
        return board;
    }

    public int getCurrentTurn(){
        return currentTurn;
    }

    public int getNumBlackPieces() {
        return numBlackPieces;
    }

    public int getNumWhitePieces() {
        return numWhitePieces;
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public void setGameEnded(boolean gameEnded) {
        this.gameEnded = gameEnded;
    }
}
