package at.aau.ase.mlg_party_app.cocktail_shaker;

import android.graphics.Matrix;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

    private ImageView imageViewSonic;
    private TextView textViewTimer;
    private ShakeHandler shakeHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail_shaker);

        imageViewSonic = findViewById(R.id.imageViewSonic);
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
        return;
//
//        shakeHandler.stop();
//
//        ShakeResult r = shakeHandler.getResults();
//        sendResultToServer(r);
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
        Log.d("mlg-party", "" + shakeResult.value + " " + shakeResult.message);

        float factor = (shakeResult.value - 1) / 10;
        factor = Math.abs(factor) < .1 ? 0 : Math.abs(factor);

        float deg = 360f;

        Log.d("mlg-party", String.valueOf(factor));

        Matrix matrix = new Matrix();
        imageViewSonic.setScaleType(ImageView.ScaleType.MATRIX);
//        matrix.postRotate((float) (1.5 - .5) * factor * deg);
//        matrix.postScale((float) (.5 * factor) + .5f, (float) (.5 * factor) + .5f);


//        runOnUiThread(() -> imageViewSonic.setImageMatrix(matrix));
    }
}
