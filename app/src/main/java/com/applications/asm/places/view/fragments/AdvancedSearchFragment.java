package com.applications.asm.places.view.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.applications.asm.places.R;
import com.applications.asm.places.databinding.FragmentAdvancedSearchBinding;
import com.applications.asm.places.databinding.PriceChipLayoutBinding;
import com.applications.asm.places.model.CategoryVM;
import com.applications.asm.places.model.CoordinatesVM;
import com.applications.asm.places.model.CriterionVM;
import com.applications.asm.places.model.ParametersAdvancedSearch;
import com.applications.asm.places.model.Resource;
import com.applications.asm.places.view.adapters.SortCriteriaAdapter;
import com.applications.asm.places.view.fragments.base.CommonMenuSearchFragment;
import com.applications.asm.places.view.utils.FormValidators;
import com.applications.asm.places.view.utils.ViewUtils;
import com.applications.asm.places.view_model.AdvancedSearchViewModel;
import com.applications.asm.places.view_model.MainViewModel;
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

public class AdvancedSearchFragment extends CommonMenuSearchFragment<FragmentAdvancedSearchBinding> {
    private CompositeDisposable formDisposable;
    private Dialog loadingGetSortAndPrices;
    private MainViewModel mainViewModel;
    private AdvancedSearchViewModel advancedSearchViewModel;
    private Map<Integer, CriterionVM> pricesMap;
    private CoordinatesVM workCoordinates;
    private CriterionVM sortCriterionSelected;
    private List<CategoryVM> categoriesSelected;
    private Boolean isRecreatedView;
    private Boolean placesOpen;

    @Named("mainVMFactory")
    @Inject
    ViewModelProvider.Factory mainViewModelFactory;

    @Named("advancedSearchVMFactory")
    @Inject
    ViewModelProvider.Factory advancedSearchViewModelFactory;

    public AdvancedSearchFragment() {
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
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainViewModel = new ViewModelProvider(requireActivity(), mainViewModelFactory).get(MainViewModel.class);
        advancedSearchViewModel = new ViewModelProvider(this, advancedSearchViewModelFactory).get(AdvancedSearchViewModel.class);
        formDisposable = new CompositeDisposable();
        categoriesSelected = new ArrayList<>();
        placesOpen = false;
        initViewObservables();
        setListeners();
        if(!isRecreatedView)
            advancedSearchViewModel.loadSortAndPrices().observe(getViewLifecycleOwner(), this::callbackLoadData);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        formDisposable.clear();
        isRecreatedView = true;
    }

    @Override
    protected FragmentAdvancedSearchBinding bindingInflater(LayoutInflater inflater, ViewGroup container) {
        return FragmentAdvancedSearchBinding.inflate(inflater, container, false);
    }

    public void callbackLoadData(Resource<Map<String, Object>> resource) {
        switch (resource.getStatus()) {
            case LOADING:
                loadingGetSortAndPrices = ViewUtils.loading(requireContext());
                break;
            case SUCCESS:
                ViewUtils.loaded(loadingGetSortAndPrices);
                advancedSearchViewModel.getSortAndPricesVM().setValue(resource.getData());
                break;
            case WARNING:
                ViewUtils.loaded(loadingGetSortAndPrices);
                ViewUtils.showGeneralWarningDialog(requireContext(), resource.getWarning());
                break;
            case ERROR:
                ViewUtils.loaded(loadingGetSortAndPrices);
                ViewUtils.showGeneralErrorDialog(requireContext(), resource.getErrorMessage());
                break;
        }
    }

    private void setListeners() {
        getViewBinding().applyFilterButton.setOnClickListener(view -> applyFilter());
        getViewBinding().categoriesTextInputLayout.setEndIconOnClickListener(view -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_advancedSearchFragment_to_categoriesFragment);
        });
        getViewBinding().statePlaceButtonToggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            int idCheckedButton = group.getCheckedButtonId();
            if(idCheckedButton != View.NO_ID) placesOpen = idCheckedButton == R.id.button_open;
            else placesOpen = null;
        });
        AutoCompleteTextView sortCriteriaAutocompleteTextView = (AutoCompleteTextView) getViewBinding().sortByTextInputLayout.getEditText();
        if(sortCriteriaAutocompleteTextView != null) {
            sortCriteriaAutocompleteTextView.setOnItemClickListener((adapterView, view, i, l) -> {
                SortCriteriaAdapter adapter = (SortCriteriaAdapter) adapterView.getAdapter();
                sortCriterionSelected = adapter.getItem(i);
            });
        }
    }

    private void setSortCriteria(List<CriterionVM> sortCriteria) {
        AutoCompleteTextView sortCriteriaAutoCompleteTextView = (AutoCompleteTextView) getViewBinding().sortByTextInputLayout.getEditText();
        if(sortCriteriaAutoCompleteTextView != null) {
            SortCriteriaAdapter sortCriteriaAdapter = new SortCriteriaAdapter(requireContext(), sortCriteria);
            sortCriteriaAutoCompleteTextView.setAdapter(sortCriteriaAdapter);
            sortCriteriaAutoCompleteTextView.setText(sortCriteria.get(0).getName(), false);
            sortCriterionSelected = sortCriteria.get(0);
        }
    }

    private void setPricesCriteria(List<CriterionVM> prices) {
        pricesMap = new HashMap<>();
        for(CriterionVM criterionVM : prices) {
            int chipId = View.generateViewId();
            pricesMap.put(chipId, criterionVM);
            Chip chip = PriceChipLayoutBinding.inflate(getLayoutInflater()).getRoot();
            chip.setText(criterionVM.getName());
            chip.setId(chipId);
            getViewBinding().chipGroupPrices.addView(chip);
        }
    }

    private void setCategories(List<CategoryVM> categoriesVM) {
        categoriesSelected = categoriesVM;
        EditText categoriesEditText = getViewBinding().categoriesTextInputLayout.getEditText();
        if(categoriesEditText != null) {
            StringBuilder categories = new StringBuilder();
            for(int i = 0; i < categoriesVM.size(); i++) {
                if(i < 1) categories.append(categoriesVM.get(0).getName());
                else categories.append(", ").append(categoriesVM.get(i).getName());
            }
            categoriesEditText.setText(categories.toString());
        }
    }

    private void setWorkCoordinates(CoordinatesVM coordinatesVM) {
        workCoordinates = coordinatesVM;
    }

    @SuppressWarnings("unchecked")
    private void setSortAndPrices(Map<String, Object> sortAndPrices) {
        List<CriterionVM> sortCriteria = (List<CriterionVM>) sortAndPrices.get(AdvancedSearchViewModel.SORT_CRITERIA_LIST);
        List<CriterionVM> prices = (List<CriterionVM>) sortAndPrices.get(AdvancedSearchViewModel.PRICES_LIST);
        setSortCriteria(sortCriteria);
        setPricesCriteria(prices);
    }

    private void initViewObservables() {
        EditText radiusEditText = getViewBinding().radiusTextInputLayout.getEditText();
        if(radiusEditText != null) {
            Observable<String> radiusObservable = RxTextView.textChanges(radiusEditText).map(CharSequence::toString);
            Observable<Boolean> observableForm = radiusObservable.map(this::validateRadiusField);
            Disposable disposable = observableForm.subscribe(getViewBinding().applyFilterButton::setEnabled);
            formDisposable.add(disposable);
        }
        mainViewModel.getWorkCoordinates().observe(getViewLifecycleOwner(), this::setWorkCoordinates);
        mainViewModel.getCategoriesSelected().observe(getViewLifecycleOwner(), this::setCategories);
        advancedSearchViewModel.getSortAndPricesVM().observe(getViewLifecycleOwner(), this::setSortAndPrices);
    }

    private Boolean validateRadiusField(String radius) {
        boolean isValidRadius;
        if(!radius.isEmpty()) {
            if(!FormValidators.isNaturalNumber(radius)) {
                getViewBinding().radiusTextInputLayout.setError(getText(R.string.natural_invalid_error_message));
                isValidRadius = false;
            } else if(!FormValidators.isRadiusInTheRange(Integer.parseInt(radius), 40000)) {
                getViewBinding().radiusTextInputLayout.setError(getText(R.string.radius_out_of_range_error_message));
                isValidRadius = false;
            } else {
                getViewBinding().radiusTextInputLayout.setError(null);
                isValidRadius = true;
            }
        } else {
            getViewBinding().radiusTextInputLayout.setError(null);
            isValidRadius = true;
        }
        return isValidRadius;
    }

    private void applyFilter() {
        String placeToSearch = "";
        EditText editTextPlace = getViewBinding().placeTextInputLayout.getEditText();
        if(editTextPlace != null) placeToSearch = editTextPlace.getText().toString();

        int radius = 0;
        EditText editTextRadius = getViewBinding().radiusTextInputLayout.getEditText();
        if(editTextRadius != null && !editTextRadius.getText().toString().isEmpty()) radius = Integer.parseInt(editTextRadius.getText().toString());

        List<Integer> chipIdsSelected = getViewBinding().chipGroupPrices.getCheckedChipIds();
        List<CriterionVM> pricesSelected = new ArrayList<>();
        for(Integer chipIdSelected : chipIdsSelected)
            pricesSelected.add(pricesMap.get(chipIdSelected));

        mainViewModel.getParametersAdvancedSearchVM().setValue(new ParametersAdvancedSearch(placeToSearch, workCoordinates, radius, categoriesSelected, sortCriterionSelected, pricesSelected, placesOpen, 1));
        NavHostFragment.findNavController(this).navigate(R.id.action_advancedSearchFragment_to_placesFragment);
    }
}