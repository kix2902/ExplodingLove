package es.kix2902.explodingkittens.love;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

public class LoveActivity extends Activity implements View.OnClickListener {

    private ImageView catMeow;

    private MediaPlayer player;

    private Handler handler;
    private boolean isActive = false, isBlinking = false;
    private Random mRandom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        player = MediaPlayer.create(this, R.raw.meow);
        handler = new Handler();
        mRandom = new Random();

        catMeow = (ImageView) findViewById(R.id.cat_meow);
        catMeow.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        isActive = true;
        handler.postDelayed(catBlinker, 1000);
    }

    @Override
    protected void onPause() {
        isActive = false;

        super.onPause();
    }

    @Override
    protected void onDestroy() {
        player.release();

        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cat_meow:
                if (player.isPlaying()) {
                    player.seekTo(0);
                } else {
                    player.start();
                }
                catMeow.setImageResource(R.drawable.cat_open_mouth);

                break;
        }
    }

    Runnable catBlinker = new Runnable() {
        @Override
        public void run() {
            if (isActive) {
                if (isBlinking) {
                    catMeow.setImageResource(R.drawable.cat_open_eyes);
                    handler.postDelayed(this, mRandom.nextInt(2000) + 500);

                } else {
                    catMeow.setImageResource(R.drawable.cat_closed_eyed);
                    handler.postDelayed(this, mRandom.nextInt(200) + 100);
                }

                isBlinking = !isBlinking;
            }
        }
    };

}
