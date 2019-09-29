package com.gc.popbutton.utils;

import android.app.Activity;
import android.view.View;

public class ControlWidthHeight {
    /**
     * @param view
     * @return 获取控件高度转化dp
     */
    public static float getHeight(Activity activity, View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        float height = view.getMeasuredHeight();
        return PopDensityUtils.px2dp(activity, height);
    }

    /**
     * @param view
     * @return 获取控件宽度转化dp
     */
    public static float getWidth(Activity activity, View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        float width = view.getMeasuredWidth();
        return PopDensityUtils.px2dp(activity, width);
    }

}
