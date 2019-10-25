package com.example.saumyaawasthi.audio;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
MediaPlayer mediaPlayer;
int value;
AudioManager audioManager;
public void play(View view) {
mediaPlayer.start();
}
public void pause(View view){
mediaPlayer.pause();
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer = MediaPlayer.create(this,R.raw.one_direction_steal_my_girl);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int maxVolume=audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        final SeekBar volume=(SeekBar)findViewById(R.id.volumeSeekBar);
        final SeekBar song=(SeekBar)findViewById(R.id.songSeekBar);
        volume.setMax(maxVolume);
        volume.setProgress(currentVolume);
        song.setMax(mediaPlayer.getDuration());
        volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.i("volume info", Integer.toString(i));
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,i,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        song.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.i("Song info",Integer.toString(i));
                //mediaPlayer.seekTo(i);
                value=i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mediaPlayer.pause();
                Log.i("play info","playing");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.start();
                mediaPlayer.seekTo(value);
            }
        });
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                song.setProgress(mediaPlayer.getCurrentPosition());
            }
        },0,1000);
    }
}
