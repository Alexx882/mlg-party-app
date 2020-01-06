package at.aau.ase.mlg_party_app.bombgame;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.squareup.seismic.ShakeDetector;

import java.util.Timer;
import java.util.TimerTask;

import at.aau.ase.mlg_party_app.R;
import at.aau.ase.mlg_party_app.bombgame.audio.AudioManager;
import at.aau.ase.mlg_party_app.bombgame.task.IListenToLoose;
import at.aau.ase.mlg_party_app.bombgame.task.ITask;
import at.aau.ase.mlg_party_app.bombgame.task.TaskManager;
import at.aau.ase.mlg_party_app.bombgame.task.response.ShakeResponse;
import at.aau.ase.mlg_party_app.bombgame.task.response.TapLeftResponse;
import at.aau.ase.mlg_party_app.bombgame.task.response.TapRightResponse;
import pl.droidsonroids.gif.GifImageView;

public class BombGame extends AppCompatActivity implements ShakeDetector.Listener, IListenToLoose {

    private final Timer timer = new Timer();
    private ShakeDetector sd;

    private boolean lost = false;
    private boolean listeningToSensors = false;

    @Override
    protected void onDestroy() {
        AudioManager.instance().stopAll();
        super.onDestroy();
    }

    /**
     * starts listening to all sensors in order to recognize solution events
     */
    private void startSensors() {
        listeningToSensors = true;
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sd.start(sensorManager);
    }

    /**
     * stops all sensors from listening
     */
    private void endSensors() {
        listeningToSensors = false;
        sd.stop();
    }

    private final TimerTask beepTask = new TimerTask() {
        @Override
        public void run() {
            AudioManager.instance().playSound(getApplicationContext(), R.raw.bomb_beep, 0.9f);
        }
    };

    /**
     * Called when one of the task is not executed successfully.
     * Plays the lost-sound and stops all timers
     */
    @Override
    public void loose() {
        Log.d("[BOMBGAME]", "You lost");
        lost = true;
        AudioManager.instance().playSound(getApplicationContext(), R.raw.bomb_dumb_fuck);
        AudioManager.instance().playSound(getApplicationContext(), R.raw.bomb_exploding);
        AudioManager.instance().stopSound(getApplicationContext(), R.raw.bomb_beep);
        timer.cancel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bomb_game);

        GifImageView view = findViewById(R.id.frogTop);
        Animation breathingAnimation = AnimationUtils.loadAnimation(this, R.anim.breathing);
        Animation rotationAnimation = AnimationUtils.loadAnimation(this, R.anim.rotation);
        view.startAnimation(breathingAnimation);

        breathingAnimation.setDuration(150);
        findViewById(R.id.triangle).startAnimation(breathingAnimation);
        findViewById(R.id.bomb1).startAnimation(rotationAnimation);

        findViewById(R.id.bomb2).startAnimation(rotationAnimation);

        TaskManager.getInstance().subscribeToLooseListener(this);

        // plays an annoying "beep" all 0.5s
        timer.scheduleAtFixedRate(beepTask, 1000, 500);

        sd = new ShakeDetector(this);

        findViewById(R.id.bomb1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLeftBombClick();
            }
        });
        findViewById(R.id.bomb2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRightBombClick();
            }
        });

        // get the next challenge in 0.1s
        timer.schedule(buildChallengeTask(), 100);
    }

    private void giveChallenge() {
        endSensors();

        Log.d("[BOMBGAME]", "Giving new task ... ");

        TaskManager.getInstance().getNextTask().execute(this);

        // starts the sensor-listening after the delay of the task
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        startSensors();
                    }
                },
                TaskManager.getInstance().getCurrentTask().getAudioDelay() * 1000
        );
    }

    public void onLeftBombClick() {
        if (!lost && listeningToSensors) {
            endSensors();

            ITask.TaskState stateNew = TaskManager.getInstance().reportResponse(new TapLeftResponse());

            if (stateNew == ITask.TaskState.SOLVED)
                timer.schedule(buildChallengeTask(), 3000);
            else
                startSensors();
        }
    }

    public void onRightBombClick() {
        if (!lost && listeningToSensors) {
            endSensors();

            ITask.TaskState stateNew = TaskManager.getInstance().reportResponse(new TapRightResponse());

            if (stateNew == ITask.TaskState.SOLVED)
                timer.schedule(buildChallengeTask(), 3000);
            else
                startSensors();
        }
    }

    @Override
    public void hearShake() {
        if (!lost && listeningToSensors) {
            endSensors();

            ITask.TaskState stateNew = TaskManager.getInstance().reportResponse(new ShakeResponse());

            if (stateNew == ITask.TaskState.SOLVED)
                timer.schedule(buildChallengeTask(), 3000);
            else
                startSensors();
        }
    }

    private TimerTask buildChallengeTask() {
        return new TimerTask() {
            @Override
            public void run() {
                giveChallenge();
            }
        };
    }
}
