package com.priesniakov.viktor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.graphics.Color;
import android.os.Bundle;

import com.priesniakov.viktor.databinding.ActivityMainBinding;
import com.priesniakov.viktor.dialog.ColorPickerDialog;
import com.priesniakov.viktor.dialog.ColorPickerListener;

public class MainActivity extends AppCompatActivity implements ColorPickerListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupListeners();
    }

    private void setupListeners() {
        binding.cleanAllDrawing.setOnClickListener(button -> resetView());
        binding.decreaseWidth.setOnClickListener(button -> binding.customCanvas.decreaseDrawingWidth());
        binding.increaseWidth.setOnClickListener(button -> binding.customCanvas.increaseDrawingWidth());
        binding.undoDrawing.setOnClickListener(v -> binding.customCanvas.removeLastPaintedView());

        binding.changeColor.setOnClickListener(v -> {
            ColorPickerDialog dialog = ColorPickerDialog.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            dialog.show(fragmentManager, ColorPickerDialog.TAG);
        });
    }

    private void resetView() {
        binding.customCanvas.clearAllView();
    }

    @Override
    public void setColor(int color) {
        binding.customCanvas.changeColor(color);
    }
}