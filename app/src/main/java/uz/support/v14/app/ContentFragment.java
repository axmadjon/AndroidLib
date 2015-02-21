package uz.support.v14.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uz.support.v14.util.SupportUtil;
import uz.support.v14.util.ViewSetup;

public abstract class ContentFragment extends SupportFragment {

    protected abstract ViewSetup onCreateViewSetup(LayoutInflater inflater, ViewGroup container);

    public ViewSetup vsRoot;

    @Override
    public final View onCreateView(LayoutInflater inflater,
                                   @Nullable ViewGroup container,
                                   @Nullable Bundle savedInstanceState) {
        vsRoot = onCreateViewSetup(inflater, container);
        return vsRoot.view;
    }

    @Override
    public void onStart() {
        super.onStart();
        SupportActivity activity = (SupportActivity) getActivity();
        activity.onContentStarted(this);
    }

    protected void setToolbarColor(int color) {
        Toolbar toolbar = SupportUtil.getSupportActivity(getActivity()).getToolbar();
        toolbar.setBackgroundColor(color);
    }

    public SupportOptionMenu getOptionMenu() {
        return null;
    }
}
