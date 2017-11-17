package com.lichao.orderfood.presenter;

import android.util.Log;
import com.google.gson.Gson;
import com.lichao.orderfood.presenter.net.bean.BusinessInfo;
import com.lichao.orderfood.presenter.net.bean.GoodsInfo;
import com.lichao.orderfood.presenter.net.bean.GoodsTypeInfo;
import com.lichao.orderfood.presenter.net.bean.ResponseInfo;
import com.lichao.orderfood.presenter.net.bean.Seller;
import com.lichao.orderfood.ui.adapter.GoodsAdapter;
import com.lichao.orderfood.ui.adapter.GoodsTypeAdapter;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;

/**
 * Created by Administrator on 2017-11-17.
 */

public class GoodsPresenter extends BasePresenter {
    private GoodsAdapter goodsAdapter;
    private Seller seller;
    private GoodsTypeAdapter goodsTypeAdapter;
    private List<GoodsTypeInfo> goodsTypeInfoList;
    private ArrayList<GoodsInfo> goodsInfoList;

    public GoodsPresenter(GoodsTypeAdapter goodsTypeAdapter, GoodsAdapter goodsAdapter,Seller seller) {
        this.goodsTypeAdapter = goodsTypeAdapter;
        this.goodsAdapter = goodsAdapter;
        this.seller = seller;
    }

    @Override
    protected void showError(String message) {

    }

    @Override
    protected void parseJson(String json) {
        Log.i("GoodsPresenter:",json);
        Gson gson = new Gson();
        BusinessInfo businessInfo = gson.fromJson(json, BusinessInfo.class);

        //businessInfo中包含了左侧分类的数据,右侧商品列表数据
        //商品分类:
        // 核心字段分类id,
        // 用于计算此分类的商品的总数count

        //获取分类的数据集合
        goodsTypeInfoList = businessInfo.getList();
        //商品分类集合需要填充分类的数据适配器
        goodsTypeAdapter.setData(goodsTypeInfoList);

        // 商品核心字段:
        // 所属分类的typeId,此typeId和分类的id,进行关联,
        // count用于记录商品购买数量
        // sellerId用于记录此商品所属商家
        //typeName用于记录此件商品所属分类的名称

        //生成用于在右侧商品列表种展示数据集合
        initGoodsList();
        //将商品集合在数据适配器中展示
        goodsAdapter.setData(goodsInfoList);
    }

    private void initGoodsList() {
        goodsInfoList = new ArrayList<>();
        for (int i = 0; i < goodsTypeInfoList.size(); i++) {
            //获取每一个分类对象
            GoodsTypeInfo goodsTypeInfo = goodsTypeInfoList.get(i);
            //获取分类对象中的每一个具体的商品对象
            for (int j = 0; j < goodsTypeInfo.getList().size(); j++) {
                GoodsInfo goodsInfo = goodsTypeInfo.getList().get(j);
                //给每一件商品设置分类名称
                goodsInfo.setTypeName(goodsTypeInfo.getName());
                //给每一件商品设置分类id
                goodsInfo.setTypeId(goodsTypeInfo.getId());
                //给每一件商品设置商家
                goodsInfo.setSellerId((int) seller.getId());

                goodsInfoList.add(goodsInfo);
            }
        }
    }

    //提供一个触发网络请求的方法
    public void getBusinessData(long sellerId) {
        Call<ResponseInfo> businessInfo = responseInfoAPI.getBusinessInfo(sellerId);
        businessInfo.enqueue(new CallBackAdapter());
    }
}
