package at.aau.ase.mlg_party_app.game_setup;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import at.aau.ase.mlg_party_app.Game;
import at.aau.ase.mlg_party_app.R;
import at.aau.ase.mlg_party_app.networking.MessageType;
import at.aau.ase.mlg_party_app.networking.dtos.lobby.CreateLobbyRequest;
import at.aau.ase.mlg_party_app.networking.dtos.lobby.CreateLobbyResponse;
import at.aau.ase.mlg_party_app.networking.dtos.lobby.PlayerJoinedResponse;
import at.aau.ase.mlg_party_app.networking.websocket.WebSocketClient;

public class NewGameActivity extends AppCompatActivity {

    TextView textViewInformation, textViewPlayerList;
    Button buttonStartGame, buttonOpenLobby;
    EditText editTextPlayerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        initUi();

    }

    private void initUi() {
        textViewInformation = findViewById(R.id.textViewInformation);
        textViewPlayerList = findViewById(R.id.textViewPlayerList);
        editTextPlayerName = findViewById(R.id.editTextPlayerName);

        buttonOpenLobby = findViewById(R.id.buttonOpenLobby);
        buttonOpenLobby.setOnClickListener(v -> openLobby());
        buttonStartGame = findViewById(R.id.buttonStartGame);
        buttonStartGame.setOnClickListener(v -> startGame());
    }

    private void startGame() {
        Toast.makeText(this, "start!", Toast.LENGTH_SHORT).show();
        // todo start game.
    }

    private void openLobby() {
        String playerName = editTextPlayerName.getText().toString();

        WebSocketClient.getInstance().connectToServer();
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
    }

    private void handlePlayerJoined(PlayerJoinedResponse response) {
        // String.join("\n", response.playerNames)
        StringBuilder sb = new StringBuilder();
        for(String s : response.playerNames) {
            sb.append(s);
            sb.append("\n");
        }

        runOnUiThread(()->textViewPlayerList.setText(sb.toString()));
    }

    private void updateUiForGameStart(String lobbyName) {
        textViewInformation.setText("Lobby name: " + lobbyName);
        buttonStartGame.setEnabled(true);
    }
}
