package at.aau.ase.mlg_party_app.networking.websocket;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import at.aau.ase.mlg_party_app.Callback;
import at.aau.ase.mlg_party_app.networking.JsonParser;
import at.aau.ase.mlg_party_app.networking.NetworkConstants;
import at.aau.ase.mlg_party_app.networking.dtos.BaseRequest;
import at.aau.ase.mlg_party_app.networking.dtos.TicTacToeEndGameResponse;
import at.aau.ase.mlg_party_app.networking.dtos.TicTacToeErrorResponse;
import at.aau.ase.mlg_party_app.networking.dtos.TicTacToeMoveResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class TicTacToeSocketClient extends WebSocketListener {
    //TODO?? More solid/Reusable Approach
    private OkHttpClient client;
    private WebSocket webSocket;
    private JsonParser jsonParser = new JsonParser();

    private Map<MessageType, Callback> callbacks = new HashMap<>();

    private TicTacToeSocketClient() {
    }

    private static TicTacToeSocketClient instance;

    public static TicTacToeSocketClient getInstance() {
        if (instance == null)
            instance = new TicTacToeSocketClient();
        return instance;
    }

    public void connectToServer() {
        client = new OkHttpClient();
        Request request = new Request.Builder().url(NetworkConstants.TicTacToeEndpoint).build();
        webSocket = client.newWebSocket(request, this);
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        BaseRequest base = jsonParser.fromJson(text, BaseRequest.class);

        if (base.type == null) {
            Log.e("mlg-party", "JSON must not have type null");

        } else if (base.type.equals(MessageType.TicTacToe_Move.name())) {
            if (callbacks.containsKey(MessageType.TicTacToe_Move))
                callbacks.get(MessageType.TicTacToe_Move).callback(jsonParser.fromJson(text, TicTacToeMoveResponse.class));

        } else if (base.type.equals(MessageType.TicTacToe_EndGame.name())) {
            if (callbacks.containsKey(MessageType.TicTacToe_EndGame))
                callbacks.get(MessageType.TicTacToe_EndGame).callback(jsonParser.fromJson(text, TicTacToeEndGameResponse.class));

        }else if (base.type.equals(MessageType.TicTacToe_Error.name())) {
            if (callbacks.containsKey(MessageType.TicTacToe_Error))
                callbacks.get(MessageType.TicTacToe_Error).callback(jsonParser.fromJson(text, TicTacToeErrorResponse.class));

        }
    }

    public void sendMessage(BaseRequest request) {
        webSocket.send(jsonParser.toJson(request));
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
