package at.aau.ase.mlg_party_app.game_setup;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.aau.ase.mlg_party_app.Game;
import at.aau.ase.mlg_party_app.R;
import at.aau.ase.mlg_party_app.networking.MessageType;
import at.aau.ase.mlg_party_app.networking.dtos.game.StartGameResponse;
import at.aau.ase.mlg_party_app.networking.dtos.lobby.StartGameRequest;
import at.aau.ase.mlg_party_app.networking.websocket.WebSocketClient;

public class BetweenGamesActivity extends AppCompatActivity {

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;
    private ProgressBar loadingBar;
    private String player1ID;
    private String player2ID;
    private String player1Name;
    private String player2Name;
    private int player1Score;
    private int player2Score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_between_games);

        // todo show players
        textViewPlayer1 = findViewById(R.id.textViewPlayerOrder);
        textViewPlayer2 = findViewById(R.id.textViewPlayerOrder);
        loadingBar = findViewById(R.id.progressBarLoadingGame);
        mapData();
        displayStatistics();
        saveData();
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

    private void mapData() {
        // Map result to variables
        if (Game.getInstance().getOtherPlayerScore() == 0 && Game.getInstance().getPlayerScore() == 0) {
            
        }

    }

    private void displayStatistics() {
        textViewPlayer1.setText(player1Name + ": " + player1Score);
        textViewPlayer2.setText(player2Name + ": " + player2Score);

        // Make current leader bold
        // If a draw make both bold
        if(player1Score>player2Score) {
            textViewPlayer1.setTypeface(null, Typeface.BOLD);
        } else if (player1Score<player2Score) {
            textViewPlayer2.setTypeface(null, Typeface.BOLD);
        } else {
            textViewPlayer1.setTypeface(null, Typeface.BOLD);
            textViewPlayer2.setTypeface(null, Typeface.BOLD);
        }
    }
    private void saveData() {
        Game.getInstance().setPlayerScore(player1Score);
        Game.getInstance().setOtherPlayerScore(player2Score);
    }

}
