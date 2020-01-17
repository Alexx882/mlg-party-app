package at.aau.ase.mlg_party_app.networking.websocket;


import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import at.aau.ase.mlg_party_app.Callback;
import at.aau.ase.mlg_party_app.networking.JsonParser;
import at.aau.ase.mlg_party_app.networking.NetworkConstants;
import at.aau.ase.mlg_party_app.networking.dtos.BaseRequest;
import at.aau.ase.mlg_party_app.networking.dtos.CreateLobbyRequest;
import at.aau.ase.mlg_party_app.networking.dtos.CreateLobbyResponse;
import at.aau.ase.mlg_party_app.networking.dtos.PlayerJoinedResponse;
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

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        BaseRequest base = jsonParser.fromJson(text, BaseRequest.class);

        if (base.type == null) {
            Log.e("mlg-party", "JSON must not have type null");

        } else if (base.type.equals(MessageType.CreateLobby.name())) {
            if (callbacks.containsKey(MessageType.CreateLobby))
                callbacks.get(MessageType.CreateLobby).callback(jsonParser.fromJson(text, CreateLobbyResponse.class));

        } else if (base.type.equals(MessageType.PlayerJoined.name())) {
            if (callbacks.containsKey(MessageType.PlayerJoined))
                callbacks.get(MessageType.PlayerJoined).callback(jsonParser.fromJson(text, PlayerJoinedResponse.class));
        }
    }

    public void sendMessage(BaseRequest request) {
        webSocket.send(jsonParser.toJson(request));
    }

    public <t> void removeCallback(MessageType messageType){
        callbacks.remove(messageType);
    }
    public <T> void registerCallback(MessageType messageType, Callback<T> callback) {
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
