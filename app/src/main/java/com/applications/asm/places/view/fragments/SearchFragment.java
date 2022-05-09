package com.applications.asm.places.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.applications.asm.places.R;
import com.applications.asm.places.databinding.FragmentSearchBinding;
import com.applications.asm.places.model.CategoriesConstants;
import com.applications.asm.places.view.activities.interfaces.MainViewParent;
import com.applications.asm.places.view.utils.AfterTextChanged;
import com.applications.asm.places.view.utils.ViewUtils;
import com.applications.asm.places.view_model.MainViewModel;
import com.applications.asm.places.view_model.SearchViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

public class SearchFragment extends Fragment {
    private FragmentSearchBinding binding;
    private MainViewParent mainViewParent;
    private final String TAG = "SearchFragment";
    private SearchViewModel searchViewModel;
    private MainViewModel mainViewModel;
    private Boolean doSearch = true;
    private String[] modeView;

    @Named("search_view_model")
    @Inject
    ViewModelProvider.Factory factorySearchViewModel;

    @Named("main_view_model")
    @Inject
    ViewModelProvider.Factory factoryMainViewModel;

    public SearchFragment() {
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
        modeView = new String[] {getString(R.string.text_value_mode_list), getString(R.string.text_value_mode_map)};
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        mainViewParent.getMainComponent().inject(this);
        searchViewModel = new ViewModelProvider(this, factorySearchViewModel).get(SearchViewModel.class);
        mainViewModel = new ViewModelProvider(requireActivity(), factoryMainViewModel).get(MainViewModel.class);
        setObservables();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initInputTextChooseMode();
        setListeners();
    }

    private void initInputTextChooseMode() {
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) binding.showModeTextInputLayout.getEditText();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.place_suggested_item_layout, modeView);
        if(autoCompleteTextView != null) {
            autoCompleteTextView.setAdapter(adapter);
            autoCompleteTextView.setText(modeView[0], false);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void setListeners() {
        binding.setCoordinatesButton.setOnClickListener(this::setCoordinates);
        binding.searchButton.setOnClickListener(this::filterPlaces);
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) binding.placeTextInputLayout.getEditText();
        if(autoCompleteTextView != null) {
            autoCompleteTextView.addTextChangedListener((AfterTextChanged) editable -> searchViewModel.getSuggestedPlaces(editable.toString()));
            autoCompleteTextView.setOnItemClickListener((adapterView, view, i, l) -> doSearch = false);
        }
    }

    private void setObservables() {
        searchViewModel.suggestedPlaces().observe(getViewLifecycleOwner(), namePlaces -> {
            if(doSearch) {
                ArrayAdapter<String> placeAutocompleteAdapter = new ArrayAdapter<>(requireContext(), R.layout.place_suggested_item_layout, namePlaces);
                AutoCompleteTextView autoCompleteSearchPlace = (AutoCompleteTextView) binding.placeTextInputLayout.getEditText();
                if(autoCompleteSearchPlace != null) {
                    autoCompleteSearchPlace.setAdapter(placeAutocompleteAdapter);
                    if(!namePlaces.isEmpty())
                        autoCompleteSearchPlace.showDropDown();
                    else autoCompleteSearchPlace.dismissDropDown();
                }
            } else doSearch = true;
        });
    }

    private List<String> getCategories() {
        List<String> categories = new ArrayList<>();
        if(binding.foodChip.isChecked()) categories.add(CategoriesConstants.FOOD);
        if(binding.activeLifeChip.isChecked()) categories.add(CategoriesConstants.ACTIVE_LIFE);
        if(binding.healthChip.isChecked()) categories.add(CategoriesConstants.HEALTH);
        if(binding.artsEntertainmentChip.isChecked()) categories.add(CategoriesConstants.ARTS_ENTERTAINMENT);
        if(binding.automotiveChip.isChecked()) categories.add(CategoriesConstants.AUTOMOTIVE);
        if(binding.beautySpasChip.isChecked()) categories.add(CategoriesConstants.BEAUTY_SPAS);
        if(binding.educationChip.isChecked()) categories.add(CategoriesConstants.EDUCATION);
        if(binding.nightlifeChip.isChecked()) categories.add(CategoriesConstants.NIGHTLIFE);
        if(binding.petsChip.isChecked()) categories.add(CategoriesConstants.PETS);
        if(binding.publicServicesChip.isChecked()) categories.add(CategoriesConstants.PUBLIC_SERVICES);
        return categories;
    }

    private void setCoordinates(View view) {
        EditText editTextLatitude = binding.latitudeTextInputLayout.getEditText();
        EditText editTextLongitude = binding.longitudeTextInputLayout.getEditText();
        if (editTextLatitude != null && editTextLatitude.getText().toString().compareTo("") != 0) {
            double latitude = Double.parseDouble(editTextLatitude.getText().toString());
            searchViewModel.setLatitude(latitude);
        } else searchViewModel.setLatitude(null);
        if (editTextLongitude != null && editTextLongitude.getText().toString().compareTo("") != 0) {
            double longitude = Double.parseDouble(editTextLongitude.getText().toString());
            searchViewModel.setLongitude(longitude);
        } else searchViewModel.setLongitude(null);
        ViewUtils.showSnackBar(binding.getRoot(), getString(R.string.text_info_coordinates_set));
    }

    private void filterPlaces(View view) {
        List<String> categories = getCategories();
        String place = null;
        Double latitude = null;
        Double longitude = null;
        Integer radius = null;
        EditText editTextPlace = binding.placeTextInputLayout.getEditText();
        EditText editTextLatitude = binding.latitudeTextInputLayout.getEditText();
        EditText editTextLongitude = binding.longitudeTextInputLayout.getEditText();
        EditText editTextRadius = binding.radiusTextInputLayout.getEditText();
        if(editTextPlace != null)
            place = editTextPlace.getText().toString();
        if (editTextLatitude != null && editTextLatitude.getText().toString().compareTo("") != 0)
            latitude = Double.parseDouble(editTextLatitude.getText().toString());
        if (editTextLongitude != null && editTextLongitude.getText().toString().compareTo("") != 0)
            longitude = Double.parseDouble(editTextLongitude.getText().toString());;
        if(editTextRadius != null && editTextRadius.getText().toString().compareTo("") != 0)
            radius = Integer.parseInt(editTextRadius.getText().toString());
        mainViewModel.searchNearPlaces(place, latitude, longitude, radius, categories);

        int action = R.id.action_searchFragment_to_listFragment;
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) binding.showModeTextInputLayout.getEditText();
        if(autoCompleteTextView != null)
            if(autoCompleteTextView.getText().toString().compareTo(modeView[1]) == 0) action = R.id.action_searchFragment_to_placesMapFragment;
        Navigation.findNavController(view).navigate(action);
    }
}