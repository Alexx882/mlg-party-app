package at.aau.ase.mlg_party_app.networking.websocket;

import android.os.Message;

import org.junit.Assert;
import org.junit.Before;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import at.aau.ase.mlg_party_app.Callback;
import at.aau.ase.mlg_party_app.SingletonTestsHelper;
import at.aau.ase.mlg_party_app.networking.MessageType;

public class WebSocketClientTests {
    private SingletonTestsHelper singletonHelper = new SingletonTestsHelper();
    private WebSocketClient client;

    @Before
    public void before() {
        singletonHelper.resetSingletonWithReflection(WebSocketClient.getInstance());
        client = WebSocketClient.getInstance();
    }


}