package com.lichao.orderfood.ui.fragment;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lichao.orderfood.R;
import com.lichao.orderfood.presenter.HomePresenter;
import com.lichao.orderfood.ui.adapter.HomeRecyclerViewAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017-11-16.
 */

public class HomeFragment extends BaseFragment {

    @BindView(R.id.rv_home)
    RecyclerView rvHome;
    @BindView(R.id.home_tv_address)
    TextView homeTvAddress;
    @BindView(R.id.ll_title_search)
    LinearLayout llTitleSearch;
    @BindView(R.id.ll_title_container)
    LinearLayout llTitleContainer;
    Unbinder unbinder;

    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    private int sumY = 0;
    private float duration = 300.0f;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        sumY = 0;
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_home, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //首页的数据适配器
        HomeRecyclerViewAdapter homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(getActivity());
        rvHome.setAdapter(homeRecyclerViewAdapter);
        rvHome.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        rvHome.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //滚动发生改变调用方法
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //滚动的时候调用方法 RecyclerView水平,竖直

                //累加dy结果,计算一共滚动了多少个像素,根据滚动的像素,决定顶部的title背景颜色
                //如果dy累加的结果等于<0,没有偏移量,颜色固定
                //如果dy累加的结果大于>300,颜色定值
                //如果dy取值范围在0到300之前,根据滚动距离的多少,决定颜色的深浅
                int bgColor = 0X553190E8;
                sumY += dy;
                if (sumY <= 0) {
                    //没有移动
                    bgColor = 0X553190E8;
                } else if (sumY >= 300) {
                    //移动到了颜色渐变最大值
                    bgColor = 0XFF3190E8;
                } else {
                    //移动过程中颜色的渐变
                    //从哪个色值,变化到哪个色值  50个像素/300  1/6
                    bgColor = (int) argbEvaluator.evaluate(sumY/duration,0X553190E8,0XFF3190E8);
                }
                llTitleContainer.setBackgroundColor(bgColor);
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        //网络请求
        HomePresenter homePresenter = new HomePresenter(homeRecyclerViewAdapter);
        homePresenter.getHomeData("", "");

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
