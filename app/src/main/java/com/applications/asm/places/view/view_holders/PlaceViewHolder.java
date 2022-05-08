package com.applications.asm.places.view.view_holders;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.applications.asm.places.R;
import com.applications.asm.places.databinding.PlaceItemLayoutBinding;
import com.applications.asm.places.model.PlaceM;
import com.applications.asm.places.view.events.PlaceClickListener;
import com.squareup.picasso.Picasso;

public class PlaceViewHolder extends RecyclerView.ViewHolder {
    private final PlaceItemLayoutBinding binding;

    public PlaceViewHolder(View view) {
        super(view);
        binding = PlaceItemLayoutBinding.bind(view);
    }

    public void render(PlaceM placeM, PlaceClickListener placeClickListener) {
        if(placeM.getImageUrl().compareTo("") != 0) Picasso.get().load(placeM.getImageUrl()).fit().centerCrop().into(binding.placeImageView);
        else Picasso.get().load(R.drawable.place).fit().centerCrop().into(binding.placeImageView);
        binding.namePlaceTextView.setText(placeM.getName());
        binding.addressPlaceTextView.setText(placeM.getAddress());
        binding.categoriesPlaceTextView.setText(placeM.getCategories());

        itemView.setOnClickListener(view -> placeClickListener.onPlaceClickListener(placeM));
    }
}
