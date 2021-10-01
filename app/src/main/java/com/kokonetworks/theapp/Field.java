package com.kokonetworks.theapp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;

public class Field extends LinearLayout {
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private final SquareButton[] circles = new SquareButton[9];
    private int currentCircle;

    public int score;
    private Mole mole;
    public MutableLiveData<Game> gameState = new MutableLiveData();
    private int hops = 0;
    private int taps = 0;
    private SquareButton falseHope;

    private final int ACTIVE_TAG_KEY = 873374234;

    public Field(Context context) {
        super(context);
        initializeViews(context);
    }

    public Field(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public Field(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }

    public Field(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initializeViews(context);
    }

    public int totalCircles() {
        return circles.length;
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_field, this, true);

        circles[0] = (SquareButton) findViewById(R.id.hole1);
        circles[1] = (SquareButton) findViewById(R.id.hole2);
        circles[2] = (SquareButton) findViewById(R.id.hole3);
        circles[3] = (SquareButton) findViewById(R.id.hole4);
        circles[4] = (SquareButton) findViewById(R.id.hole5);
        circles[5] = (SquareButton) findViewById(R.id.hole6);
        circles[6] = (SquareButton) findViewById(R.id.hole7);
        circles[7] = (SquareButton) findViewById(R.id.hole8);
        circles[8] = (SquareButton) findViewById(R.id.hole9);

    }

    private void resetScore() {
        score = 0;
    }

    public void startGame() {
        resetScore();
        resetCircles();
        for (SquareButton squareButton : circles) {
            squareButton.setOnClickListener(view -> {
                boolean active = (boolean) view.getTag(ACTIVE_TAG_KEY);
                taps++;
                if (active) {
                    score += mole.getCurrentLevel() * 2;
                    gameState.postValue(new Game(score, GameState.RUNNING, mole.getCurrentLevel()));
                } else {
                    mole.stopHopping();
                    gameState.postValue(new Game(score, GameState.ENDED, mole.getCurrentLevel()));
                    view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.oval_yellow));
                }
            });
        }

        mole = new Mole(this);
        mole.startHopping();
    }

    //done
    public int getCurrentCircle() {
        return currentCircle;
    }

    //done
    private void resetCircles() {
        for (SquareButton squareButton : circles) {
            squareButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.hole_inactive));
            squareButton.setTag(ACTIVE_TAG_KEY, false);
        }
    }

    //done
    public void setActive(int index) {
        if (taps != hops) {
//            taps = 0;
//            hops = 0;
//            mole.stopHopping();
//            gameState.postValue(new Game(score, GameState.ENDED, mole.getCurrentLevel()));
//            return;
        }
        mainHandler.post(() -> {
            hops++;
            resetCircles();
            circles[index].setBackground(ContextCompat.getDrawable(getContext(), R.drawable.hole_active));
            circles[index].setTag(ACTIVE_TAG_KEY, true);
            currentCircle = index;
        });
    }

    public void falseHope(int index) {
        mainHandler.post(() -> {
            if (falseHope != null)
                falseHope.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.hole_inactive));
            falseHope = circles[index];
            circles[index].setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_baseline_wb_sunny_24));
        });
    }

    public interface Listener {
        void onGameEnded(int score);

        void onLevelChange(int level);
    }
}