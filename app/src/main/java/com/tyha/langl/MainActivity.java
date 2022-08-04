package com.tyha.langl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnLaunchGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Disable auto screen orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        btnLaunchGame = findViewById(R.id.btnLaunchGame);

        btnLaunchGame.setOnClickListener((View v) -> {
            changeActivity();
        });
    }

    private void changeActivity() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

}