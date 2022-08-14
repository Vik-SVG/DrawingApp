package com.priesniakov.viktor.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.priesniakov.viktor.R;
import com.priesniakov.viktor.databinding.FingerDrawViewBinding;

import java.util.ArrayList;

public class DrawingViewGroup extends FrameLayout {

    private FingerDrawViewBinding binding;
    private float xDown = 0, yDown = 0;

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
        setupEmoji();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupEmoji() {

        ArrayList<ImageView> imagesList = new ArrayList<>();
        imagesList.add(binding.smileImage);
        imagesList.add(binding.smileImage2);
        imagesList.add(binding.smileImage3);
        imagesList.add(binding.smileImage4);
        imagesList.add(binding.smileImage5);

        for (ImageView draggableImage : imagesList) {
            draggableImage.setOnClickListener(view -> {
                ImageView image = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.item_draggable_image, binding.imagesContainer, false);
                Drawable drawable = ((ImageView) view).getDrawable();
                image.setImageDrawable(drawable);
                binding.imagesContainer.addView(image);

                image.setOnTouchListener((touchView, motionEvent) -> {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        xDown = motionEvent.getX();
                        yDown = motionEvent.getY();
                    } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                        float xMove, yMove;
                        xMove = motionEvent.getX();
                        yMove = motionEvent.getY();

                        float distX = xMove - xDown;
                        float distY = yMove - yDown;
                        touchView.setX(touchView.getX() + distX);
                        touchView.setY(touchView.getY() + distY);
                    }
                    return true;
                });
            });
        }
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
        binding.imagesContainer.removeAllViews();
        binding.drawingView.resetView();
    }
}
