package at.aau.ase.mlg_party_app.cocktail_shaker;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

public class ShakeHandlerTests {
    ShakeHandler h;

    @Before
    public void before() {
        h = new ShakeHandler();
    }

    @Test
    public void receiveShakeValue_floatCallback_float() {
        Float f = 10f;
        AtomicBoolean handled = new AtomicBoolean(false);

        h.registerFloatCallback((val) -> {
            Assert.assertEquals(f, val);
            handled.set(true);
        });

        h.receiveShakeValue(10);
        Assert.assertTrue(handled.get());
    }

}
