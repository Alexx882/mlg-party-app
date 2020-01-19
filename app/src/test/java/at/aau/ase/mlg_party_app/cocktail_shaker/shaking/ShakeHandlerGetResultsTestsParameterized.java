package at.aau.ase.mlg_party_app.cocktail_shaker.shaking;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;
import java.util.LinkedList;

import at.aau.ase.mlg_party_app.cocktail_shaker.shaking.ShakeHandler;
import at.aau.ase.mlg_party_app.cocktail_shaker.shaking.ShakeResult;

@RunWith(Parameterized.class)
public class ShakeHandlerGetResultsTestsParameterized {

    private ShakeHandler h;
    private float[] pvalue;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Collection<Object[]> params = new LinkedList<>();

        // single value
        params.add(new Object[]{
                new float[]{2}
        });
        params.add(new Object[]{
                new float[]{7}
        });

        // mult values
        params.add(new Object[]{
                new float[]{2, 4}
        });
        params.add(new Object[]{
                new float[]{7, 0}
        });
        params.add(new Object[]{
                new float[]{7, 2, 7, 5, 2}
        });
        params.add(new Object[]{
                new float[]{7, 1, 1, -2}
        });
        return params;
    }

    public ShakeHandlerGetResultsTestsParameterized(float[] value) {
        pvalue = value;
    }

    @Before
    public void before() {
        h = new ShakeHandler();
        h.start();
    }

    @Test
    public void getResults() {
        float sum = 0, max = 0;
        for (float v : pvalue) {

            h.receiveShakeValue(v);

            sum += v;
            max = Math.max(max, v);
        }

        ShakeResult res = h.getResults();
        Assert.assertEquals(sum / pvalue.length, res.avg, 0.001);
        Assert.assertEquals(max, res.max, 0.001);
    }

}
