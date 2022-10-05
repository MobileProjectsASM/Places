package com.applications.asm.places.view.view_holders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.applications.asm.places.databinding.LoadingItemLayoutBinding;

public class LoadingViewHolder extends RecyclerView.ViewHolder {
    private final LoadingItemLayoutBinding binding;

    public LoadingViewHolder(@NonNull View view) {
        super(view);
        binding = LoadingItemLayoutBinding.bind(view);
    }

    public void render() {
        binding.circularProgressBar.setVisibility(View.VISIBLE);
    }
}
