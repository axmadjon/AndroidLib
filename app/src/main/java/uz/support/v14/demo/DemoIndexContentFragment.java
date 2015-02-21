package uz.support.v14.demo;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import uz.support.v14.R;
import uz.support.v14.app.ContentFragment;
import uz.support.v14.util.ViewSetup;

public class DemoIndexContentFragment extends ContentFragment {
    @Override
    protected ViewSetup onCreateViewSetup(LayoutInflater inflater, ViewGroup container) {
        return new ViewSetup(inflater, container, R.layout.demo_index_content);
    }
}
