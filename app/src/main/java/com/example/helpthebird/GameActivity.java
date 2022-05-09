package com.example.helpthebird;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class GameActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private ImageView bird, enemy1, enemy2, enemy3, coin1, coin2, life1, life2, life3, scoreLogo;
    private TextView score, startInfo;
    private ConstraintLayout constraintLayout;
    private boolean isTouching = false;
    private boolean isGameStarted = false;

    //to repeat the movements of the characters within the specified time
// using the Runnable and Handler
    private Runnable runnable;
    private Handler handler;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        bird = findViewById(R.id.game_bird);
        enemy1 = findViewById(R.id.game_enemy1);
        enemy2 = findViewById(R.id.game_enemy2);
        enemy3 = findViewById(R.id.game_enemy3);
        coin1 = findViewById(R.id.game_coin1);
        coin2 = findViewById(R.id.game_coin2);
        life1 = findViewById(R.id.game_life1);
        life2 = findViewById(R.id.game_life2);
        life3 = findViewById(R.id.game_life3);
        scoreLogo = findViewById(R.id.game_score_logo);
        score = findViewById(R.id.game_score);
        startInfo = findViewById(R.id.game_startInfo);
        constraintLayout = findViewById(R.id.game_constraintLayout);


        constraintLayout.setOnTouchListener((view, motionEvent) -> {
            startInfo.setVisibility(View.INVISIBLE);

            //it means touch screen opertion is currently active
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                isTouching = true;
            }
            //it means the process of touching screen is finished.
            else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                isTouching = false;
            }

            //screen is touched for first time (game not started)
            //therefore, this touch means start the game
            if (isGameStarted == false) {
                isGameStarted = true;
                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        //movement of characters
                        //how many milliseconds run method needs to repeat
                        handler.postDelayed(runnable, 20);
                        //put delayMilli as low as possible to keep char moving
                    }
                };
                handler.post(runnable);
            }
            //game is already started
            //therefore this touch meant for motion events (controlling the bird)
            else {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    isTouching = true;
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    isTouching = false;
                }
            }
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer = MediaPlayer.create(GameActivity.this, R.raw.bird_run);
        if (MainActivity.volumeState) {
            mediaPlayer.start();
        } else {
            mediaPlayer.pause();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.reset();
        finish();

    }

    @Override
    protected void onPause() {
        mediaPlayer.pause();
        super.onPause();
    }
}
