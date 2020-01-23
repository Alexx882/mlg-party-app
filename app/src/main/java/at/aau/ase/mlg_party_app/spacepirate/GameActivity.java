package at.aau.ase.mlg_party_app.spacepirate;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Display;

import at.aau.ase.mlg_party_app.BasicGameActivity;
import at.aau.ase.mlg_party_app.Game;
import at.aau.ase.mlg_party_app.game_setup.networking.HelloGameRequest;
import at.aau.ase.mlg_party_app.networking.MessageType;
import at.aau.ase.mlg_party_app.networking.websocket.WebSocketClient;
import at.aau.ase.mlg_party_app.spacepirate.networking.spacepirateResults;


public class GameActivity extends BasicGameActivity {

    //declaring gameview
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Getting display object
        Display display = getWindowManager().getDefaultDisplay();

        //Getting the screen resolution into point object
        Point size = new Point();
        display.getSize(size);

        startTimer(15);

        //Initializing game view object
        //this time we are also passing the screen size to the GameView constructor
        gameView = new GameView(this, size.x, size.y);

        //adding it to contentview
        setContentView(gameView);

    }

    private void startTimer(int seconds) {
        new CountDownTimer(seconds * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                sendResultToServer();
            }
        }.start();
    }


    private void sendResultToServer() {
        spacepirateResults sr = new spacepirateResults();
        sr.lobbyId = Game.getInstance().getLobbyId();
        sr.playerId = Game.getInstance().getPlayerId();
        sr.max = gameView.getScore();

        WebSocketClient.getInstance().sendMessage(sr);


    }


    //pausing the game when activity is paused
    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    //running the game when activity is resumed
    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }



}
