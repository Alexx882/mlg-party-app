package at.aau.ase.mlg_party_app.cocktail_shaker;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import at.aau.ase.mlg_party_app.Game;
import at.aau.ase.mlg_party_app.R;
import at.aau.ase.mlg_party_app.cocktail_shaker.networking.CocktailShakerResult;
import at.aau.ase.mlg_party_app.networking.MessageType;
import at.aau.ase.mlg_party_app.networking.websocket.WebSocketClient;

public class CocktailShakerActivity extends AppCompatActivity {
    /**
     * Game duration in seconds
     */
    private int gameDuration = 10;

    private TextView textViewShakeInfo;
    private TextView textViewTimer;
    private ShakeHandler shakeHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail_shaker);

        textViewShakeInfo = findViewById(R.id.textViewShakeInfo);
        textViewTimer = findViewById(R.id.textViewTimer);

        initShakeDetection();
    }

    private void initShakeDetection() {
        shakeHandler = new ShakeHandler();
        shakeHandler.registerCallback(this::handleShake);

        SensorManager sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorMgr.registerListener(shakeHandler,
                sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);

        startTimer(gameDuration);
    }

    private void startTimer(int seconds) {
        new CountDownTimer(seconds * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                updateRemainingTime((int) millisUntilFinished / 1000);
            }

            public void onFinish() {
                timeUp();
            }
        }.start();
    }

    private void updateRemainingTime(int remainingSeconds) {
        runOnUiThread(() -> textViewTimer.setText(String.valueOf(remainingSeconds)));
    }

    private void timeUp() {
        shakeHandler.stop();

        ShakeResult r = shakeHandler.getResults();
        sendResultToServer(r);
    }

    private void sendResultToServer(ShakeResult result) {
        CocktailShakerResult csr = new CocktailShakerResult();
        csr.type = MessageType.CocktailShakerResult;
        csr.playerId = Game.getInstance().getPlayerId();
        csr.avg = result.avg;
        csr.max = result.max;

        WebSocketClient.getInstance().sendMessage(csr);
    }

    private void handleShake(ShakingArgs shakeResult) {
        runOnUiThread(() -> textViewShakeInfo.setText(shakeResult.message));
    }
}
