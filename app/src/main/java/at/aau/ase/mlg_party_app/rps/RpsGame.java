package at.aau.ase.mlg_party_app.rps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import at.aau.ase.mlg_party_app.R;
import pl.droidsonroids.gif.GifImageView;

public class RpsGame extends AppCompatActivity implements View.OnClickListener{
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
        startTimer();
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
                // Send player option to microservice, so the result can be calculated

                // Multiplayer: Enemy option has to be used to check the result

                // Singleplayer: Enemy Option is random
                disableImageButton();
                RpsLogic.Option enemyOption = RpsLogic.Option.random();
                if (playerOption == null) {
                    playerOption = RpsLogic.Option.random();
                }
                status = logic.checkResult(playerOption, enemyOption);
                setUserChoice(playerOption, enemyOption, status);
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

}
