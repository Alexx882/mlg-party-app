package at.aau.ase.mlg_party_app.cocktail_shaker;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import at.aau.ase.mlg_party_app.R;

public class CocktailShakerActivity extends AppCompatActivity {
    TextView textViewShakeInfo;
    ShakeHandler shakeHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail_shaker);

        textViewShakeInfo = findViewById(R.id.textViewShakeInfo);

        initShakeDetection();
    }

    private void initShakeDetection() {
        shakeHandler = new ShakeHandler();
        shakeHandler.registerStringCallback(this::handleShake);

        SensorManager sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorMgr.registerListener(shakeHandler,
                sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);
    }

    private void handleShake(String shakeResult) {
        runOnUiThread(() -> textViewShakeInfo.setText(shakeResult));
    }
}
