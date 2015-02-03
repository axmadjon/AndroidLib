package uz.support.v14.util;

import android.app.Activity;

import uz.support.v14.common.fragment.ContentFragment;
import uz.support.v14.common.fragment.IndexContentFragment;
import uz.support.v14.common.fragment.SupportFragment;
import uz.support.v14.common.mold.SupportActivity;

public final class SupportUtil {

    public static SupportActivity getSupportActivity(Activity a) {
        return (SupportActivity) a;
    }

    public static SupportFragment getContentFragment(Activity activity) {
        return getSupportActivity(activity).getContentFragment();
    }

    public static IndexContentFragment getIndexFragment(Activity activity) {
        return getSupportActivity(activity).getIndexContent();
    }

    public static void replaceContent(Activity activity, ContentFragment cf) {
        getSupportActivity(activity).replaceContent(cf);
    }

    public static void addContent(Activity activity, ContentFragment cf) {
        getSupportActivity(activity).addContent(cf);
    }

    public static void openContent(Activity activity, ContentFragment cf) {
        getSupportActivity(activity).openContent(cf);
    }

    private SupportUtil() {
    }
}
