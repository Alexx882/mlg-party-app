package at.aau.ase.mlg_party_app.game_setup;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import at.aau.ase.mlg_party_app.Game;
import at.aau.ase.mlg_party_app.R;
import at.aau.ase.mlg_party_app.networking.MessageType;
import at.aau.ase.mlg_party_app.networking.NetworkConstants;
import at.aau.ase.mlg_party_app.networking.dtos.lobby.JoinLobbyRequest;
import at.aau.ase.mlg_party_app.networking.dtos.lobby.JoinLobbyResponse;
import at.aau.ase.mlg_party_app.networking.dtos.lobby.PlayerJoinedResponse;
import at.aau.ase.mlg_party_app.networking.websocket.WebSocketClient;

public class JoinGameActivity extends BasicLobbyActivity {

    private EditText editTextPlayerName,
            editTextLobbyName;
    private Button buttonConnect;
    private TextView textViewInformation,
            textViewPlayerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_game);

        Game.getInstance().setLobbyOwner(false);
        initUi();
        initMessaging();
    }

    private void initMessaging() {
        WebSocketClient.getInstance().connectToServer(NetworkConstants.ENDPOINT_LOBBY);
        WebSocketClient.getInstance().registerCallback(MessageType.JoinLobby, this::handleJoinLobby);
        WebSocketClient.getInstance().registerCallback(MessageType.PlayerJoined, this::handlePlayerJoined);
    }

    private void handlePlayerJoined(PlayerJoinedResponse response) {
        StringBuilder sb = new StringBuilder();
        for (String s : response.playerNames) {
            sb.append(s);
            sb.append("\n");
        }

        runOnUiThread(() -> textViewPlayerList.setText(sb.toString()));
    }

    private void handleJoinLobby(JoinLobbyResponse response) {
        if (response.status != 200) {
            runOnUiThread(() ->
            {
                String error = "Could not join lobby";
                Log.w("mlg-party", error);
                textViewInformation.setText(error);
            });
            return;
        }

        Game.getInstance().setPlayerId(response.playerId);

        runOnUiThread(this::updateUiForGameStart);
    }

    private void updateUiForGameStart() {
        Intent intent = new Intent(this, BetweenGamesActivity.class);
        intent.putExtra("game", Game.getInstance());
        startActivity(intent);
    }

    private void initUi() {
        editTextLobbyName = findViewById(R.id.editTextLobbyName);
        editTextPlayerName = findViewById(R.id.editTextPlayerName);

        buttonConnect = findViewById(R.id.buttonConnect);
        buttonConnect.setOnClickListener(v -> connectToLobby());

        textViewInformation = findViewById(R.id.textViewError);
        textViewPlayerList = findViewById(R.id.textViewPlayerList);

        playBackgroundSound();
    }

    /**
     * Connects to the lobby with lobbyname and playername.
     */
    private void connectToLobby() {
        String lobbyname = editTextLobbyName.getText().toString();
        String playername = editTextPlayerName.getText().toString();

        JoinLobbyRequest req = new JoinLobbyRequest();
        req.type = MessageType.JoinLobby;
        req.lobbyName = lobbyname;
        req.playerName = playername;
        WebSocketClient.getInstance().sendMessage(req);
    }

    @Override
    protected void onDestroy() {
        WebSocketClient.getInstance().disconnectFromServer();
        super.onDestroy();
    }
}
