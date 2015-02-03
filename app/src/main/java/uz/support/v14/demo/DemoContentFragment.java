package uz.support.v14.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uz.support.v14.R;
import uz.support.v14.common.fragment.ContentFragment;
import uz.support.v14.common.mold.SupportActivity;
import uz.support.v14.util.ViewSetup;

public class DemoContentFragment extends ContentFragment {

    public static void open(Activity activity) {
        SupportActivity.openContent(activity, DemoContentFragment.class, null);
    }

    @Override
    protected ViewSetup onCreateViewSetup(LayoutInflater inflater, ViewGroup container) {
        return new ViewSetup(inflater, container, R.layout.demo_content);
    }

    @Override
    protected void onCreateActivity(Bundle bundle) {
        vsRoot.button(R.id.btn_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DemoIndexFragment.open(getActivity());
            }
        });
    }
}
