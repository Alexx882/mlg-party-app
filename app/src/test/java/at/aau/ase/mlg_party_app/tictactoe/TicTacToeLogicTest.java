package at.aau.ase.mlg_party_app.tictactoe;

import com.google.gson.JsonSyntaxException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import at.aau.ase.mlg_party_app.tictactoe.TicTacToeLogic;
//TODO REMOVE ON CLIENTSIDE
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
    public void simpleInsertionTest(){
        int x=0;
        int y=1;
        int playerId=1; // must be 1 (currentPlayer)
        gameLogic.checkMoveStatus(x,y,playerId);
        testBoard[x][y]=playerId;
        Assert.assertArrayEquals(gameLogic.getGameBoard(), testBoard);
    }

    @Test
    public void simpleInsertionReturnTest(){
        Assert.assertEquals(1 , gameLogic.checkMoveStatus(0,2,1));
    }

    @Test
    public void duplicateInsertionTest(){
        testBoard= new int[][]{
                {0, 0, 1},
                {0, 0, 0},
                {0, 0, 0}
        };
        gameLogic.setGameBoard(testBoard);
        Assert.assertEquals(2,gameLogic.checkMoveStatus(0,2,1));
    }

    @Test
    public void notCurrentPlayerTest(){
        Assert.assertEquals(2,gameLogic.checkMoveStatus(0,0,2));
    }

    @Test
    public void wrongPlayerDuplicateInsertTest(){
        testBoard= new int[][]{
                {2, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        };
        gameLogic.setGameBoard(testBoard);
        Assert.assertEquals(2,gameLogic.checkMoveStatus(0,0,1));
    }
    @Test
    public void winHorizontalTest(){
        testBoard= new int[][]{
                {2, 1, 1},
                {2, 0, 2},
                {1, 1, 0}
        };
        gameLogic.setGameBoard(testBoard);
        Assert.assertEquals(0,gameLogic.checkMoveStatus(2,2,1));
    }
    @Test
    public void winVerticalTest(){
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
    public void winDiagonalToprightToBotleftTest(){
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
    public void winDiagonalTopleftToBotleftTest(){
        testBoard= new int[][]{
                {0, 0, 1},
                {0, 1, 0},
                {0, 0, 0}
        };
        gameLogic.setGameBoard(testBoard);
        Assert.assertEquals(0,gameLogic.checkMoveStatus(2,0,1));
    }
    @Test
    public void drawP1Test(){
        testBoard= new int[][]{
                {1, 2, 1},
                {1, 2, 2},
                {2, 1, 0}
        };
        gameLogic.setGameBoard(testBoard);
        Assert.assertEquals(3,gameLogic.checkMoveStatus(2,2,1));
    }
    @Test
    public void drawP2Test(){
        testBoard= new int[][]{
                {1, 0, 2},
                {2, 1, 0},
                {1, 1, 2}
        };
        gameLogic.setGameBoard(testBoard);
        gameLogic.checkMoveStatus(1,2,1);// To give turn to player 2
        Assert.assertEquals(3,gameLogic.checkMoveStatus(0,1,2));
    }
    @Test
    public void SetAndGetBoardTest(){
        testBoard= new int[][]{
                {0, 2, 2},
                {2, 1, 1},
                {1, 2, 0}
        };
        gameLogic.setGameBoard(testBoard);
        Assert.assertArrayEquals(testBoard,gameLogic.getGameBoard());
    }

}
