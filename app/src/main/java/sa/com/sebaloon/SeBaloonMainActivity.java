package sa.com.sebaloon;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SeBaloonMainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private LocationManager locationManager;

    private SensorEventListener sensorEventListener;
    private LocationListener locationListener;

    private TextView accelerometerValue;
    private TextView gyroscopeValue;
    private TextView magneticFieldValue;
    private TextView barometerValue;
    private TextView gpsValue;

    private static final String TAG = "SeBaloon";

    private Location mLastLocation;

    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 10; // 10 meters

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_se_baloon_main);
        accelerometerValue = (TextView)findViewById(R.id.accelerometerValue);
        gyroscopeValue = (TextView)findViewById(R.id.gyroscopeValue);
        magneticFieldValue = (TextView)findViewById(R.id.magneticFieldValue);
        barometerValue = (TextView)findViewById(R.id.barometerValue);
        gpsValue = (TextView)findViewById(R.id.gpsValue);

        sensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        // Acquire a reference to the system Location Manager
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        Thread t = new Thread() {
            public void run() {
                try {
                    Thread.sleep(1000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            sensorEventListener = new SensorEventListener() {
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
                            sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
                            sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_NORMAL);
                            sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);
                            sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE), SensorManager.SENSOR_DELAY_NORMAL);
                            locationListener = new LocationListener() {
                                @Override
                                public void onLocationChanged(Location location) {
                                    String displayString = "Lat: " + location.getLatitude() + " | Long: " + location.getLongitude();
                                    gpsValue.setText(displayString);
                                }

                                @Override
                                public void onStatusChanged(String provider, int status, Bundle extras) {

                                }

                                @Override
                                public void onProviderEnabled(String provider) {

                                }

                                @Override
                                public void onProviderDisabled(String provider) {

                                }
                            };
                            try {
                                // Register the sensorEventListener with the Location Manager to receive location updates
                                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                            } catch(SecurityException se) {
                                se.printStackTrace();
                            }

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
        sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE), SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onStart() {
        super.onStart();



    }
}
