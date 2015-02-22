package uz.support.v14.util;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;

import uz.support.v14.R;
import uz.support.v14.app.ContentFragment;
import uz.support.v14.app.IndexFragment;
import uz.support.v14.app.Setting;
import uz.support.v14.app.SupportFragment;
import uz.support.v14.app.SupportActivity;

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
    public static void setFragmentSetting(ContentFragment cf, Setting setting) {
        Bundle args = cf.getArguments();
        if (args == null) {
            args = new Bundle();
        }
        if (setting == null) {
            setting = new Setting("", R.color.project_color);
        }
        args.putParcelable(Setting.SETTING, setting);
        cf.setArguments(args);
    }

    public static void setFragmentSetting(ContentFragment cf, int logo, String title) {
        setFragmentSetting(cf, new Setting(logo, title, R.color.project_color));
    }

    //**********************************************************************************************

    public static void replaceContent(Activity activity, ContentFragment cf, Setting setting) {
        setFragmentSetting(cf, setting);
        getSupportActivity(activity).replaceContent(cf);
    }

    public static void replaceContent(Activity activity, Class<? extends ContentFragment> cls, Setting setting) {
        replaceContent(activity, newContentInstance(cls), setting);
    }

    public static void replaceContent(SupportFragment fragment, ContentFragment cf, Setting setting) {
        replaceContent(fragment.getActivity(), cf, setting);
    }

    public static void replaceContent(SupportFragment fragment, Class<? extends ContentFragment> cls, Setting setting) {
        replaceContent(fragment, newContentInstance(cls), setting);
    }

    public static void replaceContent(Activity activity, ContentFragment cf, int logo, String title) {
        replaceContent(activity, cf, new Setting(logo, title));
    }

    public static void replaceContent(Activity activity, Class<? extends ContentFragment> cls, int logo, String title) {
        replaceContent(activity, newContentInstance(cls), logo, title);
    }

    public static void replaceContent(SupportFragment fragment, ContentFragment cf, int logo, String title) {
        replaceContent(fragment.getActivity(), cf, logo, title);
    }

    public static void replaceContent(SupportFragment fragment, Class<? extends ContentFragment> cls, int logo, String title) {
        replaceContent(fragment, newContentInstance(cls), logo, title);
    }

    public static void replaceContent(Activity activity, ContentFragment cf) {
        replaceContent(activity, cf, null);
    }

    public static void replaceContent(Activity activity, Class<? extends ContentFragment> cls) {
        replaceContent(activity, newContentInstance(cls));
    }

    public static void replaceContent(SupportFragment fragment, ContentFragment cf) {
        replaceContent(fragment.getActivity(), cf);
    }

    public static void replaceContent(SupportFragment fragment, Class<? extends ContentFragment> cls) {
        replaceContent(fragment, newContentInstance(cls));
    }


    //**********************************************************************************************

    public static void addContent(Activity activity, ContentFragment cf) {
        getSupportActivity(activity).addContent(cf);
    }

    public static void addContent(Activity activity, Class<? extends ContentFragment> cls) {
        addContent(activity, newContentInstance(cls));
    }

    public static void addContent(SupportFragment fragment, ContentFragment cf) {
        addContent(fragment.getActivity(), cf);
    }

    public static void addContent(SupportFragment fragment, Class<? extends ContentFragment> cls) {
        addContent(fragment, newContentInstance(cls));
    }

    //**********************************************************************************************

    public static void openContent(Activity activity, ContentFragment cf, Setting setting) {
        setFragmentSetting(cf, setting);
        getSupportActivity(activity).openContent(cf);
    }

    public static void openContent(Activity activity, Class<? extends ContentFragment> cls, Setting setting) {
        openContent(activity, newContentInstance(cls), setting);
    }

    public static void openContent(SupportFragment fragment, ContentFragment cf, Setting setting) {
        openContent(fragment.getActivity(), cf, setting);
    }

    public static void openContent(SupportFragment fragment, Class<? extends ContentFragment> cls, Setting setting) {
        openContent(fragment, newContentInstance(cls), setting);
    }

    public static void openContent(Activity activity, ContentFragment cf, int logo, String title) {
        openContent(activity, cf, new Setting(logo, title));
    }

    public static void openContent(Activity activity, Class<? extends ContentFragment> cls, int logo, String title) {
        openContent(activity, newContentInstance(cls), logo, title);
    }

    public static void openContent(SupportFragment fragment, ContentFragment cf, int logo, String title) {
        openContent(fragment.getActivity(), cf, logo, title);
    }

    public static void openContent(SupportFragment fragment, Class<? extends ContentFragment> cls, int logo, String title) {
        openContent(fragment, newContentInstance(cls), logo, title);
    }

    public static void openContent(Activity activity, ContentFragment cf) {
        getSupportActivity(activity).openContent(cf);
    }

    public static void openContent(Activity activity, Class<? extends ContentFragment> cls) {
        openContent(activity, newContentInstance(cls));
    }

    public static void openContent(SupportFragment fragment, ContentFragment cf) {
        openContent(fragment.getActivity(), cf);
    }

    public static void openContent(SupportFragment fragment, Class<? extends ContentFragment> cls) {
        openContent(fragment, newContentInstance(cls));
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

    public static Setting parcelableSetting(ContentFragment fragment) {
        Bundle arg = fragment.getArguments();
        if (arg == null) {
            return new Setting();
        }
        return arg.getParcelable(Setting.SETTING);
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
