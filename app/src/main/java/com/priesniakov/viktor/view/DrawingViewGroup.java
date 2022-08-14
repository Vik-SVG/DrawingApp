package com.priesniakov.viktor.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.priesniakov.viktor.R;
import com.priesniakov.viktor.databinding.FingerDrawViewBinding;

public class DrawingViewGroup extends FrameLayout implements CoordinatesCallback {

    private TextView startText, moveText, endText;

    private boolean isDebugEnabled = true;
    private FingerDrawViewBinding binding;

    public DrawingViewGroup(Context context) {
        this(context, null);
    }

    public DrawingViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawingViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        binding = FingerDrawViewBinding.inflate(LayoutInflater.from(getContext()), this, true);

        startText = (TextView) findViewById(R.id.startPointText);
        moveText = (TextView) findViewById(R.id.movingPointText);
        endText = (TextView) findViewById(R.id.endPointText);

        if (isDebugEnabled) {
            startText.setVisibility(VISIBLE);
            moveText.setVisibility(VISIBLE);
            endText.setVisibility(VISIBLE);
        } else {
            startText.setVisibility(GONE);
            moveText.setVisibility(GONE);
            endText.setVisibility(GONE);
        }

        binding.drawingView.setThisCallback(this);
    }

    public void changeColor(int color) {
        binding.drawingView.setDrawColor(color);
    }

    public void removeLastPaintedView() {
        binding.drawingView.undoPath();
    }

    public void decreaseDrawingWidth() {
        binding.drawingView.decreaseWidth();
    }

    public void increaseDrawingWidth() {
        binding.drawingView.increaseWidth();
    }

    public void clearAllView() {
        binding.drawingView.resetView();
        moveText.setText("0.0");
        startText.setText("0.0");
        endText.setText("0.0");
    }

    @Override
    public void moving(float x, float y) {
        moveText.setText(String.format("%.02f", x) + ", " + String.format("%.02f", y));
    }

    @Override
    public void start(float x, float y) {
        startText.setText(String.format("%.02f", x) + ", " + String.format("%.02f", y));
    }

    @Override
    public void end(float x, float y) {
        endText.setText(String.format("%.02f", x) + ", " + String.format("%.02f", y));
    }
}
