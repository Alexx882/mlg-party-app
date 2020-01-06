package at.aau.ase.mlg_party_app.bombgame.task;

import android.content.Context;
import android.util.Log;

import at.aau.ase.mlg_party_app.R;
import at.aau.ase.mlg_party_app.bombgame.audio.AudioManager;
import at.aau.ase.mlg_party_app.bombgame.task.response.IResponse;
import at.aau.ase.mlg_party_app.bombgame.task.response.TapLeftResponse;

public class TapLeftBombTask implements ITask {

    private Context context;
    private TaskState state = TaskState.RUNNING;

    @Override
    public int getAudioDelay() {
        return 3;
    }

    @Override
    public void setState(TaskState state) {
        if (state == TaskState.SOLVED && context != null)
            AudioManager.instance().playSound(context, R.raw.bomb_nice_we_are_not_dead);

        this.state = state;
    }

    @Override
    public TaskState getState() {
        return state;
    }

    @Override
    public void processSensorInput(IResponse response) {
        if (response instanceof TapLeftResponse)
            setState(TaskState.SOLVED);
        else
            setState(TaskState.LOST);
    }

    @Override
    public void execute(Context context) {
        this.context = context;
        Log.d("[BOMBGAME]", "Execute TapLeftBombTask");

        AudioManager.instance().playSound(context, R.raw.bomb_leftbomb_to_win);
    }
}
