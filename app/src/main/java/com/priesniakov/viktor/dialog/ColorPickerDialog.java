package com.priesniakov.viktor.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.priesniakov.viktor.databinding.DialogColorPickerBinding;

import java.util.ArrayList;


public class ColorPickerDialog extends DialogFragment {

    public static final String TAG = "ColorPickerDialog";

    private DialogColorPickerBinding binding;
    private ColorPickerListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity = requireActivity();
        if (activity instanceof ColorPickerListener) {
            listener = (ColorPickerListener) activity;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = DialogColorPickerBinding.inflate(LayoutInflater.from(getContext()));
        return new AlertDialog.Builder(requireContext())
                .setView(binding.getRoot())
                .create();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setColorListener();
    }

    public void setColorListener() {

        ArrayList<ImageView> colorsList = new ArrayList<>();
        colorsList.add(binding.blue);
        colorsList.add(binding.purple);
        colorsList.add(binding.black);
        colorsList.add(binding.red);
        colorsList.add(binding.teal);

        for (ImageView imageView : colorsList) {
            imageView.setOnClickListener(view -> {
                listener.setColor(imageView.getImageTintList().getDefaultColor());
                dismiss();
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    public static ColorPickerDialog newInstance() {
        return new ColorPickerDialog();
    }
}
