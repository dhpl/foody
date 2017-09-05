package com.philong.foody.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Long on 9/4/2017.
 */

public class HinhAnh implements Parcelable {

    private String mPath;
    private boolean mChecked;

    public HinhAnh(String path, boolean checked) {
        mPath = path;
        mChecked = checked;
    }

    protected HinhAnh(Parcel in) {
        mPath = in.readString();
        mChecked = in.readByte() != 0;
    }

    public static final Creator<HinhAnh> CREATOR = new Creator<HinhAnh>() {
        @Override
        public HinhAnh createFromParcel(Parcel in) {
            return new HinhAnh(in);
        }

        @Override
        public HinhAnh[] newArray(int size) {
            return new HinhAnh[size];
        }
    };

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        mPath = path;
    }

    public boolean isChecked() {
        return mChecked;
    }

    public void setChecked(boolean checked) {
        mChecked = checked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mPath);
        parcel.writeByte((byte) (mChecked ? 1 : 0));
    }
}
