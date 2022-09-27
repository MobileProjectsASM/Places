package com.applications.asm.places.view.activities.interfaces;

import com.applications.asm.places.databinding.ActivityMainBinding;
import com.applications.asm.places.di.components.ActivityComponent;

public interface MainViewParent {
    ActivityComponent getActivityComponent();
    ActivityMainBinding getMainBinding();
}
