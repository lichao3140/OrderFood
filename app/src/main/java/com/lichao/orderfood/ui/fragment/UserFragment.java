package com.lichao.orderfood.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.j256.ormlite.dao.Dao;
import com.lichao.orderfood.R;
import com.lichao.orderfood.global.MyApplication;
import com.lichao.orderfood.model.DBHelper;
import com.lichao.orderfood.model.bean.UserInfo;
import com.lichao.orderfood.ui.LoginActivity;
import java.sql.SQLException;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017-11-16.
 */

public class UserFragment extends BaseFragment {

    @BindView(R.id.tv_user_setting)
    ImageView tvUserSetting;
    @BindView(R.id.iv_user_notice)
    ImageView ivUserNotice;
    @BindView(R.id.login)
    ImageView login;
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.ll_userinfo)
    LinearLayout llUserinfo;
    @BindView(R.id.iv_address)
    ImageView ivAddress;
    Unbinder unbinder;

    private Dao<UserInfo, Integer> dao;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        DBHelper dbHelper = DBHelper.getInstance(getActivity());
        dao = dbHelper.getDao(UserInfo.class);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_user, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onResume() {
        if (MyApplication.userId != -1) {
            try {
                login.setVisibility(View.GONE);
                llUserinfo.setVisibility(View.VISIBLE);
                UserInfo userInfo = dao.queryForId(MyApplication.userId);
                if (userInfo != null) {
                    username.setText(userInfo.getName());//从数据库中查询出来用户名称放在控件中显示
                    phone.setText(userInfo.getPhone());//从数据库中查询出来用户名称放在控件中显示
                } else {
                    llUserinfo.setVisibility(View.GONE);
                    login.setVisibility(View.VISIBLE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
