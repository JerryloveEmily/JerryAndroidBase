package com.jerry.jerryandroidbase.features;

/**
 * 主视图item
 * Created by Jerry on 2016/4/11.
 */
public class MainItem {

    private String title;
    private Class<?> cls;

    public MainItem() {
    }

    public MainItem(String title, Class<?> cls) {
        this.title = title;
        this.cls = cls;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class<?> getCls() {
        return cls;
    }

    public void setCls(Class<?> cls) {
        this.cls = cls;
    }

    @Override
    public String toString() {
        return "MainItem{" +
                "title='" + title + '\'' +
                ", cls=" + cls +
                '}';
    }
}
