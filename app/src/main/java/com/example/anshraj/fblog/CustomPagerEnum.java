package com.example.anshraj.fblog;

/**
 * Created by ansh raj on 17-May-16.
 */
public enum CustomPagerEnum {

    Girl(R.string.red, R.layout.view_all),
    Shop(R.string.blue, R.layout.view_girl),
    Single(R.string.orange, R.layout.view_boys),
    Boy(R.string.boy,R.layout.view_shop),
    All(R.string.all,R.layout.view_office);
    private int mTitleResId;
    private int mLayoutResId;

    CustomPagerEnum(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }

}
