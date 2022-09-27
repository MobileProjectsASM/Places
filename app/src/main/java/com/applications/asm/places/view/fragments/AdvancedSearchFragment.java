package com.applications.asm.places.view.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.applications.asm.places.R;
import com.applications.asm.places.databinding.FragmentAdvancedSearchBinding;
import com.applications.asm.places.databinding.PriceChipLayoutBinding;
import com.applications.asm.places.model.CategoryVM;
import com.applications.asm.places.model.CoordinatesVM;
import com.applications.asm.places.model.CriterionVM;
import com.applications.asm.places.model.Resource;
import com.applications.asm.places.model.ResourceStatus;
import com.applications.asm.places.view.AdvancedSearchView;
import com.applications.asm.places.view.activities.interfaces.MainViewParent;
import com.applications.asm.places.view.adapters.SortCriteriaAdapter;
import com.applications.asm.places.view.fragments.base.CommonMenuSearchFragment;
import com.applications.asm.places.view.utils.FormValidators;
import com.applications.asm.places.view.utils.Pair;
import com.applications.asm.places.view.utils.ViewUtils;
import com.applications.asm.places.view_model.AdvancedSearchViewModel;
import com.applications.asm.places.view_model.MainViewModel;
import com.google.android.material.chip.Chip;
import com.jakewharton.rxbinding4.widget.RxTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class AdvancedSearchFragment extends CommonMenuSearchFragment<FragmentAdvancedSearchBinding> implements AdvancedSearchView {
    private static final String CURRENT_LOCATION = "current_location";
    private static final String SORT_CRITERIA = "sort_criteria";
    private static final String PRICES = "prices";

    private CompositeDisposable formDisposable;
    private Dialog loadingGetData;
    private MainViewModel mainViewModel;
    private AdvancedSearchViewModel advancedSearchViewModel;
    private Map<Integer, CriterionVM> pricesMap;
    private CoordinatesVM coordinatesSelected;
    private CriterionVM sortCriterionSelected;
    private List<CriterionVM> pricesSelected;
    private List<CategoryVM> categoriesSelected;

    private ActivityResultLauncher<Intent> activityResultLauncher;

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
        MainViewParent mainViewParent = (MainViewParent) context;
        mainViewParent.getActivityComponent().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainViewModel = new ViewModelProvider(requireActivity(), mainViewModelFactory).get(MainViewModel.class);
        advancedSearchViewModel = new ViewModelProvider(this, advancedSearchViewModelFactory).get(AdvancedSearchViewModel.class);
        formDisposable = new CompositeDisposable();
        initViewObservables();
        setListeners();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        formDisposable.clear();
    }

    @Override
    protected FragmentAdvancedSearchBinding bindingInflater(LayoutInflater inflater, ViewGroup container) {
        return FragmentAdvancedSearchBinding.inflate(inflater, container, false);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void callbackLoadData(Resource<Map<String, Object>> resource) {
        switch (resource.getStatus()) {
            case LOADING:
                loadingGetData = ViewUtils.loading(requireContext());
                break;
            case SUCCESS:
                ViewUtils.loaded(loadingGetData);
                Map<String, Object> data = resource.getData();
                CoordinatesVM coordinates = (CoordinatesVM) data.get(CURRENT_LOCATION);
                List<CriterionVM> sortCriteria = (List<CriterionVM>) data.get(SORT_CRITERIA);
                List<CriterionVM> prices = (List<CriterionVM>) data.get(PRICES);
                setCoordinates(coordinates);
                setSortCriteria(sortCriteria);
                setPricesCriteria(prices);
                break;
            case WARNING:
                ViewUtils.loaded(loadingGetData);
                ViewUtils.showGeneralWarningDialog(requireContext(), resource.getWarning());
                break;
            case ERROR:
                ViewUtils.loaded(loadingGetData);
                ViewUtils.showGeneralErrorDialog(requireContext(), resource.getErrorMessage());
                break;
        }
    }

    private void setListeners() {
        getViewBinding().applyFilterButton.setOnClickListener(view -> {
            List<Integer> chipIdsSelected = getViewBinding().chipGroupPrices.getCheckedChipIds();
            pricesSelected = new ArrayList<>();
            for(Integer chipIdSelected : chipIdsSelected)
                pricesSelected.add(pricesMap.get(chipIdSelected));
        });
        getViewBinding().categoriesTextInputLayout.setEndIconOnClickListener(view -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_advancedSearchFragment_to_categoriesFragment);
        });
        AutoCompleteTextView sortCriteriaAutocompleteTextView = (AutoCompleteTextView) getViewBinding().sortByTextInputLayout.getEditText();
        if(sortCriteriaAutocompleteTextView != null) {
            sortCriteriaAutocompleteTextView.setOnItemClickListener((adapterView, view, i, l) -> {
                SortCriteriaAdapter adapter = (SortCriteriaAdapter) adapterView.getAdapter();
                sortCriterionSelected = adapter.getItem(i);
            });
        }
    }

    private void setCoordinates(CoordinatesVM coordinates) {
        coordinatesSelected = coordinates;
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

    private void initViewObservables() {
        EditText radiusEditText = getViewBinding().radiusTextInputLayout.getEditText();
        if(radiusEditText != null) {
            Observable<String> radiusObservable = RxTextView.textChanges(radiusEditText).map(CharSequence::toString);
            Observable<Boolean> observableForm = radiusObservable.map(this::validateForm);
            Disposable disposable = observableForm.subscribe(getViewBinding().applyFilterButton::setEnabled);
            formDisposable.add(disposable);
        }
        //loadData().observe(getViewLifecycleOwner(), this::callbackLoadData);
        mainViewModel.getCategoriesSelected().observe(getViewLifecycleOwner(), this::setCategories);
    }

    private Boolean validateForm(String radius) {
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
        } else isValidRadius = true;
        return isValidRadius;
    }

    private LiveData<Resource<Map<String, Object>>> loadData() {
        AtomicBoolean isFirstEmitted = new AtomicBoolean(false);
        AtomicBoolean isSecondEmitted = new AtomicBoolean(false);
        LiveData<Resource<CoordinatesVM>> liveDataCoordinates = mainViewModel.loadCoordinates(CoordinatesVM.StateVM.SAVED);
        LiveData<Resource<Map<String, Object>>> liveDataCriteria = advancedSearchViewModel.loadData();
        MediatorLiveData<Pair<Resource<CoordinatesVM>, Resource<Map<String, Object>>>> mediatorLiveData = new MediatorLiveData<>();
        mediatorLiveData.addSource(liveDataCoordinates, resource -> {
            isFirstEmitted.set(true);
            if(isFirstEmitted.get() && isSecondEmitted.get())
                mediatorLiveData.setValue(new Pair<>(liveDataCoordinates.getValue(), liveDataCriteria.getValue()));
        });
        mediatorLiveData.addSource(liveDataCriteria, resource -> {
            isSecondEmitted.set(true);
            if(isFirstEmitted.get() && isSecondEmitted.get())
                mediatorLiveData.setValue(new Pair<>(liveDataCoordinates.getValue(), liveDataCriteria.getValue()));
        });
        return Transformations.map(mediatorLiveData, pair -> {
            Resource<Map<String, Object>> dataResource;
            Resource<CoordinatesVM> resourceCoordinates = pair.getFirst();
            Resource<Map<String, Object>> resourceCriteria = pair.getSecond();
            if(resourceCoordinates.getStatus() == ResourceStatus.SUCCESS && resourceCriteria.getStatus() == ResourceStatus.SUCCESS) {
                Map<String, Object> data = new HashMap<>();
                data.put(CURRENT_LOCATION, resourceCoordinates.getData());
                data.put(SORT_CRITERIA, resourceCriteria.getData().get(AdvancedSearchViewModel.SORT_CRITERIA_LIST));
                data.put(PRICES, resourceCriteria.getData().get(AdvancedSearchViewModel.PRICES_LIST));
                dataResource = Resource.success(data);
            } else if(resourceCoordinates.getStatus() == ResourceStatus.SUCCESS) {
                if(resourceCriteria.getStatus() == ResourceStatus.WARNING) dataResource = Resource.warning(resourceCriteria.getWarning());
                else dataResource = Resource.error(resourceCriteria.getErrorMessage());
            } else {
                if(resourceCoordinates.getStatus() == ResourceStatus.WARNING) dataResource = Resource.warning(resourceCoordinates.getWarning());
                else dataResource = Resource.error(resourceCoordinates.getErrorMessage());
            }
            return dataResource;
        });
    }
}