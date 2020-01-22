package at.aau.ase.mlg_party_app.networking.websocket;

import android.util.Log;

import com.google.gson.JsonSyntaxException;

import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import at.aau.ase.mlg_party_app.Callback;
import at.aau.ase.mlg_party_app.networking.JsonParser;
import at.aau.ase.mlg_party_app.networking.MessageType;
import at.aau.ase.mlg_party_app.networking.NetworkConstants;
import at.aau.ase.mlg_party_app.networking.dtos.BaseRequest;
import at.aau.ase.mlg_party_app.networking.dtos.BaseResponse;
import at.aau.ase.mlg_party_app.networking.dtos.game.GameFinishedResponse;
import at.aau.ase.mlg_party_app.networking.dtos.game.StartGameResponse;
import at.aau.ase.mlg_party_app.networking.dtos.lobby.CreateLobbyResponse;
import at.aau.ase.mlg_party_app.networking.dtos.lobby.JoinLobbyResponse;
import at.aau.ase.mlg_party_app.networking.dtos.lobby.PlayerJoinedResponse;

public class WebSocketClient {
    private org.java_websocket.client.WebSocketClient webSocketClient;
    private JsonParser jsonParser = new JsonParser();

    private Map<MessageType, Callback> callbacks = new HashMap<>();

    private WebSocketClient() {
    }

    private static WebSocketClient instance;

    public static WebSocketClient getInstance() {
        if (instance == null)
            instance = new WebSocketClient();
        return instance;
    }

    public void connectToServer(String endpoint) {
        disconnectFromServer();

        Log.d("[HERRYTEST]", "connecting to server ... ");

        //Request request = new Request.Builder().url(NetworkConstants.ENDPOINT_PREFIX + endpoint).build();
        // webSocket = client.newWebSocket(request, this);

        webSocketClient = new org.java_websocket.client.WebSocketClient(URI.create(NetworkConstants.ENDPOINT_PREFIX + endpoint)) {
            @Override
            public void onOpen(ServerHandshake _) {
                Log.d("[HERRYTEST]", "openend websocket connection :OOOOO");
            }

            @Override
            public void onMessage(String message) {
                handleMessage(message, callbacks);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {

            }

            @Override
            public void onError(Exception ex) {

            }
        };

        webSocketClient.connect();
        while(!webSocketClient.isOpen());
    }

    public void disconnectFromServer() {
        if (webSocketClient != null)
            webSocketClient.close(1000);
    }

    void handleMessage(String json, Map<MessageType, Callback> callbacks) {
        IllegalArgumentException notValidJsonArgumentException = new IllegalArgumentException("json is not valid");

        if (json == null)
            throw notValidJsonArgumentException;

        BaseResponse base = null;
        try {
            base = jsonParser.fromJson(json, BaseResponse.class);
        } catch (JsonSyntaxException e) {
            throw notValidJsonArgumentException;
        }

        if (base == null || callbacks == null || !callbacks.containsKey(base.type))
            return;

        Callback cb = callbacks.get(base.type);
        Class c = null;
        switch (base.type) {
            case CreateLobby:
                c = CreateLobbyResponse.class;
                break;

            case JoinLobby:
                c = JoinLobbyResponse.class;
                break;

            case PlayerJoined:
                c = PlayerJoinedResponse.class;
                break;

            case GameFinished:
                c = GameFinishedResponse.class;
                break;

            case StartGame:
                c = StartGameResponse.class;
                break;
        }

        if (c != null)
            cb.callback(jsonParser.fromJson(json, c));
    }

    public void sendMessage(BaseRequest request) {
        if (webSocketClient != null)
            webSocketClient.send(jsonParser.toJson(request));
    }

    public <T extends BaseResponse> void registerCallback(MessageType messageType, Callback<T> callback) {
        if (!callbacks.containsKey(messageType))
            callbacks.put(messageType, callback);
    }
}
