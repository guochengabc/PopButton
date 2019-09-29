package com.xm.popwinproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import com.gc.popbutton.PopButton;
import com.gc.popbutton.bean.PopButtonBean;
import com.gc.popbutton.inter.PopItemValue;

public class MainActivity extends AppCompatActivity implements PopItemValue {
    private PopButton btn;
    private PopButton btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=((PopButton) findViewById(R.id.btn));
        btn2=((PopButton) findViewById(R.id.btn2));
        List<PopButtonBean.PopInnerBean> listOne = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            PopButtonBean.PopInnerBean bean = new PopButtonBean.PopInnerBean();
            bean.setId((i * i) + "");
            bean.setName("点击item" + i);
            listOne.add(bean);
        }
        List<PopButtonBean.PopInnerBean> pList = new ArrayList<>();
        final List<List<PopButtonBean.PopInnerBean>> cList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            PopButtonBean.PopInnerBean bean = new PopButtonBean.PopInnerBean();
            bean.setName("绥化" + i);
            bean.setId((i * i) + "");
            pList.add(bean);
            List<PopButtonBean.PopInnerBean> t = new ArrayList<>();
            for (int j = 0; j < 25; j++) {
                PopButtonBean.PopInnerBean beans = new PopButtonBean.PopInnerBean();
                beans.setName(pList.get(i).getName() + "-红旗满族乡" + j);
                beans.setId((i * j) + "123");
                t.add(beans);
            }
            cList.add(t);
        }
        btn2.setDoubleClickData(this, pList, cList);
        btn.setOneClickData(this, listOne);
    }

    @Override
    public void getBackBean(List<PopButtonBean.PopInnerBean> list) {
        StringBuffer name = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isCheck()) {
                if (i < list.size() - 1)
                    name.append(list.get(i).getName() + "-");
                else
                    name.append(list.get(i).getName());
            }
            btn.setText(name);
        }


    }

    @Override
    public void getDouleBackBean(PopButtonBean.PopInnerBean bean) {
        btn2.setText(bean.getName());
    }
}
