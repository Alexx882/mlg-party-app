package at.aau.ase.mlg_party_app.bombgame.task;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import at.aau.ase.mlg_party_app.bombgame.task.response.IResponse;

/**
 * used to manage all tasks which can be given to the user
 */
public class TaskManager {
    private static TaskManager instance;
    private static Timer timer = new Timer();

    public static TaskManager getInstance() {
        if (instance == null)
            instance = new TaskManager();
        return instance;
    }

    public enum TaskType {
        SHAKE,
        TAPLEFT,
        TAPRIGHT;

        public static TaskType random() {
            Random random = new Random();
            return values()[random.nextInt(values().length)];
        }
    }

    private ITask currentTask;
    private List<IListenToLoose> listenToLooses = new LinkedList<>();

    /**
     * allows entities to listen to the state of the current task and get notified
     * if it has failed
     *
     * @param listener - object to subscribe
     */
    public void subscribeToLooseListener(IListenToLoose listener) {
        listenToLooses.add(listener);
    }

    /**
     * @param listener - entity to remove from the listener list
     */
    public void unsubscribeFromLooseListener(IListenToLoose listener) {
        listenToLooses.remove(listener);
    }

    private void scheduleLooseTimerTask(ITask task) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (task.getState() == ITask.TaskState.RUNNING) {
                    task.setState(ITask.TaskState.LOST);
                    notifyLooseListeners();
                }
            }
        }, task.getChallengeDuration() * 1000 + task.getAudioDelay() * 1000 + 500);
    }

    private void notifyLooseListeners() {
        for (IListenToLoose listener : listenToLooses)
            listener.loose();
    }

    private TaskManager() {
    }

    public ITask.TaskState reportResponse(IResponse response) {
        if (currentTask != null) {
            currentTask.processSensorInput(response);

            if (currentTask.getState() == ITask.TaskState.LOST)
                notifyLooseListeners();

            return currentTask.getState();
        }

        return null;
    }

    public ITask getNextTask() {
        TaskType typeOfNextChallenge = TaskType.random();

        ITask taskNew;

        switch (typeOfNextChallenge) {
            case TAPLEFT:
                taskNew = new TapLeftBombTask();
                break;

            case TAPRIGHT:
                taskNew = new TapRightBombTask();
                break;

            case SHAKE:
            default:
                taskNew = new ShakeTask();
        }

        currentTask = taskNew;
        scheduleLooseTimerTask(currentTask);

        return taskNew;
    }

    public ITask getCurrentTask() {
        return currentTask;
    }
}
