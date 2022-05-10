package com.example.helpthebird;

import android.app.GameManager;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static boolean volumeState;

    private ImageView volume, bird, enemy1, enemy2, enemy3, coin;
    private Button buttonStart;
    private Animation animation;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //FULLSCREEN
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

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

        volumeState = true; //true means prefers music to be on
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Assigning song in case you started game
        //reassigning song after coming from GameActivity
        mediaPlayer = MediaPlayer.create(MainActivity.this,R.raw.waiting_music);

        if(volumeState==true) {
            mediaPlayer.start();
        }else if(volumeState==false){
            mediaPlayer.pause();
        }

        volume.setOnClickListener(view -> {
            //turning of volume
            if(mediaPlayer.isPlaying()){
                volume.setImageResource(R.drawable.volume_off);
                volumeState = false;
                mediaPlayer.pause();
            }else{
                volume.setImageResource(R.drawable.volume_on);
                volumeState = true;
                mediaPlayer.start();  //its not working for condition off<-->off and then on
            }
        });


        buttonStart.setOnClickListener(view -> {
            mediaPlayer.reset(); //audio file is closed

            Intent i = new Intent(MainActivity.this, GameActivity.class);
            startActivity(i);
        });
    }

    @Override
    protected void onPause() {
        mediaPlayer.pause();
        super.onPause();
    }
}