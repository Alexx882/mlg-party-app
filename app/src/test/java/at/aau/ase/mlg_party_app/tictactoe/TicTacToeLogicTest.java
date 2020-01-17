package at.aau.ase.mlg_party_app.tictactoe;

import com.google.gson.JsonSyntaxException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import at.aau.ase.mlg_party_app.tictactoe.TicTacToeLogic;
//TODO REMOVE ON CLIENTSIDE
public class TicTacToeLogicTest {
    private TicTacToeLogic gameLogic;
    String tmp;

    @Before
    public void before(){
        gameLogic=new TicTacToeLogic();
        tmp="RandomID";
    }

   @Test
    public void OutOfBoundsTests(){
       Assert.assertFalse(gameLogic.validMove(-1,2,tmp));
       Assert.assertFalse(gameLogic.validMove(4,0,tmp));
       Assert.assertFalse(gameLogic.validMove(1,-2,tmp));
       Assert.assertFalse(gameLogic.validMove(2,31,tmp));
   }
    @Test
    public void AlreadySetTests(){
        gameLogic.setMove(1,1,"yoloID");
        Assert.assertFalse(gameLogic.validMove(1,1,tmp));

    }
    @Test
    public void CheckLastPlayerTests(){

        gameLogic.setMove(1,1,tmp);
        Assert.assertEquals(tmp,gameLogic.getLastPlayer());
        String tmp2="AnotherRandomID";
        gameLogic.setMove(2,1,tmp2);
        Assert.assertEquals(tmp2,gameLogic.getLastPlayer());
    }

}
