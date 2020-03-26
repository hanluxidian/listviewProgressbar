package com.example.listviewjubushuaxin;

public class DataBean2 implements BeanType{
    private String mName;
    private String mAction;

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmAction(String mAction) {
        this.mAction = mAction;
    }

    public String getmName() {
        return mName;
    }

    public String getmAction() {
        return mAction;
    }

    public DataBean2(String mName, String mAction) {
        this.mName = mName;
        this.mAction = mAction;
    }

    @Override
    public int getType() {
        return BeanType.Type_2;
    }
}
