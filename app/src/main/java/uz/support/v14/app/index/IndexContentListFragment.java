package uz.support.v14.app.index;

import uz.support.v14.app.content.ContentListFragment;

public abstract class IndexContentListFragment extends ContentListFragment {

    @Override
    public boolean isContent() {
        return false;
    }
}
