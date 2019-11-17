package at.aau.ase.mlg_party_app.clicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;


import java.security.SecureRandom;

import at.aau.ase.mlg_party_app.R;

public class ClickerGame extends AppCompatActivity {

    private ImageView ivClicker;
    private TextView tvCounter;
    private ConstraintLayout cl;
    private ImageView ivHitmarker;

    private int counter;
    private int size;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicker);

        ivClicker = findViewById(R.id.iv_clicker);
        tvCounter = findViewById(R.id.tv_clicker);
        cl = findViewById(R.id.constraint);
        ivHitmarker = findViewById(R.id.iv_hitmarker);
        counter = 0;
        size = 20;
        bg_color();


        ivClicker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    cookie_counter();
                    textview_setup();
                    image_setup();
                    hitmarker(ivClicker.getX() + event.getX() - ivHitmarker.getWidth() / 2, ivClicker.getY() + event.getY() - ivHitmarker.getHeight() / 2);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                   // press_img();
                    ivHitmarker.setVisibility(View.INVISIBLE);

                }

                return true;
            }
        });


    }


    public void cookie_counter() {
        incCounter();
        if (counter == 69) {
            tvCounter.setText("Nice");

        } else
            tvCounter.setText(getCounter() + "");

    }

    public void image_setup() {
        if (counter >= 30 && counter < 60) {
            ivClicker.setImageResource(R.drawable.lvl30);
        } else if (counter >= 60) {
            if(counter==69)
                ivClicker.setImageResource(R.drawable.lvl69);
            else
                ivClicker.setImageResource(R.drawable.lvl60);

        }
        image_animation();

    }

    public void press_img() {
        if (counter < 30)
            ivClicker.setImageResource(android.R.drawable.star_big_off);
        else if (counter >= 30 && counter < 60) {
            ivClicker.setImageResource(R.drawable.lvl30);
        } else if (counter >= 60) {
            ivClicker.setImageResource(R.drawable.lvl60);
        }
    }

    public void image_animation() {
        ScaleAnimation animationBig = new ScaleAnimation(1f, 0.8f, 1f, 0.8f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animationBig.setDuration(100);
        ivClicker.setAnimation(animationBig);
    }

    public void bg_color() {
        ValueAnimator colorAnim = ObjectAnimator.ofInt(cl, "backgroundColor", Color.RED, Color.BLUE);
        colorAnim.setDuration(3000);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();
    }

    public void textview_setup() {
        int dg = new SecureRandom().nextInt(50) - 25;
        tvCounter.setRotation(dg);
        ScaleAnimation animation = new ScaleAnimation(1f, 1.2f, 1f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(200);
        tvCounter.setAnimation(animation);

        tvCounter.setTextSize(incsize());


    }

    public void hitmarker(float x, float y) {
        ivHitmarker.setX(x);
        ivHitmarker.setY(y);
        ivHitmarker.setVisibility(View.VISIBLE);

    }


    public int getCounter() {
        return counter;
    }

    private void incCounter() {
        counter++;
    }


    public int incsize() {
        return size++;
    }


}
