package uz.support.v14.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import uz.support.v14.R;

public abstract class LinearLayoutSection implements Section {

    @Override
    public View createView(LayoutInflater inflater, ViewGroup parent) {
        View v = inflater.inflate(R.layout.i_layout, parent, false);
        addViews((LinearLayout) v);
        return v;
    }

    public abstract void addViews(LinearLayout cnt);
}
