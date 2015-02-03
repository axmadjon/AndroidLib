package uz.support.v14.common.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uz.support.v14.common.mold.SupportActivity;
import uz.support.v14.util.ViewSetup;

public abstract class ContentFragment extends SupportFragment {

    protected abstract ViewSetup onCreateViewSetup(LayoutInflater inflater, ViewGroup container);

    protected abstract void onCreateActivity(Bundle bundle);

    public ViewSetup vsRoot;

    @Override
    public final View onCreateView(LayoutInflater inflater,
                                   @Nullable ViewGroup container,
                                   @Nullable Bundle savedInstanceState) {
        vsRoot = onCreateViewSetup(inflater, container);
        return vsRoot.view;
    }

    @Override
    public final void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onCreateActivity(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        SupportActivity activity = (SupportActivity) getActivity();
        activity.onContentStarted(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SupportActivity activity = (SupportActivity) getActivity();
        activity.onContentDestroy(this);
    }
}
