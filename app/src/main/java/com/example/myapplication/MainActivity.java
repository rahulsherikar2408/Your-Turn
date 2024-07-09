package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.example.myapplication.R.id;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ImageView Dice1,Dice2;
    private Button button;
    private RadioGroup rg1;
    private RadioGroup rg2;
    private ImageButton im1,im2;
    public Random r1=new Random();
    public Random r2=new Random();
    public int[] dice ={R.drawable.dice_1, R.drawable.dice_2, R.drawable.dice_3, R.drawable.dice_4, R.drawable.dice_5, R.drawable.dice_6};
    MediaPlayer mp1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Dice1=findViewById(id.imageView);
        Dice2=findViewById(id.imageView3);
        button=findViewById(id.button);
        mp1=MediaPlayer.create(this,R.raw.diceroll);
        rg1=findViewById(id.rgrp);
        rg2=findViewById(id.rgrp2);
        rg1.setOnCheckedChangeListener((group, checkedId) ->{
            if(checkedId==id.rsingle){
                Dice2.setVisibility(View.GONE);
            }
            else{
                Dice2.setVisibility(View.VISIBLE);
            }
        } );
        rg1.check(R.id.rdouble);
        rg2.check(R.id.on);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animate();
            }
        });
    }

    public void animate() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                RotateAnimation anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                anim.setInterpolator(new LinearInterpolator());
                Random ran = new Random();
                int duration = ran.nextInt(500) + 50;
                anim.setDuration(duration);
                anim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        roll();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                Dice1.startAnimation(anim);
                Dice2.startAnimation(anim);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();

        if (mp1 != null) {
            mp1.start();
        }
    }

    private void roll() {
        int i = r1.nextInt(6);
        int j = r2.nextInt(6);
        Dice1.setImageResource(dice[i]);
        Dice2.setImageResource(dice[j]);
        rg2.setOnCheckedChangeListener((group, checkedId) ->{
            if(checkedId== id.on) {
                mp1=MediaPlayer.create(this,R.raw.diceroll);
            }
            else if(checkedId==id.off) {
                mp1 = null;
            }
        } );
    }
}