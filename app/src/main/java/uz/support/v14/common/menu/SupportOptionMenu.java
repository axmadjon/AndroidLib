package uz.support.v14.common.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.FrameLayout;

import uz.support.v14.R;
import uz.support.v14.common.mold.Command;
import uz.support.v14.common.mold.SupportActivity;
import uz.support.v14.util.SupportUtil;
import uz.support.v14.util.ViewSetup;

public class SupportOptionMenu extends Fragment {

    public static SupportOptionMenu newInstance() {
        return new SupportOptionMenu();
    }

    public static int SHOW = 1;
    public static int HIDE = 2;

    private ViewSetup vsRoot;

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vsRoot = new ViewSetup(inflater, container, R.layout.s_menu);
        return vsRoot.view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hide();
    }

    public void show() {
        visibility(SHOW);
    }

    public void hide() {
        visibility(HIDE);
    }

    public void visibility(int visible) {
        if (vsRoot != null) {
            vsRoot.view.setVisibility(visible == SHOW ? View.VISIBLE : View.GONE);
        }
    }

    protected Command onNegativeButton() {
        return null;
    }

    protected Command onPositiveButton() {
        return null;
    }
}
