package at.aau.ase.mlg_party_app.Clicker;

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

import java.util.Random;

import at.aau.ase.mlg_party_app.R;

public class ClickerGame extends AppCompatActivity {

    private ImageView iv_clicker;
    private TextView tv_counter;
    private ConstraintLayout cl;
    private ImageView iv_hitmarker;

    private int counter;
    private int size;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicker);

        iv_clicker = findViewById(R.id.iv_clicker);
        tv_counter = findViewById(R.id.tv_clicker);
        cl = findViewById(R.id.constraint);
        iv_hitmarker = findViewById(R.id.iv_hitmarker);
        counter = 0;
        size = 20;
        bg_color();


        iv_clicker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    cookie_counter();
                    textview_setup();
                    image_setup();
                    hitmarker(iv_clicker.getX() + event.getX() - iv_hitmarker.getWidth() / 2, iv_clicker.getY() + event.getY() - iv_hitmarker.getHeight() / 2);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                   // press_img();
                    iv_hitmarker.setVisibility(View.INVISIBLE);

                }

                return true;
            }
        });


    }


    public void cookie_counter() {
        incCounter();
        if (counter == 69) {
            tv_counter.setText("Nice");

        } else
            tv_counter.setText(getCounter() + "");

    }

    public void image_setup() {
        if (counter >= 30 && counter < 60) {
            iv_clicker.setImageResource(R.drawable.lvl30);
        } else if (counter >= 60) {
            if(counter==69)
                iv_clicker.setImageResource(R.drawable.lvl69);
            else
                iv_clicker.setImageResource(R.drawable.lvl60);

        }
        image_animation();

    }

    public void press_img() {
        if (counter < 30)
            iv_clicker.setImageResource(android.R.drawable.star_big_off);
        else if (counter >= 30 && counter < 60) {
            iv_clicker.setImageResource(R.drawable.lvl30);
        } else if (counter >= 60) {
            iv_clicker.setImageResource(R.drawable.lvl60);
        }
    }

    public void image_animation() {
        ScaleAnimation animation_big = new ScaleAnimation(1f, 0.8f, 1f, 0.8f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation_big.setDuration(100);
        iv_clicker.setAnimation(animation_big);
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
        int dg = new Random().nextInt(50) - 25;
        tv_counter.setRotation(dg);
        ScaleAnimation animation = new ScaleAnimation(1f, 1.2f, 1f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(200);
        tv_counter.setAnimation(animation);

        tv_counter.setTextSize(incsize());


    }

    public void hitmarker(float x, float y) {
        iv_hitmarker.setX(x);
        iv_hitmarker.setY(y);
        iv_hitmarker.setVisibility(View.VISIBLE);

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
