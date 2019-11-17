package at.aau.ase.mlg_party_app.networking.websocket;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class WebSocketListenerImpl extends WebSocketListener {

    /**
     * Invoked when a web socket has been accepted by the remote peer and may begin transmitting
     * messages.
     */
    public void onOpen(WebSocket webSocket, Response response) {

    }

    /** Invoked when a text (type {@code 0x1}) message has been received. */
    public void onMessage(WebSocket webSocket, String text) {

    }

    /** Invoked when a binary (type {@code 0x2}) message has been received. */
    public void onMessage(WebSocket webSocket, ByteString bytes) {

    }

    /** Invoked when the peer has indicated that no more incoming messages will be transmitted. */
    public void onClosing(WebSocket webSocket, int code, String reason) {

    }

    /**
     * Invoked when both peers have indicated that no more messages will be transmitted and the
     * connection has been successfully released. No further calls to this listener will be made.
     */
    public void onClosed(WebSocket webSocket, int code, String reason) {

    }

    /**
     * Invoked when a web socket has been closed due to an error reading from or writing to the
     * network. Both outgoing and incoming messages may have been lost. No further calls to this
     * listener will be made.
     */
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {

    }

}
