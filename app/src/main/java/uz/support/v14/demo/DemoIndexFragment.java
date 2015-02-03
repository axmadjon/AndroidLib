package uz.support.v14.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import uz.support.v14.R;
import uz.support.v14.common.fragment.ContentFragment;
import uz.support.v14.common.fragment.IndexContentFragment;
import uz.support.v14.common.mold.SupportActivity;
import uz.support.v14.util.ViewSetup;

public class DemoIndexFragment extends IndexContentFragment {

    public static void open(Activity activity) {
        SupportActivity.openIndex(activity, DemoIndexFragment.class, null);
    }

    @Override
    protected ViewSetup onCreateViewSetup(LayoutInflater inflater, ViewGroup container) {
        return new ViewSetup(inflater, container, R.layout.demo_index);
    }

    @Override
    protected void onCreateActivity(Bundle bundle) {

    }

    @Override
    protected ContentFragment onCreateContent() {
        return DemoIndexContentFragment.newInstance();
    }
}
