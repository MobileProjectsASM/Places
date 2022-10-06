package com.applications.asm.places.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.applications.asm.places.R;
import com.applications.asm.places.databinding.CategoryChipLayoutBinding;
import com.applications.asm.places.databinding.FragmentSearchCategoriesBinding;
import com.applications.asm.places.databinding.LoadingLayoutBinding;
import com.applications.asm.places.databinding.MessageLayoutBinding;
import com.applications.asm.places.databinding.SuggestedCategoriesLayoutBinding;
import com.applications.asm.places.model.CategoryVM;
import com.applications.asm.places.model.CoordinatesVM;
import com.applications.asm.places.model.Resource;
import com.applications.asm.places.view.SearchCategoriesView;
import com.applications.asm.places.view.adapters.SuggestedCategoryAdapter;
import com.applications.asm.places.view.events.CategoryClickListener;
import com.applications.asm.places.view.fragments.base.BaseFragment;
import com.applications.asm.places.view.utils.ViewUtils;
import com.applications.asm.places.view_model.AdvancedSearchViewModel;
import com.applications.asm.places.view_model.MainViewModel;
import com.applications.asm.places.view_model.SearchCategoriesViewModel;
import com.google.android.material.chip.Chip;
import com.jakewharton.rxbinding4.widget.RxTextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class SearchCategoriesFragment extends BaseFragment<FragmentSearchCategoriesBinding> implements SearchCategoriesView, CategoryClickListener {
    private CompositeDisposable formDisposable;
    private AdvancedSearchViewModel advancedSearchViewModel;
    private SearchCategoriesViewModel searchCategoriesViewModel;
    private MainViewModel mainViewModel;
    private CoordinatesVM workCoordinates;
    private Map<String, CategoryVM> categoriesMap;
    private Boolean categoriesAreBeingApplied;

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
        getActivityComponent().inject(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainViewModel = new ViewModelProvider(requireActivity(), mainViewModelFactory).get(MainViewModel.class);
        searchCategoriesViewModel = new ViewModelProvider(this, searchCategoriesViewModelFactory).get(SearchCategoriesViewModel.class);
        formDisposable = new CompositeDisposable();
        categoriesAreBeingApplied = false;
        initViewObservables();
        setListeners();
        categoriesMap = new HashMap<>();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        formDisposable.clear();
    }

    @Override
    protected FragmentSearchCategoriesBinding bindingInflater(LayoutInflater inflater, ViewGroup container) {
        return FragmentSearchCategoriesBinding.inflate(inflater, container, false);
    }

    @Override
    public void callbackCategories(Resource<List<CategoryVM>> resource) {
        getViewBinding().suggestedCategoriesView.removeAllViews();
        switch (resource.getStatus()) {
            case LOADING:
                LoadingLayoutBinding viewLoadingBinding = LoadingLayoutBinding.inflate(getLayoutInflater());
                getViewBinding().suggestedCategoriesView.addView(viewLoadingBinding.getRoot());
                break;
            case SUCCESS:
                List<CategoryVM> categories = resource.getData();
                View suggestedCategoriesView;
                if(categories.isEmpty()) {
                    MessageLayoutBinding viewEmptyBinding = MessageLayoutBinding.inflate(getLayoutInflater());
                    viewEmptyBinding.messageTextView.setText(R.string.there_are_no_categories_label  );
                    suggestedCategoriesView = viewEmptyBinding.getRoot();
                } else {
                    SuggestedCategoryAdapter suggestedCategoryAdapter = new SuggestedCategoryAdapter(categories, this);
                    SuggestedCategoriesLayoutBinding suggestedCategoriesLayoutBinding = SuggestedCategoriesLayoutBinding.inflate(getLayoutInflater());
                    suggestedCategoriesLayoutBinding.suggestedCategoriesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                    suggestedCategoriesLayoutBinding.suggestedCategoriesRecyclerView.setAdapter(suggestedCategoryAdapter);
                    suggestedCategoriesView = suggestedCategoriesLayoutBinding.getRoot();
                }
                getViewBinding().suggestedCategoriesView.addView(suggestedCategoriesView);
                break;
            case WARNING:
                MessageLayoutBinding  warningBinding = MessageLayoutBinding.inflate(getLayoutInflater());
                warningBinding.messageTextView.setText(resource.getWarning());
                getViewBinding().suggestedCategoriesView.addView(warningBinding.getRoot());
                break;
            case ERROR:
                MessageLayoutBinding  errorBinding = MessageLayoutBinding.inflate(getLayoutInflater());
                errorBinding.messageTextView.setText(resource.getErrorMessage());
                getViewBinding().suggestedCategoriesView.addView(errorBinding.getRoot());
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
            chip.setOnCloseIconClickListener(view -> removeChip(view, categoryVM));
            getViewBinding().categoriesChipGroup.addView(chip, 0);
            categoriesMap.put(categoryVM.getId(), categoryVM);
        } else Toast.makeText(requireContext(), R.string.categories_cannot_be_repeated, Toast.LENGTH_SHORT).show();
    }

    private void renderView() {
        getViewBinding().linearLayout.setVisibility(View.VISIBLE);
        getViewBinding().divider3.setVisibility(View.VISIBLE);
        getViewBinding().searchCategoryTextInputLayout.setVisibility(View.VISIBLE);
        getViewBinding().applyCategoriesButton.setVisibility(View.VISIBLE);
    }

    private void callbackWorkingCoordinates(CoordinatesVM coordinatesVM) {
        workCoordinates = coordinatesVM;
        renderView();
    }

    private void callbackCategoriesSelected(List<CategoryVM> categoriesVM) {
        if(!categoriesAreBeingApplied) {
            Collections.reverse(categoriesVM);
            for(CategoryVM categoryVM: categoriesVM) {
                int chipId = View.generateViewId();
                Chip chip = CategoryChipLayoutBinding.inflate(getLayoutInflater()).getRoot();
                chip.setText(categoryVM.getName());
                chip.setId(chipId);
                chip.setCheckable(false);
                chip.setOnCloseIconClickListener(view -> removeChip(view, categoryVM));
                getViewBinding().categoriesChipGroup.addView(chip, 0);
                categoriesMap.put(categoryVM.getId(), categoryVM);
            }
        }
    }

    private void removeChip(View view, CategoryVM categoryVM) {
        getViewBinding().categoriesChipGroup.removeView(view);
        categoriesMap.remove(categoryVM.getId());
    }

    private void initViewObservables() {
        EditText categoryEditText = getViewBinding().searchCategoryTextInputLayout.getEditText();
        if(categoryEditText != null) {
            Observable<String> categoryObservable = RxTextView.textChanges(categoryEditText).skip(1).map(CharSequence::toString);
            Disposable disposable = categoryObservable.subscribe(text -> {
                if(!text.isEmpty()) searchCategoriesViewModel.getCategories(text, "es_MX", workCoordinates).observe(getViewLifecycleOwner(), this::callbackCategories);
                else callbackCategories(Resource.success(new ArrayList<>()));
            });
            formDisposable.add(disposable);
        }
        mainViewModel.getWorkCoordinates().observe(getViewLifecycleOwner(), this::callbackWorkingCoordinates);
        mainViewModel.getCategoriesSelected().observe(getViewLifecycleOwner(), this::callbackCategoriesSelected);
    }

    private void setListeners() {
        getViewBinding().applyCategoriesButton.setOnClickListener(view -> {
            List<CategoryVM> categoriesSelected = new ArrayList<>();
            boolean isSuccessful = categoriesSelected.addAll(categoriesMap.values());
            if(isSuccessful) Collections.reverse(categoriesSelected);
            categoriesAreBeingApplied = true;
            mainViewModel.getCategoriesSelected().setValue(categoriesSelected);
            NavHostFragment.findNavController(this).popBackStack();
        });
    }
}