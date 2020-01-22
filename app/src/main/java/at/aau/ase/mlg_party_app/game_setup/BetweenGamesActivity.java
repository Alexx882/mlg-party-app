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
import java.util.Timer;
import java.util.TimerTask;

import at.aau.ase.mlg_party_app.Game;
import at.aau.ase.mlg_party_app.PlayerInfo;
import at.aau.ase.mlg_party_app.R;
import at.aau.ase.mlg_party_app.networking.MessageType;
import at.aau.ase.mlg_party_app.networking.dtos.game.StartGameResponse;
import at.aau.ase.mlg_party_app.networking.dtos.lobby.StartGameRequest;
import at.aau.ase.mlg_party_app.networking.websocket.WebSocketClient;

public class BetweenGamesActivity extends AppCompatActivity {

    private static final int START_NEXT_GAME_DELAY_MS = 4000;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_between_games);

        textViewPlayer1 = findViewById(R.id.textViewPlayerOrder);
        textViewPlayer2 = findViewById(R.id.textViewPlayerOrder2);

        WebSocketClient.getInstance().registerCallback(MessageType.StartGame, this::handleStartGame);

        displayStatistics();

        loadNextGameWithDelay();
    }

    private void loadNextGameWithDelay() {
        if (!Game.getInstance().isLobbyOwner())
            return;

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                StartGameRequest r = new StartGameRequest(Game.getInstance().getLobbyId());
                WebSocketClient.getInstance().sendMessage(r);
            }
        }, START_NEXT_GAME_DELAY_MS);

    }

    private void handleStartGame(StartGameResponse response) {
        String wsEndpoint = response.gameEndpoint;

        WebSocketClient.getInstance().disconnectFromServer();

        Class<? extends AppCompatActivity> c = MiniGameManager.getGameMap().get(wsEndpoint);
        Intent intent = new Intent(this, c);
        intent.putExtra("WS", wsEndpoint);
        startActivity(intent);
    }

    private void displayStatistics() {
        if (Game.getInstance().getPlayerRanking() == null || Game.getInstance().getPlayerRanking().size() == 0)
            // first round
            return;

        PlayerInfo p1 = Game.getInstance().getPlayerRanking().get(0);
        PlayerInfo p2 = Game.getInstance().getPlayerRanking().get(1);

        String player1ID = p1.id;
        String player1Name = p1.name;
        int player1Score = p1.points;
        String player2ID = p2.id;
        String player2Name = p2.name;
        int player2Score = p2.points;

        //Put overall winner on top
        if (player1Score >= player2Score) {
            textViewPlayer1.setText(player1Name + ": " + player1Score);
            textViewPlayer2.setText(player2Name + ": " + player2Score);

            if (Game.getInstance().getLastWinnerId().equals(player1ID)) {
                textViewPlayer1.setTypeface(null, Typeface.BOLD);
            } else {
                textViewPlayer2.setTypeface(null, Typeface.BOLD);
            }
        } else {
            textViewPlayer2.setText(player1Name + ": " + player1Score);
            textViewPlayer1.setText(player2Name + ": " + player2Score);
            if (Game.getInstance().getLastWinnerId().equals(player2ID)) {
                textViewPlayer2.setTypeface(null, Typeface.BOLD);
            } else {
                textViewPlayer1.setTypeface(null, Typeface.BOLD);
            }

        }

    }

}
