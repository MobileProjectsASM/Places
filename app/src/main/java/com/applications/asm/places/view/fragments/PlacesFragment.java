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
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applications.asm.places.R;
import com.applications.asm.places.databinding.FragmentPlacesBinding;
import com.applications.asm.places.databinding.LoadingItemLayoutBinding;
import com.applications.asm.places.databinding.LoadingLayoutBinding;
import com.applications.asm.places.databinding.MessageLayoutBinding;
import com.applications.asm.places.databinding.PlacesLayoutBinding;
import com.applications.asm.places.model.FoundPlacesVM;
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
    private ParametersAdvancedSearch parametersAdvancedSearch;
    private boolean isLoading;
    private boolean isRecreatedView;
    private int maxPage;

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
        initAdapter();
        isRecreatedView = false;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainViewModel = new ViewModelProvider(requireActivity(), mainViewModelFactory).get(MainViewModel.class);
        placesViewModel = new ViewModelProvider(this, placesViewModelFactory).get(PlacesViewModel.class);
        isLoading = false;
        initViewObservables();
        setListeners();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isRecreatedView = true;
    }

    @Override
    protected FragmentPlacesBinding bindingInflater(LayoutInflater inflater, ViewGroup container) {
        return FragmentPlacesBinding.inflate(inflater, container, false);
    }

    @Override
    public void onPlaceClickListener(PlaceVM placeVM) {
        Bundle bundle = new Bundle();
        bundle.putString(PLACE_ID_KEY, placeVM.getId());
        NavHostFragment.findNavController(this).navigate(R.id.action_global_placeDetailsFragment, bundle);
    }

    private void initViewObservables() {
        if(!isRecreatedView) mainViewModel.getParametersAdvancedSearchVM().observe(getViewLifecycleOwner(), this::getParametersSearch);
        else renderViewPlaces();
    }

    private void getParametersSearch(ParametersAdvancedSearch parametersAdvancedSearch) {
        this.parametersAdvancedSearch = parametersAdvancedSearch;
        placesViewModel.getPlaces(parametersAdvancedSearch).observe(getViewLifecycleOwner(), this::getFirstPlaces);
    }

    private void initAdapter() {
        placeAdapter = new PlaceAdapter(new ArrayList<>(), this);
    }

    private void setListeners() {
        getViewBinding().changeViewFloatingActionButton.setOnClickListener(view -> {
            mainViewModel.getPlacesVM().setValue(placeAdapter.getPlacesList());
            NavHostFragment.findNavController(this).navigate(R.id.action_placesFragment_to_mapPlacesFragment);
        });
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
                        if(maxPage >= parametersAdvancedSearch.getPage() + 1) {
                            isLoading = true;
                            parametersAdvancedSearch.setPage(parametersAdvancedSearch.getPage() + 1);
                            placesViewModel.getPlaces(parametersAdvancedSearch).observe(getViewLifecycleOwner(), PlacesFragment.this::getMorePlaces);
                        }
                    }
                }
            }
        });
    }

    private void getFirstPlaces(Resource<FoundPlacesVM> resource) {
     getViewBinding().resultsPlacesFrameLayout.removeAllViews();
        switch(resource.getStatus()) {
            case LOADING:
                LoadingLayoutBinding viewLoadingBinding = LoadingLayoutBinding.inflate(getLayoutInflater());
                getViewBinding().resultsPlacesFrameLayout.addView(viewLoadingBinding.getRoot());
                break;
            case SUCCESS:
                FoundPlacesVM foundPlacesVM = resource.getData();
                View placesView;
                if(foundPlacesVM.getPlaces().isEmpty()) {
                    MessageLayoutBinding viewEmptyBinding = MessageLayoutBinding.inflate(getLayoutInflater());
                    viewEmptyBinding.messageTextView.setText(R.string.empty_results);
                    placesView = viewEmptyBinding.getRoot();
                } else {
                    PlacesLayoutBinding placesLayoutBinding = PlacesLayoutBinding.inflate(getLayoutInflater());
                    placesLayoutBinding.placesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                    placesLayoutBinding.placesRecyclerView.setAdapter(placeAdapter);
                    initScrollListener(placesLayoutBinding.placesRecyclerView);
                    placeAdapter.addPlaces(foundPlacesVM.getPlaces());
                    placesView = placesLayoutBinding.getRoot();
                }
                String results = getString(R.string.number_results_text) + " " + placeAdapter.getItemCount();
                String pages = getString(R.string.page) + " " + parametersAdvancedSearch.getPage() + " " + getString(R.string.of) + " " + foundPlacesVM.getPages();
                maxPage = foundPlacesVM.getPages();
                getViewBinding().totalResultsTextView.setText(results);
                getViewBinding().totalPagesTextView.setText(pages);
                getViewBinding().resultsPlacesFrameLayout.addView(placesView);
                break;
            case WARNING:
                MessageLayoutBinding  warningBinding = MessageLayoutBinding.inflate(getLayoutInflater());
                warningBinding.messageTextView.setText(resource.getWarning());
                getViewBinding().resultsPlacesFrameLayout.addView(warningBinding.getRoot());
                break;
            case ERROR:
                MessageLayoutBinding  errorBinding = MessageLayoutBinding.inflate(getLayoutInflater());
                errorBinding.messageTextView.setText(resource.getErrorMessage());
                getViewBinding().resultsPlacesFrameLayout.addView(errorBinding.getRoot());
                break;
        }
    }

    private void getMorePlaces(Resource<FoundPlacesVM> resource) {
        switch(resource.getStatus()) {
            case LOADING:
                placeAdapter.addPlace(null);
                break;
            case SUCCESS:
                FoundPlacesVM foundPlacesVM = resource.getData();
                placeAdapter.removeLastPlace();
                if(!foundPlacesVM.getPlaces().isEmpty()) {
                    placeAdapter.addPlaces(foundPlacesVM.getPlaces());
                    String results = getString(R.string.number_results_text) + " " + placeAdapter.getItemCount();
                    String pages = getString(R.string.page) + " " + parametersAdvancedSearch.getPage() + " " + getString(R.string.of) + " " + foundPlacesVM.getPages();
                    getViewBinding().totalResultsTextView.setText(results);
                    getViewBinding().totalPagesTextView.setText(pages);
                } else Toast.makeText(requireContext(), R.string.no_more_results, Toast.LENGTH_SHORT).show();
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

    private void renderViewPlaces() {
        PlacesLayoutBinding placesLayoutBinding = PlacesLayoutBinding.inflate(getLayoutInflater());
        placesLayoutBinding.placesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        placesLayoutBinding.placesRecyclerView.setAdapter(placeAdapter);
        initScrollListener(placesLayoutBinding.placesRecyclerView);
        getViewBinding().resultsPlacesFrameLayout.addView(placesLayoutBinding.getRoot());
        String message = getString(R.string.number_results_text) + " " + placeAdapter.getItemCount();
        String pages = getString(R.string.page) + " " + parametersAdvancedSearch.getPage() + " " + getString(R.string.of) + " " + maxPage;
        getViewBinding().totalResultsTextView.setText(message);
        getViewBinding().totalPagesTextView.setText(pages);
    }
}