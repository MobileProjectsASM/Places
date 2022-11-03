package com.applications.asm.places.view.view_holders;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.applications.asm.places.R;
import com.applications.asm.places.databinding.PlaceItemLayoutBinding;
import com.applications.asm.places.model.PlaceVM;
import com.applications.asm.places.view.events.PlaceClickListener;
import com.squareup.picasso.Picasso;

public class PlaceViewHolder extends RecyclerView.ViewHolder {
    private final PlaceItemLayoutBinding binding;

    public PlaceViewHolder(View view) {
        super(view);
        binding = PlaceItemLayoutBinding.bind(view);
    }

    public void render(PlaceVM placeVM, PlaceClickListener placeClickListener) {
        if(placeVM.getImageUrl().compareTo("") != 0) Picasso.get().load(placeVM.getImageUrl()).placeholder(R.drawable.place_image).error(R.drawable.no_image).fit().centerCrop().into(binding.placeImageView);
        else Picasso.get().load(R.drawable.no_image).fit().centerCrop().into(binding.placeImageView);
        binding.namePlaceTextView.setText(placeVM.getName());
        binding.addressPlaceTextView.setText(placeVM.getAddress());
        binding.categoriesPlaceTextView.setText(placeVM.getCategories());

        itemView.setOnClickListener(view -> placeClickListener.onPlaceClickListener(placeVM));
    }
}
