package uz.support.v14.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uz.support.v14.R;
import uz.support.v14.app.ContentFragment;
import uz.support.v14.app.IndexContentFragment;
import uz.support.v14.util.ViewSetup;

public class DemoIndexContentFragment extends IndexContentFragment {

    @Override
    protected ViewSetup onCreateViewSetup(LayoutInflater inflater, ViewGroup container) {
        return new ViewSetup(inflater, container, R.layout.demo_index_content);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vsRoot.button(R.id.btn_next_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContent(DemoContentFragment2.class);
            }
        });
    }
}
