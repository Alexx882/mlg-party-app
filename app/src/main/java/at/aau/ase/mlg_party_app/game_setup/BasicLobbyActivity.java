package at.aau.ase.mlg_party_app.game_setup;

import android.media.MediaPlayer;

import androidx.appcompat.app.AppCompatActivity;

import at.aau.ase.mlg_party_app.R;

/**
 * Base class for all game preparation activities.
 */
public abstract class BasicLobbyActivity extends AppCompatActivity {

    protected MediaPlayer mediaPlayer;

    private int getLobbySound() {
        int sound;

        double val = Math.random();
        if (val > 0.95)
            sound = R.raw.lobby_basic_crit;
        else if (val > 0.6)
            sound = R.raw.lobby_basic_0;
        else if (val > 0.3)
            sound = R.raw.lobby_basic_1;
        else
            sound = R.raw.lobby_basic_2;

        return sound;
    }

    protected void playBackgroundSound() {
        int sound = getLobbySound();

        mediaPlayer = MediaPlayer.create(this, sound);
        mediaPlayer.setVolume(1.0f, 1.0f);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    protected void stopSound() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    protected void playLoadingSound() {
        // stop background music
        stopSound();

        mediaPlayer = MediaPlayer.create(this, R.raw.lobby_loading);
        mediaPlayer.setVolume(1.0f, 1.0f);
        mediaPlayer.start();
    }

    @Override
    protected void onPause() {
        stopSound();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        stopSound();
        super.onDestroy();
    }
}
