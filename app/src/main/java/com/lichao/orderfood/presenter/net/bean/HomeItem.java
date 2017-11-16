package com.lichao.orderfood.presenter.net.bean;

import java.util.List;

/**
 * Created by Administrator on 2017-11-16.
 */

public class HomeItem {
    public int type;
    public Seller seller;
    public List<String> recommendInfos;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public List<String> getRecommendInfos() {
        return recommendInfos;
    }

    public void setRecommendInfos(List<String> recommendInfos) {
        this.recommendInfos = recommendInfos;
    }
}
