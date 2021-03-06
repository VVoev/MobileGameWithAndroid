package me.icytower.UltimateCop.Core.Gyroscope;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import me.icytower.UltimateCop.GlobalConstants.Constants;

public class OrientationData implements SensorEventListener {

    private SensorManager manager;
    private Sensor accelerometer;
    private Sensor magnoMeter;

    private float[] accelOutput;
    private float[] magOutput;

    private float [] orientation = new float[3];
    private float[] startOrientation = null;

    public OrientationData(){
        this.manager = (SensorManager) Constants.CONTEXT.getSystemService(Context.SENSOR_SERVICE);
        this.accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.magnoMeter = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    public void register(){
        manager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_GAME);
        manager.registerListener(this,magnoMeter,SensorManager.SENSOR_DELAY_GAME);
    }

    public void pause(){
        manager.unregisterListener(this);
    }

    public float [] getOrientation(){
        return this.orientation;
    }

    public float []getStartOrientation(){
        return  this.startOrientation;
    }


    public void newGame(){
        this.startOrientation = null;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            accelOutput = event.values;
        }else if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            magOutput = event.values;
        }

        if(accelOutput != null && magOutput != null){
            float [] R = new float[9];
            float []I = new float[9];

            boolean success = SensorManager.getRotationMatrix(R,I,accelOutput,magOutput);
            if(success){
                SensorManager.getOrientation(R,orientation);
                if(startOrientation == null){
                    startOrientation = new float[orientation.length];
                    //taken from SO
                    System.arraycopy(orientation,0,startOrientation,0,orientation.length);
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
