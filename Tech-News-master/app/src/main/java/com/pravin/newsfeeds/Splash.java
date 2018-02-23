package com.pravin.newsfeeds;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Splash extends AppCompatActivity {
    LinearLayout spalshlin;
    TextView splashtext;
    ImageView splashimage;
    Toolbar toolbar;
    Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();

        //Splash_Screen_Component
        splashimage = (ImageView) findViewById(R.id.splashimage);
        splashtext = (TextView) findViewById(R.id.splashtext);
        spalshlin = (LinearLayout) findViewById(R.id.spalshlin);

        //Default_Animation
        anim = AnimationUtils.loadAnimation(this, R.anim.anim);
        anim.reset();
        spalshlin.clearAnimation();
        spalshlin.startAnimation(anim);

        //Translation_Animation
        anim = AnimationUtils.loadAnimation(this, R.anim.trans);
        anim.reset();
        splashtext.clearAnimation();
        splashtext.startAnimation(anim);

        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 5500) {
                        sleep(100);
                        waited += 100;
                    }

                    //After_Pause_Switch_To_MainActivity
                    Intent intent = new Intent(Splash.this,
                            MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);

                    Splash.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    Splash.this.finish();
                }
            }
        };
        splashTread.start();
    }
}


