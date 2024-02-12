package com.app.ecoquest.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.ecoquest.R;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class PlayActivity extends AppCompatActivity {
    // Frame
    private int frameHeight = 0;
    private int frameWidth = 0;

    // Size
    private int recycleBinSize = 0;

    // Position
    private float recycleBinX = 0f;
    private float recycleBinY = 0f;
    //    recyclable and non recyclable
    private float recyclableX = 0f;
    private float recyclableY = 0f;
    private float recyclableX2 = 0f;
    private float recyclableY2 = 0f;
    private float nonRecyclableX = 0f;
    private float nonRecyclableY = 0f;
    private float nonRecyclableX2 = 0f;
    private float nonRecyclableY2 = 0f;

    // life
    private int totalLifes = 3;
    private int life = 3;
    private int items = 0;

    // Class
    private Timer timer = new Timer();
    private final Handler handler = new Handler();

    // Status
    private boolean startFlg = false;
    private boolean actionFlg = false;
    private boolean actionLeft = false;
    private boolean actionRight = false;

    private ProgressBar progressBar;
    private FrameLayout frame;
    private TextView tvItems, startLabel;
    private ImageView recycleBin;
    private ImageView recyclable;
    private ImageView recyclable2;
    private ImageView nonRecyclable;
    private ImageView nonRecyclable2;
    String TAG = "theS";

    int[] recyclables = {R.drawable.carton_of_orange_juice_128px, R.drawable.milk_carton_96, R.drawable.water_bottle_128px, R.drawable.box_128px, R.drawable.can_128px, R.drawable.green_bottle_128px, R.drawable.newspaper_128px, R.drawable.soda_can_128px,};
    int[] nonRecyclables = {R.drawable.broken_cup_128px, R.drawable.coffee_cup_128px, R.drawable.plastic_bag_128px, R.drawable.battery_128px, R.drawable.apple_128px, R.drawable.banana_peel_128px, R.drawable.bone_128px, R.drawable.egg_shell_128px, R.drawable.fish_bone_128px,};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        progressBar = findViewById(R.id.progressBar);
        frame = findViewById(R.id.frame);
        tvItems = findViewById(R.id.tvItems);
        startLabel = findViewById(R.id.startLabel);
        recycleBin = findViewById(R.id.recycleBin);
        recyclable = findViewById(R.id.recyclable);
        recyclable2 = findViewById(R.id.recyclable2);
        nonRecyclable = findViewById(R.id.nonRecyclable);
        nonRecyclable2 = findViewById(R.id.nonRecyclable2);

        tvItems.setText(String.valueOf(items));
        progressBar.setProgress(getProgress());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (startFlg) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                actionFlg = true;
                if (event.getX() < (float) frameWidth / 2) actionLeft = true;
                else if (event.getX() >= (float) frameWidth / 2) actionRight = true;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                actionFlg = false;
                actionLeft = false;
                actionRight = false;
            }
        } else {
            if (event.getAction() == MotionEvent.ACTION_DOWN) startGame();
        }
        return true;
    }

    public void startGame() {
        Log.d(TAG, "startGame: frameHeight=" + frameHeight + " (timer != null)=" + (timer != null));
        startLabel.setVisibility(View.GONE);
        startFlg = true;
        if (frameHeight == 0) {
            frameHeight = frame.getHeight();
            frameWidth = frame.getWidth();
            recycleBinSize = recycleBin.getHeight();
            recycleBinX = recycleBin.getX();
            recycleBinY = recycleBin.getY();
        }
        Log.d(TAG, "startGame: frameHeight=" + frameHeight + " frameWidth=" + frameWidth + " recycleBinSize=" + recycleBinSize);
        recyclable.setY(3000.0f);
        recyclable2.setY(3000.0f);
        nonRecyclable.setY(3000.0f);
        nonRecyclable2.setY(3000.0f);
        recyclableY = recyclable.getY();
        recyclableY2 = recyclable2.getY();
        nonRecyclableY = nonRecyclable.getY();
        nonRecyclableY2 = nonRecyclable2.getY();

        if (timer != null) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            }, 0, 20);
        }
    }

    public void gameOver() {
        Log.d(TAG, "gameOver: ");
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        startFlg = false;

        Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
        intent.putExtra("items", items);
        startActivity(intent);
        finish();
    }

    void changePos() {
        // recyclable
        recyclableY += (10f + ((float) items / 100 * 0.5f));
        float recyclableCenterX = recyclableX + (float) recyclable.getWidth() / 2;
        float recyclableCenterY = recyclableY + (float) recyclable.getHeight() / 2;
        if (recycleBinX <= recyclableCenterX && recyclableCenterX <= recycleBinX + recycleBinSize && recycleBinY <= recyclableCenterY && recyclableCenterY <= frameHeight) {
            recyclableY = frameHeight + 100;
            items++;
            Log.d(TAG, "changePos: recyclable: items=" + items + " life=" + life);
            recyclable.setImageResource(recyclables[new Random().nextInt(recyclables.length)]);
        }
        if (recyclableY > frameHeight) {
            recyclableY = -100f;
            recyclableX = (float) Math.floor(Math.random() * (frameWidth - recyclable.getWidth()));
        }
        recyclable.setX(recyclableX);
        recyclable.setY(recyclableY);

        // recyclable2
        recyclableY2 += (15f + ((float) items / 100 * 0.5f));
        float recyclableCenterX2 = recyclableX2 + (float) recyclable2.getWidth() / 2;
        float recyclableCenterY2 = recyclableY2 + (float) recyclable2.getHeight() / 2;
        if (recycleBinX <= recyclableCenterX2 && recyclableCenterX2 <= recycleBinX + recycleBinSize && recycleBinY <= recyclableCenterY2 && recyclableCenterY2 <= frameHeight) {
            recyclableY2 = frameHeight + 100;
            items++;
            Log.d(TAG, "changePos: recyclable2: items=" + items + " life=" + life);
            recyclable2.setImageResource(recyclables[new Random().nextInt(recyclables.length)]);
        }
        if (recyclableY2 > frameHeight) {
            recyclableY2 = -100f;
            recyclableX2 = (float) Math.floor(Math.random() * (frameWidth - recyclable2.getWidth()));
        }
        recyclable2.setX(recyclableX2);
        recyclable2.setY(recyclableY2);

        // nonRecyclable
        nonRecyclableY += (20f + ((float) items / 100 * 0.5f));
        float nonRecyclableCenterX = nonRecyclableX + (float) nonRecyclable.getWidth() / 2;
        float nonRecyclableCenterY = nonRecyclableY + (float) nonRecyclable.getWidth() / 2;
        if (recycleBinX <= nonRecyclableCenterX && nonRecyclableCenterX <= recycleBinX + recycleBinSize && recycleBinY <= nonRecyclableCenterY && nonRecyclableCenterY <= frameHeight) {
            nonRecyclableY = frameHeight + 30;
            life--;
            Log.d(TAG, "changePos: nonRecyclable: items=" + items + " life=" + life);
            nonRecyclable.setImageResource(nonRecyclables[new Random().nextInt(nonRecyclables.length)]);
        }
        if (nonRecyclableY > frameHeight) {
            nonRecyclableY = -100f;
            nonRecyclableX = (float) Math.floor(Math.random() * (frameWidth - nonRecyclable.getWidth()));
        }
        nonRecyclable.setX(nonRecyclableX);
        nonRecyclable.setY(nonRecyclableY);

        // nonRecyclable2
        nonRecyclableY2 += (25f + ((float) items / 100 * 0.5f));
        float nonRecyclableCenterX2 = nonRecyclableX2 + (float) nonRecyclable2.getWidth() / 2;
        float nonRecyclableCenterY2 = nonRecyclableY2 + (float) nonRecyclable2.getWidth() / 2;
        if (recycleBinX <= nonRecyclableCenterX2 && nonRecyclableCenterX2 <= recycleBinX + recycleBinSize && recycleBinY <= nonRecyclableCenterY2 && nonRecyclableCenterY2 <= frameHeight) {
            nonRecyclableY2 = frameHeight + 30;
            life--;
            Log.d(TAG, "changePos: nonRecyclable2: items=" + items + " life=" + life);
            nonRecyclable2.setImageResource(nonRecyclables[new Random().nextInt(nonRecyclables.length)]);
        }
        if (nonRecyclableY2 > frameHeight) {
            nonRecyclableY2 = -100f;
            nonRecyclableX2 = (float) Math.floor(Math.random() * (frameWidth - nonRecyclable2.getWidth()));
        }
        nonRecyclable2.setX(nonRecyclableX2);
        nonRecyclable2.setY(nonRecyclableY2);

        // Move Box
        if (actionFlg) {
            if (actionLeft) {
                recycleBinX -= 14f;
            } else if (actionRight) {
                recycleBinX += 14f;
            }
        }

        // Check box position.
        if (recycleBinX < 0) recycleBinX = 0f;
        if (frameWidth - recycleBinSize < recycleBinX) recycleBinX = frameWidth - recycleBinSize;
        recycleBin.setX(recycleBinX);
        tvItems.setText(String.valueOf(items));
        progressBar.setProgress(getProgress());

        // end game
        if (items == 500 || life <= 0) {
            gameOver();
        }
    }

    public int getProgress() {
        int percentage = (int) (((double) life / totalLifes) * 100);
        Log.d(TAG, "getProgress: life=" + life + " totalLifes=" + totalLifes + " ((double)life/totalLifes)=" + ((double) life / totalLifes) + " percentage=" + percentage);
        return percentage;
    }
}