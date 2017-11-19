package com.lichao.orderfood.model.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2017-11-19.
 */

//根据UserInfo中的每一个字段创建一张叫t_user表
@DatabaseTable(tableName = "t_user")
public class UserInfo {
    //唯一性id,数据库中字段的名称和_id一致
    @DatabaseField(id = true)
    private int _id;
    //数据库中需要映射balance字段
    @DatabaseField()
    private float balance;

    @DatabaseField()
    private float discount;

    @DatabaseField()
    private int integral;

    @DatabaseField()
    private String name;

    @DatabaseField()
    private String phone;

    @DatabaseField()
    private int isLogin; //1 代表已经登陆     0 代表没有登陆

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getLogin() {
        return isLogin;
    }

    public void setLogin(int isLogin) {
        this.isLogin = isLogin;
    }
}
