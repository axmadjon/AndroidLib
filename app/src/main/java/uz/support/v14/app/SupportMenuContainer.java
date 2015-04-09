package uz.support.v14.app;

import android.view.View;

import uz.support.v14.common.Command;

public class SupportMenuContainer {

    public final int iconResId;
    public final Command command;
    public final View view;
    int id = 0;

    public SupportMenuContainer(int iconResId, Command command) {
        this.iconResId = iconResId;
        this.command = command;
        this.view = null;
    }

    public SupportMenuContainer(View view) {
        this.iconResId = 0;
        this.command = null;
        this.view = view;
    }
}
