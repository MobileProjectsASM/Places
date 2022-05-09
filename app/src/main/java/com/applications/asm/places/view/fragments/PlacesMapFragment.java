package com.applications.asm.places.view.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applications.asm.places.R;
import com.applications.asm.places.databinding.FragmentPlacesMapBinding;
import com.applications.asm.places.model.PlaceM;
import com.applications.asm.places.view.activities.interfaces.MainViewParent;
import com.applications.asm.places.view_model.MainViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

public class PlacesMapFragment extends Fragment implements OnMapReadyCallback {
    private MainViewParent mainViewParent;
    private MainViewModel mainViewModel;
    private GoogleMap map;
    private Map<String, String> markers;
    private SupportMapFragment mapFragment;

    @Named("main_view_model")
    @Inject
    ViewModelProvider.Factory factoryMainViewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainViewParent = (MainViewParent) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        com.applications.asm.places.databinding.FragmentPlacesMapBinding binding = FragmentPlacesMapBinding.inflate(inflater, container, false);
        mainViewParent.getMainComponent().inject(this);
        mainViewModel = new ViewModelProvider(requireActivity(), factoryMainViewModel).get(MainViewModel.class);
        setObservables();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createMapFragment();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapFragment.onDestroyView();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
    }

    private void setObservables() {
        mainViewModel.placesMV().observe(getViewLifecycleOwner(), this::renderMap);
    }

    private void createMapFragment() {
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.placesMapFragment);
        if(mapFragment != null)
            mapFragment.getMapAsync(this);
    }

    private void renderMap(List<PlaceM> placesM) {
        if(map != null) {
            markers = new HashMap<>();
            for(PlaceM placeM: placesM) {
                Marker marker = createMarker(placeM.getLatitude(), placeM.getLongitude(), placeM.getName());
                markers.put(marker.getId(), placeM.getId());
            }
            map.setOnInfoWindowClickListener(this::clickOnMarkerTitle);
        }
    }

    private Marker createMarker(Double latitude, Double longitude, String title) {
        LatLng coordinates = new LatLng(latitude, longitude);
        MarkerOptions markerOptions = new MarkerOptions().position(coordinates).title(title);
        return map.addMarker(markerOptions);
    }

    private void clickOnMarkerTitle(Marker marker) {
        String placeId = markers.get(marker.getId());
        mainViewModel.getPlaceDetail(placeId);
        NavHostFragment.findNavController(this).navigate(R.id.action_placesMapFragment_to_placeDetailsFragment);
        map.clear();
    }
}