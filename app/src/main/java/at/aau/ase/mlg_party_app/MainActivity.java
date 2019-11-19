package at.aau.ase.mlg_party_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import at.aau.ase.mlg_party_app.game_setup.NewGameActivity;
import at.aau.ase.mlg_party_app.tictactoe.TicTacToeActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonJoinGame).setOnClickListener((view)->joinGame());
        findViewById(R.id.buttonNewGame).setOnClickListener((view)->createGame());

        //DevHelper for TicTacToe
        findViewById(R.id.buttonTicTacToeTest).setOnClickListener((view)->createTicTacToe());

    }

    private void createGame(){
        Intent intent = new Intent(this, NewGameActivity.class);
        startActivity(intent);
    }

    private void joinGame(){

    }

    //DevHelper for TicTacToe
    private void createTicTacToe(){
        Intent intent = new Intent(this, TicTacToeActivity.class);
        startActivity(intent);
    }

}
