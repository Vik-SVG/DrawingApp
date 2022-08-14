package com.priesniakov.viktor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.priesniakov.viktor.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupListeners();
    }

    private void setupListeners() {
        binding.resetButton.setOnClickListener(button -> resetView());
        binding.sizeMinusButton.setOnClickListener(button -> binding.customCanvas.decreaseDrawingWidth());
        binding.sizePlusButton.setOnClickListener(button -> binding.customCanvas.increaseDrawingWidth());
        binding.undoButton.setOnClickListener(v -> binding.customCanvas.removeLastPaintedView());

//        binding.colorButton.setOnClickListener(v -> {
//
//            ColorChooserDialog dialog = new ColorChooserDialog(DrawFromFingerActivity.this);
//            dialog.setTitle("Select Color");
//            dialog.setColorListener(new ColorListener() {
//                @Override
//                public void OnColorClick(View v, int color) {
//                    //do whatever you want to with the values
//                    customCanvasForDraw.changeColor(color);
//                }
//            });
//            //customize the dialog however you want
//            dialog.show();
//        });

    }

    private void resetView() {
        binding.customCanvas.clearAllView();
    }
}