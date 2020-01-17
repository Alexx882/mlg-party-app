package at.aau.ase.mlg_party_app.rps;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class RpsLogicTest {
    private RpsLogic logic;

    @Before // setup()
    public void before() {
        System.out.println("Setting up!");
        logic = new RpsLogic();
    }

    @Test
    public void checkResult_Draw() {
        for (int i=0;i<100;i++) {
            RpsLogic.Option optionA = RpsLogic.Option.random();
            RpsLogic.Result result = logic.checkResult(optionA, optionA);
            assertEquals(RpsLogic.Result.DRAW, result);
        }
    }
    @Test
    public void checkResult_Rock_Paper() {
        RpsLogic.Option optionA = RpsLogic.Option.ROCK;
        RpsLogic.Option optionB = RpsLogic.Option.PAPER;
        RpsLogic.Result result = logic.checkResult(optionA,optionB);
        assertEquals(RpsLogic.Result.LOST, result);
    }
    @Test
    public void checkResult_Rock_Scissor() {
        RpsLogic.Option optionA = RpsLogic.Option.ROCK;
        RpsLogic.Option optionB = RpsLogic.Option.SCISSOR;
        RpsLogic.Result result = logic.checkResult(optionA,optionB);
        assertEquals(RpsLogic.Result.WON, result);
    }
    @Test
    public void checkResult_Paper_Rock() {
        RpsLogic.Option optionA = RpsLogic.Option.PAPER;
        RpsLogic.Option optionB = RpsLogic.Option.ROCK;
        RpsLogic.Result result = logic.checkResult(optionA,optionB);
        assertEquals(RpsLogic.Result.WON, result);
    }
    @Test
    public void checkResult_Paper_Scissor() {
        RpsLogic.Option optionA = RpsLogic.Option.PAPER;
        RpsLogic.Option optionB = RpsLogic.Option.SCISSOR;
        RpsLogic.Result result = logic.checkResult(optionA,optionB);
        assertEquals(RpsLogic.Result.LOST, result);
    }
    @Test
    public void checkResult_Scissor_Rock() {
        RpsLogic.Option optionA = RpsLogic.Option.SCISSOR;
        RpsLogic.Option optionB = RpsLogic.Option.ROCK;
        RpsLogic.Result result = logic.checkResult(optionA,optionB);
        assertEquals(RpsLogic.Result.LOST, result);
    }
    @Test
    public void checkResult_Scissor_Paper() {
        RpsLogic.Option optionA = RpsLogic.Option.SCISSOR;
        RpsLogic.Option optionB = RpsLogic.Option.PAPER;
        RpsLogic.Result result = logic.checkResult(optionA,optionB);
        assertEquals(RpsLogic.Result.WON, result);
    }
    @Test
    public void checkResult_SecondOptionInvalid() {
        for(int i=0; i<100; i++) {
            RpsLogic.Option optionA = RpsLogic.Option.random();
            RpsLogic.Option optionB = null;
            RpsLogic.Result result = logic.checkResult(optionA, optionB);
            assertEquals(RpsLogic.Result.ERROR, result);
        }
    }
    @Test
    public void checkResult_FirstOptionInvalid() {
        for(int i=0; i<100; i++) {
            RpsLogic.Option optionA = null;
            RpsLogic.Option optionB = RpsLogic.Option.random();
            RpsLogic.Result result = logic.checkResult(optionA, optionB);
            assertEquals(RpsLogic.Result.ERROR, result);
        }
    }
    @Test
    public void checkResult_BothOptionsInvalid() {
        for(int i=0; i<100; i++) {
            RpsLogic.Option optionA = null;
            RpsLogic.Option optionB = null;
            RpsLogic.Result result = logic.checkResult(optionA, optionB);
            assertEquals(RpsLogic.Result.ERROR, result);
        }
    }

    @Test
    public void checkRandom() {
        for (int i = 0; i<100; i++) {
            RpsLogic.Option random = RpsLogic.Option.random();
            assertTrue(random == RpsLogic.Option.SCISSOR || random == RpsLogic.Option.PAPER || random == RpsLogic.Option.ROCK);
        }
    }

    @After // tearDown
    public void after() {
        System.out.println("Tearing down");
        logic = null;
        assertNull(logic);

    }
}