package com.applications.asm.places.view.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.applications.asm.places.databinding.CategoryChipLayoutBinding;
import com.applications.asm.places.databinding.FragmentSearchCategoriesBinding;
import com.applications.asm.places.databinding.SuggestedCategoriesLayoutBinding;
import com.applications.asm.places.databinding.SuggestedPlacesEmptyLayoutBinding;
import com.applications.asm.places.databinding.SuggestedPlacesLoadingLayoutBinding;
import com.applications.asm.places.model.CategoryVM;
import com.applications.asm.places.model.CoordinatesVM;
import com.applications.asm.places.model.Resource;
import com.applications.asm.places.view.SearchCategoriesView;
import com.applications.asm.places.view.activities.interfaces.MainViewParent;
import com.applications.asm.places.view.adapters.SuggestedCategoryAdapter;
import com.applications.asm.places.view.events.CategoryClickListener;
import com.applications.asm.places.view.utils.ViewUtils;
import com.applications.asm.places.view_model.MainViewModel;
import com.applications.asm.places.view_model.SearchCategoriesViewModel;
import com.applications.asm.places.view_model.factories.MainVMFactory;
import com.applications.asm.places.view_model.factories.SearchCategoriesVMFactory;
import com.google.android.material.chip.Chip;
import com.jakewharton.rxbinding4.widget.RxTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class SearchCategoriesFragment extends Fragment implements SearchCategoriesView, CategoryClickListener {
    private FragmentSearchCategoriesBinding binding;
    private CompositeDisposable formDisposable;
    private Dialog loadingCoordinates;
    private MainViewModel mainViewModel;
    private SearchCategoriesViewModel searchCategoriesViewModel;
    private CoordinatesVM currentCoordinatesVM;
    private Map<String, CategoryVM> categoriesMap;

    @Named("mainVMFactory")
    @Inject
    ViewModelProvider.Factory mainViewModelFactory;

    @Named("searchCategoriesVMFactory")
    @Inject
    ViewModelProvider.Factory searchCategoriesViewModelFactory;

    public SearchCategoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainViewParent mainViewParent = (MainViewParent) context;
        mainViewParent.getActivityComponent().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchCategoriesBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainViewModel = new ViewModelProvider(requireActivity(), mainViewModelFactory).get(MainViewModel.class);
        searchCategoriesViewModel = new ViewModelProvider(this, searchCategoriesViewModelFactory).get(SearchCategoriesViewModel.class);
        formDisposable = new CompositeDisposable();
        initViewObservables();
        setListeners();
        categoriesMap = new HashMap<>();
        mainViewModel.loadCoordinates(CoordinatesVM.StateVM.SAVED).observe(getViewLifecycleOwner(), this::callbackCoordinates);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        formDisposable.clear();
    }

    @Override
    public void callbackCoordinates(Resource<CoordinatesVM> resource) {
        switch(resource.getStatus()) {
            case LOADING:
                loadingCoordinates = ViewUtils.loading(requireContext());
                break;
            case SUCCESS:
                ViewUtils.loaded(loadingCoordinates);
                renderView();
                currentCoordinatesVM = resource.getData();
                break;
            case WARNING:
                ViewUtils.loaded(loadingCoordinates);
                ViewUtils.showGeneralWarningDialog(requireContext(), resource.getWarning());
                break;
            case ERROR:
                ViewUtils.loaded(loadingCoordinates);
                ViewUtils.showGeneralErrorDialog(requireContext(), resource.getErrorMessage());
                break;
        }
    }

    @Override
    public void callbackCategories(Resource<List<CategoryVM>> resource) {
        binding.suggestedCategoriesView.removeAllViews();
        switch (resource.getStatus()) {
            case LOADING:
                SuggestedPlacesLoadingLayoutBinding viewLoadingBinding = SuggestedPlacesLoadingLayoutBinding.inflate(getLayoutInflater());
                binding.suggestedCategoriesView.addView(viewLoadingBinding.getRoot());
                break;
            case SUCCESS:
                List<CategoryVM> categories = resource.getData();
                View suggestedCategoriesView;
                if(categories.isEmpty()) {
                    SuggestedPlacesEmptyLayoutBinding viewEmptyBinding = SuggestedPlacesEmptyLayoutBinding.inflate(getLayoutInflater());
                    suggestedCategoriesView = viewEmptyBinding.getRoot();
                } else {
                    SuggestedCategoryAdapter suggestedCategoryAdapter = new SuggestedCategoryAdapter(categories, this);
                    SuggestedCategoriesLayoutBinding suggestedCategoriesLayoutBinding = SuggestedCategoriesLayoutBinding.inflate(getLayoutInflater());
                    suggestedCategoriesLayoutBinding.suggestedCategoriesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                    suggestedCategoriesLayoutBinding.suggestedCategoriesRecyclerView.setAdapter(suggestedCategoryAdapter);
                    suggestedCategoriesView = suggestedCategoriesLayoutBinding.getRoot();
                }
                binding.suggestedCategoriesView.addView(suggestedCategoriesView);
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
    public void onCategoryClickListener(CategoryVM categoryVM) {
        if(categoriesMap.get(categoryVM.getId()) == null) {
            int chipId = View.generateViewId();
            Chip chip = CategoryChipLayoutBinding.inflate(getLayoutInflater()).getRoot();
            chip.setText(categoryVM.getName());
            chip.setId(chipId);
            chip.setCheckable(false);
            chip.setOnCloseIconClickListener(view -> {
                binding.categoriesChipGroup.removeView(view);
                categoriesMap.remove(categoryVM.getId());
            });
            binding.categoriesChipGroup.addView(chip, 0);
            categoriesMap.put(categoryVM.getId(), categoryVM);
        } else {
            Toast.makeText(requireContext(), "No se puden repetir elementos", Toast.LENGTH_SHORT).show();
        }
    }

    private void initViewObservables() {
        EditText categoryEditText = binding.searchCategoryTextInputLayout.getEditText();
        if(categoryEditText != null) {
            Observable<String> categoryObservable = RxTextView.textChanges(categoryEditText).skip(1).map(CharSequence::toString);
            Disposable disposable = categoryObservable.subscribe(text -> searchCategoriesViewModel.getCategories(text, "es_MX", currentCoordinatesVM).observe(getViewLifecycleOwner(), this::callbackCategories));
            formDisposable.add(disposable);
        }
    }

    private void renderView() {
        binding.linearLayout.setVisibility(View.VISIBLE);
        binding.divider3.setVisibility(View.VISIBLE);
        binding.searchCategoryTextInputLayout.setVisibility(View.VISIBLE);
        binding.applyCategoriesButton.setVisibility(View.VISIBLE);
    }

    private void setListeners() {
        binding.applyCategoriesButton.setOnClickListener(view -> {
            List<CategoryVM> categoriesSelected = new ArrayList<>();
            StringBuilder categoriesBuilder = new StringBuilder();
            int countCategories = 0;
            for(CategoryVM categoryVM : categoriesMap.values()) {
                if(countCategories < 1) categoriesBuilder.append(categoryVM.getName());
                else categoriesBuilder.append(", ").append(categoryVM.getName());
                categoriesSelected.add(categoryVM);
                countCategories++;
            }
            mainViewModel.getCategoriesSelected().setValue(categoriesSelected);
        });
    }
}