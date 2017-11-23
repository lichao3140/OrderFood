package com.lichao.orderfood.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.lichao.orderfood.R;
import com.lichao.orderfood.presenter.net.bean.GoodsInfo;
import com.lichao.orderfood.utils.CountPriceFormater;
import com.lichao.orderfood.utils.OrderInfoUtil2_0;
import com.lichao.orderfood.utils.PayResult;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017-11-23.
 */

public class PayOnlineActivity extends BaseActivity {

    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_residualTime)
    TextView tvResidualTime;
    @BindView(R.id.tv_order_name)
    TextView tvOrderName;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.tv_order_detail)
    TextView tvOrderDetail;
    @BindView(R.id.iv_triangle)
    ImageView ivTriangle;
    @BindView(R.id.ll_order_toggle)
    RelativeLayout llOrderToggle;
    @BindView(R.id.tv_receipt_connect_info)
    TextView tvReceiptConnectInfo;
    @BindView(R.id.tv_receipt_address_info)
    TextView tvReceiptAddressInfo;
    @BindView(R.id.ll_goods)
    LinearLayout llGoods;
    @BindView(R.id.ll_order_detail)
    LinearLayout llOrderDetail;
    @BindView(R.id.tv_pay_money)
    TextView tvPayMoney;
    @BindView(R.id.iv_pay_alipay)
    ImageView ivPayAlipay;
    @BindView(R.id.cb_pay_alipay)
    CheckBox cbPayAlipay;
    @BindView(R.id.tv_selector_other_payment)
    TextView tvSelectorOtherPayment;
    @BindView(R.id.ll_hint_info)
    LinearLayout llHintInfo;
    @BindView(R.id.iv_pay_wechat)
    ImageView ivPayWechat;
    @BindView(R.id.cb_pay_wechat)
    CheckBox cbPayWechat;
    @BindView(R.id.iv_pay_qq)
    ImageView ivPayQq;
    @BindView(R.id.cb_pay_qq)
    CheckBox cbPayQq;
    @BindView(R.id.iv_pay_fenqile)
    ImageView ivPayFenqile;
    @BindView(R.id.cb_pay_fenqile)
    CheckBox cbPayFenqile;
    @BindView(R.id.ll_other_payment)
    LinearLayout llOtherPayment;
    @BindView(R.id.bt_confirm_pay)
    Button btConfirmPay;

    /** 支付宝支付业务：入参app_id */
    public static final String APPID = "2088702310072451";
    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /** 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1 */
    public static final String RSA2_PRIVATE = "";
    public static final String RSA_PRIVATE = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJ2l+s6KEjTWQW7COrH+aKBx+vNfRUfQfaNKe9GjJNDY+4rcXmBVokqiwmsW3rU/nYRuAh0sk2wldWDRGvol/1VBImvhXnp+H6I4P/jU8ewV5ZnH3QUorqW7RE9uLQ6ccyzneJNdEFM85dTAs2JcYyuhyngyhTQ/Wn92gJmNJCU9AgMBAAECgYAP6imfoCCmZNrGK6VxQpWdXORQnVWJImsMiQBm8WlODBmYsxZz8zEnB7dIyE5DTeDA9boQ5+caC84FsBZhGmTkNbEhUydOEWfqXyoJ2sxE8arbWVkq+PQ0JHY63Vk9hzSpUNwPxlBJ5z4a4FYKrUzMCvPsuzvzwmpaD0w3x+qYoQJBANrpCvQkh7QhWEjnGZBYCIqeuKK66wN2lJxGlJuozn+i2zP4abbrtL3Y6H0YgLzUsefG802vILXjTG/9Hf6EeZ8CQQC4W8bpzAKMeGp5PKnWeRYUudbEYMYsrh5n9CjidFoZA69IL/bKW7YKWUznoeJsMPBLgTWuVBppCSKOKg6+gyujAkBHemaIN1FUILsp+sOfHQ+U66SQBgZsuBBoQqeNd/4NMQjwHEGwZ6A5iRTIm/KVNcHtU0noaLF+knxH6NcdXvYzAkANP9tN1jGgss7EI8348f7aQnji6CmV8HWS9wMxMzd+cLtaLshHxsuQdtaGFyZUrDe41XJsMvMq9VMccH1ax8aNAkAcaKeeLa5tn7MGceIBtdL0Vq+IzkusjbxWQW8X5CMnwQMZZTUbPJTtzZPztFCtgz4K3gscObuNJcixbFoB/9RC";
    private static final int SDK_PAY_FLAG = 1;

    private List<GoodsInfo> shopCartList;
    private String deliveryFee;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(PayOnlineActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(PayOnlineActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_payment);
        ButterKnife.bind(this);

        //购买的具体商品的集合
        shopCartList = (List<GoodsInfo>) getIntent().getSerializableExtra("data");
        //运费字符串
        deliveryFee = getIntent().getStringExtra("deliveryFee");
        float totalPrice = 0.0f;
        //计算待支付的总金额 = 商品总金额+运费
        for (int i = 0; i < shopCartList.size(); i++) {
            GoodsInfo goodsInfo = shopCartList.get(i);
            totalPrice += goodsInfo.getCount() * goodsInfo.getNewPrice();
        }
        //购买商品的总金额
        totalPrice += Float.parseFloat(deliveryFee);
        tvPayMoney.setText(CountPriceFormater.format(totalPrice));
    }

    @OnClick({R.id.iv_triangle, R.id.bt_confirm_pay})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_triangle:
                //展示购买的具体商品
                showOrderDetail();
                break;
            case R.id.bt_confirm_pay:
                pay(view);
                break;
        }
    }

    private void pay(View view) {
        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         * orderInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(PayOnlineActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 展示购买的具体商品
     */
    private void showOrderDetail() {
        //判断llOrderDetail显示和隐藏的状态
        int visibility = llOrderDetail.getVisibility();
        if (View.VISIBLE == visibility) {
            //点之前是可见的,点完后隐藏
            llOrderDetail.setVisibility(View.GONE);
        } else {
            llOrderDetail.setVisibility(View.VISIBLE);
            //llGoods线性布局,用于放置购买商品的列表
            showGoodsList();
        }
    }

    private void showGoodsList() {
        llGoods.removeAllViews();
        for (int i = 0; i < shopCartList.size(); i++) {
            GoodsInfo goodsInfo = shopCartList.get(i);
            //购买此件的商品数量
            int count = goodsInfo.getCount();
            //购买此件商品的总金额
            float goodsTotalPrice = goodsInfo.getCount() * goodsInfo.getNewPrice();
            View view = View.inflate(this, R.layout.item_confirm_order_goods, null);
            TextView tvName = (TextView) view.findViewById(R.id.tv_name);
            TextView tvCount = (TextView)view.findViewById(R.id.tv_count);
            TextView tvPrice = (TextView)view.findViewById(R.id.tv_price);

            tvName.setText(goodsInfo.getName());
            tvCount.setText(count + "");
            tvPrice.setText(CountPriceFormater.format(goodsTotalPrice));

            llGoods.addView(view);
        }
    }
}
