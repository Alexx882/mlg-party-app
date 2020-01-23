package at.aau.ase.mlg_party_app;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import at.aau.ase.mlg_party_app.networking.dtos.game.GameFinishedResponse;

/**
 * This class provides functionality for all games.
 */
public abstract class BasicGameActivity extends AppCompatActivity {

    /**
     * Handles the basic cleanup of the game including storing results and closing the activity.
     *
     * @param r
     */
    public void handleGameFinished(GameFinishedResponse r) {
        Game.getInstance().setLastWinnerId(r.winnerId);
        Game.getInstance().setPlayerRanking(r.ranking);
        runOnUiThread(this::finish);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Game.setInstance((Game) getIntent().getSerializableExtra("game"));
    }
}
