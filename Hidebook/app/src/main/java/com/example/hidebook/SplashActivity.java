package com.example.hidebook;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SplashActivity extends AppCompatActivity {

    LinearLayout ivLogo;

    TextView tvName;

    CharSequence charSequence;
    int index;
    long delay = 200;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Linh
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        //
        ivLogo = findViewById(R.id.iv_logo);
        tvName = findViewById(R.id.tv_name);


        // hide the title bar
        getSupportActionBar().hide();
        //Set full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Initialize object animator
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(ivLogo,
                PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                PropertyValuesHolder.ofFloat("scaleY", 1.2f));
        //Set duration
        objectAnimator.setDuration(500);
        //Set repeat count
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        //Set repeat mode
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        //Start animator
        objectAnimator.start();

        //Set animate text
        animaText("Hidebook");

        //Bao
        //Initialize handler
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                //Redirect to mainActivity
//                startActivity(new Intent(SplashActivity.this, FragmentReplacerActivity.class));
//                //Finish activity
//                finish();;
//            }
//        }, 2500);

        //Linh
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(user == null){
                    startActivity(new Intent(SplashActivity.this, ReplacerActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
                finish();
            }
        }, 2500);


    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //When runnable run
            //Set text
            tvName.setText(charSequence.subSequence(0,index++));
            //Kiểm tra điều kiện
            if (index <= charSequence.length()){
                //When index is equal to text lenght
                //Run handler
                handler.postDelayed(runnable, delay);
            }
        }
    };
    //Create animated text method
    public void animaText(CharSequence cs){
        //Set text
        charSequence = cs;
        //Clear index
        index  = 0;
        //Clear text
        tvName.setText("");
        //Remove call back
        handler.removeCallbacks(runnable);
        //Run handler
        handler.postDelayed(runnable, delay);


    }


}