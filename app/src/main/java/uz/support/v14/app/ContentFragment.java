package uz.support.v14.app;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uz.support.v14.util.SupportUtil;
import uz.support.v14.util.ViewSetup;

public abstract class ContentFragment extends SupportFragment {

    protected abstract ViewSetup onCreateViewSetup(LayoutInflater inflater, ViewGroup container);

    protected Setting getSetting() {
        return SupportUtil.parcelableSetting(this);
    }

    protected <T extends Parcelable> T getArg() {
        return SupportUtil.parcelableArgument(this);
    }

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
        Setting setting = getSetting();
        if (setting.logoResId != 0) {
            setLogo(setting.logoResId);
        }
        if (!TextUtils.isEmpty(setting.title)) {
            setTitle(setting.title);
        }
        if (!TextUtils.isEmpty(setting.subTitle)) {
            setSubTitle(setting.subTitle);
        }
        if (setting.backgroundResId != 0) {
            setToolbarColor(getResources().getColor(setting.backgroundResId));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        SupportActivity activity = (SupportActivity) getActivity();
        activity.onContentStarted(this);
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


    public SupportOptionMenu getOptionMenu() {
        return null;
    }
}
