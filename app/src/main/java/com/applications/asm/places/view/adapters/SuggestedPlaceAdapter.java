package com.applications.asm.places.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.applications.asm.places.R;
import com.applications.asm.places.model.SuggestedPlaceVM;
import com.applications.asm.places.view.events.SuggestedPlaceClickListener;
import com.applications.asm.places.view.view_holders.SuggestedPlaceViewHolder;

import java.util.List;

public class SuggestedPlaceAdapter extends RecyclerView.Adapter<SuggestedPlaceViewHolder> {
    private final List<SuggestedPlaceVM> suggestedPlacesVM;
    private final SuggestedPlaceClickListener suggestedPlaceClickListener;
    private Context context;

    public SuggestedPlaceAdapter(List<SuggestedPlaceVM> suggestedPlacesVM, SuggestedPlaceClickListener suggestedPlaceClickListener) {
        this.suggestedPlacesVM = suggestedPlacesVM;
        this.suggestedPlaceClickListener = suggestedPlaceClickListener;
    }

    @NonNull
    @Override
    public SuggestedPlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.suggested_item_layout, parent, false);
        return new SuggestedPlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestedPlaceViewHolder holder, int position) {
        holder.render(context, suggestedPlacesVM.get(position), suggestedPlaceClickListener);
    }

    @Override
    public int getItemCount() {
        return suggestedPlacesVM.size();
    }
}
