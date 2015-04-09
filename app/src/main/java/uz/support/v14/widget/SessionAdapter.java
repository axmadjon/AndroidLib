package uz.support.v14.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import uz.support.v14.collection.MyArray;

public class SessionAdapter extends BaseAdapter {

    final LayoutInflater inflater;
    final MyArray<Section> sections;

    public SessionAdapter(Context ctx, MyArray<Section> sections) {
        inflater = LayoutInflater.from(ctx);
        this.sections = sections;
    }

    @Override
    public int getCount() {
        return sections.size();
    }

    @Override
    public int getViewTypeCount() {
        return sections.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return sections.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = sections.get(position).createView(inflater, parent);
        }
        return v;
    }
}