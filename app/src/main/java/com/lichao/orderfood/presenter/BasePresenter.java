package com.lichao.orderfood.presenter;

import com.lichao.orderfood.presenter.net.ResponseInfoAPI;
import com.lichao.orderfood.presenter.net.bean.ResponseInfo;
import com.lichao.orderfood.utils.Constant;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017-11-16.
 */

public abstract class BasePresenter {
    public ResponseInfoAPI responseInfoAPI;
    private final HashMap<String, String> errorMap;

    //网络请求
    public BasePresenter() {
        errorMap = new HashMap<>();

        errorMap.put("1","服务器数据没有更新");
        errorMap.put("2","服务器忙");
        errorMap.put("3","服务器挂掉");

        //1.初始化retrofit对象
        //2.指定了访问服务器工程的主机名
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //3.指定具体的网络请求实体对象
        //发送一个网络请求(完整的链接地址,请求方式(GET POST),请求过程中参数,请求的响应结果)
        responseInfoAPI = retrofit.create(ResponseInfoAPI.class);
    }

    class CallBackAdapter implements Callback<ResponseInfo> {

        @Override
        public void onResponse(Call<ResponseInfo> call, Response<ResponseInfo> response) {
            //请求成功,获取服务器返回的所有的json串
            ResponseInfo body = response.body();
            if (body.getCode().equals("0")) {
                //请求成功
                String json = body.getData();
                //抽象的解析json方法,让子类对json做具体的解析过程
                parseJson(json);
            } else {
                //请求失败
                String errorMessage = errorMap.get(body.getCode());
                onFailure(call,new RuntimeException(errorMessage));
            }
        }

        @Override
        public void onFailure(Call<ResponseInfo> call, Throwable t) {
            //请求失败
            if (t instanceof RuntimeException) {
                String message = t.getMessage();
                showError(message);
            }
        }
    }

    protected abstract void showError(String message);
    protected abstract void parseJson(String json);
}
