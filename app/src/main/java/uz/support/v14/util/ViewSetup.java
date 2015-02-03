package uz.support.v14.util;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;


public class ViewSetup {

    public final View view;

    private SparseArray<View> cachedViews = new SparseArray<View>();

    public ViewSetup(View parent) {
        this.view = parent;
    }

    public ViewSetup(LayoutInflater inflater, @Nullable ViewGroup container, int layoutId) {
        this.view = inflater.inflate(layoutId, container, false);
    }

    public ViewSetup(Context context, int layoutId) {
        LayoutInflater inflater = LayoutInflater.from(context);
        this.view = inflater.inflate(layoutId, null);
    }

    public <T extends View> T id(int resId) {
        View v = cachedViews.get(resId);
        if (v == null) {
            v = view.findViewById(resId);
            if (v == null) {
                throw new ViewSetupNullPointerException("View is null");
            }
            cachedViews.put(resId, v);
        }
        return (T) v;
    }

    public TextView textView(int resId) {
        return id(resId);
    }

    public ImageView imageView(int resId) {
        return id(resId);
    }

    public EditText editText(int resId) {
        return id(resId);
    }

    public Button button(int resId) {
        return id(resId);
    }

    public CheckBox checkBox(int resId) {
        return id(resId);
    }

    public Spinner spinner(int resId) {
        return id(resId);
    }
}
