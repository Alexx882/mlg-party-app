package at.aau.ase.mlg_party_app.rps;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class RpsLogicTest {
    private RpsLogic logic;

    @Before // setup()
    public void before() {
        logic = new RpsLogic();
    }

    @Test
    public void checkResult_Draw() {
        for (int i=0;i<100;i++) {
            RpsLogic.Option optionA = RpsLogic.Option.random();
            RpsLogic.Result result = RpsLogic.Result.DRAW;
            RpsLogic.Option optionB = logic.checkEnemyOption(optionA, result);
            assertEquals(optionA, optionB);
        }
    }
    @Test
    public void checkResult_Rock_Paper() {
        RpsLogic.Option optionA = RpsLogic.Option.ROCK;
        RpsLogic.Result result = RpsLogic.Result.LOST;
        RpsLogic.Option optionB = logic.checkEnemyOption(optionA, result);
        assertEquals(optionB, RpsLogic.Option.PAPER);
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
        logic = null;
        assertNull(logic);

    }
}