package com.priesniakov.viktor.view;


public interface CoordinatesCallback {
    void moving(float x, float y);
    void start(float x, float y);
    void end(float x, float y);
}