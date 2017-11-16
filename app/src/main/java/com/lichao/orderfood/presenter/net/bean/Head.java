package com.lichao.orderfood.presenter.net.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017-11-16.
 */

public class Head {
    ArrayList<Promotion> promotionList;
    ArrayList<Category> categorieList;

    public ArrayList<Promotion> getPromotionList() {
        return promotionList;
    }

    public void setPromotionList(ArrayList<Promotion> promotionList) {
        this.promotionList = promotionList;
    }

    public ArrayList<Category> getCategorieList() {
        return categorieList;
    }

    public void setCategorieList(ArrayList<Category> categorieList) {
        this.categorieList = categorieList;
    }
}
