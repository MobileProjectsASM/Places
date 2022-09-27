package com.applications.asm.places.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.applications.asm.places.application.PlacesApplication;
import com.applications.asm.places.databinding.ActivityMainBinding;
import com.applications.asm.places.di.components.ActivityComponent;
import com.applications.asm.places.view.activities.interfaces.MainViewParent;

public class MainActivity extends AppCompatActivity implements MainViewParent {
    private ActivityMainBinding binding;
    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activityComponent = ((PlacesApplication) getApplication()).getApplicationComponent().provideActivityComponentFactory().create();
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolBar);
    }

    @Override
    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }

    @Override
    public ActivityMainBinding getMainBinding() {
        return binding;
    }
}