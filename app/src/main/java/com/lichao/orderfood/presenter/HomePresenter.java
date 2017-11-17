package com.lichao.orderfood.presenter;

import android.util.Log;
import com.google.gson.Gson;
import com.lichao.orderfood.presenter.net.bean.HomeInfo;
import com.lichao.orderfood.presenter.net.bean.ResponseInfo;
import com.lichao.orderfood.ui.adapter.HomeRecyclerViewAdapter;
import retrofit2.Call;

/**
 * Created by Administrator on 2017-11-16.
 */

public class HomePresenter extends BasePresenter {
    private HomeRecyclerViewAdapter adapter;

    public HomePresenter(HomeRecyclerViewAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected void showError(String message) {

    }

    @Override
    protected void parseJson(String json) {
        //在此处解析json
        Log.i("HomePresenter:",json);
        Gson gson = new Gson();
        //获取首页的数据
        HomeInfo homeInfo = gson.fromJson(json, HomeInfo.class);
        adapter.setData(homeInfo);
    }

    /**
     * 触发网络请求的方法
     * @param lat  经度
     * @param lon  纬度
     */
    public void getHomeData(String lat, String lon) {
        Call<ResponseInfo> homeInfo = responseInfoAPI.getHomeInfo(lat, lon);
        //触发callback中的成功或者失败的回调方法
        homeInfo.enqueue(new CallBackAdapter());
    }
}
