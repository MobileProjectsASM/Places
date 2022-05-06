package com.applications.asm.places.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.applications.asm.places.databinding.ActivityMainBinding;
import com.applications.asm.places.view.activities.interfaces.MainViewParent;

public class MainActivity extends AppCompatActivity implements MainViewParent {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}