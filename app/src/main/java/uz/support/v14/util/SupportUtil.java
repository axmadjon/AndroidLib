package uz.support.v14.util;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;

import uz.support.v14.app.SupportActivity;
import uz.support.v14.app.SupportFragment;
import uz.support.v14.app.content.ContentFragment;
import uz.support.v14.app.index.IndexFragment;

public final class SupportUtil {

    private SupportUtil() {
    }

    public static SupportFragment newInstance(Class<? extends SupportFragment> cls) {
        SupportFragment fragment = null;
        try {
            fragment = cls.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fragment;
    }

    public static ContentFragment newContentInstance(Class<? extends ContentFragment> cls) {
        return (ContentFragment) newInstance(cls);
    }

    public static IndexFragment newIndexInstance(Class<? extends IndexFragment> cls) {
        return (IndexFragment) newInstance(cls);
    }

    //**********************************************************************************************

    public static SupportActivity getSupportActivity(Activity a) {
        return (SupportActivity) a;
    }

    //**********************************************************************************************

    public static ContentFragment getContentFragment(Activity activity) {
        return getSupportActivity(activity).getContentFragment();
    }

    public static ContentFragment getContentFragment(SupportFragment fragment) {
        return getContentFragment(fragment.getActivity());
    }

    //**********************************************************************************************

    public static IndexFragment getIndexFragment(Activity activity) {
        return getSupportActivity(activity).getIndexContent();
    }

    public static IndexFragment getIndexFragment(SupportFragment fragment) {
        return getIndexFragment(fragment.getActivity());
    }

    //**********************************************************************************************

    public static void replaceContent(Activity activity, ContentFragment cf) {
        getSupportActivity(activity).replaceContent(cf);
    }

    public static void replaceContent(Activity activity, Class<? extends ContentFragment> cls) {
        replaceContent(activity, newContentInstance(cls));
    }

    //**********************************************************************************************

    public static void addContent(Activity activity, ContentFragment cf) {
        getSupportActivity(activity).addContent(cf);
    }

    public static void addContent(Activity activity, Class<? extends ContentFragment> cls) {
        addContent(activity, newContentInstance(cls));
    }

    //**********************************************************************************************

    public static void openContent(Activity activity, ContentFragment cf) {
        getSupportActivity(activity).openContent(cf);
    }

    public static void openContent(Activity activity, Class<? extends ContentFragment> cls) {
        openContent(activity, newContentInstance(cls));
    }

    //**********************************************************************************************

    public static Parcelable getData(Activity activity) {
        return getSupportActivity(activity).getData();
    }

    public static Parcelable getData(SupportFragment fragment) {
        return getData(fragment.getActivity());
    }

    public static void setData(Activity activity, Parcelable data) {
        getSupportActivity(activity).setData(data);
    }

    public static void setData(SupportFragment fragment, Parcelable data) {
        setData(fragment.getActivity(), data);
    }

    //**********************************************************************************************

    public static <T extends Parcelable> T parcelableArgument(ContentFragment fragment) {
        return fragment.getArguments().getParcelable("arg");
    }

    public static Bundle parcelableArgument(Parcelable parcelable) {
        Bundle b = new Bundle();
        b.putParcelable("arg", parcelable);
        return b;
    }

    public static <T extends SupportFragment> T parcelableArgumentNewInstance(Class<T> cls, Parcelable parcelable) {
        SupportFragment f = newInstance(cls);
        f.setArguments(parcelableArgument(parcelable));
        return (T) f;
    }


    //**********************************************************************************************

    public static void popContent(Activity activity, Object param) {
        getSupportActivity(activity).popContent(param);
    }

    public static void popContent(Activity activity) {
        popContent(activity, null);
    }

    //**********************************************************************************************
    //**********************************************************************************************
    //**********************************************************************************************
    //**********************************************************************************************
    //**********************************************************************************************
    //**********************************************************************************************
}
