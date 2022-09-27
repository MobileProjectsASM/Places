package com.applications.asm.places.view.view_holders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.applications.asm.places.databinding.CategoryItemLayoutBinding;
import com.applications.asm.places.model.CategoryVM;
import com.applications.asm.places.view.events.CategoryClickListener;

public class CategoryViewHolder extends RecyclerView.ViewHolder {
    private final CategoryItemLayoutBinding categoryItemLayoutBinding;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        categoryItemLayoutBinding = CategoryItemLayoutBinding.bind(itemView);
    }

    public void render(CategoryVM categoryVM, CategoryClickListener categoryClickListener) {
        categoryItemLayoutBinding.suggestedPlaceTitleText.setText(categoryVM.getName());

        itemView.setOnClickListener(view -> categoryClickListener.onCategoryClickListener(categoryVM));
    }
}
