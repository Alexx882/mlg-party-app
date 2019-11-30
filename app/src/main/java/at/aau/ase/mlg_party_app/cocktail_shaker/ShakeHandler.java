package at.aau.ase.mlg_party_app.cocktail_shaker;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import at.aau.ase.mlg_party_app.Callback;

public class ShakeHandler implements SensorEventListener {

    private float max = 0;
    private float avg = 0;
    private float cnt = 0;

    private Callback<Float> floatCallback;

    public void registerFloatCallback(Callback<Float> callback) {
        this.floatCallback = callback;
    }

    private Callback<String> stringCallback;

    public void registerStringCallback(Callback<String> callback) {
        this.stringCallback = callback;
    }

    public void receiveShakeValue(float gForce) {
        if (max < gForce)
            max = gForce;

        cnt++;
        avg = (avg * (cnt - 1) + gForce) / cnt;

        if (floatCallback != null)
            floatCallback.callback(gForce);

        convertShakeValueToText(gForce);
    }

    private void convertShakeValueToText(float gForce) {
        String res = "Noob";

        if (gForce > 12)
            res = "Are you okay?";
        if (gForce > 8)
            res = "Fastest fap in the west.";
        else if (gForce > 6)
            res = "Pretty Deacent.";
        else if (gForce > 4)
            res = "Edging master";
        else if (gForce > 2)
            res = "Meh...";

        if (stringCallback != null) {
            stringCallback.callback(res);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
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
