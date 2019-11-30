package at.aau.ase.mlg_party_app.clicker;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import at.aau.ase.mlg_party_app.R;
import pl.droidsonroids.gif.GifImageView;

public class ClickerGame extends AppCompatActivity {

    private ImageView ivClicker;
    private TextView tvCounter;
    private ImageView ivHitmarker;
    private GifImageView gifBackground;
    private Logic logic;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicker);

        ivClicker = findViewById(R.id.iv_clicker);
        tvCounter = findViewById(R.id.tv_clicker);
        ivHitmarker = findViewById(R.id.iv_hitmarker);
        gifBackground = findViewById(R.id.gifBackground);
        logic = new Logic();


        ivClicker.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                tvCounter.setText(logic.cookie_counter());
                textview_setup();
               int imageNumber = logic.image_setup();
               if(imageNumber!=0)
                   ivClicker.setImageResource(imageNumber);

                int gifNumber = logic.gif_setup();
                if(gifNumber!=0)
                    gifBackground.setImageResource(gifNumber);

                logic.vibrate(this);
                image_animation();
                hitmarker(ivClicker.getX() + event.getX() - ivHitmarker.getWidth() / 2, ivClicker.getY() + event.getY() - ivHitmarker.getHeight() / 2);
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                ivHitmarker.setVisibility(View.INVISIBLE);
            }
            return true;
        });
    }


    public void image_animation() {
        ScaleAnimation animationBig = new ScaleAnimation(1f, 0.8f, 1f, 0.8f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animationBig.setDuration(100);
        ivClicker.setAnimation(animationBig);
    }

    public void textview_setup() {
        tvCounter.setRotation(logic.rand(50, 25));
        ScaleAnimation animation = new ScaleAnimation(1f, 1.2f, 1f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(200);
        tvCounter.setAnimation(animation);

        tvCounter.setTextSize(logic.incsize());


    }

    public void hitmarker(float x, float y) {
        ivHitmarker.setX(x);
        ivHitmarker.setY(y);
        ivHitmarker.setVisibility(View.VISIBLE);

    }





}
