package uz.support.v14.widget;

import android.support.v7.widget.SearchView;

public abstract class SearchQuery implements SearchView.OnQueryTextListener {

    public abstract void onQueryText(String s);

    @Override
    public boolean onQueryTextSubmit(String s) {
        onQueryText(s);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        onQueryText(s);
        return true;
    }
}
