package uz.support.v14.app.content;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import uz.support.v14.app.SupportActivity;
import uz.support.v14.app.SupportFragment;
import uz.support.v14.app.SupportMenuContainer;
import uz.support.v14.common.Command;
import uz.support.v14.letter.service.ServiceHelper;
import uz.support.v14.util.SupportUtil;
import uz.support.v14.widget.ViewSetup;

public abstract class ContentFragment extends SupportFragment {

    protected abstract ViewSetup onCreateViewSetup(LayoutInflater inflater, ViewGroup container);

    protected <T extends Parcelable> T getParcelable() {
        return SupportUtil.parcelableArgument(this);
    }

    protected ViewSetup vsRoot;
    protected ServiceHelper serviceHelper;
    private List<SupportMenuContainer> mMenus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceHelper = new ServiceHelper();
    }

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
        mMenus = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        serviceHelper.onStart();
        SupportActivity activity = (SupportActivity) getActivity();
        activity.onContentStarted(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        serviceHelper.onStop();
    }

    public List<SupportMenuContainer> getMenus() {
        if (mMenus == null) {
            mMenus = new ArrayList<>();
        }
        return mMenus;
    }

    public void addMenu(int iconId, Command command) {
        getMenus().add(new SupportMenuContainer(iconId, command));
    }

    public void addMenu(View view) {
        getMenus().add(new SupportMenuContainer(view));
    }


    protected void onContentCreate() {
    }

    public int getColor(int resId) {
        return getResources().getColor(resId);
    }

    public boolean isContent() {
        return true;
    }

    protected Toolbar getToolbar() {
        return SupportUtil.getSupportActivity(getActivity()).getToolbar();
    }

    protected void setLogo(int resId) {
        getToolbar().setLogo(resId);
    }

    protected void setToolbarColor(int color) {
        getToolbar().setBackgroundColor(color);
    }

    protected void setTitle(CharSequence title) {
        getToolbar().setTitle(title);
    }

    protected void setTitle(int resId) {
        setTitle(getString(resId));
    }

    private void setSubTitle(CharSequence subTitle) {
        getToolbar().setSubtitle(subTitle);
    }

    private void setSubTitle(int resId) {
        setSubTitle(getString(resId));
    }
}
