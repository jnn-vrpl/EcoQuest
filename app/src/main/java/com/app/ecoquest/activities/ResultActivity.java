package com.app.ecoquest.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.ecoquest.R;

public class ResultActivity extends AppCompatActivity {

    int items = 0;
    TextView tvItems;
    Button btnReplay, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        items = getIntent().getIntExtra("items", items);

        tvItems = findViewById(R.id.tvItems);
        btnReplay = findViewById(R.id.btnReplay);
        btnBack = findViewById(R.id.btnBack);

        tvItems.setText(String.valueOf(items));

        btnReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PlayActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}