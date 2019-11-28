package at.aau.ase.mlg_party_app.clicker;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.security.SecureRandom;

import at.aau.ase.mlg_party_app.R;
import pl.droidsonroids.gif.GifImageView;

public class ClickerGame extends AppCompatActivity {

    private ImageView ivClicker;
    private TextView tvCounter;
    private ImageView ivHitmarker;
    private GifImageView gifBackground;

    private int counter;
    private int fontsize;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicker);

        ivClicker = findViewById(R.id.iv_clicker);
        tvCounter = findViewById(R.id.tv_clicker);
        ivHitmarker = findViewById(R.id.iv_hitmarker);
        gifBackground = findViewById(R.id.gifBackground);
        counter = 0;
        fontsize = 20;


        ivClicker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    tvCounter.setText(cookie_counter());
                    textview_setup();
                    vibrate();
                    image_setup();
                    image_animation();
                    hitmarker(ivClicker.getX() + event.getX() - ivHitmarker.getWidth() / 2, ivClicker.getY() + event.getY() - ivHitmarker.getHeight() / 2);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    ivHitmarker.setVisibility(View.INVISIBLE);
                }
                return true;
            }
        });
    }


    public String cookie_counter() {
        incCounter();
        if (counter == 69) {
            return "Nice";
        } else
            return String.valueOf(getCounter());

    }

    public void vibrate() {
        Vibrator click = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        click.vibrate(500);
    }

    public void image_setup() {
        if (counter == 15) {
            ivClicker.setImageResource(R.drawable.lvl15);
        } else if (counter == 30) {
            ivClicker.setImageResource(R.drawable.lvl30);
        } else if (counter == 45) {
            ivClicker.setImageResource(R.drawable.lvl45);
        } else if (counter == 60)
            ivClicker.setImageResource(R.drawable.lvl60);
        else if (counter == 69)
            ivClicker.setImageResource(R.drawable.lvl69);
        else if (counter == 70)
            ivClicker.setImageResource(R.drawable.lvl60);
        else if (counter == 75) {
            gifBackground.setImageResource(R.drawable.asci);
            ivClicker.setImageResource(R.drawable.endboss);
        }

    }

    public void image_animation() {
        ScaleAnimation animationBig = new ScaleAnimation(1f, 0.8f, 1f, 0.8f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animationBig.setDuration(100);
        ivClicker.setAnimation(animationBig);
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
        if(fontsize>100)
            return fontsize;
        else
        return fontsize++;
    }


}
