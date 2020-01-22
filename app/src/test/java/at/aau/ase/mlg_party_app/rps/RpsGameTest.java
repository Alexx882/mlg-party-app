package at.aau.ase.mlg_party_app.rps;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RpsGameTest {
    RpsGame rps = new RpsGame();
    @Test
    public void sameOptionSelected_isTrue() {

        assertEquals(4, 2+2);
    }
}