package com.lichao.orderfood.presenter.net;

import com.lichao.orderfood.presenter.net.bean.ResponseInfo;
import com.lichao.orderfood.utils.Constant;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017-11-16.
 */

public interface ResponseInfoAPI {
    //指定请求方式
    //请求完整链接地址
    //请求方法,请求参数
    //请求的响应结果
    @GET(Constant.HOME_URL)
    Call<ResponseInfo> getHomeInfo(@Query("latitude") String latitude, @Query("longitude") String longitude);

    @GET(Constant.BUSINESS)
    Call<ResponseInfo> getBusinessInfo(@Query("sellerId")long sellerId);
}
