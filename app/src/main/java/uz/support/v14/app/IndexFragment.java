package uz.support.v14.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uz.support.v14.util.ViewSetup;

public abstract class IndexFragment extends SupportFragment {

    protected abstract ViewSetup onCreateViewSetup(LayoutInflater inflater, ViewGroup container);

    public ViewSetup vsRoot;

    @Override
    public final View onCreateView(LayoutInflater inflater,
                                   @Nullable ViewGroup container,
                                   @Nullable Bundle savedInstanceState) {
        vsRoot = onCreateViewSetup(inflater, container);
        return vsRoot.view;
    }
}
