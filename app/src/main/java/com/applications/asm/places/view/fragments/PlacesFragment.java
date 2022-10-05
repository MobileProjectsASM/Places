package com.applications.asm.places.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applications.asm.places.R;
import com.applications.asm.places.databinding.FragmentPlacesBinding;
import com.applications.asm.places.databinding.LoadingItemLayoutBinding;
import com.applications.asm.places.databinding.LoadingLayoutBinding;
import com.applications.asm.places.databinding.MessageLayoutBinding;
import com.applications.asm.places.databinding.PlacesLayoutBinding;
import com.applications.asm.places.model.ParametersAdvancedSearch;
import com.applications.asm.places.model.PlaceVM;
import com.applications.asm.places.model.Resource;
import com.applications.asm.places.view.adapters.PlaceAdapter;
import com.applications.asm.places.view.events.PlaceClickListener;
import com.applications.asm.places.view.fragments.base.BaseFragment;
import com.applications.asm.places.view.utils.ViewUtils;
import com.applications.asm.places.view_model.MainViewModel;
import com.applications.asm.places.view_model.PlacesViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

public class PlacesFragment extends BaseFragment<FragmentPlacesBinding> implements PlaceClickListener {
    private MainViewModel mainViewModel;
    private PlacesViewModel placesViewModel;
    private PlaceAdapter placeAdapter;
    private boolean isLoading = false;

    @Named("placesVMFactory")
    @Inject
    ViewModelProvider.Factory placesViewModelFactory;

    @Named("mainVMFactory")
    @Inject
    ViewModelProvider.Factory mainViewModelFactory;

    public PlacesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        getActivityComponent().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainViewModel = new ViewModelProvider(requireActivity(), mainViewModelFactory).get(MainViewModel.class);
        placesViewModel = new ViewModelProvider(this, placesViewModelFactory).get(PlacesViewModel.class);
        initViewObservables();
        initAdapter();
    }

    @Override
    protected FragmentPlacesBinding bindingInflater(LayoutInflater inflater, ViewGroup container) {
        return FragmentPlacesBinding.inflate(inflater, container, false);
    }

    @Override
    public void onPlaceClickListener(PlaceVM placeVM) {
        Toast.makeText(requireContext(), "Se hizo click en " + placeVM.getName(), Toast.LENGTH_LONG).show();
    }

    private void initViewObservables() {
        mainViewModel.getParametersAdvancedSearchVM().observe(getViewLifecycleOwner(), this::getSearchPlaces);
    }

    private void initAdapter() {
        placeAdapter = new PlaceAdapter(new ArrayList<>(), this);
    }

    private void initScrollListener(RecyclerView placesRecyclerView) {
        placesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!isLoading) {
                    LinearLayoutManager linearLayoutManager= (LinearLayoutManager) placesRecyclerView.getLayoutManager();
                    if(linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == placeAdapter.getItemCount() - 1) {
                        isLoading = true;
                        ParametersAdvancedSearch parametersAdvancedSearch = mainViewModel.getParametersAdvancedSearchVM().getValue();
                        if(parametersAdvancedSearch != null)
                            parametersAdvancedSearch.setPage(parametersAdvancedSearch.getPage() + 1);
                        mainViewModel.getParametersAdvancedSearchVM().setValue(parametersAdvancedSearch);
                    }
                }
            }
        });
    }

    private void getSearchPlaces(ParametersAdvancedSearch parametersAdvancedSearch) {
        if(parametersAdvancedSearch.getPage() > 1) placesViewModel.getPlaces(parametersAdvancedSearch).observe(getViewLifecycleOwner(), this::getMorePlaces);
        else placesViewModel.getPlaces(parametersAdvancedSearch).observe(getViewLifecycleOwner(), this::getFirstPlaces);
    }

    private void getFirstPlaces(Resource<List<PlaceVM>> resource) {
        getViewBinding().resultsPlacesFrameLayout.removeAllViews();
        switch(resource.getStatus()) {
            case LOADING:
                LoadingLayoutBinding viewLoadingBinding = LoadingLayoutBinding.inflate(getLayoutInflater());
                getViewBinding().resultsPlacesFrameLayout.addView(viewLoadingBinding.getRoot());
                break;
            case SUCCESS:
                List<PlaceVM> placesVM = resource.getData();
                View placesView;
                if(placesVM.isEmpty()) {
                    MessageLayoutBinding viewEmptyBinding = MessageLayoutBinding.inflate(getLayoutInflater());
                    viewEmptyBinding.noSuggestionsTextView.setText(R.string.empty_places);
                    String message = getString(R.string.number_results_text) + " " + placeAdapter.getItemCount();
                    getViewBinding().totalResultsTextView.setText(message);
                    placesView = viewEmptyBinding.getRoot();
                } else {
                    PlacesLayoutBinding placesLayoutBinding = PlacesLayoutBinding.inflate(getLayoutInflater());
                    placesLayoutBinding.placesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                    placesLayoutBinding.placesRecyclerView.setAdapter(placeAdapter);
                    initScrollListener(placesLayoutBinding.placesRecyclerView);
                    placeAdapter.addPlaces(placesVM);
                    String message = getString(R.string.number_results_text) + " " + placeAdapter.getItemCount();
                    getViewBinding().totalResultsTextView.setText(message);
                    placesView = placesLayoutBinding.getRoot();
                }
                getViewBinding().resultsPlacesFrameLayout.addView(placesView);
                break;
            case WARNING:
                ViewUtils.showGeneralWarningDialog(requireContext(), resource.getWarning());
                break;
            case ERROR:
                ViewUtils.showGeneralWarningDialog(requireContext(), resource.getErrorMessage());
                break;
        }
    }

    private void getMorePlaces(Resource<List<PlaceVM>> resource) {
        switch(resource.getStatus()) {
            case LOADING:
                placeAdapter.addPlace(null);
                break;
            case SUCCESS:
                placeAdapter.removeLastPlace();
                if(!resource.getData().isEmpty()) {
                    placeAdapter.addPlaces(resource.getData());
                    String message = getString(R.string.number_results_text) + " " + placeAdapter.getItemCount();
                    getViewBinding().totalResultsTextView.setText(message);
                } else Toast.makeText(requireContext(), R.string.empty_places, Toast.LENGTH_SHORT).show();
                isLoading = false;
                break;
            case WARNING:
                ViewUtils.showGeneralWarningDialog(requireContext(), resource.getWarning());
                break;
            case ERROR:
                ViewUtils.showGeneralWarningDialog(requireContext(), resource.getErrorMessage());
                break;
        }
    }

}