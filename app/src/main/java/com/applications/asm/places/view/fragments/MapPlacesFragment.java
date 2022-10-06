package com.applications.asm.places.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.applications.asm.places.databinding.FragmentMapPlacesBinding;
import com.applications.asm.places.model.CoordinatesVM;
import com.applications.asm.places.model.PlaceVM;
import com.applications.asm.places.view.fragments.base.BaseFragment;
import com.applications.asm.places.view.utils.MultipleLiveDataTransformation;
import com.applications.asm.places.view_model.MainViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

public class MapPlacesFragment extends BaseFragment<FragmentMapPlacesBinding> {
    private final String WORK_COORDINATES = "WORK_COORDINATES";
    private final String PLACES = "PLACES";
    private MainViewModel mainViewModel;

    @Named("mainVMFactory")
    @Inject
    ViewModelProvider.Factory mainViewModelFactory;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        getActivityComponent().inject(this);
    }

    public MapPlacesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainViewModel = new ViewModelProvider(requireActivity(), mainViewModelFactory).get(MainViewModel.class);
        initViewObservables();
    }

    private void initViewObservables() {
        LiveData<CoordinatesVM> coordinatesVM = mainViewModel.getWorkCoordinates();
        LiveData<List<PlaceVM>> placesVW = mainViewModel.getPlacesVM();
        LiveData<Map<String, Object>> placesAndCoordinates = MultipleLiveDataTransformation.combineLatest(coordinatesVM, placesVW, (coordinates, places) -> {
            Map<String, Object> data = new HashMap<>();
            data.put(WORK_COORDINATES, coordinates);
            data.put(PLACES, places);
            return data;
        });
        placesAndCoordinates.observe(getViewLifecycleOwner(), this::callbackPlacesAndCoordinates);
    }

    @Override
    protected FragmentMapPlacesBinding bindingInflater(LayoutInflater inflater, ViewGroup container) {
        return FragmentMapPlacesBinding.inflate(inflater, container, false);
    }

    private void callbackPlacesAndCoordinates(Map<String, Object> placesAndCoordinates) {
        //Pintar en el mapa
        List<PlaceVM> placesVM = (List<PlaceVM>) placesAndCoordinates.get(PLACES);
        CoordinatesVM coordinatesVM = (CoordinatesVM) placesAndCoordinates.get(WORK_COORDINATES);
    }
}