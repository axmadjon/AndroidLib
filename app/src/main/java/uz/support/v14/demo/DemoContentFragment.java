package uz.support.v14.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uz.support.v14.R;
import uz.support.v14.app.ContentFragment;
import uz.support.v14.app.SupportActivity;
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setToolbarColor(getResources().getColor(R.color.red));
        vsRoot.button(R.id.btn_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DemoIndexFragment.open(getActivity());
            }
        });

    }
}
