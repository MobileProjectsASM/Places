package com.applications.asm.places.view.view_holders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.applications.asm.places.R;
import com.applications.asm.places.databinding.ReviewItemLayoutBinding;
import com.applications.asm.places.model.ReviewM;
import com.squareup.picasso.Picasso;

public class ReviewPlaceHolder extends RecyclerView.ViewHolder {
    private final ReviewItemLayoutBinding binding;

    public ReviewPlaceHolder(@NonNull View itemView) {
        super(itemView);
        binding = ReviewItemLayoutBinding.bind(itemView);
    }

    public void render(ReviewM review) {
        if(review.getImageUrl().compareTo("") != 0) Picasso.get().load(review.getImageUrl()).fit().centerCrop().into(binding.placeImageView);
        else Picasso.get().load(R.drawable.usuario);
        binding.nameUserTextView.setText(review.getUserName());
        binding.rateReviewTextView.setText(String.valueOf(review.getRate()));
        binding.ratingBarReview.setRating(review.getRate().floatValue());
        binding.dateReviewTextView.setText(review.getDate());
        binding.descriptionReviewTextView.setText(review.getDescription());
    }
}
