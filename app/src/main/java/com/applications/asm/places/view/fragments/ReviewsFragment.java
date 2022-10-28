package com.applications.asm.places.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.applications.asm.places.R;
import com.applications.asm.places.databinding.FragmentReviewsBinding;
import com.applications.asm.places.databinding.LoadingLayoutBinding;
import com.applications.asm.places.databinding.MessageLayoutBinding;
import com.applications.asm.places.databinding.ReviewsLayoutBinding;
import com.applications.asm.places.model.Resource;
import com.applications.asm.places.model.ReviewVM;
import com.applications.asm.places.view.adapters.ReviewAdapter;
import com.applications.asm.places.view.fragments.base.BaseFragment;
import com.applications.asm.places.view_model.ReviewsViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

public class ReviewsFragment extends BaseFragment<FragmentReviewsBinding> {
    public static final String PLACE_REVIEWS_KEY = "place_reviews";

    private ReviewsViewModel reviewsViewModel;
    private ReviewAdapter reviewAdapter;

    @Named("reviewsVMFactory")
    @Inject
    ViewModelProvider.Factory reviewsViewModelFactory;

    public ReviewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        getActivityComponent().inject(this);
    }

    @Override
    protected FragmentReviewsBinding bindingInflater(LayoutInflater inflater, ViewGroup container) {
        return FragmentReviewsBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reviewsViewModel = new ViewModelProvider(this, reviewsViewModelFactory).get(ReviewsViewModel.class);
        initViewObservables();
        initAdapter();
    }

    private void initViewObservables() {
        Bundle bundle = getArguments();
        if(bundle != null) reviewsViewModel.getReviews(bundle.getString(PLACE_REVIEWS_KEY)).observe(getViewLifecycleOwner(), this::getReviews);
    }

    private void initAdapter() {
        reviewAdapter = new ReviewAdapter(new ArrayList<>());
    }

    private void getReviews(Resource<List<ReviewVM>> resource) {
        getViewBinding().resultsReviewsFrameLayout.removeAllViews();
        switch (resource.getStatus()) {
            case LOADING:
                LoadingLayoutBinding viewLoadingBinding = LoadingLayoutBinding.inflate(getLayoutInflater());
                getViewBinding().resultsReviewsFrameLayout.addView(viewLoadingBinding.getRoot());
                break;
            case SUCCESS:
                List<ReviewVM> reviewsVM = resource.getData();
                View placesView;
                if(reviewsVM.isEmpty()) {
                    MessageLayoutBinding emptyBinding = MessageLayoutBinding.inflate(getLayoutInflater());
                    emptyBinding.messageTextView.setText(R.string.empty_results);
                    placesView = emptyBinding.getRoot();
                } else {
                    ReviewsLayoutBinding reviewsLayoutBinding = ReviewsLayoutBinding.inflate(getLayoutInflater());
                    reviewsLayoutBinding.reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                    reviewsLayoutBinding.reviewsRecyclerView.setAdapter(reviewAdapter);
                    reviewAdapter.addReviews(resource.getData());
                    placesView = reviewsLayoutBinding.getRoot();
                }
                String message = getString(R.string.number_results_text) + " " + reviewAdapter.getItemCount();
                getViewBinding().totalReviewsTextView.setText(message);
                getViewBinding().resultsReviewsFrameLayout.addView(placesView);
                break;
            case WARNING:
                MessageLayoutBinding warningBinding = MessageLayoutBinding.inflate(getLayoutInflater());
                warningBinding.messageTextView.setText(resource.getWarning());
                getViewBinding().resultsReviewsFrameLayout.addView(warningBinding.getRoot());
                break;
            case ERROR:
                MessageLayoutBinding errorBinding = MessageLayoutBinding.inflate(getLayoutInflater());
                errorBinding.messageTextView.setText(resource.getErrorMessage());
                getViewBinding().resultsReviewsFrameLayout.addView(errorBinding.getRoot());
                break;
        }
    }
}