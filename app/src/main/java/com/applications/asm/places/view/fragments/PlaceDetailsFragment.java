package com.applications.asm.places.view.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applications.asm.places.R;
import com.applications.asm.places.databinding.FragmentPlaceDetailsBinding;
import com.applications.asm.places.model.PlaceDetailsM;
import com.applications.asm.places.model.PriceM;
import com.applications.asm.places.model.ScheduleM;
import com.applications.asm.places.view.activities.interfaces.MainViewParent;
import com.applications.asm.places.view_model.MainViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

public class PlaceDetailsFragment extends Fragment implements OnMapReadyCallback {
    private FragmentPlaceDetailsBinding binding;
    private MainViewParent mainViewParent;
    private MainViewModel mainViewModel;
    private GoogleMap map;

    @Named("main_view_model")
    @Inject
    ViewModelProvider.Factory factoryMainViewModel;

    public PlaceDetailsFragment() {
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
        binding = FragmentPlaceDetailsBinding.inflate(inflater, container, false);
        createMapFragment();
        mainViewParent.getMainComponent().inject(this);
        mainViewModel = new ViewModelProvider(requireActivity(), factoryMainViewModel).get(MainViewModel.class);
        setObservables();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListeners();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
    }

    private void createMapFragment() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragmentMap);
        if(mapFragment != null)
            mapFragment.getMapAsync(this);
    }

    private void setListeners() {
        binding.showReviewsButton.setOnClickListener(view -> {});
    }

    private void setObservables() {
        mainViewModel.placeDetail().observe(getViewLifecycleOwner(), this::renderView);
    }

    private void renderView(PlaceDetailsM placeDetailsM) {
        if(placeDetailsM != null) {
            if(placeDetailsM.getImageUrl() != null) Picasso.get().load(placeDetailsM.getImageUrl()).fit().centerCrop().into(binding.placeDetailsImageView);
            else Picasso.get().load(R.drawable.place).fit().centerCrop().into(binding.placeDetailsImageView);
            binding.namePlaceTitle.setText(placeDetailsM.getName());
            binding.ratingPlaceText.setText(String.valueOf(placeDetailsM.getRating()));
            binding.ratingBar.setRating(placeDetailsM.getRating().floatValue());
            String rates = placeDetailsM.getReviewsCounter() + " " + getString(R.string.text_helper_rates);
            binding.countReviewsPlaceText.setText(rates);
            String price = getString(R.string.text_helper_price) + " " + getPrice(placeDetailsM.getPrice());
            binding.pricePlaceText.setText(price);
            binding.phoneNumberPlaceText.setText(placeDetailsM.getPhoneNumber());
            binding.isOpenText.setText(placeDetailsM.getOpen() ? getString(R.string.text_helper_place_open) : getString(R.string.text_helper_place_close));
            String schedule = getSchedule(placeDetailsM.getSchedule());
            binding.scheduleText.setText(schedule);
            createMarker(placeDetailsM.getLatitude(), placeDetailsM.getLongitude(), placeDetailsM.getName());
        }
    }

    private String getPrice(PriceM priceM) {
        switch (priceM) {
            case VERY_EXPENSIVE: return getString(R.string.text_value_price_very_expensive);
            case EXPENSIVE: return getString(R.string.text_value_price_expensive);
            case REGULAR: return getString(R.string.text_value_price_regular);
            case CHEAP: return getString(R.string.text_value_price_cheap);
            default: return getString(R.string.text_value_price_unknown);
        }
    }

    private String getSchedule(List<ScheduleM> schedule) {
        StringBuilder aux = new StringBuilder();
        for (int i = 0; i < schedule.size(); i++) {
            aux.append(schedule.get(i).getDay()).append(": ").append(schedule.get(i).getHours());
            if(i < schedule.size() - 1) aux.append("\n");
        }

        return aux.toString();
    }

    private void createMarker(Double latitude, Double longitude, String title) {
        if(map != null) {
            LatLng coordinates = new LatLng(latitude, longitude);
            MarkerOptions markerOptions = new MarkerOptions().position(coordinates).title(title);
            map.addMarker(markerOptions);
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 18f), 4000, null);
        }
    }
}