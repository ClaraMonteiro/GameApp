package edu.cmonteiro.gameapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;

public class SequenceActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    boolean isFlat;

    private final double NORTH_MOVE_FORWARD = 9.0;
    private final double NORTH_MOVE_BACKWARD = 6.0;
    boolean highLimit = false;
    public int[] counter = new int[120];
    public int[] arrayMainAct = new int[120];
    int sequenceCount, btnClicked = 0;
    int score = 0;
    int arrayIndex = 0;
    private final int YELLOW = 1;
    private final int RED = 2;
    private final int BLUE = 3;
    private final int GREEN = 4;

    Button btnRed, btnBlue, btnYellow, btnGreen;
    TextView tvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sequence);

        btnRed = findViewById(R.id.btnLeft);
        btnYellow = findViewById(R.id.btnTop);
        btnBlue = findViewById(R.id.btnRight);
        btnGreen = findViewById(R.id.btnBottom);


        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        tvTest = findViewById(R.id.tvTest);
        Bundle extras = getIntent().getExtras();
        sequenceCount = extras.getInt("sequenceCount");
        arrayMainAct = extras.getIntArray("numbers");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float x, y, z;
        x = Math.abs(event.values[0]);
        y = Math.abs(event.values[1]);
        z = Math.abs(event.values[2]);

        //top
        if ((x > NORTH_MOVE_FORWARD) && (z > 0) && (highLimit == false)) {
            highLimit = true;
            counter[arrayIndex++] = YELLOW;

        }
        //bottom
        if ((x < NORTH_MOVE_BACKWARD) && (z < 0) && (highLimit == true)) {
            // we have a tilt to the north
            counter[arrayIndex++] = GREEN;
            highLimit = false;
        }
        //right
        if ((y > NORTH_MOVE_FORWARD) && (y > 0) && (highLimit == false)) {
            highLimit = true;
            counter[arrayIndex++] = BLUE;
        }
        //left
        if ((y > NORTH_MOVE_FORWARD) && (y < 0) && (highLimit == true)) {
            // we have a tilt to the north
            counter[arrayIndex++] = RED;
            highLimit = false;
        }

        if(counter == arrayMainAct){
            Intent i = new Intent(SequenceActivity.this, MainActivity.class);

        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public void doYellow(View view) {
        counter[arrayIndex] = YELLOW;
        arrayIndex++;
        btnClicked++;
        Intent toPrevious = new Intent(SequenceActivity.this, MainActivity.class);
        Intent toNext = new Intent(SequenceActivity.this, GameActivity.class);

        Log.d("User sequence", String.valueOf(counter));


        if(sequenceCount == btnClicked ){
            if(Arrays.equals(counter, arrayMainAct)){
                toPrevious.putExtra("sequenceCount", sequenceCount+2);
                setResult(SequenceActivity.RESULT_OK, toPrevious);

                finish();
            }
            else{
                score = score + sequenceCount*2;
                toNext.putExtra("score", score);
                startActivity(toNext);
            }

        }

    }

    public void doGreen(View view) {
        counter[arrayIndex] = GREEN;
        arrayIndex++;
        btnClicked++;
        Intent toPrevious = new Intent(SequenceActivity.this, MainActivity.class);
        Intent toNext = new Intent(SequenceActivity.this, GameActivity.class);

        Log.d("User sequence", String.valueOf(counter));
        Log.d("game sequence", String.valueOf(arrayMainAct));



        if(sequenceCount == btnClicked ){
            if(Arrays.equals(counter, arrayMainAct)){
                toPrevious.putExtra("sequenceCount", sequenceCount+2);
                finish();
            }
            else{
                score = score + sequenceCount*2;
                toNext.putExtra("score", score);
                startActivity(toNext);
            }

        }
    }

    public void doBlue(View view) {
        counter[arrayIndex] = BLUE;
        arrayIndex++;
        btnClicked++;
        Intent toPrevious = new Intent(SequenceActivity.this, MainActivity.class);
        Intent toNext = new Intent(SequenceActivity.this, GameActivity.class);

        Log.d("User sequence", String.valueOf(counter));
        Log.d("game sequence", String.valueOf(arrayMainAct));


        if(sequenceCount == btnClicked ){
            if(Arrays.equals(counter, arrayMainAct)){
                toPrevious.putExtra("sequenceCount", sequenceCount+2);
                finish();
            }
            else{score = score + sequenceCount*2;
                toNext.putExtra("score", score);
                startActivity(toNext);
            }

        }
    }

    public void doRed(View view) {
        counter[arrayIndex] = RED;
        arrayIndex++;
        btnClicked++;
        Intent toPrevious = new Intent(SequenceActivity.this, MainActivity.class);
        Intent toNext = new Intent(SequenceActivity.this, GameActivity.class);

        Log.d("User sequence", String.valueOf(counter));
        Log.d("game sequence", String.valueOf(arrayMainAct));

        if(sequenceCount == btnClicked ){
            if(Arrays.equals(counter, arrayMainAct)){
                toPrevious.putExtra("sequenceCount", sequenceCount+2);
                finish();
            }
            else{
                score = score + sequenceCount*2;
                toNext.putExtra("score", score);
                startActivity(toNext);
            }

        }
    }
}