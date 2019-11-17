package at.aau.ase.mlg_party_app.rps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import at.aau.ase.mlg_party_app.R;

public class Rps_Game extends AppCompatActivity implements View.OnClickListener{
    ImageView enemyView, playerView;
    ImageButton buttonRock, buttonPaper, buttonScissor;
    TextView textOutput;
    Rps_Logic.option option;
    Rps_Logic.result status;
    Rps_Logic logic = new Rps_Logic();

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
        buttonPaper.setOnClickListener(this);
        buttonRock.setOnClickListener(this);
        buttonScissor.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.imageButton_Rock:
                status = logic.checkResult(Rps_Logic.option.ROCK, Rps_Logic.option.PAPER);
                setUserChoice(Rps_Logic.option.ROCK,status);
                break;
            case R.id.imageButton_Paper:
                status = logic.checkResult(Rps_Logic.option.PAPER, Rps_Logic.option.PAPER);
                setUserChoice(Rps_Logic.option.PAPER,status);
                break;
            case R.id.imageButton_Scissor:
                status = logic.checkResult(Rps_Logic.option.SCISSOR, Rps_Logic.option.PAPER);
                setUserChoice(Rps_Logic.option.SCISSOR,status);
                break;
            default:
                status = Rps_Logic.result.ERROR;
        }
    }
    private void setUserChoice(Rps_Logic.option option, Rps_Logic.result result) {
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
        }
    }
}
