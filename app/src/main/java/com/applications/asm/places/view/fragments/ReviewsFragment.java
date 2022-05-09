package com.applications.asm.places.view.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applications.asm.places.databinding.FragmentReviewsBinding;
import com.applications.asm.places.model.ReviewM;
import com.applications.asm.places.view.activities.interfaces.MainViewParent;
import com.applications.asm.places.view.adapters.ReviewAdapter;
import com.applications.asm.places.view_model.MainViewModel;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

public class ReviewsFragment extends Fragment {
    private FragmentReviewsBinding binding;
    private MainViewParent mainViewParent;
    private MainViewModel mainViewModel;

    @Named("main_view_model")
    @Inject
    ViewModelProvider.Factory factoryMainViewModel;

    public ReviewsFragment() {
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReviewsBinding.inflate(inflater, container, false);
        mainViewParent.getMainComponent().inject(this);
        mainViewModel = new ViewModelProvider(requireActivity(), factoryMainViewModel).get(MainViewModel.class);
        setObservables();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setObservables() {
        mainViewModel.reviews().observe(getViewLifecycleOwner(), this::renderList);
    }

    private void renderList(List<ReviewM> reviews) {
        if(reviews.isEmpty()) {
            binding.reviewsRecyclerView.setVisibility(View.GONE);
            binding.infoReviewsList.setVisibility(View.VISIBLE);
        } else {
            ReviewAdapter adapter = new ReviewAdapter(reviews);
            binding.reviewsRecyclerView.setAdapter(adapter);
            binding.reviewsRecyclerView.setVisibility(View.VISIBLE);
            binding.infoReviewsList.setVisibility(View.GONE);
        }
    }
}