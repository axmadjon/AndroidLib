package uz.support.v14.app;

import android.os.Parcel;
import android.os.Parcelable;

import uz.support.v14.R;

public class Setting implements Parcelable {

    public static final String SETTING = "uz.support.v14.app.setting";

    public final int logoResId;
    public final String title;
    public final String subTitle;
    public final int toolbarResId;
    public final boolean swipe;

    public Setting(int logoResId, String title, String subTitle, int toolbarResId, boolean swipe) {
        this.logoResId = logoResId;
        this.title = title;
        this.subTitle = subTitle;
        this.toolbarResId = toolbarResId;
        this.swipe = swipe;
    }

    public static final Setting SETTING_DEFAULT = new Setting(0, "", "", R.color.app_color, false);

    public Setting(Parcel parcel) {
        this(parcel.readInt(),
                parcel.readString(),
                parcel.readString(),
                parcel.readInt(),
                "1".equals(parcel.readString()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(logoResId);
        dest.writeString(title);
        dest.writeString(subTitle);
        dest.writeInt(toolbarResId);
        dest.writeString(swipe ? "1" : "0");
    }

    public static final Creator<Setting> CREATOR = new Creator<Setting>() {
        @Override
        public Setting createFromParcel(Parcel source) {
            return new Setting(source);
        }

        @Override
        public Setting[] newArray(int size) {
            return new Setting[size];
        }
    };
}
