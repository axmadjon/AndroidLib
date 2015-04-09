package uz.support.v14.app.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uz.support.v14.app.SupportFragment;
import uz.support.v14.widget.ViewSetup;

public abstract class IndexFragment extends SupportFragment {

    protected abstract ViewSetup onCreateViewSetup(LayoutInflater inflater, ViewGroup container);

    protected abstract IndexContentFragment defaultContent();

    public ViewSetup vsRoot;

    @Override
    public final View onCreateView(LayoutInflater inflater,
                                   @Nullable ViewGroup container,
                                   @Nullable Bundle savedInstanceState) {
        vsRoot = onCreateViewSetup(inflater, container);
        return vsRoot.view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        replaceContent(defaultContent());
    }

    @Override
    public void onStart() {
        super.onStart();
        onContentCreate();
    }

    protected void onContentCreate() {

    }

}
