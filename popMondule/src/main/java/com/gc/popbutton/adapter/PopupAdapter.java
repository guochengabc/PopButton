package com.gc.popbutton.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import com.gc.popbutton.R;
import com.gc.popbutton.bean.PopButtonBean;

/**
 * 自定义的弹出框列表适配器,类似于大众点评或美团,如果想要此种效果可以直接使用
 * Created by Chris on 2014/12/8.
 */
public class PopupAdapter extends ArrayAdapter<PopButtonBean.PopInnerBean> {
    private int resource;
    private int normalBg;
    private int pressBg;
    private List<PopButtonBean.PopInnerBean> list;
    public PopupAdapter(Context context, int resource, List<PopButtonBean.PopInnerBean> objects, int normalBg, int pressBg) {
        super(context, resource, objects);
        initParams(resource, normalBg, pressBg);
        this.list=objects;
    }

    private void initParams(int resource, int normalBg, int pressBg) {
        this.resource = resource;
        this.normalBg = normalBg;
        this.pressBg = pressBg;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final PopButtonBean.PopInnerBean popInnerBean = list.get(position);
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resource, null);
            final ViewHolder  holder = new ViewHolder();
            holder.tv = (TextView) view.findViewById(R.id.tv);
            holder.cb=((CheckBox) view.findViewById(R.id.cb));
            holder.line_lv=((LinearLayout) view.findViewById(R.id.line_lv));
            holder.line_lv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopButtonBean.PopInnerBean checkTag = (PopButtonBean.PopInnerBean) holder.cb.getTag();
                    boolean check = checkTag.isCheck();
                    if (check){//如果设置勾选，进行反向
                        holder.line_lv.setBackgroundResource(normalBg);
                    }else{
                        holder.line_lv.setBackgroundResource(pressBg);
                    }
                    checkTag.setCheck(check==true?false:true);
                    holder.cb.setChecked(checkTag.isCheck());
                }
            });
            holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    PopButtonBean.PopInnerBean checkTag = (PopButtonBean.PopInnerBean) holder.cb.getTag();
                    if (buttonView.isChecked()){
                        holder.line_lv.setBackgroundResource(pressBg);
                    }else{
                        holder.line_lv.setBackgroundResource(normalBg);
                    }
                    checkTag.setCheck(buttonView.isChecked());
                }
            });
            view.setTag(holder);
            holder.cb.setTag(popInnerBean);
        } else {
            view = convertView;
            ((ViewHolder) view.getTag()).cb.setTag(popInnerBean);
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.cb.setChecked(popInnerBean.isCheck());
        holder.tv.setText(popInnerBean.getName());
        holder.tv.setGravity(Gravity.LEFT);
        if (popInnerBean.isCheck()) {
            holder.line_lv.setBackgroundResource(pressBg);
        } else {
            holder.line_lv.setBackgroundResource(normalBg);
        }
        return view;
    }

    public List<PopButtonBean.PopInnerBean> getList() {
        return list;
    }

    class ViewHolder {
        LinearLayout line_lv;
        TextView tv;
        CheckBox cb;
    }

}
