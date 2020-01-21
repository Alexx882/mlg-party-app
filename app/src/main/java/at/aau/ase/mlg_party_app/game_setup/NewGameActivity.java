package at.aau.ase.mlg_party_app.game_setup;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import at.aau.ase.mlg_party_app.Game;
import at.aau.ase.mlg_party_app.R;
import at.aau.ase.mlg_party_app.networking.MessageType;
import at.aau.ase.mlg_party_app.networking.NetworkConstants;
import at.aau.ase.mlg_party_app.networking.dtos.lobby.CreateLobbyRequest;
import at.aau.ase.mlg_party_app.networking.dtos.lobby.CreateLobbyResponse;
import at.aau.ase.mlg_party_app.networking.dtos.lobby.PlayerJoinedResponse;
import at.aau.ase.mlg_party_app.networking.websocket.WebSocketClient;

public class NewGameActivity extends AppCompatActivity {

   private  TextView textViewInformation,
           textViewPlayerList;
    private Button buttonStartGame,
            buttonOpenLobby;
    private  EditText editTextPlayerName;
    private ProgressBar progressBar;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        Game.getInstance().setLobbyOwner(true);
        initUi();
    }

    private void initUi() {
        textViewInformation = findViewById(R.id.textViewInformation);
        textViewPlayerList = findViewById(R.id.textViewPlayerList);
        editTextPlayerName = findViewById(R.id.editTextPlayerName);
        progressBar = findViewById(R.id.progressBar);

        buttonOpenLobby = findViewById(R.id.buttonOpenLobby);
        buttonOpenLobby.setOnClickListener(v -> openLobby());
        buttonStartGame = findViewById(R.id.buttonStartGame);
        buttonStartGame.setOnClickListener(v -> startGame());

        playBackgroundSound();
    }

    private void playBackgroundSound() {
        mediaPlayer = MediaPlayer.create(this, R.raw.lobby_basic);
        mediaPlayer.setVolume(1.0f, 1.0f);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    private void startGame() {
        buttonStartGame.setEnabled(false);

        playLoadingSound();
        progressBar.setVisibility(View.VISIBLE);

        Timer t = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                    Intent intent = new Intent(NewGameActivity.this, BetweenGamesActivity.class);
                    startActivity(intent);
            }
        };
        t.schedule(task, 1000);
    }

    private void playLoadingSound() {
        // stop background music
        if (mediaPlayer != null)
            mediaPlayer.stop();

        mediaPlayer = MediaPlayer.create(this, R.raw.lobby_loading);
        mediaPlayer.setVolume(1.0f, 1.0f);
        mediaPlayer.start();
    }

    private void openLobby() {
        buttonOpenLobby.setEnabled(false);
        String playerName = editTextPlayerName.getText().toString();

        WebSocketClient.getInstance().connectToServer(NetworkConstants.ENDPOINT_LOBBY);
        WebSocketClient.getInstance().registerCallback(MessageType.CreateLobby, this::handleCreateLobby);
        WebSocketClient.getInstance().registerCallback(MessageType.PlayerJoined, this::handlePlayerJoined);

        CreateLobbyRequest req = new CreateLobbyRequest();
        req.type = MessageType.CreateLobby;
        req.playerName = playerName;

        WebSocketClient.getInstance().sendMessage(req);
    }

    private void handleCreateLobby(CreateLobbyResponse response) {
        if (response.lobbyName == null) {
            runOnUiThread(() ->
            {
                String error = "Lobby could not be created";
                Log.w("mlg-party", error);
                textViewInformation.setText(error);
            });
            return;
        }

        runOnUiThread(() -> updateUiForGameStart(response.lobbyName));

        Game.getInstance().setPlayerId(response.playerId);
        Game.getInstance().setLobbyId(response.lobbyName);
    }

    private void handlePlayerJoined(PlayerJoinedResponse response) {
        StringBuilder sb = new StringBuilder();
        for (String s : response.playerNames) {
            sb.append(s);
            sb.append("\n");
        }

        runOnUiThread(() -> textViewPlayerList.setText(sb.toString()));
    }

    private void updateUiForGameStart(String lobbyName) {
        textViewInformation.setText(String.format("Lobby name: %s", lobbyName));
        buttonStartGame.setEnabled(true);
    }

    @Override
    protected void onDestroy() {
        WebSocketClient.getInstance().disconnectFromServer();
        mediaPlayer.stop();
        super.onDestroy();
    }
}
