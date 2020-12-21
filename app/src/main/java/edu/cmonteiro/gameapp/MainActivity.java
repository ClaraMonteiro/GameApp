package edu.cmonteiro.gameapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    final Random myRandom = new Random();

    Button btnPlay, btnTest, bBlue, bRed, bYellow, bGreen, fb;
    int sequenceCount = 4, n = 0;
    //private Object mutex = new Object();
    int[] gameSequence = new int[120];
    int arrayIndex = 0;

    private SensorManager mSensorManager;
    private Sensor mSensor;

    private final int YELLOW = 1;
    private final int RED = 2;
    private final int BLUE = 3;
    private final int GREEN = 4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bRed = findViewById(R.id.btnRed);
        bBlue = findViewById(R.id.btnBlue);
        bYellow = findViewById(R.id.btnYellow);
        bGreen = findViewById(R.id.btnGreen);

        btnPlay = findViewById(R.id.btnPlay);

        DatabaseHandler db = new DatabaseHandler(this);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        db.emptyHiScores();


        Log.i("Insert: ", "Inserting ..");
        db.addHiScore(new HiScore("22 OCT 2020", "Kiwi", 12));
        db.addHiScore(new HiScore("28 OCT 2020", "Luigi", 16));
        db.addHiScore(new HiScore("1 NOV 2020", "Kiwi", 20));
        db.addHiScore(new HiScore("20 NOV 2020", "Bob", 18));
        db.addHiScore(new HiScore("22 NOV 2020", "Gemma", 22));
        db.addHiScore(new HiScore("30 NOV 2020", "Joe", 30));
        db.addHiScore(new HiScore("01 DEC 2020", "Kiwi", 22));
        db.addHiScore(new HiScore("02 DEC 2020", "Chewie", 132));



        Log.i("Reading: ", "Reading all scores..");
        List<HiScore> hiScores = db.getAllHiScores();


        for (HiScore hs : hiScores) {
            String log =
                    "Id: " + hs.getScore_id() +
                            ", Date: " + hs.getGame_date() +
                            " , Player: " + hs.getPlayer_name() +
                            " , Score: " + hs.getScore();


            Log.i("Score: ", log);
        }

        Log.i("divider", "====================");

        HiScore singleScore = db.getHiScore(5);
        Log.i("High Score 5 is by ", singleScore.getPlayer_name() + " with a score of " +
                singleScore.getScore());

        Log.i("divider", "====================");


        List<HiScore> top5HiScores = db.getTopFiveScores();

        for (HiScore hs : top5HiScores) {
            String log =
                    "Id: " + hs.getScore_id() +
                            ", Date: " + hs.getGame_date() +
                            " , Player: " + hs.getPlayer_name() +
                            " , Score: " + hs.getScore();


            Log.i("Score: ", log);
        }
        Log.i("divider", "====================");

        HiScore hiScore = top5HiScores.get(top5HiScores.size() - 1);

        Log.i("fifth Highest score: ", String.valueOf(hiScore.getScore()) );


        int myCurrentScore = 40;

        if (hiScore.getScore() < myCurrentScore) {
            db.addHiScore(new HiScore("21 DEC 2020", "Clara", 40));
        }

        Log.i("divider", "====================");


        top5HiScores = db.getTopFiveScores();

        for (HiScore hs : top5HiScores) {
            String log =
                    "Id: " + hs.getScore_id() +
                            ", Date: " + hs.getGame_date() +
                            " , Player: " + hs.getPlayer_name() +
                            " , Score: " + hs.getScore();


            Log.i("Score: ", log);
        }
    }
    CountDownTimer ct = new CountDownTimer(1000*sequenceCount,  1000) {

        public void onTick(long millisUntilFinished) {

            oneButton();

        }

        public void onFinish() {


            for (int i = 0; i< arrayIndex; i++)
                Log.d("game sequence", String.valueOf(gameSequence[i]));

            Intent i = new Intent(MainActivity.this, SequenceActivity.class);
            i.putExtra("numbers", gameSequence);
            i.putExtra("sequenceCount", sequenceCount);
            startActivity(i);

        }
    };

    private void oneButton() {
        n = getRandom(sequenceCount);

        Toast.makeText(this, "Number = " + n, Toast.LENGTH_SHORT).show();

        switch (n) {
            case 1:
                flashButton(bYellow);
                gameSequence[arrayIndex++] = YELLOW;
                break;
            case 2:
                flashButton(bRed);
                gameSequence[arrayIndex++] = RED;
                break;
            case 3:
                flashButton(bBlue);
                gameSequence[arrayIndex++] = BLUE;
                break;
            case 4:
                flashButton(bGreen);
                gameSequence[arrayIndex++] = GREEN;
                break;
            default:
                break;
        }
    }
    private void flashButton(Button button) {
        fb = button;
        Handler handler = new Handler();
        Runnable r = new Runnable() {
            public void run() {

                fb.setPressed(true);
                fb.invalidate();
                fb.performClick();
                Handler handler1 = new Handler();
                Runnable r1 = new Runnable() {
                    public void run() {
                        fb.setPressed(false);
                        fb.invalidate();
                    }
                };
                handler1.postDelayed(r1, 600);

            } // end runnable
        };
        handler.postDelayed(r, 600);
    }

    public void doPlay(View view) {


        ct.start();
    }
    private int getRandom(int maxValue) {
        return ((int) ((Math.random() * maxValue) + 1));
    }
}
