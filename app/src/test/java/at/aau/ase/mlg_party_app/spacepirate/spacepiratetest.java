package at.aau.ase.mlg_party_app.spacepirate;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.mock;

@RunWith(PowerMockRunner.class)
@PrepareForTest({BitmapFactory.class, ThumbnailUtils.class})
public class spacepiratetest {
    private Player player;
    private Context context;

    @Before
    public void before() {
        context = mock(Context.class);
        PowerMockito.mockStatic(BitmapFactory.class);
        player = new Player(context, 400, 400);
    }

    @Test
    public void playerspeed() {
        player.setRight();
        Assert.assertEquals(player.getSpeed(), 10);
        player.dontmove();
        Assert.assertEquals(player.getSpeed(), 0);
        player.setLeft();
        Assert.assertEquals(player.getSpeed(), -10);


    }
}
