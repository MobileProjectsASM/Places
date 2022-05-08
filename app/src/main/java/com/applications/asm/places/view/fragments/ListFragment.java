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

import com.applications.asm.places.databinding.FragmentListBinding;
import com.applications.asm.places.model.PlaceM;
import com.applications.asm.places.view.activities.interfaces.MainViewParent;
import com.applications.asm.places.view.adapters.PlaceAdapter;
import com.applications.asm.places.view_model.MainViewModel;

import javax.inject.Inject;
import javax.inject.Named;

public class ListFragment extends Fragment {
    private FragmentListBinding binding;
    private MainViewParent mainViewParent;
    private MainViewModel mainViewModel;

    @Named("main_view_model")
    @Inject
    ViewModelProvider.Factory factoryMainViewModel;

    public ListFragment() {
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
        binding = FragmentListBinding.inflate(inflater, container, false);
        mainViewParent.getMainComponent().inject(this);
        mainViewModel = new ViewModelProvider(requireActivity(), factoryMainViewModel).get(MainViewModel.class);
        setObservables();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.placesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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

    private void setObservables() {
        mainViewModel.placesMV().observe(getViewLifecycleOwner(), placesMV -> {
            PlaceAdapter adapter = new PlaceAdapter(placesMV, this::clickOnItemList);
            binding.placesRecyclerView.setAdapter(adapter);
        });
    }

    private void clickOnItemList(PlaceM placeM) {

    }
}