package at.aau.ase.mlg_party_app.cocktail_shaker;

import android.content.Intent;
import android.graphics.Matrix;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import at.aau.ase.mlg_party_app.BasicGameActivity;
import at.aau.ase.mlg_party_app.Game;
import at.aau.ase.mlg_party_app.R;
import at.aau.ase.mlg_party_app.cocktail_shaker.networking.CocktailShakerResult;
import at.aau.ase.mlg_party_app.cocktail_shaker.shaking.ShakeHandler;
import at.aau.ase.mlg_party_app.cocktail_shaker.shaking.ShakeIntensity;
import at.aau.ase.mlg_party_app.cocktail_shaker.shaking.ShakeResult;
import at.aau.ase.mlg_party_app.cocktail_shaker.shaking.ShakingArgs;
import at.aau.ase.mlg_party_app.game_setup.networking.HelloGameRequest;
import at.aau.ase.mlg_party_app.networking.MessageType;
import at.aau.ase.mlg_party_app.networking.dtos.game.GameFinishedResponse;
import at.aau.ase.mlg_party_app.networking.websocket.WebSocketClient;

public class CocktailShakerActivity extends BasicGameActivity {
    /**
     * Game duration in seconds
     */
    private static final int GAME_DURATION = 10;
    private static final int HALF_IMG_SIZE = 600;

    private ImageView imageViewSonic;
    private ShakeHandler shakeHandler;

    MediaPlayer mediaPlayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail_shaker);

        imageViewSonic = findViewById(R.id.imageViewSonic);

        Intent intent = getIntent();
        String wsEndpoint = intent.getStringExtra("WS");
        WebSocketClient.getInstance().connectToServer(wsEndpoint);
        HelloGameRequest helloReq = new HelloGameRequest(Game.getInstance().getLobbyId(), Game.getInstance().getPlayerId());
        WebSocketClient.getInstance().sendMessage(helloReq);

        WebSocketClient.getInstance().registerCallback(MessageType.GameFinished, this::handleGameFinished);
        initShakeDetection();
    }

    private void initShakeDetection() {
        shakeHandler = new ShakeHandler();
        shakeHandler.registerCallback(this::handleShake);

        SensorManager sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorMgr.registerListener(shakeHandler,
                sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);

        shakeHandler.start();
        startTimer(GAME_DURATION);
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
        TextView textViewTimer = findViewById(R.id.textViewTimer);
        runOnUiThread(() -> textViewTimer.setText(String.valueOf(remainingSeconds)));
    }

    private void timeUp() {
        shakeHandler.stop();

        ShakeResult r = shakeHandler.getResults();
        sendResultToServer(r);
    }

    private void sendResultToServer(ShakeResult result) {
        CocktailShakerResult csr = new CocktailShakerResult();
        csr.playerId = Game.getInstance().getPlayerId();
        csr.avg = result.avg;
        csr.max = result.max;

        WebSocketClient.getInstance().sendMessage(csr);
    }

    private void handleShake(ShakingArgs shakeResult) {
       updateImage(shakeResult.value);
       makeSound(shakeResult.message);
    }

    private void updateImage(float shakeValue) {
        float factor = (shakeValue - 1) / 5;
        factor = Math.abs(factor) < .1 ? 0 : Math.abs(factor);

        Matrix matrix = new Matrix();
        imageViewSonic.setScaleType(ImageView.ScaleType.MATRIX);

        float rotDegrees = 90 * factor * (Math.round(Math.random()) == 0 ? 1 : -1);
        matrix.postRotate(rotDegrees, HALF_IMG_SIZE, HALF_IMG_SIZE);
        matrix.postScale(1 + factor, 1 + factor, HALF_IMG_SIZE, HALF_IMG_SIZE);

        runOnUiThread(() -> imageViewSonic.setImageMatrix(matrix));
    }

    private void makeSound(ShakeIntensity intensity) {
        switch (intensity) {
            case CRAZY:
            case FAST:
                playSound(R.raw.air_horn_triple);
                break;
            case DEACENT:
            case MEDIUM:
                playSound(R.raw.air_horn_single);
                break;
            case LOW:
            case NON_EXISTENT:
                loopSound(R.raw.sad_violin);
                break;
        }
    }

    private void playSound(int sound){
        if(mediaPlayer != null)
            mediaPlayer.stop();

        mediaPlayer = MediaPlayer.create(this, sound);
        mediaPlayer.setVolume(1.0f, 1.0f);
        mediaPlayer.start();
        mediaPlayer = null;
    }

    private void loopSound(int sound){
        if (mediaPlayer != null)
            return;

        mediaPlayer = MediaPlayer.create(this, sound);
        mediaPlayer.setVolume(1.0f, 1.0f);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }
}
