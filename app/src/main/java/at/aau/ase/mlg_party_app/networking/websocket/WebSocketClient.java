package at.aau.ase.mlg_party_app.networking.websocket;


import java.util.HashMap;
import java.util.Map;

import at.aau.ase.mlg_party_app.Callback;
import at.aau.ase.mlg_party_app.networking.JsonParser;
import at.aau.ase.mlg_party_app.networking.MessageType;
import at.aau.ase.mlg_party_app.networking.NetworkConstants;
import at.aau.ase.mlg_party_app.networking.dtos.BaseRequest;
import at.aau.ase.mlg_party_app.networking.dtos.BaseResponse;
import at.aau.ase.mlg_party_app.networking.dtos.game.GameFinishedResponse;
import at.aau.ase.mlg_party_app.networking.dtos.lobby.CreateLobbyResponse;
import at.aau.ase.mlg_party_app.networking.dtos.lobby.JoinLobbyResponse;
import at.aau.ase.mlg_party_app.networking.dtos.lobby.PlayerJoinedResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class WebSocketClient extends WebSocketListener {
    private OkHttpClient client;
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

    public void connectToServer() {
        client = new OkHttpClient();
        Request request = new Request.Builder().url(NetworkConstants.LobbyEndpoint).build();
        webSocket = client.newWebSocket(request, this);
    }

    public void disconnectFromServer() {
        if (webSocket != null)
            webSocket.close(1000, "closing app");
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        handleMessage(text, callbacks);
    }

    public void handleMessage(String json, Map<MessageType, Callback> callbacks) {
        BaseResponse base = jsonParser.fromJson(json, BaseResponse.class);

        Callback cb = callbacks.get(base.type);
        if (cb == null)
            return;

        if (callbacks.containsKey(base.type)) {
            if (base.type == MessageType.CreateLobby)
                cb.callback(jsonParser.fromJson(json, CreateLobbyResponse.class));

            else if (base.type == MessageType.JoinLobby)
                cb.callback(jsonParser.fromJson(json, JoinLobbyResponse.class));

            else if (base.type == MessageType.PlayerJoined)
                cb.callback(jsonParser.fromJson(json, PlayerJoinedResponse.class));

            else if (base.type == MessageType.GameFinished)
                cb.callback(jsonParser.fromJson(json, GameFinishedResponse.class));
        }
    }

    public void sendMessage(BaseRequest request) {
        webSocket.send(jsonParser.toJson(request));
    }

    public <T extends BaseResponse> void registerCallback(MessageType messageType, Callback<T> callback) {
        if (!callbacks.containsKey(messageType)) {
            callbacks.put(messageType, callback);
        }
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        super.onClosing(webSocket, code, reason);
        // todo
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        super.onFailure(webSocket, t, response);
        // todo
    }

}
