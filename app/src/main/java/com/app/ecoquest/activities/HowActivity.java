package com.app.ecoquest.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.ecoquest.R;

public class HowActivity extends AppCompatActivity {

    LinearLayout layout0, layout1, layout2, layout3, layout4, layout5;
    ImageView ivLeft, ivRight;
    TextView tvTitle,tvMsg;
    Button btnBack;

    String[] titles = {"drag to move bin", "COLLECT-RECYCLABLES AND AVOID RESIDUAL AND BIODEGRADABLE ONĖS", "RECYCLABLE ITEMS", "RESIDUAL ITEMS", "BIODEGRADABLE ITEMS"};
    String[] messages = {"", "", "(Nareresiklo/Nabebenta)\nWaste materials that can be cleaned; retrieved, and converted into suitable use or other purposes", "(Non-recyclable)\nWaste materials that cannot be recycled or decomposed.", "(Nabubulok/Compostable)\nWaste materials that decomposed under natural conditions"};

    int index = 0;

    String TAG = "theS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how);

        layout0 = findViewById(R.id.layout0);
        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);
        layout3 = findViewById(R.id.layout3);
        layout4 = findViewById(R.id.layout4);
        layout5 = findViewById(R.id.layout5);
        ivLeft = findViewById(R.id.ivLeft);
        ivRight = findViewById(R.id.ivRight);
        tvTitle = findViewById(R.id.tvTitle);
        tvMsg = findViewById(R.id.tvMsg);
        btnBack = findViewById(R.id.btnBack);

        updateView();

        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ivLeft=" + index);
                if (index != 0) {
                    index--;
                    tvTitle.setText(titles[index]);
                    tvMsg.setText(messages[index]);
                }
                updateView();
            }
        });

        ivRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ivRight=" + index);
                if (index < messages.length - 1) {
                    index++;
                    tvTitle.setText(titles[index]);
                    tvMsg.setText(messages[index]);
                }
                updateView();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void updateView() {
        if (index == 0) {
            ivLeft.setVisibility(View.INVISIBLE);
            ivRight.setVisibility(View.VISIBLE);
            layout0.setVisibility(View.VISIBLE);
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.GONE);
            layout3.setVisibility(View.GONE);
            layout4.setVisibility(View.GONE);
            layout5.setVisibility(View.GONE);
            tvMsg.setVisibility(View.GONE);
        } else if (index == 1) {
            ivLeft.setVisibility(View.VISIBLE);
            ivRight.setVisibility(View.VISIBLE);
            layout0.setVisibility(View.GONE);
            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.VISIBLE);
            layout3.setVisibility(View.GONE);
            layout4.setVisibility(View.GONE);
            layout5.setVisibility(View.GONE);
            tvMsg.setVisibility(View.GONE);
        } else if (index == 2) {
            ivLeft.setVisibility(View.VISIBLE);
            ivRight.setVisibility(View.VISIBLE);
            layout0.setVisibility(View.GONE);
            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.GONE);
            layout3.setVisibility(View.VISIBLE);
            layout4.setVisibility(View.GONE);
            layout5.setVisibility(View.GONE);
            tvMsg.setVisibility(View.VISIBLE);

        } else if (index == 3) {
            ivLeft.setVisibility(View.VISIBLE);
            ivRight.setVisibility(View.VISIBLE);
            layout0.setVisibility(View.GONE);
            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.GONE);
            layout3.setVisibility(View.GONE);
            layout4.setVisibility(View.VISIBLE);
            layout5.setVisibility(View.GONE);
            tvMsg.setVisibility(View.VISIBLE);
        } else if (index == 4) {
            ivLeft.setVisibility(View.VISIBLE);
            ivRight.setVisibility(View.INVISIBLE);
            layout0.setVisibility(View.GONE);
            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.GONE);
            layout3.setVisibility(View.GONE);
            layout4.setVisibility(View.GONE);
            layout5.setVisibility(View.VISIBLE);
            tvMsg.setVisibility(View.VISIBLE);
        }
    }
}