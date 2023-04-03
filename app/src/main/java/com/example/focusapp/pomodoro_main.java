package com.example.focusapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.media.MediaPlayer;

import java.util.Locale;


public class pomodoro_main extends Fragment {


    private static final long START_TIME_IN_MILLIS = 1500000;
    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunnig;
    private long mTimerLeftInMillis = START_TIME_IN_MILLIS;
    MediaPlayer player;
    View view;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pomodoro_main, container, false);
        super.onCreate(savedInstanceState);
        mTextViewCountDown = view.findViewById(R.id.text_view_countdown);
        mButtonStartPause = view.findViewById(R.id.button_start_pause);
        mButtonReset = view.findViewById(R.id.button_reset);
        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunnig) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });
        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
        updateCountDownText();
        return view;
    }


    private void startTimer() {
        if(player==null) {
            player = MediaPlayer.create(getContext(),R.raw.notification);
        }
        mCountDownTimer = new CountDownTimer(mTimerLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimerLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish(){
                mTimerRunnig = false;
                mButtonStartPause.setText("Start");
                mButtonStartPause.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);
                player.start();
            }
        }.start();
        mTimerRunnig = true;
        mButtonStartPause.setText("pause");
        mButtonReset.setVisibility((View.INVISIBLE));
    }
    private void pauseTimer(){
        mCountDownTimer.cancel();
        mTimerRunnig = false;
        mButtonStartPause.setText("Start");
        mButtonReset.setVisibility(View.VISIBLE);
    }
    private void resetTimer(){
        mTimerLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
    }
    private void updateCountDownText(){
        int minutes = (int)(mTimerLeftInMillis/1000)/60;
        int seconds = (int)(mTimerLeftInMillis/1000)%60;
        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        mTextViewCountDown.setText(timeLeftFormatted);
    }
}