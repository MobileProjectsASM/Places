package com.applications.asm.places.view.fragments.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewbinding.ViewBinding;

import com.applications.asm.places.R;

public abstract class CommonMenuSearchFragment<VB extends ViewBinding> extends BaseFragment<VB> {

    public CommonMenuSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.change_coordinates_option) {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.coordinatesFragment);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}