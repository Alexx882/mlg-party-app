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
    ShakeHandler h;
    private float pvalue;
    private String pres;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {

        Collection<Object[]> params = new LinkedList<>();

        params.add(new Object[]{1.5f, "Noob"});
        params.add(new Object[]{3.5f, "Meh..."});

        return params;
    }

    public ShakeHandlerTestsParameterized(float value, String res) {
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

        h.registerStringCallback((val) -> {
            Assert.assertEquals(pres, val);
            handled.set(true);
        });

        h.receiveShakeValue(pvalue);
        Assert.assertTrue(handled.get());
    }
}
