package uz.support.v14.common.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uz.support.v14.util.ViewSetup;

public abstract class IndexContentFragment extends SupportFragment {

    protected abstract ViewSetup onCreateViewSetup(LayoutInflater inflater, ViewGroup container);

    protected abstract void onCreateActivity(Bundle bundle);

    protected abstract ContentFragment onCreateContent();

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
        make(onCreateContent());
    }

    public void make(ContentFragment contentFragment) {
        replaceContent(contentFragment);
    }

}
