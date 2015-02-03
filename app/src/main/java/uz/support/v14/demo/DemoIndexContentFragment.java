package uz.support.v14.demo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import uz.support.v14.R;
import uz.support.v14.common.fragment.ContentFragment;
import uz.support.v14.util.ViewSetup;

public class DemoIndexContentFragment extends ContentFragment {

    public static DemoIndexContentFragment newInstance() {
        return new DemoIndexContentFragment();
    }

    @Override
    protected ViewSetup onCreateViewSetup(LayoutInflater inflater, ViewGroup container) {
        return new ViewSetup(inflater,container, R.layout.demo_index_content);
    }

    @Override
    protected void onCreateActivity(Bundle bundle) {

    }
}
