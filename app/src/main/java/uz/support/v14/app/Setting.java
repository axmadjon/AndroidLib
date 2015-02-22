package uz.support.v14.app;

import android.os.Parcel;
import android.os.Parcelable;

public class Setting implements Parcelable {

    public static final String SETTING = "uz.support.v14.app.setting";

    public final int logoResId;
    public final String title;
    public final String subTitle;
    public final int backgroundResId;


    public Setting(int logoResId, String title, String subTitle, int backgroundResId) {
        this.logoResId = logoResId;
        this.title = title;
        this.subTitle = subTitle;
        this.backgroundResId = backgroundResId;
    }

    public Setting(int logoResId, String title, String subTitle) {
        this(logoResId, title, subTitle, 0);
    }

    public Setting(String title, String subTitle, int backgroundResId) {
        this(0, title, subTitle, backgroundResId);
    }

    public Setting(String title, String subTitle) {
        this(0, title, subTitle, 0);
    }

    public Setting(String title, int backgroundResId) {
        this(0, title, "", backgroundResId);
    }

    public Setting(int logoResId, String title, int backgroundResId) {
        this(logoResId, title, "", backgroundResId);
    }

    public Setting(int logoResId, String title) {
        this(logoResId, title, "", 0);
    }

    public Setting(String title) {
        this(0, title, "", 0);
    }

    public Setting() {
        this(0, "", "", 0);
    }

    public Setting(Parcel parcel) {
        this(parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readInt());
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
        dest.writeInt(backgroundResId);
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
