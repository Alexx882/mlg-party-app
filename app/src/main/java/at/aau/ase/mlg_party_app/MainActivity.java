package at.aau.ase.mlg_party_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import at.aau.ase.mlg_party_app.game_setup.JoinGameActivity;
import at.aau.ase.mlg_party_app.game_setup.NewGameActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonJoinGame).setOnClickListener(view -> joinGame());
        findViewById(R.id.buttonNewGame).setOnClickListener(view -> createGame());

    }

    private void createGame() {
        Intent intent = new Intent(this, NewGameActivity.class);
        startActivity(intent);
    }

    private void joinGame() {
        Intent intent = new Intent(this, JoinGameActivity.class);
        startActivity(intent);
    }

}
