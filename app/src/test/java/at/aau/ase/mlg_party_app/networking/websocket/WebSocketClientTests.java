package at.aau.ase.mlg_party_app.networking.websocket;

import android.os.Message;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import at.aau.ase.mlg_party_app.Callback;
import at.aau.ase.mlg_party_app.SingletonTestsHelper;
import at.aau.ase.mlg_party_app.networking.MessageType;

public class WebSocketClientTests {
    private WebSocketClient client;

    @Before
    public void before() {
        SingletonTestsHelper.tryResetSingletonWithReflection(WebSocketClient.getInstance());
        client = WebSocketClient.getInstance();
    }

    @Test
    public void handleMessage_nullCallbacks_noError(){
        client.handleMessage("{}", null);
    }

    @Test
    public void handleMessage_emptyCallbacks_noError(){
        client.handleMessage("{}", null);
    }

    @Test
    public void handleMessage_differentCallbacks_noCall(){
        AtomicBoolean handled = new AtomicBoolean(false);

        Map<MessageType, Callback> callbacks = new HashMap<>();
        callbacks.put(MessageType.JoinLobby, (response) -> {
            handled.set(true);
        });

        client.handleMessage("{\"type\":\"CreateLobby\", \"lobbyName\":\"String\", \"playerId\":\"String\"}", callbacks);

        Assert.assertFalse(handled.get());
    }
}