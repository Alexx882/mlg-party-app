package at.aau.ase.mlg_party_app.tictactoe;

class TicTacToeLogic {
    private int [][] gameBoard;
    final private int boardSize=3; // for easy acces instead of .length
    private int currentPlayer;
    TicTacToeLogic() {
       resetGameBoard();
        //Player 1 starts first
         this.currentPlayer=1;
    }
    /*
    Check if the move is valid,
    if yes -> apply the move
    after that check if the game is won.
    Parameter explanation:
    x,y -> where the Player wants to place his move
    playerId    -> used to identify the player (either 1 or 2)

    RETURN VALUES:
        0 -> CURRENT MOVE WON THE GAME
        1 -> Valid move, added to the game board
        2 -> Invalid move
        Maybe:
        3 -> DRAW
        4->  ?? not your turn

     */
    int checkMoveStatus(int x, int y, int playerId){
        int count=0;
        //Check if Move is Valid
        if(!validMove(playerId,x,y))return 2;

        //Change current player (for singleplayer)
            if(currentPlayer==1){
                currentPlayer=2;
            }else{
                currentPlayer=1;
            }

        //Update the game board
        gameBoard[x][y]=playerId;
        //VERTICAL CHECK
        for(int j=0;j<gameBoard[x].length;j++){
            if(gameBoard[x][j]==playerId){
                count++;
            }else{
                break;
            }
        }
        if(count==3){
            return 0;
        }
        count=0;

        //Horizontal CHECK!
        for (int[] ints : gameBoard) {
            if (ints[y] == playerId) {
                count++;
            } else {
                break;
            }
        }
        if(count==3){
            return 0;
        }
        count=0;

        //Diagonal TopRight -> BottomLeft
        for(int i=0;i<gameBoard.length;i++){
            if(gameBoard[i][i]==playerId){
                count++;
            }
        }
        if(count==3){
            return 0;
        }
        count=0;
        //Diagonal BottomLeft -> TopRight
        for(int i=0;i<gameBoard.length;i++){
            int tmp=gameBoard.length-1;
            if(gameBoard[tmp-i][i]==playerId){
                count++;
            }
        }
        if(count==3){
            return 0;
        }

        //Check if there is still a move possible
        if(checkDraw()) return 3;
        return 1;
    }

    private boolean checkDraw(){
        for(int [] i:gameBoard){
            for(int j:i){
                if(j==0)return false;
            }
        }
        return true;
    }
    private boolean validMove(int playerId, int x,int y){
        //Field where player wants to place his move must be empty (0)
        //Must be the correct players turn
        return gameBoard[x][y] == 0 && this.currentPlayer == playerId;
    }
    //GETTER AND SETTERS
    int[][] getGameBoard() {
        return gameBoard;
    }

    //Mainly for Testing purposes -> to test for different boards.
    void setGameBoard(int[][] gameBoard) {
        this.gameBoard = gameBoard;
    }

    void resetGameBoard() {
        //Creating new game board
        this.gameBoard= new int[][]{
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        };
    }

    int getBoardSize() {
        return boardSize;
    }
}
