package com.lichao.orderfood.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lichao.orderfood.R;
import com.lichao.orderfood.global.MyApplication;
import com.lichao.orderfood.model.ReceiptAddressDao;
import com.lichao.orderfood.model.bean.ReceiptAddressBean;
import com.lichao.orderfood.presenter.net.bean.GoodsInfo;
import com.lichao.orderfood.utils.CountPriceFormater;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017-11-22.
 */

public class ConfirmOrderActivity extends BaseActivity {

    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.iv_location)
    ImageView ivLocation;
    @BindView(R.id.tv_hint_select_receipt_address)
    TextView tvHintSelectReceiptAddress;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_label)
    TextView tvLabel;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_receipt_address)
    LinearLayout llReceiptAddress;
    @BindView(R.id.iv_arrow)
    ImageView ivArrow;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_seller_name)
    TextView tvSellerName;
    @BindView(R.id.ll_select_goods)
    LinearLayout llSelectGoods;
    @BindView(R.id.tv_deliveryFee)
    TextView tvDeliveryFee;
    @BindView(R.id.tv_CountPrice)
    TextView tvCountPrice;
    @BindView(R.id.tvSubmit)
    TextView tvSubmit;

    private List<GoodsInfo> shopCartList;
    private String deliveryFee;
    private String[] addressLabels;
    private int[] bgLabels;
    private ReceiptAddressBean receiptAddressBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        ButterKnife.bind(this);

        //获取由购物车界面传递过来的购买商品集合
        shopCartList = (List<GoodsInfo>) getIntent().getSerializableExtra("shopCartList");
        //获取由购物车界面传递过来的商品运费
        deliveryFee = getIntent().getStringExtra("deliveryFee");
        String strDeliveryFee = CountPriceFormater.format(Float.parseFloat(deliveryFee));
        //设置运费
        tvDeliveryFee.setText(strDeliveryFee);

        //初始化购买的商品列表
        initShopCartList();

        float totalPrice = 0.0f;
        //计算待支付的总金额 = 商品总金额+运费
        for (int i = 0; i < shopCartList.size(); i++) {
            GoodsInfo goodsInfo = shopCartList.get(i);
            totalPrice += goodsInfo.getCount() * goodsInfo.getNewPrice();
        }

        totalPrice += Float.parseFloat(deliveryFee);
        tvCountPrice.setText("待支付:" + CountPriceFormater.format(totalPrice));

        addressLabels = new String[]{"家", "公司", "学校"};
        bgLabels = new int[]{
                Color.parseColor("#fc7251"),//家  橙色
                Color.parseColor("#468ade"),//公司 蓝色
                Color.parseColor("#02c14b"),//学校   绿色
        };
    }

    private void initShopCartList() {
        llSelectGoods.removeAllViews();
        for (int i = 0; i < shopCartList.size(); i++) {
            View view = View.inflate(this, R.layout.item_confirm_order_goods, null);
            TextView tvName = (TextView) view.findViewById(R.id.tv_name);
            TextView tvCount = (TextView)view.findViewById(R.id.tv_count);
            TextView tvPrice = (TextView)view.findViewById(R.id.tv_price);
            GoodsInfo goodsInfo = shopCartList.get(i);
            if (goodsInfo.getCount() > 0) {
                //购买的此商品的数量
                int count = goodsInfo.getCount();
                //购买此商品的金额
                float totalPrice = goodsInfo.getCount() * goodsInfo.getNewPrice();
                tvName.setText(goodsInfo.getName());
                tvCount.setText(count + "");
                tvPrice.setText(CountPriceFormater.format(totalPrice));
            }
            llSelectGoods.addView(view);
        }
    }

    @Override
    protected void onResume() {
        ReceiptAddressDao receiptAddressDao = new ReceiptAddressDao(this);
        //查询数据库对应当前登录用户,已选中的默认地址
        List<ReceiptAddressBean> receiptAddressBeanList = receiptAddressDao.querySelectAddress(MyApplication.userId);
        if (receiptAddressBeanList!=null && receiptAddressBeanList.size()>0){
            receiptAddressBean = receiptAddressBeanList.get(0);
            showReceiptAddress(receiptAddressBean);
        }
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == 101 && data != null) {
            ReceiptAddressBean receiptAddressBean = (ReceiptAddressBean) data.getSerializableExtra("receiptAddress");
            showReceiptAddress(receiptAddressBean);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showReceiptAddress(ReceiptAddressBean receiptAddressBean) {
        tvName.setText(receiptAddressBean.getName());
        tvSex.setText(receiptAddressBean.getSex());

        if (!TextUtils.isEmpty(receiptAddressBean.getPhone())
                && !TextUtils.isEmpty(receiptAddressBean.getPhoneOther())) {
            tvPhone.setText(receiptAddressBean.getPhone() + "," + receiptAddressBean.getPhoneOther());
        }
        if (!TextUtils.isEmpty(receiptAddressBean.getPhone())
                && TextUtils.isEmpty(receiptAddressBean.getPhoneOther())) {
            tvPhone.setText(receiptAddressBean.getPhone());
        }
        tvAddress.setText(receiptAddressBean.getReceiptAddress() + receiptAddressBean.getDetailAddress());

        if (!TextUtils.isEmpty(receiptAddressBean.getLabel())) {
            tvLabel.setVisibility(View.VISIBLE);
            tvLabel.setText(receiptAddressBean.getLabel());
            //设置tvLabel背景颜色,根据label中的字符串,获取索引值,根据索引值去指定背景颜色
            int index = getIndex(receiptAddressBean.getLabel());
            tvLabel.setBackgroundColor(bgLabels[index]);
        } else {
            tvLabel.setVisibility(View.GONE);
        }
    }

    private int getIndex(String label) {
        int index = 0;
        for (int i = 0; i < addressLabels.length; i++) {
            if (label.equals(addressLabels[i])){
                index = i;
                return index;
            }
        }
        return 0;
    }

    @OnClick({R.id.tvSubmit, R.id.rl_location})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSubmit:
                Intent intent = new Intent(this, PayOnlineActivity.class);
                intent.putExtra("data", (Serializable) shopCartList);
                intent.putExtra("deliveryFee", deliveryFee);
                startActivity(intent);
                break;
            case R.id.rl_location:
                Intent intentAddressList = new Intent(this, AddressListActivity.class);
                startActivityForResult(intentAddressList, 100);
                break;
        }
    }
}
