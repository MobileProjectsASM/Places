package com.applications.asm.places.view.fragments.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.applications.asm.places.di.components.ActivityComponent;
import com.applications.asm.places.view.activities.interfaces.MainViewParent;

public abstract class BaseFragment<VB extends ViewBinding> extends Fragment {
    private VB viewBinding;
    private MainViewParent mainViewParent;

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainViewParent = (MainViewParent) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewBinding = bindingInflater(inflater, container);
        return viewBinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewBinding = null;
    }

    protected abstract VB bindingInflater(LayoutInflater inflater, ViewGroup container);

    public VB getViewBinding() {
        return viewBinding;
    }

    public ActivityComponent getActivityComponent() {
        return mainViewParent.getActivityComponent();
    }
}