package at.aau.ase.mlg_party_app;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;

import at.aau.ase.mlg_party_app.networking.websocket.WebSocketClient;

public class SingletonTestsHelper {

    /**
     * This method can be used to reset Singletons in the test fixture.
     * @param instance A Singleton which stores the object in field "instance".
     * @return True if the reset worked
     */
    public static boolean tryResetSingletonWithReflection(Object instance) {
        try {
            Field instanceField = instance.getClass().getDeclaredField("instance");
            instanceField.setAccessible(true);
            instanceField.set(instance, null);
            return true;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Test
    public void resetSingletonWithReflection_WebSocketClient() {
        WebSocketClient c1 = WebSocketClient.getInstance();
        WebSocketClient c2 = WebSocketClient.getInstance();
        Assert.assertEquals(c1, c2);

        tryResetSingletonWithReflection(WebSocketClient.getInstance());

        WebSocketClient c3 = WebSocketClient.getInstance();
        Assert.assertNotEquals(c1, c3);
    }

}
