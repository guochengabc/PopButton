package com.gc.popbutton;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.gc.popbutton.adapter.PopupAdapter;
import com.gc.popbutton.adapter.PopupDoubleAdapter;
import com.gc.popbutton.bean.PopButtonBean;
import com.gc.popbutton.inter.PopItemValue;
import com.gc.popbutton.inter.PopupButtonListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义的带弹出框的按钮,类似于美团和大众点评的筛选框
 * Created by Chris on 2014/12/8.
 */
@SuppressLint("AppCompatCustomView")
public class PopButton extends Button implements PopupWindow.OnDismissListener {
    private int normalBg;//正常状态下的背景
    private int pressBg;//按下状态下的背景
    private int normalIcon;//正常状态下的图标
    private int pressIcon;//按下状态下的图标
    private PopupWindow popupWindow;
    private Context context;
    private int screenWidth;
    private int screenHeight;
    private int paddingTop;
    private int paddingLeft;
    private int paddingRight;
    private int paddingBottom;
    private PopupButtonListener listener;
    private PopItemValue popItemValue;

    public PopButton(Context context) {
        super(context);
        this.context = context;
    }

    public PopButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initAttrs(context, attrs);
        initBtn(context);
    }

    public PopButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    //初始化各种自定义参数
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, com.gc.popbutton.R.styleable.popupbtn);

        normalBg = typedArray.getResourceId(com.gc.popbutton.R.styleable.popupbtn_normalBg, -1);
        pressBg = typedArray.getResourceId(com.gc.popbutton.R.styleable.popupbtn_pressBg, -1);
        normalIcon = typedArray.getResourceId(com.gc.popbutton.R.styleable.popupbtn_normalIcon, -1);
        pressIcon = typedArray.getResourceId(com.gc.popbutton.R.styleable.popupbtn_pressIcon, -1);
        typedArray.recycle();
    }

    /**
     * 初始话各种按钮样式
     */
    private void initBtn(final Context context) {
        paddingTop = this.getPaddingTop();
        paddingLeft = this.getPaddingLeft();
        paddingRight = this.getPaddingRight();
        paddingBottom = this.getPaddingBottom();
        setNormal();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();
        screenHeight = wm.getDefaultDisplay().getHeight();
    }

    /**
     * 隐藏弹出框
     */
    public void hidePopup() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    /**
     * 设置自定义接口
     *
     * @param listener
     */
    private void setListener(PopupButtonListener listener) {
        this.listener = listener;
    }

    /**
     * 设置popupwindow的view
     *
     * @param view
     */
    private void setPopupView(final View view) {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow == null) {
                    LinearLayout layout = new LinearLayout(context);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    view.setLayoutParams(params);
                    layout.addView(view);
                    layout.setBackgroundColor(context.getResources().getColor(com.gc.popbutton.R.color.white));
                    popupWindow = new PopupWindow(layout, screenWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
                    popupWindow.setFocusable(true);
                    popupWindow.setBackgroundDrawable(context.getResources().getDrawable(com.gc.popbutton.R.color.white));
                    popupWindow.setOutsideTouchable(true);
                    popupWindow.setOnDismissListener(PopButton.this);
                    layout.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });
                }
                if (listener != null) {
                    listener.onShow();
                }
                setPress();
                popupWindow.showAsDropDown(PopButton.this);
            }
        });
    }

    /**
     * 设置选中时候的按钮状态
     */
    private void setPress() {
        if (pressBg != -1) {
            this.setBackgroundResource(pressBg);
            this.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        }
        if (pressIcon != -1) {
            Drawable drawable = getResources().getDrawable(pressIcon);
            /// 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            this.setCompoundDrawables(null, null, drawable, null);
        }
    }

    /**
     * 设置正常模式下的按钮状态
     */
    private void setNormal() {
        if (normalBg != -1) {
            this.setBackgroundResource(normalBg);
            this.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        }
        if (normalIcon != -1) {
            Drawable drawable = getResources().getDrawable(normalIcon);
            /// 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            this.setCompoundDrawables(null, null, drawable, null);
        }
    }

    @Override
    public void onDismiss() {
        setNormal();
        if (listener != null) {
            listener.onHide();
        }
    }

    /**
     * @param activity
     * @param listLeft
     */
    public void setOneClickData(Activity activity, final List<PopButtonBean.PopInnerBean> listLeft) {
        if (popItemValue == null) {
            popItemValue = (PopItemValue) activity;
        }

        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(com.gc.popbutton.R.layout.popup, null);
        ListView lv = (ListView) view.findViewById(com.gc.popbutton.R.id.lv);
        Button btn_pop = ((Button) view.findViewById(com.gc.popbutton.R.id.btn_pop));
        final PopupAdapter adapterOne = new PopupAdapter(activity, com.gc.popbutton.R.layout.popup_item, listLeft, com.gc.popbutton.R.drawable.normal, com.gc.popbutton.R.drawable.press);
        lv.setAdapter(adapterOne);
        setPopupView(view);
        btn_pop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                List<PopButtonBean.PopInnerBean> list = adapterOne.getList() == null ? new ArrayList<PopButtonBean.PopInnerBean>() : adapterOne.getList();
                popItemValue.getBackBean(list);
                hidePopup();
            }
        });
    }

    /**
     * @param activity
     * @param listLeft
     * @param listRight 2级联动
     */
    public void setDoubleClickData(Activity activity, List<PopButtonBean.PopInnerBean> listLeft, final List<List<PopButtonBean.PopInnerBean>> listRight) {
        if (popItemValue == null) {
            popItemValue = (PopItemValue) activity;
        }
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view2 = inflater.inflate(com.gc.popbutton.R.layout.popup2, null);
        ListView pLv = (ListView) view2.findViewById(com.gc.popbutton.R.id.parent_lv);
        final ListView cLv = (ListView) view2.findViewById(com.gc.popbutton.R.id.child_lv);
        final List<PopButtonBean.PopInnerBean> cValues = new ArrayList<>();
        cValues.addAll(listRight.get(0));
        final PopupDoubleAdapter pAdapter = new PopupDoubleAdapter(activity, com.gc.popbutton.R.layout.popup_double_item, listLeft, com.gc.popbutton.R.drawable.normal, com.gc.popbutton.R.drawable.press2);
        final PopupDoubleAdapter cAdapter = new PopupDoubleAdapter(activity, com.gc.popbutton.R.layout.popup_double_item, cValues, com.gc.popbutton.R.drawable.normal, com.gc.popbutton.R.drawable.press);
        pAdapter.setPressPostion(0);
        pLv.setAdapter(pAdapter);
        cLv.setAdapter(cAdapter);
        pLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pAdapter.setPressPostion(position);
                pAdapter.notifyDataSetChanged();
                cValues.clear();
                cValues.addAll(listRight.get(position));
                cAdapter.notifyDataSetChanged();
                cAdapter.setPressPostion(-1);
                cLv.setSelection(0);
            }
        });
        cLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cAdapter.setPressPostion(position);
                cAdapter.notifyDataSetChanged();
                popItemValue.getDouleBackBean(cValues.get(position));
                hidePopup();
            }
        });
        setPopupView(view2);
    }
}
