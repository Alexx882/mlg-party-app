package at.aau.ase.mlg_party_app.game_setup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import at.aau.ase.mlg_party_app.Game;
import at.aau.ase.mlg_party_app.R;
import at.aau.ase.mlg_party_app.networking.dtos.lobby.JoinLobbyRequest;
import at.aau.ase.mlg_party_app.networking.dtos.lobby.JoinLobbyResponse;
import at.aau.ase.mlg_party_app.networking.MessageType;
import at.aau.ase.mlg_party_app.networking.dtos.lobby.PlayerJoinedResponse;
import at.aau.ase.mlg_party_app.networking.websocket.WebSocketClient;

public class JoinGameActivity extends AppCompatActivity {

    EditText editTextPlayerName, editTextLobbyName;
    Button buttonConnect;
    TextView textViewInformation, textViewPlayerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_game);

        initUi();
        initMessaging();
    }

    private void initMessaging() {
        WebSocketClient.getInstance().connectToServer();
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

        runOnUiThread(this::updateUiForGameStart);

        Game.getInstance().setPlayerId(response.playerId);
    }

    private void updateUiForGameStart() {
        textViewInformation.setText("Waiting for others...");
        buttonConnect.setEnabled(false);
    }

    private void initUi() {
        editTextLobbyName = findViewById(R.id.editTextLobbyName);
        editTextPlayerName = findViewById(R.id.editTextPlayerName);

        buttonConnect = findViewById(R.id.buttonConnect);
        buttonConnect.setOnClickListener(v -> connectToLobby());

        textViewInformation = findViewById(R.id.textViewError);
        textViewPlayerList = findViewById(R.id.textViewPlayerList);
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
