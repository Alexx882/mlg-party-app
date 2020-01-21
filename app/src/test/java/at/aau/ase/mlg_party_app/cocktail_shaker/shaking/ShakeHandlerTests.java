package at.aau.ase.mlg_party_app.cocktail_shaker.shaking;

import android.hardware.Sensor;
import android.hardware.SensorEvent;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import at.aau.ase.mlg_party_app.cocktail_shaker.shaking.ShakeHandler;

public class ShakeHandlerTests {
    ShakeHandler h;
    AtomicBoolean handled;

    @Before
    public void before() {
        handled = new AtomicBoolean(false);
        h = new ShakeHandler();
        h.start();
    }

    @Test
    public void receiveShakeValue_floatCallback_float() {
        float f = 10f;

        h.registerCallback((val) -> {
            Assert.assertEquals(f, val.value, 0.001);
            handled.set(true);
        });

        h.receiveShakeValue(10);
        Assert.assertTrue(handled.get());
    }

    @Test
    public void receiveShakeValue_notStarted_NoCallbackReceived() {
        h = new ShakeHandler();
        h.registerCallback((val) -> {
            handled.set(true);
        });

        h.receiveShakeValue(4);
        Assert.assertFalse(handled.get());
    }

    @Test
    public void receiveShakeValue_started_callbackReceived() {
        h = new ShakeHandler();
        h.start();
        h.registerCallback((val) -> {
            handled.set(true);
        });

        h.receiveShakeValue(4);
        Assert.assertTrue(handled.get());
    }

    @Test
    public void receiveShakeValue_startedStopped_NoCallbackReceived() {
        h = new ShakeHandler();
        h.start();
        h.registerCallback((val) -> {
            handled.set(true);
        });

        h.receiveShakeValue(4);
        Assert.assertTrue(handled.get());

        handled.set(false);
        h.stop();
        h.receiveShakeValue(4);
        Assert.assertFalse(handled.get());
    }
}
