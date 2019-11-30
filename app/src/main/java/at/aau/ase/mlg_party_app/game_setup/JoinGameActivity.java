package at.aau.ase.mlg_party_app.game_setup;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import at.aau.ase.mlg_party_app.R;
import at.aau.ase.mlg_party_app.networking.MessageType;
import at.aau.ase.mlg_party_app.networking.dtos.lobby.JoinLobbyRequest;
import at.aau.ase.mlg_party_app.networking.dtos.lobby.JoinLobbyResponse;
import at.aau.ase.mlg_party_app.networking.dtos.lobby.PlayerJoinedResponse;
import at.aau.ase.mlg_party_app.networking.websocket.WebSocketClient;

public class JoinGameActivity extends AppCompatActivity {

    EditText editTextPlayerName, editTextLobbyName;
    Button buttonConnect;

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
        // todo show players
    }

    private void handleJoinLobby(JoinLobbyResponse response) {
        // todo disable button and store playerId
    }

    private void initUi() {
        editTextLobbyName = findViewById(R.id.editTextLobbyName);
        editTextPlayerName = findViewById(R.id.editTextPlayerName);
        buttonConnect = findViewById(R.id.buttonConnect);

        buttonConnect.setOnClickListener(v -> connectToLobby());
    }

    private void connectToLobby() {
        String lobbyname = editTextLobbyName.getText().toString();
        String playername = editTextPlayerName.getText().toString();

        JoinLobbyRequest req = new JoinLobbyRequest();
        req.type = MessageType.JoinLobby;
        req.lobbyName = lobbyname;
        req.playerName = playername;
        WebSocketClient.getInstance().sendMessage(req);
    }
}
