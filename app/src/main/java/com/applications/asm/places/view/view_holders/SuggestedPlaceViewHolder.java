package com.applications.asm.places.view.view_holders;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.applications.asm.places.R;
import com.applications.asm.places.databinding.SuggestedItemLayoutBinding;
import com.applications.asm.places.model.SuggestedPlaceVM;
import com.applications.asm.places.view.events.SuggestedPlaceClickListener;

public class SuggestedPlaceViewHolder extends RecyclerView.ViewHolder {
    private final SuggestedItemLayoutBinding suggestedItemLayoutBinding;

    public SuggestedPlaceViewHolder(@NonNull View itemView) {
        super(itemView);
        suggestedItemLayoutBinding = SuggestedItemLayoutBinding.bind(itemView);
    }

    public void render(Context context, SuggestedPlaceVM suggestedPlaceVM, SuggestedPlaceClickListener suggestedPlaceClickListener) {
        suggestedItemLayoutBinding.suggestedPlaceTitleText.setText(suggestedPlaceVM.getName());
        suggestedItemLayoutBinding.suggestedPlaceAddressText.setText(suggestedPlaceVM.getAddress());

        itemView.setOnClickListener(view -> suggestedPlaceClickListener.onSuggestedPlaceClickListener(suggestedPlaceVM));
    }
}
