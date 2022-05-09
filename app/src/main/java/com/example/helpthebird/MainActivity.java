package com.example.helpthebird;

import android.app.GameManager;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ImageView volume, bird, enemy1, enemy2, enemy3, coin;
    private Button buttonStart;
    private Animation animation;
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        volume = findViewById(R.id.volume);
        bird = findViewById(R.id.bird);
        enemy1 = findViewById(R.id.enemy1);
        enemy2 = findViewById(R.id.enemy2);
        enemy3 = findViewById(R.id.enemy3);
        coin = findViewById(R.id.coin);
        buttonStart = findViewById(R.id.button_start);

        //start animation as soon as main activity starts
        animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scale_animation);
        bird.setAnimation(animation);
        enemy1.setAnimation(animation);
        enemy2.setAnimation(animation);
        enemy3.setAnimation(animation);
        coin.setAnimation(animation);

        mediaPlayer = MediaPlayer.create(MainActivity.this,R.raw.waiting_music);

    }

    @Override
    protected void onResume() {
        super.onResume();

        mediaPlayer.start();

        volume.setOnClickListener(view -> {
            if(mediaPlayer.isPlaying()){
                mediaPlayer.pause();
                volume.setImageResource(R.drawable.volume_off);
            }else{
                mediaPlayer.start();
                volume.setImageResource(R.drawable.volume_on);
            }
        });

        buttonStart.setOnClickListener(view -> {
            mediaPlayer.reset(); //audio file is closed
            //setting another audio file for the game activity

            Intent i = new Intent(MainActivity.this, GameActivity.class);
            startActivity(i);
        });
    }
}