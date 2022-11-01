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
import com.applications.asm.places.databinding.CustomInfoWindowBinding;
import com.applications.asm.places.databinding.FragmentMapPlaceBinding;
import com.applications.asm.places.model.PlaceMapVM;
import com.applications.asm.places.model.PlaceVM;
import com.applications.asm.places.view.fragments.base.BaseFragment;
import com.applications.asm.places.view_model.MainViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;
import javax.inject.Named;

public class MapPlaceFragment extends BaseFragment<FragmentMapPlaceBinding> implements OnMapReadyCallback, GoogleMap.InfoWindowAdapter {
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
        createMarker(placeMapVM);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(placeMapVM.getLatitude(), placeMapVM.getLongitude()), 15f), 2000, null);
        map.setInfoWindowAdapter(this);
    }

    private void createMarker(PlaceMapVM placeMapVM) {
        LatLng coordinates = new LatLng(placeMapVM.getLatitude(), placeMapVM.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(coordinates);
        Marker marker = map.addMarker(markerOptions);
        if(marker != null) marker.setTag(placeMapVM);
    }

    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        CustomInfoWindowBinding binding = CustomInfoWindowBinding.inflate(getLayoutInflater());
        PlaceMapVM placeMapVM = (PlaceMapVM) marker.getTag();
        String placeImage = placeMapVM != null ? placeMapVM.getImageUrl() : "";
        if(!placeImage.isEmpty()) Picasso.get().load(placeImage).placeholder(R.drawable.place_image).resize(80, 80).onlyScaleDown().centerCrop().into(binding.placeImageMap, new MarkerCallback(marker));
        else Picasso.get().load(R.drawable.no_image).placeholder(R.drawable.place_image).resize(80, 80).onlyScaleDown().centerCrop().into(binding.placeImageMap, new MarkerCallback(marker));
        binding.titleMapTextView.setText(placeMapVM != null ? placeMapVM.getName() : "");
        binding.addressMapTextView.setText(placeMapVM != null ? placeMapVM.getAddress() : "");
        return binding.getRoot();
    }

    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        return null;
    }

    static class MarkerCallback implements Callback {
        private Marker marker = null;

        public MarkerCallback(Marker marker) {
            this.marker = marker;
        }

        @Override
        public void onSuccess() {
            if (marker == null)
            {
                return;
            }

            if (!marker.isInfoWindowShown())
            {
                return;
            }

            marker.hideInfoWindow();
            marker.showInfoWindow();
        }

        @Override
        public void onError(Exception e) { }
    }
}