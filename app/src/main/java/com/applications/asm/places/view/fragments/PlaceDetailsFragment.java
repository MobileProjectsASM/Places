package com.applications.asm.places.view.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.applications.asm.domain.entities.Hours;
import com.applications.asm.places.R;
import com.applications.asm.places.databinding.FragmentPlaceDetailsBinding;
import com.applications.asm.places.model.PlaceDetailsVM;
import com.applications.asm.places.model.PriceVM;
import com.applications.asm.places.model.Resource;
import com.applications.asm.places.model.ScheduleVM;
import com.applications.asm.places.view.fragments.base.BaseFragment;
import com.applications.asm.places.view.utils.ViewUtils;
import com.applications.asm.places.view_model.PlaceDetailsViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

public class PlaceDetailsFragment extends BaseFragment<FragmentPlaceDetailsBinding> {
    public static final String PLACE_ID_KEY = "place_id_key";

    private PlaceDetailsViewModel placeDetailsViewModel;
    private Dialog loadingGetCoordinates;
    private PlaceDetailsVM placeDetailsVM;
    private boolean isRecreated;

    @Named("placeDetailsVMFactory")
    @Inject
    ViewModelProvider.Factory placeDetailsViewModelFactory;

    public PlaceDetailsFragment() {
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
        isRecreated = false;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        placeDetailsViewModel = new ViewModelProvider(this, placeDetailsViewModelFactory).get(PlaceDetailsViewModel.class);
        initViewObservables();
        setListeners();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isRecreated = true;
    }

    @Override
    protected FragmentPlaceDetailsBinding bindingInflater(LayoutInflater inflater, ViewGroup container) {
        return FragmentPlaceDetailsBinding.inflate(inflater, container, false);
    }

    private void initViewObservables() {
        if(!isRecreated) {
            Bundle bundle = getArguments();
            if(bundle != null) placeDetailsViewModel.loadPlaceDetailsVM(bundle.getString(PLACE_ID_KEY)).observe(getViewLifecycleOwner(), this::getPlaceDetails);
        } else renderPlaceDetail(placeDetailsVM);
    }

    private void setListeners() {
        getViewBinding().showMapButton.setOnClickListener(view -> {

        });
        getViewBinding().showReviewsButton.setOnClickListener(view -> {
            Bundle bundle = getArguments();
            if(bundle != null) {
                Bundle bundle2 = new Bundle();
                bundle2.putString(ReviewsFragment.PLACE_REVIEWS_KEY, bundle.getString(PLACE_ID_KEY));
                NavHostFragment.findNavController(this).navigate(R.id.action_placeDetailsFragment_to_reviewsFragment, bundle2);
            }
        });
    }

    private void getPlaceDetails(Resource<PlaceDetailsVM> resource) {
        switch (resource.getStatus()) {
            case LOADING:
                loadingGetCoordinates = ViewUtils.loading(requireContext());
                break;
            case SUCCESS:
                ViewUtils.loaded(loadingGetCoordinates);
                placeDetailsVM = resource.getData();
                renderPlaceDetail(resource.getData());
                break;
            case WARNING:
                ViewUtils.loaded(loadingGetCoordinates);
                ViewUtils.showGeneralWarningDialog(requireContext(), resource.getWarning());
                break;
            case ERROR:
                ViewUtils.loaded(loadingGetCoordinates);
                ViewUtils.showGeneralWarningDialog(requireContext(), resource.getErrorMessage());
                break;
        }
    }

    private void renderPlaceDetail(PlaceDetailsVM placeDetailsVM) {
        Picasso.get().load(placeDetailsVM.getImageUrl()).placeholder(R.drawable.place_image).error(R.drawable.no_image).fit().centerCrop().into(getViewBinding().placeDetailsImageView);
        getViewBinding().namePlaceTitleText.setText(placeDetailsVM.getName());
        getViewBinding().ratingPlaceText.setText(String.valueOf(placeDetailsVM.getRating()));
        getViewBinding().ratingBar.setRating(placeDetailsVM.getRating().floatValue());
        String ratings = placeDetailsVM.getReviewsCounter() + " " + (placeDetailsVM.getReviewsCounter() == 1 ? getString(R.string.rating) : getString(R.string.ratings));
        getViewBinding().countReviewsPlaceText.setText(ratings);
        PriceVM priceVM = placeDetailsVM.getPrice();
        String priceName = getString(R.string.there_is_no_information);
        if(priceVM != null) {
            priceName = priceVM.getName();
            int priceColor;
            if(priceVM.getId().compareTo("$") == 0)
                priceColor = ContextCompat.getColor(requireContext(), R.color.blue_2);
            else if(priceVM.getId().compareTo("$$") == 0)
                priceColor = ContextCompat.getColor(requireContext(), R.color.green_2);
            else if(priceVM.getId().compareTo("$$$") == 0)
                priceColor = ContextCompat.getColor(requireContext(), R.color.orange_2);
            else priceColor = ContextCompat.getColor(requireContext(), R.color.red_2);
            getViewBinding().priceValueText.setTextColor(priceColor);
        }
        getViewBinding().priceValueText.setText(priceName);
        getViewBinding().phoneNumberPlaceValueText.setText(!placeDetailsVM.getPhoneNumber().isEmpty() ? placeDetailsVM.getPhoneNumber() : getString(R.string.there_is_no_information));
        getViewBinding().addressValueText.setText(placeDetailsVM.getAddress());
        getViewBinding().categoriesValueText.setText(placeDetailsVM.getCategories());
        String statePlace;
        int textColor;
        if(placeDetailsVM.getOpen()) {
            statePlace = getString(R.string.place_open_text);
            textColor = ContextCompat.getColor(requireContext(), R.color.blue_2);
        } else {
            statePlace = getString(R.string.place_close_text);
            textColor = ContextCompat.getColor(requireContext(), R.color.red_2);
        }
        getViewBinding().isOpenText.setTextColor(textColor);
        getViewBinding().isOpenText.setText(statePlace);
        List<ScheduleVM> scheduleVM = placeDetailsVM.getSchedule();
        StringBuilder schedule = new StringBuilder(getString(R.string.there_is_no_information));
        if(scheduleVM != null && !scheduleVM.isEmpty()) {
            schedule = new StringBuilder();
            for(int i = 0; i < scheduleVM.size() ; i++) {
                if(i != 0) schedule.append("\n");
                StringBuilder hoursS = new StringBuilder();
                for(int j = 0; j < scheduleVM.get(i).getHours().size() ; j++) {
                    if(j != 0) hoursS.append(", ");
                    hoursS.append(scheduleVM.get(i).getHours().get(j));
                }
                schedule.append(scheduleVM.get(i).getDay()).append(": ").append(hoursS);
            }
        }
        getViewBinding().scheduleText.setText(schedule.toString());
    }
}