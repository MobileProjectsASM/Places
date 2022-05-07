package com.applications.asm.places.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.applications.asm.domain.entities.Category;
import com.applications.asm.places.databinding.FragmentSearchBinding;
import com.applications.asm.places.model.CategoriesConstants;
import com.applications.asm.places.view.utils.AfterTextChanged;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    private FragmentSearchBinding binding;
    private final String TAG = "SearchFragment";

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListeners();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void setListeners() {
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) binding.placeTextInputLayout.getEditText();
        if(autoCompleteTextView != null)
            autoCompleteTextView.addTextChangedListener((AfterTextChanged) editable -> Log.i(TAG, editable.toString()));
    }

    private List<String> getCategories() {
        List<String> categories = new ArrayList<>();
        if(binding.foodChip.isChecked()) categories.add(CategoriesConstants.FOOD);
        if(binding.activeLifeChip.isChecked()) categories.add(CategoriesConstants.ACTIVE_LIFE);
        if(binding.healthChip.isChecked()) categories.add(CategoriesConstants.HEALTH);
        if(binding.artsEntertainmentChip.isChecked()) categories.add(CategoriesConstants.ARTS_ENTERTAINMENT);
        if(binding.automotiveChip.isChecked()) categories.add(CategoriesConstants.AUTOMOTIVE);
        if(binding.beautySpasChip.isChecked()) categories.add(CategoriesConstants.BEAUTY_SPAS);
        if(binding.educationChip.isChecked()) categories.add(CategoriesConstants.EDUCATION);
        if(binding.nightlifeChip.isChecked()) categories.add(CategoriesConstants.NIGHTLIFE);
        if(binding.petsChip.isChecked()) categories.add(CategoriesConstants.PETS);
        if(binding.publicServicesChip.isChecked()) categories.add(CategoriesConstants.PUBLIC_SERVICES);
        return categories;
    }
}