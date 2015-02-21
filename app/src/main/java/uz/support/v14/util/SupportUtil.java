package uz.support.v14.util;

import android.app.Activity;

import uz.support.v14.app.ContentFragment;
import uz.support.v14.app.IndexFragment;
import uz.support.v14.app.SupportFragment;
import uz.support.v14.app.SupportActivity;

public final class SupportUtil {

    private SupportUtil() {
    }

    public static SupportActivity getSupportActivity(Activity a) {
        return (SupportActivity) a;
    }

    public static SupportFragment getContentFragment(Activity activity) {
        return getSupportActivity(activity).getContentFragment();
    }

    public static IndexFragment getIndexFragment(Activity activity) {
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
}
