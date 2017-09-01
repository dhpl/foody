package com.philong.foody.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Long on 9/1/2017.
 */

public class TienIch implements Parcelable{

    private String hinhtienich;
    private String tentienich;

    public TienIch() {
    }

    protected TienIch(Parcel in) {
        hinhtienich = in.readString();
        tentienich = in.readString();
    }

    public static final Creator<TienIch> CREATOR = new Creator<TienIch>() {
        @Override
        public TienIch createFromParcel(Parcel in) {
            return new TienIch(in);
        }

        @Override
        public TienIch[] newArray(int size) {
            return new TienIch[size];
        }
    };

    public String getHinhtienich() {
        return hinhtienich;
    }

    public void setHinhtienich(String hinhtienich) {
        this.hinhtienich = hinhtienich;
    }

    public String getTentienich() {
        return tentienich;
    }

    public void setTentienich(String tentienich) {
        this.tentienich = tentienich;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(hinhtienich);
        parcel.writeString(tentienich);
    }
}
