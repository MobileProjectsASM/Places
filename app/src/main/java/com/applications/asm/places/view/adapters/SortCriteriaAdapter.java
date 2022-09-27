package com.applications.asm.places.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.applications.asm.places.databinding.CriterionItemLayoutBinding;
import com.applications.asm.places.model.CriterionVM;

import java.util.List;

public class SortCriteriaAdapter extends ArrayAdapter<CriterionVM> {

    public SortCriteriaAdapter(Context context, List<CriterionVM> sortCriteria) {
        super(context, 0, sortCriteria);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView != null ? convertView : CriterionItemLayoutBinding.inflate(LayoutInflater.from(getContext()), parent, false).getRoot();
        String criterionName = getItem(position).getName();
        TextView criterionTextView = (TextView) view;
        criterionTextView.setText(criterionName);
        return view;
    }
}
