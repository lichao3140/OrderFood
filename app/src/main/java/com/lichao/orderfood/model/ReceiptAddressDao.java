package com.lichao.orderfood.model;

import android.content.Context;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.lichao.orderfood.model.bean.ReceiptAddressBean;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2017-11-23.
 */

public class ReceiptAddressDao {
    private Dao<ReceiptAddressBean, Integer> dao;

    public ReceiptAddressDao(Context context) {
        if (dao == null) {
            //获取操作ReceiptAddressBean的dao对象
            dao = DBHelper.getInstance(context).getDao(ReceiptAddressBean.class);
        }
    }

    //插入一个地址方法
    public void insert(ReceiptAddressBean receiptAddressBean) {
        try {
            dao.create(receiptAddressBean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //删除一个地址方法
    public void delete(ReceiptAddressBean receiptAddressBean) {
        try {
            dao.delete(receiptAddressBean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //修改一个地址方法
    public void update(ReceiptAddressBean receiptAddressBean) {
        try {
            dao.update(receiptAddressBean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //查询所有地址的方法
    public List<ReceiptAddressBean> queryAllAddress(int userId) {
        try {
            QueryBuilder<ReceiptAddressBean, Integer> queryBuilder = dao.queryBuilder();
            List<ReceiptAddressBean> userAddressList = queryBuilder.where().eq("uid", userId).query();
            return userAddressList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //查询选中默认送货地址方法
    public List<ReceiptAddressBean> querySelectAddress(int userId) {
        try {
            QueryBuilder<ReceiptAddressBean, Integer> queryBuilder = dao.queryBuilder();
            List<ReceiptAddressBean> isSelectList = queryBuilder.where().eq("isSelect", 1).and().eq("uid",userId).query();
            return isSelectList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
