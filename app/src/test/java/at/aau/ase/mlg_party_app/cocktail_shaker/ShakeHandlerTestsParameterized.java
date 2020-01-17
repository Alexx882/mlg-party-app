package at.aau.ase.mlg_party_app.cocktail_shaker;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

import at.aau.ase.mlg_party_app.networking.MessageType;
import at.aau.ase.mlg_party_app.networking.dtos.lobby.CreateLobbyResponse;
import at.aau.ase.mlg_party_app.networking.dtos.lobby.JoinLobbyResponse;
import at.aau.ase.mlg_party_app.networking.dtos.lobby.PlayerJoinedResponse;

@RunWith(Parameterized.class)
public class ShakeHandlerTestsParameterized {
    private ShakeHandler h;
    private float pvalue;
    private ShakeIntensity pres;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {

        Collection<Object[]> params = new LinkedList<>();

        params.add(new Object[]{1.9f, ShakeIntensity.NonExistent});

        params.add(new Object[]{2f, ShakeIntensity.Low});
        params.add(new Object[]{2.1f, ShakeIntensity.Low});
        params.add(new Object[]{3.9f, ShakeIntensity.Low});

        params.add(new Object[]{4f, ShakeIntensity.Medium});
        params.add(new Object[]{4.1f, ShakeIntensity.Medium});
        params.add(new Object[]{5.9f, ShakeIntensity.Medium});

        return params;
    }

    public ShakeHandlerTestsParameterized(float value, ShakeIntensity res) {
        pvalue = value;
        pres = res;
    }

    @Before
    public void before() {
        h = new ShakeHandler();
    }

    @Test
    public void receiveShakeValue_stringCallback_meh() {
        AtomicBoolean handled = new AtomicBoolean(false);

        h.registerCallback((val) -> {
            Assert.assertEquals(pres, val.message);
            handled.set(true);
        });

        h.receiveShakeValue(pvalue);
        Assert.assertTrue(handled.get());
    }
}