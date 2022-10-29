package com.applications.asm.places.view.view_holders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.applications.asm.places.R;
import com.applications.asm.places.databinding.ReviewItemLayoutBinding;
import com.applications.asm.places.model.ReviewVM;
import com.squareup.picasso.Picasso;

public class PlaceReviewViewHolder extends RecyclerView.ViewHolder {
    private final ReviewItemLayoutBinding binding;

    public PlaceReviewViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = ReviewItemLayoutBinding.bind(itemView);
    }

    public void render(ReviewVM review) {
        if(!review.getImageUrl().isEmpty()) Picasso.get().load(review.getImageUrl()).placeholder(R.drawable.place_image).fit().centerCrop().into(binding.placeImageView);
        else Picasso.get().load(R.drawable.usuario).into(binding.placeImageView);
        binding.nameUserTextView.setText(review.getUserName());
        binding.rateReviewTextView.setText(String.valueOf(review.getRate()));
        binding.ratingBarReview.setRating(review.getRate().floatValue());
        binding.dateReviewTextView.setText(review.getDate());
        binding.descriptionReviewTextView.setText(review.getDescription());
    }
}
