package com.applications.asm.places.view.fragments;

import static com.applications.asm.places.view.fragments.PlaceDetailsFragment.PLACE_ID_KEY;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.applications.asm.places.R;
import com.applications.asm.places.databinding.FragmentMapPlacesBinding;
import com.applications.asm.places.model.CoordinatesVM;
import com.applications.asm.places.model.PlaceVM;
import com.applications.asm.places.view.fragments.base.BaseFragment;
import com.applications.asm.places.view.utils.MultipleLiveDataTransformation;
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

public class MapPlacesFragment extends BaseFragment<FragmentMapPlacesBinding> implements OnMapReadyCallback {
    private final String WORK_COORDINATES = "WORK_COORDINATES";
    private final String PLACES = "PLACES";
    private MainViewModel mainViewModel;
    private GoogleMap map;
    private Map<String, String> markers;

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
        createMapFragment();
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

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        initViewObservables();
    }

    private void createMapFragment() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.places_map_fragment);
        if(mapFragment != null) mapFragment.getMapAsync(this);
    }

    private void callbackPlacesAndCoordinates(Map<String, Object> placesAndCoordinates) {
        List<PlaceVM> placesVM = (List<PlaceVM>) placesAndCoordinates.get(PLACES);
        CoordinatesVM coordinatesVM = (CoordinatesVM) placesAndCoordinates.get(WORK_COORDINATES);
        renderMap(coordinatesVM, placesVM);
    }

    private void renderMap(CoordinatesVM workCoordinates, List<PlaceVM> places) {
        if(map != null) {
            markers = new HashMap<>();
            for(PlaceVM placeVM: places) {
                Marker marker = createMarker(placeVM.getLatitude(), placeVM.getLongitude(), placeVM.getName());
                markers.put(marker.getId(), placeVM.getId());
            }
            map.setOnInfoWindowClickListener(this::clickOnMarkerTitle);
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(workCoordinates.getLatitude(), workCoordinates.getLongitude()), 15f), 2000, null);
        }
    }

    private Marker createMarker(Double latitude, Double longitude, String title) {
        LatLng coordinates = new LatLng(latitude, longitude);
        MarkerOptions markerOptions = new MarkerOptions().position(coordinates).title(title);
        return map.addMarker(markerOptions);
    }

    private void clickOnMarkerTitle(Marker marker) {
        String placeId = markers.get(marker.getId());
        Bundle bundle = new Bundle();
        bundle.putString(PLACE_ID_KEY, placeId);
        NavHostFragment.findNavController(this).navigate(R.id.action_global_placeDetailsFragment, bundle);
        /*map.clear();*/
    }
}