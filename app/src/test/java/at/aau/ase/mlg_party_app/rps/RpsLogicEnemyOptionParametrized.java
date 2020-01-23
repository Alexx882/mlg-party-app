package at.aau.ase.mlg_party_app.rps;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertNull;

@RunWith(Parameterized.class)
public class RpsLogicEnemyOptionParametrized {
    private RpsLogic.Option optionA;
    private RpsLogic.Option optionB;
    private RpsLogic.Result res;
    private RpsLogic logic;

    @Before // setup()
    public void before() {
        logic = new RpsLogic();
    }

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] {
                { RpsLogic.Option.SCISSOR, RpsLogic.Result.DRAW, RpsLogic.Option.SCISSOR },
                { RpsLogic.Option.PAPER, RpsLogic.Result.DRAW, RpsLogic.Option.PAPER },
                { RpsLogic.Option.ROCK, RpsLogic.Result.DRAW, RpsLogic.Option.ROCK },
                { RpsLogic.Option.SCISSOR, RpsLogic.Result.WON, RpsLogic.Option.PAPER },
                { RpsLogic.Option.PAPER, RpsLogic.Result.WON, RpsLogic.Option.ROCK },
                { RpsLogic.Option.ROCK, RpsLogic.Result.WON, RpsLogic.Option.SCISSOR },
                { RpsLogic.Option.SCISSOR, RpsLogic.Result.LOST, RpsLogic.Option.ROCK },
                { RpsLogic.Option.PAPER, RpsLogic.Result.LOST, RpsLogic.Option.SCISSOR },
                { RpsLogic.Option.ROCK, RpsLogic.Result.LOST, RpsLogic.Option.PAPER }

        });
    }

    public RpsLogicEnemyOptionParametrized(RpsLogic.Option optionA, RpsLogic.Result res, RpsLogic.Option optionB) {
        this.optionA = optionA;
        this.optionB = optionB;
        this.res = res;
    }
    @Test
    public void checkEnemyOptionPara() {
        RpsLogic.Option enemyOption = logic.checkEnemyOption(optionA, res);
        Assert.assertEquals(this.optionB, enemyOption);
    }

    @After // tearDown
    public void after() {
        logic = null;
        assertNull(logic);

    }
}
