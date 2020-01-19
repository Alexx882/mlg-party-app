package at.aau.ase.mlg_party_app.game_setup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import at.aau.ase.mlg_party_app.Game;
import at.aau.ase.mlg_party_app.R;
import at.aau.ase.mlg_party_app.networking.MessageType;
import at.aau.ase.mlg_party_app.networking.dtos.BaseResponse;
import at.aau.ase.mlg_party_app.networking.dtos.game.StartGameResponse;
import at.aau.ase.mlg_party_app.networking.dtos.lobby.StartGameRequest;
import at.aau.ase.mlg_party_app.networking.websocket.WebSocketClient;

public class BetweenGamesActivity extends AppCompatActivity {

    private TextView textViewPlayerOrder;
    private ProgressBar loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_between_games);

        textViewPlayerOrder = findViewById(R.id.textViewPlayerOrder);
        loadingBar = findViewById(R.id.progressBarLoadingGame);

        loadNextGame();
    }

    private void loadNextGame() {
        WebSocketClient.getInstance().registerCallback(MessageType.StartGame, this::handleStartGame);

        if (!Game.getInstance().isLobbyOwner())
            return;

        StartGameRequest r = new StartGameRequest(Game.getInstance().getLobbyId());
        WebSocketClient.getInstance().sendMessage(r);
    }

    private void handleStartGame(StartGameResponse response) {
        String wsEndpoint = response.gameEndpoint;

        WebSocketClient.getInstance().disconnectFromServer();

        Class<? extends AppCompatActivity> c = MiniGameManager.getGameMap().get(wsEndpoint);
        Intent intent = new Intent(this, c);
        intent.putExtra("WS", wsEndpoint);
        startActivity(intent);
    }

}
