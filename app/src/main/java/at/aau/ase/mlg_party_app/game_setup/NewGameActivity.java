package at.aau.ase.mlg_party_app.game_setup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import at.aau.ase.mlg_party_app.R;
import at.aau.ase.mlg_party_app.networking.dtos.CreateLobbyRequest;
import at.aau.ase.mlg_party_app.networking.dtos.CreateLobbyResponse;
import at.aau.ase.mlg_party_app.networking.dtos.PlayerJoinedResponse;
import at.aau.ase.mlg_party_app.networking.websocket.MessageType;
import at.aau.ase.mlg_party_app.networking.websocket.WebSocketClient;

public class NewGameActivity extends AppCompatActivity {

    TextView textViewWaiting;
    TextView textViewPlayerList;
    Button buttonStartGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        textViewWaiting = findViewById(R.id.textViewWaiting);
        textViewPlayerList = findViewById(R.id.textViewPlayerList);
        buttonStartGame = findViewById(R.id.buttonStartGame);
        buttonStartGame.setOnClickListener(v -> startGame());

        requestNewGame();
    }

    private void startGame() {
        Toast.makeText(this, "start!", Toast.LENGTH_SHORT).show();
        // todo start game.
    }

    private void requestNewGame() {
        WebSocketClient.getInstance().connectToServer();

        WebSocketClient.getInstance().registerCallback(MessageType.CreateLobby, this::handleNewGame);
        WebSocketClient.getInstance().registerCallback(MessageType.PlayerJoined, this::handlePlayerJoined);

        CreateLobbyRequest req = new CreateLobbyRequest();
        req.type = "CreateLobby";

        WebSocketClient.getInstance().sendMessage(req);
    }

    private void handleNewGame(CreateLobbyResponse response) {
        if (response.lobbyName == null) {
            runOnUiThread(() ->
            {
                String error = "Lobby could not be created";
                Log.w("mlg-party", error);
                textViewWaiting.setText(error);
            });
            return;
        }

        runOnUiThread(() -> updateUi(response.lobbyName));

        // todo show lobby number, store player id
    }

    private void handlePlayerJoined(PlayerJoinedResponse response) {
        CharSequence text = textViewPlayerList.getText();
        textViewPlayerList.setText(text + "\n" + response.playerName);
    }

    private void updateUi(String lobbyName) {
        textViewWaiting.setText(lobbyName);
        buttonStartGame.setEnabled(true);
    }
}
