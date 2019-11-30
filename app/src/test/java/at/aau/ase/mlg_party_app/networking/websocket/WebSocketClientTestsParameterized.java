package at.aau.ase.mlg_party_app.networking.websocket;


import android.graphics.Paint;
import android.os.Message;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Field;
import java.net.ResponseCache;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import at.aau.ase.mlg_party_app.Callback;
import at.aau.ase.mlg_party_app.SingletonTestsHelper;
import at.aau.ase.mlg_party_app.networking.MessageType;
import at.aau.ase.mlg_party_app.networking.dtos.BaseResponse;
import at.aau.ase.mlg_party_app.networking.dtos.lobby.CreateLobbyResponse;
import at.aau.ase.mlg_party_app.networking.dtos.lobby.JoinLobbyResponse;
import at.aau.ase.mlg_party_app.networking.dtos.lobby.PlayerJoinedResponse;

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

        params.add(new Object[]{MessageType.CreateLobby, CreateLobbyResponse.class, "{\"type\":\"CreateLobby\", \"lobbyName\":\"String\", \"playerId\":\"String\"}"});
        params.add(new Object[]{MessageType.JoinLobby, JoinLobbyResponse.class, "{\"type\":\"JoinLobby\", \"status\":Integer, \"playerId\":\"String\"}"});
        params.add(new Object[]{MessageType.PlayerJoined, PlayerJoinedResponse.class, "{\"type\":\"PlayerJoined\", \"playerNames\":[\"Name1\", \"Name2\"]}"});

        return params;
    }

    private MessageType pMessageType;
    private Class responseClass;
    private String pJson;

    public WebSocketClientTestsParameterized(MessageType messageType, Class responseClass, String json) {
        this.pMessageType = messageType;
        this.responseClass = responseClass;
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
            Assert.assertTrue(responseClass.isInstance(response));
            handled.set(true);
        });

        client.handleMessage(pJson, callbacks);

        // check if called at all
        Assert.assertTrue(handled.get());
    }

}
