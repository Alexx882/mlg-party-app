package at.aau.ase.mlg_party_app.bombgame.audio;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AudioManager {
    public static AudioManager instance() {
        if (instance == null)
            instance = new AudioManager();

        return instance;
    }

    private static AudioManager instance;

    private Map<Context, Map<Integer, MediaPlayer>> players = new HashMap<>();

    private MediaPlayer getPlayerForContextAndResource(Context context, int resId) {
        if (!players.containsKey(context))
            players.put(context, new HashMap<>());

        Map<Integer, MediaPlayer> soundPlayers = players.get(context);

        if (soundPlayers != null && !soundPlayers.containsKey(resId))
            soundPlayers.put(resId, MediaPlayer.create(context, resId));

        if (soundPlayers != null)
            return soundPlayers.get(resId);

        return null;
    }

    public void playSound(Context context, int resId, boolean isLoop, float volume) {
        MediaPlayer player = getPlayerForContextAndResource(context, resId);

        Log.d("[AudioManager]", String.format("Playing sound %d", resId));

        if (player != null) {
            player.setVolume(volume, volume);
            player.setLooping(isLoop);

            if (player.isPlaying()) {
                player.stop();
                try {
                    player.prepare();
                } catch (IOException e) {
                    Log.e("[AudioManager]", "Unable to prepare Audiofile.");
                }
            }
            player.start();
        }
    }

    public void playSound(Context context, int resId, float volume) {
        playSound(context, resId, false, volume);
    }

    public void playSound(Context context, int resId) {
        playSound(context, resId, false, 1.0f);
    }

    public void stopSound(Context context, int resId) {
        MediaPlayer player = getPlayerForContextAndResource(context, resId);
        if (player != null && player.isPlaying())
            player.stop();
    }

    public void stopAll() {
        for (Context context : players.keySet()) {
            Map<Integer, MediaPlayer> playersMap = players.get(context);

            if (playersMap != null)
                for (Integer resId : playersMap.keySet()) {
                    MediaPlayer player = playersMap.get(resId);

                    if (player != null && player.isPlaying())
                        player.stop();
                }
        }
    }
}
