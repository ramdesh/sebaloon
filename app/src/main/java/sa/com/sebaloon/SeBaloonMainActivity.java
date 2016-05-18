package sa.com.sebaloon;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SeBaloonMainActivity extends AppCompatActivity {

    private SensorManager manager;
    private SensorEventListener listener;
    private TextView accelerometerValue;
    private TextView gyroscopeValue;
    private TextView magneticFieldValue;
    private TextView barometerValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_se_baloon_main);
        accelerometerValue = (TextView)findViewById(R.id.accelerometerValue);
        gyroscopeValue = (TextView)findViewById(R.id.gyroscopeValue);
        magneticFieldValue = (TextView)findViewById(R.id.magneticFieldValue);
        barometerValue = (TextView)findViewById(R.id.barometerValue);

        manager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);

        Thread t = new Thread() {
            public void run() {
                try {
                    Thread.sleep(1000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listener = new SensorEventListener() {
                                @Override
                                public void onSensorChanged(SensorEvent event) {
                                    Sensor mSensor = event.sensor;
                                    if (mSensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                                        float acceloX = event.values[0];
                                        float acceloY = event.values[0];
                                        String displayString = "X: " + acceloX + " | Y: " + acceloY;
                                        accelerometerValue.setText(displayString);
                                    } else if (mSensor.getType() == Sensor.TYPE_GYROSCOPE) {
                                        float gyroX = event.values[0];
                                        float gyroY = event.values[1];
                                        float gyroZ = event.values[2];
                                        String displayString = "X: " + gyroX + " | Y: " + gyroY + " | Z: " + gyroZ;
                                        gyroscopeValue.setText(displayString);
                                    } else if (mSensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                                        float magneticX = event.values[0];
                                        float magneticY = event.values[1];
                                        float magneticZ = event.values[2];
                                        String displayString = "X: " + magneticX + " | Y: " + magneticY + " | Z: " + magneticZ;
                                        magneticFieldValue.setText(displayString);
                                    } else if (mSensor.getType() == Sensor.TYPE_PRESSURE) {
                                        float pressure = event.values[0];
                                        String displayString = pressure + "hPa";
                                        barometerValue.setText(displayString);
                                    }
                                }

                                @Override
                                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                                }
                            };
                            manager.registerListener(listener, manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
                            manager.registerListener(listener, manager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_NORMAL);
                            manager.registerListener(listener, manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);
                            manager.registerListener(listener, manager.getDefaultSensor(Sensor.TYPE_PRESSURE), SensorManager.SENSOR_DELAY_NORMAL);
                        }
                    });

                } catch(InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        };

        t.start();

    }
    @Override
    protected void onResume() {
        super.onResume();
    }
}
