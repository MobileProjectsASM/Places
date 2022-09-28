package com.applications.asm.places.view.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.applications.asm.places.R;
import com.applications.asm.places.databinding.FragmentSearchPlacesBinding;
import com.applications.asm.places.databinding.SuggestedPlacesEmptyLayoutBinding;
import com.applications.asm.places.databinding.SuggestedPlacesLayoutBinding;
import com.applications.asm.places.databinding.SuggestedPlacesLoadingLayoutBinding;
import com.applications.asm.places.model.CoordinatesVM;
import com.applications.asm.places.model.Resource;
import com.applications.asm.places.model.SuggestedPlaceVM;
import com.applications.asm.places.view.SearchPlacesView;
import com.applications.asm.places.view.activities.interfaces.MainViewParent;
import com.applications.asm.places.view.adapters.SuggestedPlaceAdapter;
import com.applications.asm.places.view.events.SuggestedPlaceClickListener;
import com.applications.asm.places.view.fragments.base.BaseFragment;
import com.applications.asm.places.view.fragments.base.CommonMenuSearchFragment;
import com.applications.asm.places.view.utils.ViewUtils;
import com.applications.asm.places.view_model.MainViewModel;
import com.applications.asm.places.view_model.SearchPlacesViewModel;
import com.jakewharton.rxbinding4.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class SearchPlacesFragment extends CommonMenuSearchFragment<FragmentSearchPlacesBinding> implements SearchPlacesView, SuggestedPlaceClickListener {
    private CompositeDisposable formDisposable;
    private Dialog loadingGetCoordinates;
    private MainViewModel mainViewModel;
    private SearchPlacesViewModel searchPlacesViewModel;
    private CoordinatesVM workCoordinates;
    private boolean isRecreatedView;

    @Named("mainVMFactory")
    @Inject
    ViewModelProvider.Factory mainViewModelFactory;

    @Named("searchPlacesVMFactory")
    @Inject
    ViewModelProvider.Factory searchPlacesViewModelFactory;

    public SearchPlacesFragment() {
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
        isRecreatedView = false;
        NavController navController = NavHostFragment.findNavController(this);
        NavBackStackEntry backStackEntry = navController.getCurrentBackStackEntry();
        if(backStackEntry != null)  {
            SavedStateHandle savedStateHandle = backStackEntry.getSavedStateHandle();
            savedStateHandle.getLiveData(CoordinatesFragment.COORDINATES_ESTABLISHED).observe(backStackEntry, value -> {
                Boolean coordinatesEstablished = (Boolean) value;
                if(!coordinatesEstablished) {
                    FragmentActivity activity = getActivity();
                    if(activity != null) activity.finish();
                }
            });
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainViewModel = new ViewModelProvider(requireActivity(), mainViewModelFactory).get(MainViewModel.class);
        searchPlacesViewModel = new ViewModelProvider(this, searchPlacesViewModelFactory).get(SearchPlacesViewModel.class);
        formDisposable = new CompositeDisposable();
        initViewObservables();
        if(!isRecreatedView)
            mainViewModel.loadCoordinates(CoordinatesVM.StateVM.SAVED).observe(getViewLifecycleOwner(), this::callbackCoordinates);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        formDisposable.clear();
        isRecreatedView = true;
    }

    @Override
    protected FragmentSearchPlacesBinding bindingInflater(LayoutInflater inflater, ViewGroup container) {
        return FragmentSearchPlacesBinding.inflate(inflater, container, false);
    }

    @Override
    public void callbackCoordinates(Resource<CoordinatesVM> resource) {
        switch(resource.getStatus()) {
            case LOADING:
                loadingGetCoordinates = ViewUtils.loading(requireContext());
                break;
            case SUCCESS:
                ViewUtils.loaded(loadingGetCoordinates);
                mainViewModel.getWorkCoordinates().setValue(resource.getData());
                break;
            case WARNING:
                ViewUtils.loaded(loadingGetCoordinates);
                ViewUtils.showGeneralWarningDialog(requireContext(), resource.getWarning());
                break;
            case ERROR:
                ViewUtils.loaded(loadingGetCoordinates);
                ViewUtils.showGeneralErrorDialog(requireContext(), resource.getErrorMessage());
                break;
        }
    }

    @Override
    public void callbackSuggestedPlaces(Resource<List<SuggestedPlaceVM>> resource) {
        getViewBinding().suggestedPlacesView.removeAllViews();
        switch(resource.getStatus()) {
            case LOADING:
                SuggestedPlacesLoadingLayoutBinding viewLoadingBinding = SuggestedPlacesLoadingLayoutBinding.inflate(getLayoutInflater());
                getViewBinding().suggestedPlacesView.addView(viewLoadingBinding.getRoot());
                break;
            case SUCCESS:
                List<SuggestedPlaceVM> suggestedPlacesVM = resource.getData();
                View suggestedPlacesView;
                if(suggestedPlacesVM.isEmpty()) {
                    SuggestedPlacesEmptyLayoutBinding viewEmptyBinding = SuggestedPlacesEmptyLayoutBinding.inflate(getLayoutInflater());
                    suggestedPlacesView = viewEmptyBinding.getRoot();
                } else {
                    SuggestedPlacesLayoutBinding suggestedPlacesLayoutBinding = SuggestedPlacesLayoutBinding.inflate(getLayoutInflater());
                    suggestedPlacesLayoutBinding.suggestedPlacesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                    SuggestedPlaceAdapter suggestedPlaceAdapter = new SuggestedPlaceAdapter(suggestedPlacesVM, this);
                    suggestedPlacesLayoutBinding.suggestedPlacesRecyclerView.setAdapter(suggestedPlaceAdapter);
                    suggestedPlacesView = suggestedPlacesLayoutBinding.getRoot();
                }
                getViewBinding().suggestedPlacesView.addView(suggestedPlacesView);
                break;
            case WARNING:
                ViewUtils.showGeneralWarningDialog(requireContext(), resource.getWarning());
                break;
            case ERROR:
                ViewUtils.showGeneralErrorDialog(requireContext(), resource.getErrorMessage());
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.add(Menu.NONE, R.id.advancedSearchOption, Menu.NONE, R.string.advanced_search_option);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.advancedSearchOption) {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_searchPlacesFragment_to_advancedSearchFragment);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuggestedPlaceClickListener(SuggestedPlaceVM suggestedPlaceVM) {

    }

    private void initViewObservables() {
        EditText placeEditText = getViewBinding().searchPlaceTextInputLayout.getEditText();
        if(placeEditText != null) {
            Observable<String> placeObservable = RxTextView.textChanges(placeEditText)
                    .skip(1)
                    .map(CharSequence::toString);
            Disposable disposable = placeObservable.subscribe(text -> {
                if(!text.isEmpty()) searchPlacesViewModel.getSuggestedPlaces(text, workCoordinates).observe(getViewLifecycleOwner(), this::callbackSuggestedPlaces);
                else callbackSuggestedPlaces(Resource.success(new ArrayList<>()));
            });
            formDisposable.add(disposable);
        }
        mainViewModel.getWorkCoordinates().observe(getViewLifecycleOwner(), coordinatesVM -> {
            if(areValidCoordinates(coordinatesVM)) {
                renderView();
                workCoordinates = coordinatesVM;
            } else {
                NavController navController = NavHostFragment.findNavController(this);
                navController.navigate(R.id.coordinatesFragment);
            }
        });
    }

    private void renderView() {
        getViewBinding().searchPlaceTextInputLayout.setVisibility(View.VISIBLE);
        getViewBinding().suggestedPlaceTextView.setVisibility(View.VISIBLE);
        getViewBinding().searchDivider1.setVisibility(View.VISIBLE);
    }

    private boolean areValidCoordinates(CoordinatesVM coordinatesVM) {
        return coordinatesVM.getLatitude() != MainViewModel.INVALID_LONGITUDE && coordinatesVM.getLongitude() != MainViewModel.INVALID_LONGITUDE;
    }
}