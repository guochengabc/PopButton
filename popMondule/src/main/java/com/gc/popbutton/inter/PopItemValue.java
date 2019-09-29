package com.gc.popbutton.inter;

import java.util.List;

import com.gc.popbutton.bean.PopButtonBean;

public interface PopItemValue {
    /**
     * @param list 多选回传过来的值
     */
    void getBackBean(List<PopButtonBean.PopInnerBean> list);

    /**
     * @param bean 设置点击二次返回值
     */
    void getDouleBackBean(PopButtonBean.PopInnerBean bean);
}
