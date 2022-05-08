package com.applications.asm.places.view.view_holders;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.applications.asm.places.R;
import com.applications.asm.places.databinding.PlaceItemLayoutBinding;
import com.applications.asm.places.model.PlaceMV;
import com.applications.asm.places.view.events.PlaceClickListener;
import com.squareup.picasso.Picasso;

public class PlaceViewHolder extends RecyclerView.ViewHolder {
    private final PlaceItemLayoutBinding binding;

    public PlaceViewHolder(View view) {
        super(view);
        binding = PlaceItemLayoutBinding.bind(view);
    }

    public void render(PlaceMV placeMV, PlaceClickListener placeClickListener) {
        if(placeMV.getImageUrl().compareTo("") != 0) Picasso.get().load(placeMV.getImageUrl()).fit().centerCrop().into(binding.placeImageView);
        else Picasso.get().load(R.drawable.place).fit().centerCrop().into(binding.placeImageView);
        binding.namePlaceTextView.setText(placeMV.getName());
        binding.addressPlaceTextView.setText(placeMV.getAddress());
        binding.categoriesPlaceTextView.setText(placeMV.getCategories());

        itemView.setOnClickListener(view -> placeClickListener.onPlaceClickListener(placeMV));
    }
}
