package com.example.helpthebird;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    private TextView textViewResultInfo, textViewMyScore, textViewHighestScore;
    private Button buttonAgain;
    private int score;

    //To store high score locally
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        textViewHighestScore = findViewById(R.id.result_highScore);
        textViewMyScore = findViewById(R.id.result_userScore);
        textViewResultInfo = findViewById(R.id.result_ResultInfo);
        buttonAgain = findViewById(R.id.result_playAgain);

        score = getIntent().getIntExtra("score", 0);
        textViewMyScore.setText("Your score : " + score);

        sharedPreferences = getSharedPreferences("Score", MODE_PRIVATE);
        int highestScore = sharedPreferences.getInt("highestScore", 0);

        //WON THE GAME
        if (score >= GameActivity.SCORE_TO_WIN_GAME) {
            textViewResultInfo.setText("You won the game");
            textViewHighestScore.setText("Highest score : " + score);
            sharedPreferences.edit().putInt("highestScore", score).apply();
        }
        //LOST THE GAME, BUT SCORED HIGHER THAN B4
        else if (score >= highestScore) {
            textViewResultInfo.setText("Sorry, you lost the game");
            textViewHighestScore.setText("Highest score : " + score);
            sharedPreferences.edit().putInt("highestScore", score).apply();
        } else //LOST THE GAME, NEITHER SCORED HIGHER THAN B4
        {
            textViewResultInfo.setText("Sorry, you lost the game");
            textViewHighestScore.setText("Highest score : " + highestScore);
        }

        buttonAgain.setOnClickListener(view -> {
            Intent i = new Intent(ResultActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        });
    }

}