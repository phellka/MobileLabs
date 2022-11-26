package com.example.mobilelabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Random;

public class StartActivity extends AppCompatActivity {
    int clickCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ImageButton btn = findViewById(R.id.imageButtonStartClick);

        btn.setOnClickListener(v -> {
            clickCount += 1;
            int width = Resources.getSystem().getDisplayMetrics().widthPixels;
            int height = Resources.getSystem().getDisplayMetrics().heightPixels;
            Random r = new Random();
            btn.setX(r.nextInt(width - 170));
            btn.setY(r.nextInt(height - 400));
            if (clickCount == 10){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        View img = findViewById(R.id.imageViewStartActivity);
        img.setOnClickListener(v -> {
            Toast.makeText(this, clickCount + "/10", Toast.LENGTH_SHORT).show();
        });
    }
}