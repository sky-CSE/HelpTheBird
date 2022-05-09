package com.example.helpthebird;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mediaPlayer = MediaPlayer.create(GameActivity.this,R.raw.bird_run);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //if music is not playing then play
        if(!mediaPlayer.isPlaying())
            mediaPlayer.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.reset();
        Intent i = new Intent(GameActivity.this,MainActivity.class);
        startActivity(i);
    }
}
