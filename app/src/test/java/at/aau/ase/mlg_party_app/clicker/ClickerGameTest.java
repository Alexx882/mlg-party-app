package at.aau.ase.mlg_party_app.clicker;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import at.aau.ase.mlg_party_app.R;

public class ClickerGameTest {
    int counter = 0;
    Logic logic;

    @Before
    public void before() {
        logic = new Logic();

    }

    @Test
    public void counter() {
        logic.setCounter(0);
        Assert.assertEquals(0, logic.getCounter());
        logic.setCounter(10);
        Assert.assertEquals(10, logic.getCounter());

    }

    @Test
    public void counter_add() {

        logic.setCounter(0);
        for (int i = 0; i < 9; i++)
            logic.setCounter(logic.getCounter() + 1);

        Assert.assertEquals(9, logic.getCounter());

    }

    @Test
    public void cookie_counter(){
        logic.setCounter(0);
        Assert.assertEquals(1 + "", logic.cookiecounter());

        logic.setCounter(68);
        Assert.assertEquals("Nice", logic.cookiecounter());

    }

    @Test
    public void image_setup() {
        logic.setCounter(0);
        Assert.assertEquals(0, logic.imagesetup());
        logic.setCounter(15);
        Assert.assertEquals(R.drawable.lvl15, logic.imagesetup());
        logic.setCounter(30);
        Assert.assertEquals(R.drawable.lvl30, logic.imagesetup());
        logic.setCounter(45);
        Assert.assertEquals(R.drawable.lvl45, logic.imagesetup());
        logic.setCounter(60);
        Assert.assertEquals(R.drawable.lvl60, logic.imagesetup());
        logic.setCounter(69);
        Assert.assertEquals(R.drawable.lvl69, logic.imagesetup());
        logic.setCounter(70);
        Assert.assertEquals(R.drawable.lvl60, logic.imagesetup());
        logic.setCounter(75);
        Assert.assertEquals(R.drawable.endboss, logic.imagesetup());

    }

    @Test
    public void gif_setup(){
        logic.setCounter(0);
        Assert.assertEquals(0, logic.gifsetup());

        logic.setCounter(75);
        Assert.assertEquals(R.drawable.asci, logic.gifsetup());

    }

    @Test
    public void rand() {
        for (int i = 0; i < 100; i++) {
            int dg = logic.rand(10, 5);
            Assert.assertTrue(dg >= -5 && dg < 5);
        }
    }

    @Test
    public void fontsize() {
        logic.setFontsize(0);
        Assert.assertEquals(0, logic.getFontsize());

        logic.setFontsize(10);
        Assert.assertEquals(10, logic.getFontsize());
    }

    @Test
    public void incsize() {
        logic.setFontsize(0);
        for (int i = 1; i < 100; i++)
            Assert.assertEquals(i, logic.incsize());

        logic.setFontsize(99);
        for (int i = 0; i < 100; i++)
            Assert.assertEquals(100, logic.incsize());
    }
}

