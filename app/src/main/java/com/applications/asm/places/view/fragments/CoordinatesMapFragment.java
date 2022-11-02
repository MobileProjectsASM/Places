package com.applications.asm.places.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.applications.asm.places.R;
import com.applications.asm.places.databinding.FragmentCoordinatesMapBinding;
import com.applications.asm.places.model.CoordinatesVM;
import com.applications.asm.places.view.fragments.base.BaseFragment;
import com.applications.asm.places.view_model.MainViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;
import javax.inject.Named;

public class CoordinatesMapFragment extends BaseFragment<FragmentCoordinatesMapBinding> implements OnMapReadyCallback, GoogleMap.OnCameraMoveListener {
    private MainViewModel mainViewModel;
    private GoogleMap map;
    private CoordinatesVM workCoordinates;

    @Named("mainVMFactory")
    @Inject
    ViewModelProvider.Factory mainViewModelFactory;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        getActivityComponent().inject(this);
    }

    @Override
    protected FragmentCoordinatesMapBinding bindingInflater(LayoutInflater inflater, ViewGroup container) {
        return FragmentCoordinatesMapBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainViewModel = new ViewModelProvider(requireActivity(), mainViewModelFactory).get(MainViewModel.class);
        createMapFragment();
    }

    private void createMapFragment() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.coordinates_map);
        if(mapFragment != null) mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.setOnCameraMoveListener(this);
        mainViewModel.getWorkCoordinates().observe(getViewLifecycleOwner(), this::callbackWorkCoordinates);
        setListeners();
    }

    private void callbackWorkCoordinates(CoordinatesVM coordinatesVM) {
        if(coordinatesVM != null && coordinatesVM.getLatitude() != null && coordinatesVM.getLongitude() != null) workCoordinates = coordinatesVM;
        else workCoordinates = new CoordinatesVM(19.43271, -99.13321);
        renderMap();
    }

    @Override
    public void onCameraMove() {
        workCoordinates.setLatitude(map.getCameraPosition().target.latitude);
        workCoordinates.setLongitude(map.getCameraPosition().target.longitude);
    }

    private void setListeners() {
        getViewBinding().pointPlaceButton.setOnClickListener(view -> {
            mainViewModel.getWorkCoordinates().setValue(workCoordinates);
            NavHostFragment.findNavController(this).popBackStack();
        });
    }

    private void renderMap() {
        if(map != null) map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(workCoordinates.getLatitude(), workCoordinates.getLongitude()), 15f), 2000, null);
    }
}