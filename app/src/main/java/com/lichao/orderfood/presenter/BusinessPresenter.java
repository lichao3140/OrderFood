package com.lichao.orderfood.presenter;

import android.content.Context;
import android.util.Log;
import com.lichao.orderfood.presenter.net.bean.GoodsInfo;
import com.lichao.orderfood.presenter.net.bean.GoodsTypeInfo;
import com.lichao.orderfood.ui.BusinessActivity;
import com.lichao.orderfood.ui.fragment.GoodsFragment;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * 获取购物车中商品列表集合的方法
     * @return
     */
    public List<GoodsInfo> getShopCartList() {
        ArrayList<GoodsInfo> shopCartList = new ArrayList<>();
        //1.获取右侧商品列表的集合,判断其每一件商品的数据是否大于0,如果大于0就需要在购物车中出现
        //右侧商品的集合放置在右侧商品对应得数据适配器中
        //goodsFragment创建右侧数据适配器
        //通过上下文环境获取BusinessActivity--->GoodsFragment---->GoodsAdapter----->商品列表集合
        GoodsFragment goodsFragment = ((BusinessActivity) context).getGoodsFragment();
        ArrayList<GoodsInfo> goodsInfoList = goodsFragment.goodsAdapter.getData();
        for (int i =0; i < goodsInfoList.size(); i++) {
            GoodsInfo goodsInfo = goodsInfoList.get(i);
            if (goodsInfo.getCount() > 0) {
                //在购物车中显示
                shopCartList.add(goodsInfo);
            }
        }
        return shopCartList;
    }

    /**
     * 清空购物车
     */
    public void clearShopCart() {
        //1.清空右侧商品列表中购买商品
        clearGoodsAdapterData();
        //2.清空左侧分类列表中选中商品
        clearGoodsTypeAdapterData();
        //3.清空购物车总数和总金额,调用更新购物车总数和金额方法即可
        updateShopCart();
        //4.隐藏购物车的列表
        ((BusinessActivity) context).dismissBottomSheetLayout();
    }

    private void clearGoodsTypeAdapterData() {
        GoodsFragment goodsFragment = ((BusinessActivity) context).getGoodsFragment();
        if (goodsFragment != null) {
            List<GoodsTypeInfo> goodsTypeInfoList = goodsFragment.getGoodsTypeAdapter().getData();
            for (int i = 0; i < goodsTypeInfoList.size(); i++) {
                if (goodsTypeInfoList.get(i).getCount() > 0) {
                    goodsTypeInfoList.get(i).setCount(0);
                }
            }
            goodsFragment.getGoodsTypeAdapter().notifyDataSetChanged();
        }
    }

    private void clearGoodsAdapterData() {
        GoodsFragment goodsFragment = ((BusinessActivity) context).getGoodsFragment();
        if (goodsFragment != null) {
            ArrayList<GoodsInfo> goodsInfoList = goodsFragment.goodsAdapter.getData();
            for (int i = 0; i < goodsInfoList.size(); i++) {
                if (goodsInfoList.get(i).getCount() > 0) {
                    goodsInfoList.get(i).setCount(0);
                }
            }
            goodsFragment.goodsAdapter.notifyDataSetChanged();
        }
    }
}
