package com.applications.asm.places.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.applications.asm.places.R;
import com.applications.asm.places.databinding.FragmentSearchBinding;
import com.applications.asm.places.model.CategoriesConstants;
import com.applications.asm.places.view.activities.interfaces.MainViewParent;
import com.applications.asm.places.view.utils.AfterTextChanged;
import com.applications.asm.places.view_model.SearchViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

public class SearchFragment extends Fragment {
    private FragmentSearchBinding binding;
    private MainViewParent mainViewParent;
    private final String TAG = "SearchFragment";
    private SearchViewModel searchViewModel;
    private Boolean doSearch = true;

    @Named("search_view_model")
    @Inject
    ViewModelProvider.Factory factory;

    public SearchFragment() {
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
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        mainViewParent.getMainComponent().inject(this);
        searchViewModel = new ViewModelProvider(this, factory).get(SearchViewModel.class);
        searchViewModel.suggestedPlaces().observe(getViewLifecycleOwner(), namePlaces -> {
            if(doSearch) {
                ArrayAdapter<String> placeAutocompleteAdapter = new ArrayAdapter<>(requireContext(), R.layout.place_suggested_item_layout, namePlaces);
                AutoCompleteTextView autoCompleteSearchPlace = (AutoCompleteTextView) binding.placeTextInputLayout.getEditText();
                if(autoCompleteSearchPlace != null) {
                    autoCompleteSearchPlace.setAdapter(placeAutocompleteAdapter);
                    if(!namePlaces.isEmpty())
                        autoCompleteSearchPlace.showDropDown();
                    else autoCompleteSearchPlace.dismissDropDown();
                }
            } else doSearch = true;
        });
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
        if(autoCompleteTextView != null) {
            autoCompleteTextView.addTextChangedListener((AfterTextChanged) editable -> searchViewModel.getSuggestedPlaces(editable.toString()));
            autoCompleteTextView.setOnItemClickListener((adapterView, view, i, l) -> doSearch = false);
        }
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