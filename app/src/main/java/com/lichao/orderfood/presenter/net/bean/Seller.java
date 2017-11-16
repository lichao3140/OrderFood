package com.lichao.orderfood.presenter.net.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017-11-16.
 */

public class Seller implements Serializable{
    private long id;
    private String pic;//商家头像
    private String name;

    private String score;
    private String sale;
    private String ensure;

    private String invoice;
    private String sendPrice;//起送价
    private String deliveryFee;//配送费

    private String recentVisit;
    private String distance;
    private String time;

    private ArrayList<ActivityInfo> activityList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public String getEnsure() {
        return ensure;
    }

    public void setEnsure(String ensure) {
        this.ensure = ensure;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getSendPrice() {
        return sendPrice;
    }

    public void setSendPrice(String sendPrice) {
        this.sendPrice = sendPrice;
    }

    public String getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(String deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public String getRecentVisit() {
        return recentVisit;
    }

    public void setRecentVisit(String recentVisit) {
        this.recentVisit = recentVisit;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<ActivityInfo> getActivityList() {
        return activityList;
    }

    public void setActivityList(ArrayList<ActivityInfo> activityList) {
        this.activityList = activityList;
    }
}
