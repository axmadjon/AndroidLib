package uz.support.v14.common.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

import uz.support.v14.util.SupportUtil;

public abstract class SupportFragment extends Fragment {

    private static final String ARG_FRAGMENT_DATA = "uz.greenwhite.support.v10.fragment.data";

    //**********************************************************************************************

    protected final SupportFragment newInstance(Class<? extends SupportFragment> cls) {
        SupportFragment fragment = null;
        try {
            fragment = cls.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fragment;
    }

    //**********************************************************************************************

    private Parcelable mData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mData = savedInstanceState.getParcelable(ARG_FRAGMENT_DATA);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mData != null)
            outState.putParcelable(ARG_FRAGMENT_DATA, mData);
    }

    public <T extends Parcelable> T manageFragmentData(Parcelable data) {
        if (mData == null) {
            mData = data;
        }
        return (T) mData;
    }

    public void onAboveContentPopped(Object result) {
    }

    //**********************************************************************************************

    protected final void replaceContent(ContentFragment contentFragment) {
        SupportUtil.replaceContent(getActivity(), contentFragment);
    }

    protected final void addContent(ContentFragment contentFragment) {
        SupportUtil.addContent(getActivity(), contentFragment);
    }

    protected final void openContent(ContentFragment contentFragment) {
        SupportUtil.openContent(getActivity(), contentFragment);
    }

    //**********************************************************************************************

    protected final void replaceContent(Class<? extends ContentFragment> cls) {
        SupportUtil.replaceContent(getActivity(), (ContentFragment) newInstance(cls));
    }

    protected final void addContent(Class<? extends ContentFragment> cls) {
        SupportUtil.addContent(getActivity(), (ContentFragment) newInstance(cls));
    }

    protected final void openContent(Class<? extends ContentFragment> cls) {
        SupportUtil.openContent(getActivity(), (ContentFragment) newInstance(cls));
    }

    //**********************************************************************************************

    protected final void replaceContent(Class<? extends ContentFragment> cls, Bundle args) {
        ContentFragment contentFragment = (ContentFragment) newInstance(cls);
        contentFragment.setArguments(args);
        SupportUtil.replaceContent(getActivity(), contentFragment);
    }

    protected final void addContent(Class<? extends ContentFragment> cls, Bundle args) {
        ContentFragment contentFragment = (ContentFragment) newInstance(cls);
        contentFragment.setArguments(args);

        SupportUtil.addContent(getActivity(), contentFragment);
    }

    protected final void openContent(Class<? extends ContentFragment> cls, Bundle args) {
        ContentFragment contentFragment = (ContentFragment) newInstance(cls);
        contentFragment.setArguments(args);
        SupportUtil.openContent(getActivity(), contentFragment);
    }

    //**********************************************************************************************
}