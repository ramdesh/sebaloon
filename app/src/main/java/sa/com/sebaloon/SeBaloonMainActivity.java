package sa.com.sebaloon;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SeBaloonMainActivity extends AppCompatActivity {

    private SensorManager manager;
    private SensorEventListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_se_baloon_main);

        manager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);

        listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                Sensor mSensor = event.sensor;
                if(mSensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    // Accelerometer stuff
                } else if (mSensor.getType() == Sensor.TYPE_GYROSCOPE) {
                    // Gyroscope stuff
                } else if (mSensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                    // Magnetometer stuff
                } else if (mSensor.getType() == Sensor.TYPE_PRESSURE) {
                    // Barometer stuff
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }
}
