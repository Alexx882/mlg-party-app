package at.aau.ase.mlg_party_app.bombgame.task;

import android.content.Context;

import at.aau.ase.mlg_party_app.bombgame.task.response.IResponse;

public interface ITask {
    enum TaskState { RUNNING, SOLVED, LOST };

    int getAudioDelay();
    default int getChallengeDuration() {
        return 1;
    }

    void setState(TaskState state);
    TaskState getState();

    void processSensorInput(IResponse response);

    void execute(Context context);
}
