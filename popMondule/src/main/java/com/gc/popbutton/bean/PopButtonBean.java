package com.gc.popbutton.bean;

import java.util.List;

public class PopButtonBean {
    private List<PopInnerBean> popInnerBeans;

    public List<PopInnerBean> getPopInnerBeans() {
        return popInnerBeans;
    }

    public void setPopInnerBeans(List<PopInnerBean> popInnerBeans) {
        this.popInnerBeans = popInnerBeans;
    }

    public static class PopInnerBean {
        String name;
        private String type;
        private String id;
        private boolean isCheck=false;//是否选中  false默认未选中
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }
    }

}
