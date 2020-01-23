package at.aau.ase.mlg_party_app.clicker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import at.aau.ase.mlg_party_app.BasicGameActivity;
import at.aau.ase.mlg_party_app.Game;
import at.aau.ase.mlg_party_app.R;
import at.aau.ase.mlg_party_app.clicker.networking.clickerResults;
import at.aau.ase.mlg_party_app.game_setup.networking.HelloGameRequest;
import at.aau.ase.mlg_party_app.networking.MessageType;
import at.aau.ase.mlg_party_app.networking.websocket.WebSocketClient;
import pl.droidsonroids.gif.GifImageView;

public class ClickerGame extends BasicGameActivity {

    private ImageView ivClicker;
    private TextView tvCounter;
    private ImageView ivHitmarker;
    private Logic logic;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicker);

        ivClicker = findViewById(R.id.iv_clicker);
        tvCounter = findViewById(R.id.tv_clicker);
        ivHitmarker = findViewById(R.id.iv_hitmarker);
        GifImageView gifBackground = findViewById(R.id.gifBackground);
        socketHandling();

        logic = new Logic();
        startTimer(15);


        ivClicker.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                tvCounter.setText(logic.cookiecounter());
                textviewsetup();
                if (logic.imagesetup() != 0)
                    ivClicker.setImageResource(logic.imagesetup());


                if (logic.gifsetup() != 0)
                    gifBackground.setImageResource(logic.gifsetup());

                logic.vibrate(this);
                imageanimation();
                hitmarker(ivClicker.getX() + event.getX() - ivHitmarker.getWidth() / 2, ivClicker.getY() + event.getY() - ivHitmarker.getHeight() / 2);
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                ivHitmarker.setVisibility(View.INVISIBLE);
            }
            return true;
        });
    }

    private void socketHandling() {
        Intent intent = getIntent();
        String wsEndpoint = intent.getStringExtra("WS");

        WebSocketClient.getInstance().connectToServer(wsEndpoint);
        HelloGameRequest helloReq = new HelloGameRequest(Game.getInstance().getLobbyId(), Game.getInstance().getPlayerId());
        WebSocketClient.getInstance().sendMessage(helloReq);

        WebSocketClient.getInstance().registerCallback(MessageType.GameFinished, this::handleGameFinished);


    }

    private void startTimer(int seconds) {
        new CountDownTimer(seconds * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                updateRemainingTime((int) millisUntilFinished / 1000);
            }

            public void onFinish() {
                ivClicker.setOnTouchListener(null);
                sendResultToServer();
            }
        }.start();
    }

    private void updateRemainingTime(int remainingSeconds) {
        TextView textViewTimer = findViewById(R.id.tvTimer);
        runOnUiThread(() -> textViewTimer.setText(remainingSeconds + " Sekunden"));
    }

    private void sendResultToServer() {
        clickerResults cr = new clickerResults();
        cr.lobbyId = Game.getInstance().getLobbyId();
        cr.playerId = Game.getInstance().getPlayerId();
        cr.max = logic.getCounter();
        WebSocketClient.getInstance().sendMessage(cr);


    }

    public void imageanimation() {
        ScaleAnimation animationBig = new ScaleAnimation(1f, 0.8f, 1f, 0.8f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animationBig.setDuration(100);
        ivClicker.setAnimation(animationBig);
    }

    public void textviewsetup() {
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
