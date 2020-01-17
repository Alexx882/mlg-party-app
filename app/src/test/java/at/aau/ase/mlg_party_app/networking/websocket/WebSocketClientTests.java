package at.aau.ase.mlg_party_app.networking.websocket;

import org.junit.Before;

import at.aau.ase.mlg_party_app.SingletonTestsHelper;

public class WebSocketClientTests {
    private SingletonTestsHelper singletonHelper = new SingletonTestsHelper();
    private WebSocketClient client;

    @Before
    public void before() {
        singletonHelper.resetSingletonWithReflection(WebSocketClient.getInstance());
        client = WebSocketClient.getInstance();
    }


}