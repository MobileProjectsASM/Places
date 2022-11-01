package com.applications.asm.places.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.applications.asm.places.R;
import com.applications.asm.places.databinding.FragmentMapPlaceBinding;
import com.applications.asm.places.model.PlaceMapVM;
import com.applications.asm.places.view.fragments.base.BaseFragment;
import com.applications.asm.places.view_model.MainViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import javax.inject.Inject;
import javax.inject.Named;

public class MapPlaceFragment extends BaseFragment<FragmentMapPlaceBinding> implements OnMapReadyCallback {
    private MainViewModel mainViewModel;
    private GoogleMap map;

    @Named("mainVMFactory")
    @Inject
    ViewModelProvider.Factory mainViewModelFactory;

    public MapPlaceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        getActivityComponent().inject(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainViewModel = new ViewModelProvider(requireActivity(), mainViewModelFactory).get(MainViewModel.class);
        createMapFragment();
    }

    @Override
    protected FragmentMapPlaceBinding bindingInflater(LayoutInflater inflater, ViewGroup container) {
        return FragmentMapPlaceBinding.inflate(getLayoutInflater(), container, false);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        initViewObservables();
    }

    private void createMapFragment() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.place_map_fragment);
        if(mapFragment != null) mapFragment.getMapAsync(this);
    }

    private void initViewObservables() {
        mainViewModel.getPlaceMapVM().observe(getViewLifecycleOwner(), this::callbackGetPlaceMapVM);
    }

    private void callbackGetPlaceMapVM(PlaceMapVM placeMapVM) {
        createMarker(placeMapVM.getLatitude(), placeMapVM.getLongitude(), placeMapVM.getName(), placeMapVM.getAddress());
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(placeMapVM.getLatitude(), placeMapVM.getLongitude()), 15f), 2000, null);
    }

    private void createMarker(Double latitude, Double longitude, String title, String address) {
        LatLng coordinates = new LatLng(latitude, longitude);
        MarkerOptions markerOptions = new MarkerOptions().position(coordinates).title(title).snippet(address);
        map.addMarker(markerOptions);
    }
}