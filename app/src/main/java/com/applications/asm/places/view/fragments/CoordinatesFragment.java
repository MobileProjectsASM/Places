package com.applications.asm.places.view.fragments;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.fragment.NavHostFragment;

import com.applications.asm.places.R;
import com.applications.asm.places.databinding.AlertDialogLayoutBinding;
import com.applications.asm.places.databinding.FragmentCoordinatesBinding;
import com.applications.asm.places.model.CoordinatesVM;
import com.applications.asm.places.model.Resource;
import com.applications.asm.places.view.fragments.base.BaseFragment;
import com.applications.asm.places.view.utils.FormValidators;
import com.applications.asm.places.view.utils.ViewUtils;
import com.applications.asm.places.view_model.CoordinatesViewModel;
import com.applications.asm.places.view_model.MainViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.jakewharton.rxbinding4.widget.RxTextView;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class CoordinatesFragment extends BaseFragment<FragmentCoordinatesBinding> {
    public static String COORDINATES_ESTABLISHED = "COORDINATES_ESTABLISHED";

    private CompositeDisposable formDisposable;
    private ActivityResultLauncher<String> requestLocationLauncher;
    private Dialog loadingCoordinatesSave;
    private Dialog loadingGetCoordinates;
    private CoordinatesViewModel coordinatesViewModel;
    private MainViewModel mainViewModel;
    private SavedStateHandle savedStateHandle;
    private int countChangesLat, countChangesLon;

    @Named("mainVMFactory")
    @Inject
    ViewModelProvider.Factory mainViewModelFactory;

    @Named("coordinatesVMFactory")
    @Inject
    ViewModelProvider.Factory coordinatesViewModelFactory;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        getActivityComponent().inject(this);
    }

    public CoordinatesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainViewModel = new ViewModelProvider(requireActivity(), mainViewModelFactory).get(MainViewModel.class);
        coordinatesViewModel = new ViewModelProvider(this, coordinatesViewModelFactory).get(CoordinatesViewModel.class);
        formDisposable = new CompositeDisposable();
        countChangesLat = countChangesLon = 0;
        NavBackStackEntry backStackEntry = NavHostFragment.findNavController(this).getPreviousBackStackEntry();
        if(backStackEntry != null) savedStateHandle = backStackEntry.getSavedStateHandle();
        initLaunchers();
        initViewObservables();
        setListeners();
        mainViewModel.loadCoordinates(CoordinatesVM.StateVM.SAVED).observe(getViewLifecycleOwner(), this::callbackCoordinates);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        formDisposable.clear();
    }

    @Override
    protected FragmentCoordinatesBinding bindingInflater(LayoutInflater inflater, ViewGroup container) {
        return FragmentCoordinatesBinding.inflate(inflater, container, false);
    }

    private void callbackCoordinates(Resource<CoordinatesVM> resource) {
        switch(resource.getStatus()) {
            case LOADING:
                loadingGetCoordinates = ViewUtils.loading(requireContext());
                break;
            case SUCCESS:
                ViewUtils.loaded(loadingGetCoordinates);
                CoordinatesVM location = resource.getData();
                if(location.getLatitude() != MainViewModel.INVALID_LATITUDE && location.getLongitude() != MainViewModel.INVALID_LONGITUDE) {
                    setLatitudeText(String.valueOf(location.getLatitude()));
                    setLongitudeText(String.valueOf(location.getLongitude()));
                    savedStateHandle.set(COORDINATES_ESTABLISHED, true);
                } else {
                    if(location.getLatitude() == MainViewModel.INVALID_LATITUDE) setLatitudeText("");
                    if(location.getLongitude() == MainViewModel.INVALID_LONGITUDE) setLongitudeText("");
                    savedStateHandle.set(COORDINATES_ESTABLISHED, false);
                }
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

    private void callbackCoordinatesSaved(Resource<Boolean> resource) {
        switch(resource.getStatus()) {
            case LOADING:
                loadingCoordinatesSave = ViewUtils.loading(requireContext());
                break;
            case SUCCESS:
                ViewUtils.loaded(loadingCoordinatesSave);
                showDialogLocationSaved();
                break;
            case WARNING:
                ViewUtils.loaded(loadingCoordinatesSave);
                ViewUtils.showGeneralWarningDialog(requireContext(), resource.getWarning());
                break;
            case ERROR:
                ViewUtils.loaded(loadingCoordinatesSave);
                ViewUtils.showGeneralErrorDialog(requireContext(), resource.getErrorMessage());
                break;
        }
    }

    private void setListeners() {
        getViewBinding().setCoordinatesButton.setOnClickListener(view -> {
            Double latitude = Double.parseDouble(getLatitudeText());
            Double longitude = Double.parseDouble(getLongitudeText());
            coordinatesViewModel.saveCoordinates(new CoordinatesVM(latitude, longitude)).observe(getViewLifecycleOwner(), this::callbackCoordinatesSaved);
        });
        getViewBinding().locationButton.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED)
                mainViewModel.loadCoordinates(CoordinatesVM.StateVM.CURRENT).observe(getViewLifecycleOwner(), this::callbackCoordinates);
            else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION))
                    ViewUtils.showRequestPermissionRationaleDialog(requireContext(), getString(R.string.location_permission_rational_message), this::requestPermissionLocation);
                else
                    requestPermissionLocation();
            }
        });
    }

    private void initViewObservables() {
        EditText latitudeEditText = getViewBinding().latitudeTextInputLayout.getEditText();
        EditText longitudeEditText = getViewBinding().longitudeTextInputLayout.getEditText();
        if (latitudeEditText != null && longitudeEditText != null) {
            Observable<String> latitudeObservable = RxTextView.textChanges(latitudeEditText)
                    .map(charSequence -> {
                        String lat = charSequence.toString();
                        if(!lat.isEmpty()) countChangesLat++;
                        return lat;
                    }).skip(1);
            Observable<String> longitudeObservable = RxTextView.textChanges(longitudeEditText)
                    .map(charSequence -> {
                        String lon = charSequence.toString();
                        if(!lon.isEmpty()) countChangesLon++;
                        return lon;
                    }).skip(1);
            Observable<Boolean> observableForm = Observable.combineLatest(latitudeObservable, longitudeObservable, (lat, lon) -> {
                boolean isValidLatitude, isValidLongitude;
                isValidLatitude = isValidLongitude = false;
                if(countChangesLat > 0) isValidLatitude = validateLatitudeField(lat);
                if(countChangesLon > 0) isValidLongitude = validateLongitudeField(lon);
                return isValidLatitude && isValidLongitude;
            });
            Disposable disposable = observableForm.subscribe(getViewBinding().setCoordinatesButton::setEnabled);
            formDisposable.add(disposable);
        }
    }

    private void initLaunchers() {
        requestLocationLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if(isGranted)
                mainViewModel.loadCoordinates(CoordinatesVM.StateVM.CURRENT);
        });
    }

    private void requestPermissionLocation() {
        requestLocationLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    private boolean validateLatitudeField(String latitude) {
        boolean isValidLatitude;
        if(latitude.isEmpty()) {
            getViewBinding().latitudeTextInputLayout.setError(getString(R.string.text_empty_error_message));
            isValidLatitude = false;
        } else if(!FormValidators.isDecimalNumber(latitude)) {
            getViewBinding().latitudeTextInputLayout.setError(getString(R.string.decimal_invalid_error_message));
            isValidLatitude = false;
        } else if(!FormValidators.isLatitudeInTheRange(Double.parseDouble(latitude))) {
            getViewBinding().latitudeTextInputLayout.setError(getString(R.string.latitude_out_of_range_error_message));
            isValidLatitude = false;
        } else {
            getViewBinding().latitudeTextInputLayout.setError(null);
            isValidLatitude = true;
        }
        return isValidLatitude;
    }

    private boolean validateLongitudeField(String longitude) {
        boolean isValidLongitude;
        if(longitude.isEmpty()) {
            getViewBinding().longitudeTextInputLayout.setError(getString(R.string.text_empty_error_message));
            isValidLongitude = false;
        } else if(!FormValidators.isDecimalNumber(longitude)) {
            getViewBinding().longitudeTextInputLayout.setError(getString(R.string.decimal_invalid_error_message));
            isValidLongitude = false;
        } else if(!FormValidators.isLongitudeInTheRange(Double.parseDouble(longitude))) {
            getViewBinding().longitudeTextInputLayout.setError(getString(R.string.longitude_out_of_range_error_message));
            isValidLongitude = false;
        } else {
            getViewBinding().longitudeTextInputLayout.setError(null);
            isValidLongitude = true;
        }

        return isValidLongitude;
    }

    private void showDialogLocationSaved() {
        AlertDialogLayoutBinding alertDialogLayoutBinding = AlertDialogLayoutBinding.inflate(getLayoutInflater(), null, false);
        Picasso.get().load(R.drawable.success).fit().centerCrop().into(alertDialogLayoutBinding.iconImageView);
        alertDialogLayoutBinding.messageTextView.setText(getString(R.string.location_saved_message_dialog));
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.info_title_dialog)
                .setView(alertDialogLayoutBinding.getRoot())
                .setPositiveButton(R.string.find_searches_dialog_positive_button, (dialogInterface, i) -> dialogInterface.dismiss())
                .setOnDismissListener(dialogInterface -> {
                    Double latitude = Double.parseDouble(getLatitudeText());
                    Double longitude = Double.parseDouble(getLongitudeText());
                    mainViewModel.getWorkCoordinates().setValue(new CoordinatesVM(latitude, longitude));
                    savedStateHandle.set(COORDINATES_ESTABLISHED, true);
                    NavHostFragment.findNavController(this).popBackStack();
                })
                .show();
    }

    private String getLatitudeText() {
        EditText editTextLatitude = getViewBinding().latitudeTextInputLayout.getEditText();
        if(editTextLatitude != null)
            return editTextLatitude.getText().toString();
        return "";
    }

    private String getLongitudeText() {
        EditText editTextLongitude = getViewBinding().longitudeTextInputLayout.getEditText();
        if(editTextLongitude != null)
            return editTextLongitude.getText().toString();
        return "";
    }

    private void setLatitudeText(String latitude) {
        EditText editTextLatitude = getViewBinding().latitudeTextInputLayout.getEditText();
        if(editTextLatitude != null) editTextLatitude.setText(latitude);
    }

    private void setLongitudeText(String longitude) {
        EditText editTextLongitude = getViewBinding().longitudeTextInputLayout.getEditText();
        if(editTextLongitude != null) editTextLongitude.setText(longitude);
    }
}