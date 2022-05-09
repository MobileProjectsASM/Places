package com.applications.asm.places.view.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.applications.asm.places.R;
import com.applications.asm.places.model.ReviewM;
import com.applications.asm.places.view.view_holders.ReviewPlaceHolder;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewPlaceHolder> {
    private final List<ReviewM> reviews;

    public ReviewAdapter(List<ReviewM> reviews) {
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewPlaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ReviewPlaceHolder(layoutInflater.inflate(R.layout.review_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewPlaceHolder holder, int position) {
        holder.render(reviews.get(position));
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }
}
