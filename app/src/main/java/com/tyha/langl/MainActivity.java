package com.tyha.langl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    private Button btnLaunchGame;
    private RadioGroup rgLang;
    private int lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Disable auto screen orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        // Set the target language
        rgLang = findViewById(R.id.rgLang);

        rgLang.setOnCheckedChangeListener((group, checkedId) -> {
            switch(checkedId) {
                case R.id.rbFR:
                    lang = 1;
                    break;
                case R.id.rbRU:
                    lang = 2;
                    break;
                default:
                    // Default and German will have same value for now
                    lang = 0;
            }
        });

        btnLaunchGame = findViewById(R.id.btnLaunchGame);

        btnLaunchGame.setOnClickListener((View v) -> {
            changeActivity(lang);
        });
    }

    private void changeActivity(int lang) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("lang", lang);
        startActivity(intent);
    }

}