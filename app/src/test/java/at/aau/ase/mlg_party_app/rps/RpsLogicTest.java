package at.aau.ase.mlg_party_app.rps;

import org.junit.Test;

import static org.junit.Assert.*;

public class RpsLogicTest {
    @Test
    public void checkResult_Draw_Rock() {
        RpsLogic logic = new RpsLogic();
        RpsLogic.OPTION optionA = RpsLogic.OPTION.ROCK;
        RpsLogic.RESULT result = logic.checkResult(optionA,optionA);
        assertEquals(RpsLogic.RESULT.DRAW, result);
    }
    @Test
    public void checkResult_Draw_Paper() {
        RpsLogic logic = new RpsLogic();
        RpsLogic.OPTION optionA = RpsLogic.OPTION.PAPER;
        RpsLogic.RESULT result = logic.checkResult(optionA,optionA);
        assertEquals(RpsLogic.RESULT.DRAW, result);
    }
    @Test
    public void checkResult_Draw_Scissor() {
        RpsLogic logic = new RpsLogic();
        RpsLogic.OPTION optionA = RpsLogic.OPTION.SCISSOR;
        RpsLogic.RESULT result = logic.checkResult(optionA,optionA);
        assertEquals(RpsLogic.RESULT.DRAW, result);
    }
    @Test
    public void checkResult_Rock_Paper() {
        RpsLogic logic = new RpsLogic();
        RpsLogic.OPTION optionA = RpsLogic.OPTION.ROCK;
        RpsLogic.OPTION optionB = RpsLogic.OPTION.PAPER;
        RpsLogic.RESULT result = logic.checkResult(optionA,optionB);
        assertEquals(RpsLogic.RESULT.LOST, result);
    }
    @Test
    public void checkResult_Rock_Scissor() {
        RpsLogic logic = new RpsLogic();
        RpsLogic.OPTION optionA = RpsLogic.OPTION.ROCK;
        RpsLogic.OPTION optionB = RpsLogic.OPTION.SCISSOR;
        RpsLogic.RESULT result = logic.checkResult(optionA,optionB);
        assertEquals(RpsLogic.RESULT.WON, result);
    }
    @Test
    public void checkResult_Paper_Rock() {
        RpsLogic logic = new RpsLogic();
        RpsLogic.OPTION optionA = RpsLogic.OPTION.PAPER;
        RpsLogic.OPTION optionB = RpsLogic.OPTION.ROCK;
        RpsLogic.RESULT result = logic.checkResult(optionA,optionB);
        assertEquals(RpsLogic.RESULT.WON, result);
    }
    @Test
    public void checkResult_Paper_Scissor() {
        RpsLogic logic = new RpsLogic();
        RpsLogic.OPTION optionA = RpsLogic.OPTION.PAPER;
        RpsLogic.OPTION optionB = RpsLogic.OPTION.SCISSOR;
        RpsLogic.RESULT result = logic.checkResult(optionA,optionB);
        assertEquals(RpsLogic.RESULT.LOST, result);
    }
    @Test
    public void checkResult_Scissor_Rock() {
        RpsLogic logic = new RpsLogic();
        RpsLogic.OPTION optionA = RpsLogic.OPTION.SCISSOR;
        RpsLogic.OPTION optionB = RpsLogic.OPTION.ROCK;
        RpsLogic.RESULT result = logic.checkResult(optionA,optionB);
        assertEquals(RpsLogic.RESULT.LOST, result);
    }
    @Test
    public void checkResult_Scissor_Paper() {
        RpsLogic logic = new RpsLogic();
        RpsLogic.OPTION optionA = RpsLogic.OPTION.SCISSOR;
        RpsLogic.OPTION optionB = RpsLogic.OPTION.PAPER;
        RpsLogic.RESULT result = logic.checkResult(optionA,optionB);
        assertEquals(RpsLogic.RESULT.WON, result);
    }
}