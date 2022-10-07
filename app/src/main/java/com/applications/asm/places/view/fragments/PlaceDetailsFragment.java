package com.applications.asm.places.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.applications.asm.places.databinding.FragmentMapPlacesBinding;
import com.applications.asm.places.databinding.FragmentPlaceDetailsBinding;
import com.applications.asm.places.view.fragments.base.BaseFragment;

public class PlaceDetailsFragment extends BaseFragment<FragmentPlaceDetailsBinding> {

    public PlaceDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected FragmentPlaceDetailsBinding bindingInflater(LayoutInflater inflater, ViewGroup container) {
        return FragmentPlaceDetailsBinding.inflate(inflater, container, false);
    }
}