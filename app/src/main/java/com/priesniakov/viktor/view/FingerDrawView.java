package com.priesniakov.viktor.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.priesniakov.viktor.R;

import java.util.ArrayList;

public class FingerDrawView extends View {

    private static final String PARENT_VIEW_SAVED_STATE = "parent_view";
    private static final String CHILD_VIEW_SAVED_STATE = "child_view";
    private static final int STATE_IDLE = 0;
    private static final int STATE_MOVING = 1;
    private static int DEFAULT_COLOR;

    private static final int MAX_PAINT_WIDTH = 50;
    private static final int CHANGE_WIDTH_STEP = 10;
    private static final int MIN_WIDTH = 5;
    private static final int DEFAULT_LINE_WIDTH = 20;

    private CoordinatesCallback coordinatesCallback;

    private final ArrayList<Paint> paintList = new ArrayList<>();
    private final ArrayList<Path> pathList = new ArrayList<>();
    private Path latestPath;
    private Paint latestPaint;
    private int defaultLineWidth = DEFAULT_LINE_WIDTH;
    private int currentColor;
    private int currentState = 0;


    public FingerDrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        DEFAULT_COLOR = ContextCompat.getColor(getContext(), R.color.purple_200);
        currentColor = DEFAULT_COLOR;
        initPaintAndPath(currentColor);
    }

    private void initPaintAndPath(int color) {
        latestPaint = getNewPaint(color);
        latestPath = new Path();

        paintList.add(latestPaint);
        pathList.add(latestPath);
    }

    private Paint getNewPaint(int color) {
        Paint paint = new Paint();
        paint.setStrokeWidth(defaultLineWidth);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.MITER);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(color);
        return paint;
    }

    public void setThisCallback(CoordinatesCallback callback) {
        this.coordinatesCallback = callback;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        Log.i("CO-ordinate", event.getX() + " : " + event.getY());

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            coordinatesCallback.start(x, y);
            startPath(x, y);
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            coordinatesCallback.moving(x, y);
            updatePath(x, y);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            coordinatesCallback.end(x, y);
            endPath(x, y);
        }
        invalidate();
        return true;
    }

    private void startPath(float x, float y) {
        initPaintAndPath(currentColor);
        latestPath.moveTo(x, y);
    }

    private void updatePath(float x, float y) {
        currentState = STATE_MOVING;
        latestPath.lineTo(x, y);
    }

    private void endPath(float x, float y) {
    }

    public void setDrawColor(int color) {
        currentColor = color;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < paintList.size(); i++) {
            canvas.drawPath(pathList.get(i), paintList.get(i));
        }
    }

    public void decreaseWidth() {
        if (defaultLineWidth > MIN_WIDTH) {
            defaultLineWidth = defaultLineWidth - CHANGE_WIDTH_STEP;
            invalidate();
        }
    }

    public void increaseWidth() {
        if (defaultLineWidth < MAX_PAINT_WIDTH) {
            defaultLineWidth = defaultLineWidth + CHANGE_WIDTH_STEP;
            invalidate();
        }
    }

    public void resetView() {
        currentColor = DEFAULT_COLOR;
        currentState = STATE_IDLE;
        latestPath.reset();
        latestPaint.reset();

        pathList.clear();
        paintList.clear();
        this.defaultLineWidth = DEFAULT_LINE_WIDTH;

        initPaintAndPath(currentColor);

        invalidate();
    }


    public void undoPath() {
        if (paintList.size() > 1) {

            paintList.remove(paintList.size() - 1);
            pathList.remove(pathList.size() - 1);

            latestPaint = paintList.get(paintList.size() - 1);
            latestPath = pathList.get(pathList.size() - 1);

            currentColor = latestPaint.getColor();
            defaultLineWidth = Math.round(latestPaint.getStrokeWidth());
        } else {
            resetView();
        }
        invalidate();
    }
}

