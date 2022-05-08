package com.applications.asm.places.view.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.applications.asm.places.R;
import com.applications.asm.places.model.PlaceMV;
import com.applications.asm.places.view.events.PlaceClickListener;
import com.applications.asm.places.view.view_holders.PlaceViewHolder;

import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceViewHolder> {
    private final List<PlaceMV> places;
    private final PlaceClickListener placeClickListener;

    public PlaceAdapter(List<PlaceMV> places, PlaceClickListener placeClickListener) {
        this.places = places;
        this.placeClickListener = placeClickListener;
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new PlaceViewHolder(layoutInflater.inflate(R.layout.place_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        holder.render(places.get(position), placeClickListener);
    }

    @Override
    public int getItemCount() {
        return places.size();
    }
}
