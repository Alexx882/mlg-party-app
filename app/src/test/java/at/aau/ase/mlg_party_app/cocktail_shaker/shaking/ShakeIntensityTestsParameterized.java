package at.aau.ase.mlg_party_app.cocktail_shaker.shaking;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;
import java.util.LinkedList;

@RunWith(Parameterized.class)
public class ShakeIntensityTestsParameterized {
    private float pvalue;
    private ShakeIntensity pres;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {

        Collection<Object[]> params = new LinkedList<>();

        params.add(new Object[]{0f, ShakeIntensity.NON_EXISTENT});
        params.add(new Object[]{1.9f, ShakeIntensity.NON_EXISTENT});

        params.add(new Object[]{2f, ShakeIntensity.LOW});
        params.add(new Object[]{2.1f, ShakeIntensity.LOW});
        params.add(new Object[]{3.9f, ShakeIntensity.LOW});

        params.add(new Object[]{4f, ShakeIntensity.MEDIUM});
        params.add(new Object[]{4.1f, ShakeIntensity.MEDIUM});
        params.add(new Object[]{5.9f, ShakeIntensity.MEDIUM});

        params.add(new Object[]{6f, ShakeIntensity.DEACENT});
        params.add(new Object[]{6.1f, ShakeIntensity.DEACENT});
        params.add(new Object[]{7.9f, ShakeIntensity.DEACENT});

        params.add(new Object[]{8f, ShakeIntensity.FAST});
        params.add(new Object[]{8.1f, ShakeIntensity.FAST});
        params.add(new Object[]{11.9f, ShakeIntensity.FAST});

        params.add(new Object[]{12f, ShakeIntensity.CRAZY});
        params.add(new Object[]{12.1f, ShakeIntensity.CRAZY});
        params.add(new Object[]{100f, ShakeIntensity.CRAZY});

        return params;
    }

    public ShakeIntensityTestsParameterized(float value, ShakeIntensity res) {
        pvalue = value;
        pres = res;
    }

    @Test
    public void convertFromFloat() {
        Assert.assertEquals(pres, ShakeIntensity.convertFromFloat(pvalue));
    }
}
