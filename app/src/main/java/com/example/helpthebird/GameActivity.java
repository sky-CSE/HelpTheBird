package com.example.helpthebird;

import android.annotation.SuppressLint;
import android.content.Intent;
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
    private static final int SCORE_TO_WIN_GAME = 100;
    private MediaPlayer mediaPlayer;
    private ImageView bird, enemy1, enemy2, enemy3, coin1, coin2, life1, life2, life3, scoreLogo;
    private TextView textViewScore, startInfo;
    private ConstraintLayout constraintLayout;
    private boolean isTouching = false;
    private boolean isGameStarted = false;

    //to repeat the movements of the characters within the specified time
    // using the Runnable and Handler
    private Runnable runnable;
    private Handler handler;

    //Positions
    int birdX, enemy1X, enemy2X, enemy3X, coin1X, coin2X;
    int birdY, enemy1Y, enemy2Y, enemy3Y, coin1Y, coin2Y;

    //dimensions of screen
    int screenWidth;
    int screenHeight;

    //life
    int life = 3;

    //game score
    int score = 0;

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
        textViewScore = findViewById(R.id.game_score);
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

                screenWidth = (int) constraintLayout.getWidth();
                screenHeight = (int) constraintLayout.getHeight();

                birdX = (int) bird.getX();
                birdY = (int) bird.getY();

                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {

                        moveTheBird();
                        moveEnemies();
                        collision();

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

    //just like flappy bird
    //invert graph .... going down means increase in Y
    //going right means increase in X
    public void moveTheBird() {
        if (isTouching) {//going up (updating the coordinate)
            birdY = birdY - (screenHeight / 50);
        } else { //going down (updating the coordinate)
            birdY = birdY + (screenHeight / 50);
        }

        //touches top of screen and still pressing
        //then set to roof of screen
        if (birdY <= 0) {
            birdY = 0;
        }
        //touches bottom of screen and still pressing
        //then set to bottom of screen
        else if (birdY >= (screenHeight - bird.getHeight())) {
            birdY = (screenHeight - bird.getHeight());
        }
        //applying to bird image
        bird.setY(birdY);
    }

    public void moveEnemies() {
        enemy1.setVisibility(View.VISIBLE);
        enemy2.setVisibility(View.VISIBLE);
        enemy3.setVisibility(View.VISIBLE);
        coin1.setVisibility(View.VISIBLE);
        coin2.setVisibility(View.VISIBLE);

        //making enemy to move towards left of the screen
        enemy1X = enemy1X - (screenWidth / 150);

        //REAPPEARING OF ENEMY
        if (enemy1X < 0) { //moved out of screen from left side
            enemy1X = screenWidth + 200;

            //to make enemy appear at different height every time it reappears
            enemy1Y = (int) Math.floor(Math.random() * screenHeight);

            //LIMIT ENEMY HEIGHT WITHIN SCREEN
            if (enemy1Y <= 0) { //is at top of screen or above it
                enemy1Y = 0;      //set to top of screen
            }
            //is at bottom of screen or lower than it
            //set to bottom of screen
            else if (enemy1Y >= (screenHeight - enemy1.getHeight())) {
                enemy1Y = (screenHeight - enemy1.getHeight());
            }
        }

        enemy1.setX(enemy1X);
        enemy1.setY(enemy1Y);

        //SAME CODE FOR ENEMY2, WITH DIFFERENT SPEED
        enemy2X = enemy2X - (screenWidth / 120);

        if (enemy2X < 0) { //moved out of screen from left side
            enemy2X = screenWidth + 200;

            //to make enemy appear at different height every time it reappears
            enemy2Y = (int) Math.floor(Math.random() * screenHeight);

            //LIMIT ENEMY HEIGHT WITHIN SCREEN
            if (enemy2Y <= 0) { //is at top of screen or above it
                enemy2Y = 0;      //set to top of screen
            }
            //is at bottom of screen or lower than it
            //set to bottom of screen
            else if (enemy2Y >= (screenHeight - enemy2.getHeight())) {
                enemy2Y = (screenHeight - enemy2.getHeight());
            }
        }

        enemy2.setX(enemy2X);
        enemy2.setY(enemy2Y);

        //SAME CODE FOR ENEMY3, WITH DIFFERENT SPEED
        enemy3X = enemy3X - (screenWidth / 100);

        //REAPPEARING OF ENEMY
        if (enemy3X < 0) { //moved out of screen from left side
            enemy3X = screenWidth + 200;

            //to make enemy appear at different height every time it reappears
            enemy3Y = (int) Math.floor(Math.random() * screenHeight);

            //LIMIT ENEMY HEIGHT WITHIN SCREEN
            if (enemy3Y <= 0) { //is at top of screen or above it
                enemy3Y = 0;      //set to top of screen
            }
            //is at bottom of screen or lower than it
            //set to bottom of screen
            else if (enemy3Y >= (screenHeight - enemy3.getHeight())) {
                enemy3Y = (screenHeight - enemy3.getHeight());
            }
        }

        enemy3.setX(enemy3X);
        enemy3.setY(enemy3Y);

        //SAME CODE FOR COIN1, WITH DIFFERENT SPEED
        coin1X = coin1X - (screenWidth / 180);

        //REAPPEARING OF ENEMY
        if (coin1X < 0) { //moved out of screen from left side
            coin1X = screenWidth + 200;

            //to make enemy appear at different height every time it reappears
            coin1Y = (int) Math.floor(Math.random() * screenHeight);

            //LIMIT ENEMY HEIGHT WITHIN SCREEN
            if (coin1Y <= 0) { //is at top of screen or above it
                coin1Y = 0;      //set to top of screen
            }
            //is at bottom of screen or lower than it
            //set to bottom of screen
            else if (coin1Y >= (screenHeight - coin1.getHeight())) {
                coin1Y = (screenHeight - coin1.getHeight());
            }
        }

        coin1.setX(coin1X);
        coin1.setY(coin1Y);

        //SAME CODE FOR COIN2, WITH DIFFERENT SPEED
        coin2X = coin2X - (screenWidth / 170);

        //REAPPEARING OF ENEMY
        if (coin2X < 0) { //moved out of screen from left side
            coin2X = screenWidth + 200;

            //to make enemy appear at different height every time it reappears
            coin2Y = (int) Math.floor(Math.random() * screenHeight);

            //LIMIT ENEMY HEIGHT WITHIN SCREEN
            if (coin2Y <= 0) { //is at top of screen or above it
                coin2Y = 0;      //set to top of screen
            }
            //is at bottom of screen or lower than it
            //set to bottom of screen
            else if (coin2Y >= (screenHeight - coin2.getHeight())) {
                coin2Y = (screenHeight - coin2.getHeight());
            }
        }

        coin2.setX(coin2X);
        coin2.setY(coin2Y);

    }

    public void collision() {

        //setting enemy1 center in its middle (by default : at top left)
        int centerEnemy1X = enemy1X + (enemy1.getWidth() / 2);
        int centerEnemy1Y = enemy1Y + (enemy1.getHeight() / 2);

        if (centerEnemy1X >= birdX //enemy on right side of bird or at 'X' of bird
                && centerEnemy1X <= (birdX + bird.getWidth()) //enemy on left side of bird or at right wall of bird
                && centerEnemy1Y >= birdY //enemy below the bird or at 'Y' of bird
                && centerEnemy1Y <= (birdY + bird.getHeight())) //enemy above the bird or at bottom wall of bird
        {
            enemy1X = screenWidth + 200;
            life--;
        }

        //setting enemy2 center in its middle (by default : at top left)
        int centerEnemy2X = enemy2X + (enemy2.getWidth() / 2);
        int centerEnemy2Y = enemy2Y + (enemy2.getHeight() / 2);

        if (centerEnemy2X >= birdX //enemy on right side of bird or at 'X' of bird
                && centerEnemy2X <= (birdX + bird.getWidth()) //enemy on left side of bird or at right wall of bird
                && centerEnemy2Y >= birdY //enemy below the bird or at 'Y' of bird
                && centerEnemy2Y <= (birdY + bird.getHeight())) //enemy above the bird or at bottom wall of bird
        {
            enemy2X = screenWidth + 200;
            life--;
        }

        //setting enemy3 center in its middle (by default : at top left)
        int centerEnemy3X = enemy3X + (enemy3.getWidth() / 2);
        int centerEnemy3Y = enemy3Y + (enemy3.getHeight() / 2);

        if (centerEnemy3X >= birdX //enemy on right side of bird or at 'X' of bird
                && centerEnemy3X <= (birdX + bird.getWidth()) //enemy on left side of bird or at right wall of bird
                && centerEnemy3Y >= birdY //enemy below the bird or at 'Y' of bird
                && centerEnemy3Y <= (birdY + bird.getHeight())) //enemy above the bird or at bottom wall of bird
        {
            enemy3X = screenWidth + 200;
            life--;
        }

        //setting coin1 center in its middle (by default : at top left)
        int centerCoin1X = coin1X + (coin1.getWidth() / 2);
        int centerCoin1Y = coin1Y + (coin1.getHeight() / 2);

        if (centerCoin1X >= birdX //enemy on right side of bird or at 'X' of bird
                && centerCoin1X <= (birdX + bird.getWidth()) //enemy on left side of bird or at right wall of bird
                && centerCoin1Y >= birdY //enemy below the bird or at 'Y' of bird
                && centerCoin1Y <= (birdY + bird.getHeight())) //enemy above the bird or at bottom wall of bird
        {
            coin1X = screenWidth + 200;
            score += 10;
            textViewScore.setText(""+score);
        }

        //setting coin2 center in its middle (by default : at top left)
        int centerCoin2X = coin2X + (coin2.getWidth() / 2);
        int centerCoin2Y = coin2Y + (coin2.getHeight() / 2);

        if (centerCoin2X >= birdX //enemy on right side of bird or at 'X' of bird
                && centerCoin2X <= (birdX + bird.getWidth()) //enemy on left side of bird or at right wall of bird
                && centerCoin2Y >= birdY //enemy below the bird or at 'Y' of bird
                && centerCoin2Y <= (birdY + bird.getHeight())) //enemy above the bird or at bottom wall of bird
        {
            coin2X = screenWidth + 200;
            score += 10;
            textViewScore.setText(""+score);
        }

        //GAME IS RUNNING
        if(life > 0 && score < SCORE_TO_WIN_GAME){
            if(life == 2){
                life1.setVisibility(View.INVISIBLE);
            }
            else if(life == 1){
                life2.setVisibility(View.INVISIBLE);
            }
            /*movement of characters is provided by this
            /*every 20 milliseconds handler is running
            /*it will only run inside the if condition telling
            /*game is running till you lose all your life or win the game
            */
            handler.postDelayed(runnable, 20);
            //put delayMilli as low as possible to keep char moving
        }
        //WON THE GAME
        /*
            First : Stop the run method
         */
        else if(score >= SCORE_TO_WIN_GAME){
            handler.removeCallbacks(runnable);
        }
        //LOST THE GAME
        /*
            Stop the run method
         */
        else if(life == 0){
            handler.removeCallbacks(runnable);

            life3.setVisibility(View.INVISIBLE);
            Intent i = new Intent(GameActivity.this,ResultActivity.class);
            i.putExtra("score",score);
            startActivity(i);
            finish();
        }

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
