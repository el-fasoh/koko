package com.kokonetworks.theapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;


public class MainActivity extends AppCompatActivity {

    private Field field;
    private TextView tvLevel;
    private TextView tvScore;

    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        field = findViewById(R.id.field);
        tvLevel = findViewById(R.id.tvLevel);
        btnStart = findViewById(R.id.btnStart);
        tvScore = findViewById(R.id.tvScore);

        setEventListeners();

        setupObservers();
    }

    private void setupObservers() {
        field.gameState.observe(this, game -> {
            if (game.state == GameState.ENDED) {
                btnStart.setVisibility(View.VISIBLE);
                tvScore.setVisibility(View.VISIBLE);
                tvScore.setText(String.format(getString(R.string.your_score), game.score));
            } else {
                tvScore.setVisibility(View.VISIBLE);
                tvScore.setText(String.format(getString(R.string.your_score), game.score));
            }
        });
    }

    void setEventListeners(){
        btnStart.setOnClickListener(view -> {
            btnStart.setVisibility(View.GONE);
            tvScore.setVisibility(View.GONE);
            field.startGame();
        });

    }
}