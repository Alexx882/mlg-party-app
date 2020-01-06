package at.aau.ase.mlg_party_app.rps;

import org.junit.Test;

import static org.junit.Assert.*;

public class RpsLogicTest {
    @Test
    public void checkResult_Draw_Rock() {
        RpsLogic logic = new RpsLogic();
        RpsLogic.Option optionA = RpsLogic.Option.ROCK;
        RpsLogic.Result result = logic.checkResult(optionA,optionA);
        assertEquals(RpsLogic.Result.DRAW, result);
    }
    @Test
    public void checkResult_Draw_Paper() {
        RpsLogic logic = new RpsLogic();
        RpsLogic.Option optionA = RpsLogic.Option.PAPER;
        RpsLogic.Result result = logic.checkResult(optionA,optionA);
        assertEquals(RpsLogic.Result.DRAW, result);
    }
    @Test
    public void checkResult_Draw_Scissor() {
        RpsLogic logic = new RpsLogic();
        RpsLogic.Option optionA = RpsLogic.Option.SCISSOR;
        RpsLogic.Result result = logic.checkResult(optionA,optionA);
        assertEquals(RpsLogic.Result.DRAW, result);
    }
    @Test
    public void checkResult_Rock_Paper() {
        RpsLogic logic = new RpsLogic();
        RpsLogic.Option optionA = RpsLogic.Option.ROCK;
        RpsLogic.Option optionB = RpsLogic.Option.PAPER;
        RpsLogic.Result result = logic.checkResult(optionA,optionB);
        assertEquals(RpsLogic.Result.LOST, result);
    }
    @Test
    public void checkResult_Rock_Scissor() {
        RpsLogic logic = new RpsLogic();
        RpsLogic.Option optionA = RpsLogic.Option.ROCK;
        RpsLogic.Option optionB = RpsLogic.Option.SCISSOR;
        RpsLogic.Result result = logic.checkResult(optionA,optionB);
        assertEquals(RpsLogic.Result.WON, result);
    }
    @Test
    public void checkResult_Paper_Rock() {
        RpsLogic logic = new RpsLogic();
        RpsLogic.Option optionA = RpsLogic.Option.PAPER;
        RpsLogic.Option optionB = RpsLogic.Option.ROCK;
        RpsLogic.Result result = logic.checkResult(optionA,optionB);
        assertEquals(RpsLogic.Result.WON, result);
    }
    @Test
    public void checkResult_Paper_Scissor() {
        RpsLogic logic = new RpsLogic();
        RpsLogic.Option optionA = RpsLogic.Option.PAPER;
        RpsLogic.Option optionB = RpsLogic.Option.SCISSOR;
        RpsLogic.Result result = logic.checkResult(optionA,optionB);
        assertEquals(RpsLogic.Result.LOST, result);
    }
    @Test
    public void checkResult_Scissor_Rock() {
        RpsLogic logic = new RpsLogic();
        RpsLogic.Option optionA = RpsLogic.Option.SCISSOR;
        RpsLogic.Option optionB = RpsLogic.Option.ROCK;
        RpsLogic.Result result = logic.checkResult(optionA,optionB);
        assertEquals(RpsLogic.Result.LOST, result);
    }
    @Test
    public void checkResult_Scissor_Paper() {
        RpsLogic logic = new RpsLogic();
        RpsLogic.Option optionA = RpsLogic.Option.SCISSOR;
        RpsLogic.Option optionB = RpsLogic.Option.PAPER;
        RpsLogic.Result result = logic.checkResult(optionA,optionB);
        assertEquals(RpsLogic.Result.WON, result);
    }
    @Test
    public void checkResult_Invalid() {
        RpsLogic logic = new RpsLogic();
        RpsLogic.Option optionA = RpsLogic.Option.SCISSOR;
        RpsLogic.Option optionB = null;
        RpsLogic.Result result = logic.checkResult(optionA,optionB);
        assertEquals(RpsLogic.Result.ERROR, result);
    }
}