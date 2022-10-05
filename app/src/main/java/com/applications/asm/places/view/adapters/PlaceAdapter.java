package com.applications.asm.places.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.applications.asm.places.R;
import com.applications.asm.places.model.PlaceVM;
import com.applications.asm.places.view.events.PlaceClickListener;
import com.applications.asm.places.view.view_holders.LoadingViewHolder;
import com.applications.asm.places.view.view_holders.PlaceViewHolder;

import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private final List<PlaceVM> places;
    private final PlaceClickListener placeClickListener;

    public PlaceAdapter(List<PlaceVM> places, PlaceClickListener placeClickListener) {
        this.places = places;
        this.placeClickListener = placeClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if(viewType == VIEW_TYPE_ITEM) {
            return new PlaceViewHolder(layoutInflater.inflate(R.layout.place_item_layout, parent, false));
        } else {
            return new LoadingViewHolder(layoutInflater.inflate(R.layout.loading_item_layout, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof PlaceViewHolder) {
            PlaceViewHolder placeViewHolder = (PlaceViewHolder) holder;
            placeViewHolder.render(places.get(position), placeClickListener);
        } else {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.render();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return places.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return places == null ? 0 : places.size();
    }

    public void addPlaces(List<PlaceVM> placesVM) {
        int positionStart = places.size();
        places.addAll(placesVM);
        notifyItemRangeInserted(positionStart, placesVM.size());
    }

    public void addPlace(PlaceVM placeVM) {
        places.add(placeVM);
        notifyItemInserted(places.size() - 1);
    }

    public void removeLastPlace() {
        places.remove(places.size() - 1);
        notifyItemRemoved(places.size());
    }
}
