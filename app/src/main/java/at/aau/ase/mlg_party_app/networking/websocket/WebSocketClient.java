package at.aau.ase.mlg_party_app.networking.websocket;


import com.google.gson.JsonSyntaxException;

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
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class WebSocketClient extends WebSocketListener {
    private OkHttpClient client = new OkHttpClient();
    private WebSocket webSocket;
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

        Request request = new Request.Builder().url(NetworkConstants.ENDPOINT_PREFIX + endpoint).build();
        webSocket = client.newWebSocket(request, this);
    }

    public void disconnectFromServer() {
        if (webSocket != null) {
            webSocket.close(1000, "closing");
            webSocket = null;
        }
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        handleMessage(text, callbacks);
    }

    public void handleMessage(String json, Map<MessageType, Callback> callbacks) {
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
        webSocket.send(jsonParser.toJson(request));
    }

    public <T extends BaseResponse> void registerCallback(MessageType messageType, Callback<T> callback) {
        if (!callbacks.containsKey(messageType)) {
            callbacks.put(messageType, callback);
        }
    }

}
