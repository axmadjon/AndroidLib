package uz.support.v14.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uz.support.v14.R;
import uz.support.v14.app.content.ContentFragment;
import uz.support.v14.widget.ViewSetup;

public class DemoContentFragment2 extends ContentFragment {

    @Override
    protected ViewSetup onCreateViewSetup(LayoutInflater inflater, ViewGroup container) {
        return new ViewSetup(inflater, container, R.layout.demo_content);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setToolbarColor(getColor(R.color.blue));
        vsRoot.button(R.id.btn_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContent(DemoLetterFragment.class);
            }
        });

    }
}
