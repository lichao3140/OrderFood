package com.lichao.orderfood.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lichao.orderfood.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017-11-23.
 */

public class AddAddressActivity extends BaseActivity {

    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ib_delete)
    ImageButton ibDelete;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.rb_man)
    RadioButton rbMan;
    @BindView(R.id.rb_women)
    RadioButton rbWomen;
    @BindView(R.id.rg_sex)
    RadioGroup rgSex;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.ib_delete_phone)
    ImageButton ibDeletePhone;
    @BindView(R.id.ib_add_phone_other)
    ImageButton ibAddPhoneOther;
    @BindView(R.id.et_phone_other)
    EditText etPhoneOther;
    @BindView(R.id.ib_delete_phone_other)
    ImageButton ibDeletePhoneOther;
    @BindView(R.id.rl_phone_other)
    RelativeLayout rlPhoneOther;
    @BindView(R.id.tv_receipt_address)
    TextView tvReceiptAddress;
    @BindView(R.id.et_detail_address)
    EditText etDetailAddress;
    @BindView(R.id.tv_label)
    TextView tvLabel;
    @BindView(R.id.ib_select_label)
    ImageView ibSelectLabel;
    @BindView(R.id.bt_ok)
    Button btOk;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_receipt_address);
        ButterKnife.bind(this);

    }
}
