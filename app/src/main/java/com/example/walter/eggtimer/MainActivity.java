package com.example.walter.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int countdownTimer = 0;
    private boolean isTimerRunning = false;
    private CountDownTimer countDownTimer;


    public void setCountdownTimer(int countdownTimer) {
        this.countdownTimer = countdownTimer;
    }

    public  void setTimerRunning(boolean isTimerRunning) {
        this.isTimerRunning = isTimerRunning;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        final TextView timerText = (TextView) findViewById(R.id.timerText);
        final Button timerControlButton = (Button) findViewById(R.id.timerControlButton);
        Button resetButton = (Button) findViewById(R.id.resetButton);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setCountdownTimer(progress);
                int minutes = progress / 60;
                int seconds = progress % 60;
                String timer = String.valueOf(minutes) + ":" + String.format("%02d", seconds);
                timerText.setText(timer);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        timerControlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isTimerRunning) {
                    timerControlButton.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                    timerControlButton.setText(R.string.buttonStop);
                    seekBar.setEnabled(false);
                    setTimerRunning(!isTimerRunning);
                    countDownTimer = new CountDownTimer(countdownTimer * 1000, 1000) {
                        public void onTick(long millisecondsUntilDone) {
                            seekBar.setProgress(seekBar.getProgress() - 1);
                            int minutes = (int) (millisecondsUntilDone / 1000) / 60;
                            int seconds = (int) (millisecondsUntilDone / 1000) % 60;
                            String timer = String.valueOf(minutes) + ":" + String.format("%02d", seconds);
                            timerText.setText(timer);
                        }

                        public void onFinish() {
                            MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.alarm);
                            mediaPlayer.start();
                            timerControlButton.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
                            timerControlButton.setText(R.string.buttonStart);
                            setTimerRunning(!isTimerRunning);
                            seekBar.setEnabled(true);
                        }
                    };
                    countDownTimer.start();
                } else {
                    timerControlButton.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
                    timerControlButton.setText(R.string.buttonStart);
                    setTimerRunning(!isTimerRunning);
                    seekBar.setEnabled(true);
                    countDownTimer.cancel();
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerControlButton.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
                timerControlButton.setText(R.string.buttonStart);
                seekBar.setProgress(0);
                seekBar.setEnabled(true);
                countDownTimer.cancel();
                setTimerRunning(false);
            }
        });
    }
}
