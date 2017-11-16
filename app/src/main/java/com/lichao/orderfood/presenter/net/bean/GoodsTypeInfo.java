package com.lichao.orderfood.presenter.net.bean;

import java.util.List;

/**
 * Created by Administrator on 2017-11-16.
 */

public class GoodsTypeInfo {
    private int id;//商品分类id
    private int count;//选择此类商品的总数量
    private String name;//商品分类名称
    private String info;//商品分类详细信息
    private List<GoodsInfo> list;//商品信息的集合

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<GoodsInfo> getList() {
        return list;
    }

    public void setList(List<GoodsInfo> list) {
        this.list = list;
    }
}
