package at.aau.ase.mlg_party_app.tictactoe;

import com.google.gson.JsonSyntaxException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import at.aau.ase.mlg_party_app.tictactoe.TicTacToeLogic;

public class TicTacToeLogicTest {
    private TicTacToeLogic gameLogic;
    private  int[][] testBoard;


    @Before
    public void before(){
        gameLogic=new TicTacToeLogic();
        //Creating an empty board
        testBoard= new int[][]{
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        };
    }

    @Test
    public void simpleInsertion(){
        int x=0;
        int y=1;
        int playerId=1; // must be 1 (currentPlayer)
        gameLogic.checkMoveStatus(x,y,playerId);
        testBoard[x][y]=playerId;
        Assert.assertArrayEquals(gameLogic.getGameBoard(), testBoard);
    }

    @Test
    public void simpleInsertionReturn(){
        Assert.assertEquals(1 , gameLogic.checkMoveStatus(0,2,1));
    }

    @Test
    public void duplicateInsertion(){
        testBoard= new int[][]{
                {0, 0, 1},
                {0, 0, 0},
                {0, 0, 0}
        };
        gameLogic.setGameBoard(testBoard);
        Assert.assertEquals(2,gameLogic.checkMoveStatus(0,2,1));
    }

    @Test
    public void notCurrentPlayer(){
        Assert.assertEquals(2,gameLogic.checkMoveStatus(0,0,2));
    }

    @Test
    public void wrongPlayerDuplicateInsert(){
        testBoard= new int[][]{
                {2, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        };
        gameLogic.setGameBoard(testBoard);
        Assert.assertEquals(2,gameLogic.checkMoveStatus(0,0,1));
    }

    @Test
    public void winHorizontal(){
        testBoard= new int[][]{
                {2, 1, 1},
                {2, 0, 2},
                {1, 1, 0}
        };
        gameLogic.setGameBoard(testBoard);
        Assert.assertEquals(0,gameLogic.checkMoveStatus(2,2,1));
    }
    @Test
    public void winVertical(){
        testBoard= new int[][]{
                {0, 1, 0},
                {0, 0, 2},
                {1, 0, 2}
        };
        gameLogic.checkMoveStatus(1,1,1);//to shift current player to 2
        gameLogic.setGameBoard(testBoard);

        Assert.assertEquals(0,gameLogic.checkMoveStatus(0,2,2));
    }
    @Test
    public void winDiagonalToprightToBotleft(){
        testBoard= new int[][]{
                {2, 0, 0},
                {0, 0, 0},
                {0, 0, 2}
        };
        gameLogic.checkMoveStatus(1,1,1);//to shift current player to 2
        gameLogic.setGameBoard(testBoard);
        Assert.assertEquals(0,gameLogic.checkMoveStatus(1,1,2));
    }
    @Test
    public void winDiagonalTopleftToBotleft(){
        testBoard= new int[][]{
                {0, 0, 1},
                {0, 1, 0},
                {0, 0, 0}
        };
        gameLogic.setGameBoard(testBoard);
        Assert.assertEquals(0,gameLogic.checkMoveStatus(2,0,1));
    }

}
