package com.example.helpthebird;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
            textViewResultInfo.setText(R.string.won_game_message);
            textViewHighestScore.setText("Highest score : " + score);
            sharedPreferences.edit().putInt("highestScore", score).apply();
        }
        //LOST THE GAME, BUT SCORED HIGHER THAN B4
        else if (score >= highestScore) {
            textViewResultInfo.setText(R.string.lost_game_message);
            textViewHighestScore.setText("Highest score : " + score);
            sharedPreferences.edit().putInt("highestScore", score).apply();
        } else //LOST THE GAME, NEITHER SCORED HIGHER THAN B4
        {
            textViewResultInfo.setText(R.string.lost_game_message);
            textViewHighestScore.setText("Highest score : " + highestScore);
        }

        buttonAgain.setOnClickListener(view -> {
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ResultActivity.this);
        builder.setTitle("Help the Bird");
        builder.setMessage("Are you sure you want to quit game?");
        builder.setCancelable(false);

        builder.setNegativeButton(R.string.quit_game_option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
        });

        builder.setPositiveButton(R.string.cancel_game_option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();
            }
        });

        builder.create().show();
    }
}