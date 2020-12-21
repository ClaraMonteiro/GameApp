package edu.cmonteiro.gameapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {
    int gamerScore;
    TextView tvFinalScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Bundle extras = getIntent().getExtras();
        gamerScore = extras.getInt("score");

        tvFinalScore = findViewById(R.id.tvFinalScore);

        tvFinalScore.setText("Your Final Score is : "+ gamerScore );


    }

    public void doPlayAgain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        //intent.addFlags(IntentCompat);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void doDisplay(View view) {
        Intent intent = new Intent(this, ResultActivity.class);
        startActivity(intent);
    }
}