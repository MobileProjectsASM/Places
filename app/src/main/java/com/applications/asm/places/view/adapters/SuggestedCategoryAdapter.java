package com.applications.asm.places.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.applications.asm.places.R;
import com.applications.asm.places.model.CategoryVM;
import com.applications.asm.places.view.events.CategoryClickListener;
import com.applications.asm.places.view.view_holders.CategoryViewHolder;

import java.util.List;

public class SuggestedCategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {
    private final List<CategoryVM> categories;
    private final CategoryClickListener categoryClickListener;

    public SuggestedCategoryAdapter(List<CategoryVM> categories, CategoryClickListener categoryClickListener) {
        this.categories = categories;
        this.categoryClickListener = categoryClickListener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_layout, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.render(categories.get(position), categoryClickListener);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
