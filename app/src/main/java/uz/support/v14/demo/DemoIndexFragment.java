package uz.support.v14.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import uz.support.v14.R;
import uz.support.v14.app.IndexFragment;
import uz.support.v14.app.SupportActivity;
import uz.support.v14.util.ViewSetup;

public class DemoIndexFragment extends IndexFragment {

    public static void open(Activity activity) {
        SupportActivity.openIndex(activity, DemoIndexFragment.class, null);
    }

    @Override
    protected ViewSetup onCreateViewSetup(LayoutInflater inflater, ViewGroup container) {
        return new ViewSetup(inflater, container, R.layout.demo_index);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        replaceContent(DemoIndexContentFragment.class);
    }
}
