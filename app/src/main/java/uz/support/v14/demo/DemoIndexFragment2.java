package uz.support.v14.demo;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import uz.support.v14.R;
import uz.support.v14.app.index.IndexContentFragment;
import uz.support.v14.app.index.IndexFragment;
import uz.support.v14.widget.ViewSetup;

public class DemoIndexFragment2 extends IndexFragment {

    @Override
    protected ViewSetup onCreateViewSetup(LayoutInflater inflater, ViewGroup container) {
        return new ViewSetup(inflater, container, R.layout.demo_index);
    }

    @Override
    protected IndexContentFragment defaultContent() {
        return new DemoIndexContentFragment();
    }
}
