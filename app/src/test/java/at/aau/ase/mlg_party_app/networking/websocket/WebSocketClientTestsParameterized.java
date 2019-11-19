package at.aau.ase.mlg_party_app.networking.websocket;


import android.os.Message;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import at.aau.ase.mlg_party_app.Callback;
import at.aau.ase.mlg_party_app.SingletonTestsHelper;
import at.aau.ase.mlg_party_app.networking.MessageType;

/**
 * This class checks if the client can handle all MessageTypes.
 */
@RunWith(Parameterized.class)
public class WebSocketClientTestsParameterized {
    private SingletonTestsHelper singletonHelper = new SingletonTestsHelper();
    private WebSocketClient client;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {

        Collection<Object[]> params = new LinkedList<>();

        for(MessageType type : MessageType.values()){
            params.add(new Object[]{type, "{\"type\":\"" + type + "\"}"});
        }

        return params;
    }

    private MessageType pMessageType;
    private String pJson;

    public WebSocketClientTestsParameterized(MessageType messageType, String json) {
        this.pMessageType = messageType;
        this.pJson = json;
    }

    @Before
    public void before() {
        singletonHelper.resetSingletonWithReflection(WebSocketClient.getInstance());
        client = WebSocketClient.getInstance();
    }

    @Test
    public void handleMessage_MessageType_handled() {
        AtomicBoolean handled = new AtomicBoolean(false);

        Map<MessageType, Callback> callbacks = new HashMap<>();
        callbacks.put(pMessageType, (response) -> {
            handled.set(true);
        });

        client.handleMessage(pJson, callbacks);

        Assert.assertTrue(handled.get());
    }

}
