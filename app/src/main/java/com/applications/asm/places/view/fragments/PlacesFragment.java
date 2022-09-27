package com.applications.asm.places.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.applications.asm.places.databinding.FragmentPlacesBinding;

public class PlacesFragment extends Fragment {
    private FragmentPlacesBinding binding;

    public PlacesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPlacesBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }
}