package com.applications.asm.places.view.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.applications.asm.places.R;
import com.applications.asm.places.model.ReviewVM;
import com.applications.asm.places.view.view_holders.PlaceReviewViewHolder;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<PlaceReviewViewHolder> {
    private final List<ReviewVM> reviews;

    public ReviewAdapter(List<ReviewVM> reviews) {
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public PlaceReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new PlaceReviewViewHolder(layoutInflater.inflate(R.layout.review_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceReviewViewHolder holder, int position) {
        holder.render(reviews.get(position));
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public void addReviews(List<ReviewVM> reviewsVM) {
        int positionStart = reviews.size();
        reviews.addAll(reviewsVM);
        notifyItemRangeInserted(positionStart, reviewsVM.size());
    }
}
