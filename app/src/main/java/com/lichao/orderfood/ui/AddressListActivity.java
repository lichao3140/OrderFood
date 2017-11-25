package com.lichao.orderfood.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lichao.orderfood.R;
import com.lichao.orderfood.global.MyApplication;
import com.lichao.orderfood.model.ReceiptAddressDao;
import com.lichao.orderfood.model.bean.ReceiptAddressBean;
import com.lichao.orderfood.ui.adapter.MyAddressListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017-11-23.
 */

public class AddressListActivity extends BaseActivity {

    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_receipt_address)
    RecyclerView rvReceiptAddress;
    @BindView(R.id.tv_add_address)
    TextView tvAddAddress;

    private ReceiptAddressDao receiptAddresDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_address);
        ButterKnife.bind(this);

        receiptAddresDao = new ReceiptAddressDao(this);
    }

    @Override
    protected void onResume() {
        //给recyclerView设置显示地址的列表数据
        //查询此用户的数据库列表,查询所有的地址
        List<ReceiptAddressBean> receiptAddressBeanList = receiptAddresDao.queryAllAddress(MyApplication.userId);

        MyAddressListAdapter myAddressListAdapter = new MyAddressListAdapter(this, receiptAddressBeanList);
        rvReceiptAddress.setAdapter(myAddressListAdapter);
        rvReceiptAddress.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        super.onResume();
    }

    @OnClick({R.id.tv_add_address})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_add_address:
                Intent intentAddressList = new Intent(this, AddAddressActivity.class);
                startActivity(intentAddressList);
                break;
        }
    }
}
