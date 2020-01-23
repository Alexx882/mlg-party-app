package at.aau.ase.mlg_party_app.tictactoe;

/*
    Logic clientside is only used to check if the moves are valid
 */
class TicTacToeLogic {
    private boolean [][] gameBoard;
    private String lastPlayer;
    final int boardSize=3;
    TicTacToeLogic() {
        this.gameBoard = new boolean[][]{
                {false, false, false},
                {false, false, false},
                {false, false, false}
        };
        this.lastPlayer="";
    }
    void setMove(int x, int y,String playerId){
            this.gameBoard[x][y]=true;
            this.lastPlayer=playerId;
    }

    //Checks if the field is already set or if the position is out of bounds
    boolean validMove(int x, int y,String player){
        return !(x<0 || x>2 ||
                y<0 || y>2 ||
                gameBoard[x][y]||
                player.equals(lastPlayer));
    }


    //GETTER AND SETTERS
    boolean[][] getGameBoard() {
        return gameBoard;
    }

    void setGameBoard(boolean[][] gameBoard) {
        this.gameBoard = gameBoard;
    }
    int getBoardSize(){
        return boardSize;
    }
    String getLastPlayer(){
        return lastPlayer;
    };

    public void setLastPlayer(String lastPlayer) {
        this.lastPlayer = lastPlayer;
    }
}
