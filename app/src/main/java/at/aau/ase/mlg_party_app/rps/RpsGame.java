package at.aau.ase.mlg_party_app.rps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import at.aau.ase.mlg_party_app.R;

public class RpsGame extends AppCompatActivity implements View.OnClickListener{
    ImageView enemyView;
    ImageView playerView;
    ImageButton buttonRock;
    ImageButton buttonPaper;
    ImageButton buttonScissor;
    TextView textOutput;
    TextView textTimer;
    RpsLogic.RESULT status;
    RpsLogic logic = new RpsLogic();
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
        int id = v.getId();
        switch (id) {
            case R.id.imageButton_Rock:
                status = logic.checkResult(RpsLogic.OPTION.ROCK, RpsLogic.OPTION.PAPER);
                setUserChoice(RpsLogic.OPTION.ROCK,status);
                break;
            case R.id.imageButton_Paper:
                status = logic.checkResult(RpsLogic.OPTION.PAPER, RpsLogic.OPTION.PAPER);
                setUserChoice(RpsLogic.OPTION.PAPER,status);
                break;
            case R.id.imageButton_Scissor:
                status = logic.checkResult(RpsLogic.OPTION.SCISSOR, RpsLogic.OPTION.PAPER);
                setUserChoice(RpsLogic.OPTION.SCISSOR,status);
                break;
            default:
                status = RpsLogic.RESULT.ERROR;
        }
    }
    private void setUserChoice(RpsLogic.OPTION option, RpsLogic.RESULT result) {
        textOutput.setText(""+result);
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
                textTimer.setText(""+millisUntilFinished / 1000);
            }
            public void onFinish() {
                textTimer.setText("Time out!");
            }
        };
        cTimer.start();
    }


    //cancel timer
    void cancelTimer() {
        if(cTimer!=null)
            cTimer.cancel();
    }

}
