package at.aau.ase.mlg_party_app.rps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import at.aau.ase.mlg_party_app.BasicGameActivity;
import at.aau.ase.mlg_party_app.Game;
import at.aau.ase.mlg_party_app.R;
import at.aau.ase.mlg_party_app.game_setup.networking.HelloGameRequest;
import at.aau.ase.mlg_party_app.networking.MessageType;
import at.aau.ase.mlg_party_app.networking.dtos.game.GameFinishedResponse;
import at.aau.ase.mlg_party_app.networking.websocket.WebSocketClient;
import at.aau.ase.mlg_party_app.rps.networking.RpsResult;

public class RpsGame extends BasicGameActivity implements View.OnClickListener{
    ImageView enemyView;
    ImageView playerView;
    ImageButton buttonRock;
    ImageButton buttonPaper;
    ImageButton buttonScissor;
    TextView textOutput;
    TextView textTimer;
    RpsLogic.Result status;
    RpsLogic logic = new RpsLogic();
    RpsLogic.Option playerOption;
    RpsGameResult result;
    CountDownTimer cTimer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rps);
        enemyView =  findViewById(R.id.imageView_Enemy);
        playerView = findViewById(R.id.imageView_Own);
        buttonRock = findViewById(R.id.imageButton_Rock);
        buttonPaper = findViewById(R.id.imageButton_Paper);
        buttonScissor = findViewById(R.id.imageButton_Scissor);
        textOutput =  findViewById(R.id.textView_Result);
        textTimer = findViewById(R.id.textView_TimeLeft);
        buttonPaper.setOnClickListener(this);
        buttonRock.setOnClickListener(this);
        buttonScissor.setOnClickListener(this);
        Intent intent = getIntent();
        String wsEndpoint = intent.getStringExtra("WS");
        WebSocketClient.getInstance().connectToServer(wsEndpoint);
        HelloGameRequest helloReq = new HelloGameRequest();
        WebSocketClient.getInstance().sendMessage(helloReq);

        WebSocketClient.getInstance().registerCallback(MessageType.GameFinished, this::receiveGameFinished);

        startTimer();
    }

    private void receiveGameFinished(GameFinishedResponse response){
        // Receive winner and set enemyoption based on the result
        // Status is result of game
        if (Game.getInstance().getPlayerId().equals(response.winnerId)) {
            status = RpsLogic.Result.WON;
        } else if (response.winnerId.equals("Draw")) {
            status = RpsLogic.Result.DRAW;
        } else if (response.winnerId.equals("Error")) {
            status = RpsLogic.Result.ERROR;
        } else {
            status = RpsLogic.Result.LOST;
        }
        RpsLogic.Option enemyOption = logic.checkEnemyOption(playerOption, status);
        setUserChoice(playerOption, enemyOption, status);
    }

    @Override
    public void onClick(View v) {

        // Option has to be highlighted till the countdown is over
        int id = v.getId();
        switch (id) {
            case R.id.imageButton_Rock:
                playerOption = RpsLogic.Option.ROCK;
                updateViewBasedOnOption(playerOption, playerView);
                break;
            case R.id.imageButton_Paper:
                playerOption = RpsLogic.Option.PAPER;
                updateViewBasedOnOption(playerOption, playerView);
                break;
            case R.id.imageButton_Scissor:
                playerOption = RpsLogic.Option.SCISSOR;
                updateViewBasedOnOption(playerOption, playerView);
                break;
            default:
                status = RpsLogic.Result.ERROR;
        }
    }
    private void setUserChoice(RpsLogic.Option option, RpsLogic.Option enemyOption, RpsLogic.Result result) {
        textOutput.append(String.valueOf(result));
        updateViewBasedOnOption(option, playerView);
        updateViewBasedOnOption(enemyOption, enemyView);
    }

    private void updateViewBasedOnOption(RpsLogic.Option option, ImageView playerView) {
        switch (option) {
            case ROCK:
                playerView.setImageResource(R.drawable.rock);
                break;
            case PAPER:
                playerView.setImageResource(R.drawable.paper);
                break;
            case SCISSOR:
                playerView.setImageResource(R.drawable.scissor);
                break;
            default:
                playerView.setImageResource(R.drawable.ic_launcher_foreground);
        }
    }

    void startTimer() {
        cTimer = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                textTimer.setText(String.valueOf(millisUntilFinished / 1000));
            }
            public void onFinish() {
                textTimer.setText("Time out!");
                disableImageButton();
                // Singleplayer: Enemy Option is random
                // Choose a random option, if the player did not choose one
                if (playerOption == null) {
                    playerOption = RpsLogic.Option.random();
                }
                sendDataToServer(result);
            }


        };
        cTimer.start();
    }

    private void disableImageButton() {
        buttonRock.setClickable(false);
        buttonPaper.setClickable(false);
        buttonScissor.setClickable(false);
    }


    //cancel timer
    void cancelTimer() {
        if(cTimer!=null) {
            cTimer.cancel();
        }
    }
     private void sendDataToServer(RpsGameResult result) {
         RpsResult rpsr = new RpsResult();
         rpsr.type = MessageType.RpsResult;
         rpsr.playerId = Game.getInstance().getPlayerId();
         rpsr.option = result.option;

         WebSocketClient.getInstance().sendMessage(rpsr);
     }

}
