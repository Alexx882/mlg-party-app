package at.aau.ase.mlg_party_app.cocktail_shaker;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import at.aau.ase.mlg_party_app.Callback;

public class ShakeHandler implements SensorEventListener {

    private float max = 0;
    private float avg = 0;
    private float cnt = 0;
    private boolean running = true;

    private Callback<ShakingArgs> callback;

    public void registerCallback(Callback<ShakingArgs> callback) {
        this.callback = callback;
    }

    public void receiveShakeValue(float gForce) {
        if (max < gForce)
            max = gForce;

        cnt++;
        avg = (avg * (cnt - 1) + gForce) / cnt;

        if (callback != null) {
            ShakingArgs a = new ShakingArgs();
            a.value = gForce;
            a.message = ShakeIntensity.convertFromFloat(gForce);

            callback.callback(a);
        }
    }

    public void stop() {
        running = false;
    }

    public ShakeResult getResults() {
        return new ShakeResult(max, avg);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (!running)
            return;

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        float gX = x / SensorManager.GRAVITY_EARTH;
        float gY = y / SensorManager.GRAVITY_EARTH;
        float gZ = z / SensorManager.GRAVITY_EARTH;

        // gForce will be close to 1 when there is no movement.
        float gForce = (float) Math.sqrt(gX * gX + gY * gY + gZ * gZ);

        receiveShakeValue(gForce);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // nothing to do here
    }
}
