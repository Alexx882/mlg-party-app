package at.aau.ase.mlg_party_app.spacepirate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Display;
import android.widget.TextView;

import at.aau.ase.mlg_party_app.BasicGameActivity;
import at.aau.ase.mlg_party_app.Game;
import at.aau.ase.mlg_party_app.R;
import at.aau.ase.mlg_party_app.clicker.networking.clickerResults;
import at.aau.ase.mlg_party_app.game_setup.networking.HelloGameRequest;
import at.aau.ase.mlg_party_app.networking.MessageType;
import at.aau.ase.mlg_party_app.networking.dtos.game.GameFinishedResponse;
import at.aau.ase.mlg_party_app.networking.websocket.WebSocketClient;


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

        socketHandling();
        startTimer(15);

        //Initializing game view object
        //this time we are also passing the screen size to the GameView constructor
        gameView = new GameView(this, size.x, size.y);

        //adding it to contentview
        setContentView(gameView);
    }

    private void socketHandling() {
        Intent intent = getIntent();
        String wsEndpoint = intent.getStringExtra("WS");

        WebSocketClient.getInstance().connectToServer(wsEndpoint);
        HelloGameRequest helloReq = new HelloGameRequest(Game.getInstance().getLobbyId(), Game.getInstance().getPlayerId());
        WebSocketClient.getInstance().sendMessage(helloReq);

        WebSocketClient.getInstance().registerCallback(MessageType.GameFinished, this::handleEnd);


    }

    private void handleEnd(GameFinishedResponse r) {
        Log.e("mlg", "finished with " + r.winnerId);
        finish();

    }

    private void startTimer(int seconds) {
        new CountDownTimer(seconds * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                updateRemainingTime((int) millisUntilFinished / 1000);
            }

            public void onFinish() {
                sendResultToServer();
            }
        }.start();
    }

    private void updateRemainingTime(int remainingSeconds) {
        TextView textViewTimer = findViewById(R.id.tvTimer);
        runOnUiThread(() -> textViewTimer.setText(remainingSeconds + " Sekunden"));
    }

    private void sendResultToServer() {
        clickerResults cr = new clickerResults(Game.getInstance().getLobbyId(), Game.getInstance().getPlayerId(), gameView.score);
        cr.max = gameView.score;
        WebSocketClient.getInstance().sendMessage(cr);


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

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        GameView.stopMusic();
                        Intent startMain = new Intent(Intent.ACTION_MAIN);
                        startMain.addCategory(Intent.CATEGORY_HOME);
                        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(startMain);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }


}
