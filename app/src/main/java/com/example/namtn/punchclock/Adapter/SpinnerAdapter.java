package com.example.namtn.punchclock.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.namtn.punchclock.R;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<String> {

    Typeface font = Typeface.createFromAsset(getContext().getAssets(),
            "roboto_regular.ttf");

    public SpinnerAdapter(Context context, int resource, List<String> listString) {
        super(context, resource, listString);
    }

    // Affects default (closed) state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        view.setTextColor(getContext().getResources().getColor(R.color.colorBlack));
        view.setTypeface(font);
        return view;
    }

    // Affects opened state of the spinner
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getDropDownView(position, convertView, parent);
        view.setTextColor(getContext().getResources().getColor(R.color.colorBlack));
        view.setTypeface(font);
        return view;
    }
}

