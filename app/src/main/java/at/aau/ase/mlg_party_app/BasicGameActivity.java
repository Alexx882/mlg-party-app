package at.aau.ase.mlg_party_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import at.aau.ase.mlg_party_app.game_setup.networking.HelloGameRequest;
import at.aau.ase.mlg_party_app.networking.MessageType;
import at.aau.ase.mlg_party_app.networking.dtos.game.GameFinishedResponse;
import at.aau.ase.mlg_party_app.networking.websocket.WebSocketClient;

/**
 * This class provides functionality for all games.
 */
public abstract class BasicGameActivity extends AppCompatActivity {

    /**
     * Handles the basic cleanup of the game including storing results and closing the activity.
     *
     * @param r
     */
    public void handleGameFinished(GameFinishedResponse r) {
        WebSocketClient.getInstance().removeCallback(MessageType.GameFinished);

        Game.getInstance().setLastWinnerId(r.winnerId);
        Game.getInstance().setPlayerRanking(r.ranking);
        runOnUiThread(this::finish);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Game.setInstance((Game) getIntent().getSerializableExtra("game"));

        String wsEndpoint = intent.getStringExtra("WS");
        WebSocketClient.getInstance().connectToServer(wsEndpoint);
        WebSocketClient.getInstance().registerCallback(MessageType.GameFinished, this::handleGameFinished);
        HelloGameRequest helloReq = new HelloGameRequest(Game.getInstance().getLobbyId(), Game.getInstance().getPlayerId());
        WebSocketClient.getInstance().sendMessage(helloReq);
    }
}
