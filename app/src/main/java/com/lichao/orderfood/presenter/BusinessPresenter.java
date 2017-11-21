package com.lichao.orderfood.presenter;

import android.content.Context;
import android.util.Log;

import com.lichao.orderfood.presenter.net.bean.GoodsInfo;
import com.lichao.orderfood.ui.BusinessActivity;
import com.lichao.orderfood.ui.fragment.GoodsFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017-11-21.
 */

public class BusinessPresenter extends BasePresenter {
    private Context context;

    public BusinessPresenter(Context context) {
        this.context = context;
    }

    @Override
    protected void showError(String message) {

    }

    @Override
    protected void parseJson(String json) {

    }

    /**
     * 更新购物车中数据方法
     */
    public void updateShopCart() {
        //获取右侧商品列表中的每一件商品,判断其count值是否大于0,如果大于0,数量累加
        int totalCount = 0;
        //提供一个金额计数变量
        float totalPrice = 0.0f;
        GoodsFragment goodsFragment = ((BusinessActivity)context).getGoodsFragment();
        if (goodsFragment != null) {
            ArrayList<GoodsInfo> goodsInfoList = goodsFragment.goodsAdapter.getData();
            for (int i = 0; i < goodsInfoList.size(); i++) {
                GoodsInfo goodsInfo = goodsInfoList.get(i);
                if (goodsInfo.getCount() > 0) {
                    //购物车中商品数量
                    totalCount += goodsInfo.getCount();
                    //购物车总金额  商品数量*单价 然后多个商品总金额进行累加
                    totalPrice += goodsInfo.getCount() * goodsInfo.getNewPrice();
                }
            }
            //根据总数量和总金额更新购物车气泡,金钱字符串
            Log.i("BusinessPresenter:","totalCount = "+totalCount);
            Log.i("BusinessPresenter:","totalPrice = "+totalPrice);
            //更新钱和数量方法
            ((BusinessActivity) context).updateShopCartCount(totalCount, totalPrice);
        }
    }
}
