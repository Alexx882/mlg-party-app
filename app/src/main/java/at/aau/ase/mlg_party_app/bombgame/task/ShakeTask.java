package at.aau.ase.mlg_party_app.bombgame.task;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import at.aau.ase.mlg_party_app.R;
import at.aau.ase.mlg_party_app.bombgame.task.response.IResponse;
import at.aau.ase.mlg_party_app.bombgame.task.response.ShakeResponse;

public class ShakeTask implements ITask {

    private Context context;
    private TaskState state = TaskState.RUNNING;

    @Override
    public int getAudioDelay() {
        return 4;
    }

    @Override
    public void setState(TaskState state) {
        if (state == TaskState.SOLVED && context != null) {
            MediaPlayer player = MediaPlayer.create(context, R.raw.bomb_nice_we_are_not_dead);
            player.start();
        }

        this.state = state;
    }

    @Override
    public TaskState getState() {
        return state;
    }

    @Override
    public void processSensorInput(IResponse response) {
        if (response instanceof ShakeResponse)
            setState(TaskState.SOLVED);
        else
            setState(TaskState.LOST);
    }

    @Override
    public void execute(Context context) {
        this.context = context;
        Log.d("[BOMBGAME]", "Execute ShakeTask");
        MediaPlayer player = MediaPlayer.create(context, R.raw.bomb_shake_to_win);
        player.start();
    }
}
