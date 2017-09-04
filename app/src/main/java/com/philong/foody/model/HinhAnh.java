package com.philong.foody.model;

/**
 * Created by Long on 9/4/2017.
 */

public class HinhAnh {

    private String mPath;
    private boolean mChecked;

    public HinhAnh(String path, boolean checked) {
        mPath = path;
        mChecked = checked;
    }

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
}
