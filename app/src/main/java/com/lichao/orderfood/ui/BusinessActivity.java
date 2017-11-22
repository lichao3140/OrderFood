package com.lichao.orderfood.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.lichao.orderfood.R;
import com.lichao.orderfood.presenter.BusinessPresenter;
import com.lichao.orderfood.presenter.net.bean.Seller;
import com.lichao.orderfood.ui.adapter.BusinessFragmentPagerAdapter;
import com.lichao.orderfood.ui.fragment.GoodsFragment;
import com.lichao.orderfood.utils.CountPriceFormater;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017-11-17.
 */

public class BusinessActivity extends BaseActivity {

    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ib_menu)
    ImageButton ibMenu;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.bottomSheetLayout)
    BottomSheetLayout bottomSheetLayout;
    @BindView(R.id.imgCart)
    ImageView imgCart;
    @BindView(R.id.tvSelectNum)
    TextView tvSelectNum;
    @BindView(R.id.tvCountPrice)
    TextView tvCountPrice;
    @BindView(R.id.tvDeliveryFee)
    TextView tvDeliveryFee;
    @BindView(R.id.tvSendPrice)
    TextView tvSendPrice;
    @BindView(R.id.tvSubmit)
    TextView tvSubmit;
    @BindView(R.id.bottom)
    LinearLayout bottom;
    @BindView(R.id.fl_Container)
    FrameLayout flContainer;

    private String[] stringArray = new String[]{"商品","评价","商家"};
    private Seller seller;
    private BusinessFragmentPagerAdapter businessFragmentPagerAdapter;
    public BusinessPresenter businessPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bussiness);
        ButterKnife.bind(this);
        businessPresenter = new BusinessPresenter(this);

        seller = (Seller) getIntent().getSerializableExtra("seller");

        //顶部的tabLayout+viewPager联动效果
        initTab();
        //填充viewPager
        initViewPager();
        //选项卡和viewpager绑定
        tabs.setupWithViewPager(vp);
    }

    private void initViewPager() {
        //PagerAdapter----->viewpager中直接指定添加的view对象
        //FragmentPagerAdapter----->viewpager中添加的是fragment onCreateView方法中返回的view对象
        businessFragmentPagerAdapter = new BusinessFragmentPagerAdapter(getSupportFragmentManager(), stringArray, seller);
        vp.setAdapter(businessFragmentPagerAdapter);
    }

    private void initTab() {
        for (int i = 0; i < stringArray.length; i++) {
            tabs.addTab(tabs.newTab().setText(stringArray[i]));
        }
    }

    /**
     * @param imageView 添加在帧布局中的图片,添加位置已经通过setX和setY指定过了
     * @param width 添加控件宽度
     * @param height 添加控件的高度
     */
    public void addImageView(ImageView imageView, int width, int height) {
        flContainer.addView(imageView,width,height);
    }

    /**
     * @return  返回购物图片所在屏幕中的x,y的坐标
     */
    public int[] getShopCartLocation(){
        int[] shopCart = new int[2];
        imgCart.getLocationInWindow(shopCart);
        return shopCart;
    }

    /**
     * @param imageView 动画结束以后,移除图片
     */
    public void removeImageView(ImageView imageView) {
        if (imageView!=null){
            flContainer.removeView(imageView);
        }
    }

    /**
     * 获取GoodsFragment方法
     * @return
     */
    public GoodsFragment getGoodsFragment() {
        ArrayList<Fragment> fragmentList = businessFragmentPagerAdapter.getFragmentList();
        if (fragmentList != null && fragmentList.size() > 0) {
            GoodsFragment goodsFragment = (GoodsFragment) fragmentList.get(0);
            return goodsFragment;
        }
        return null;
    }

    /**
     * 更新钱和数量方法
     * @param totalCount    购物车中数量
     * @param totalPrice    购物车中金额
     */
    public void updateShopCartCount(int totalCount, float totalPrice) {
        if (totalCount == 0){
            tvSelectNum.setVisibility(View.GONE);
            tvCountPrice.setText(CountPriceFormater.format(0.0f));
        }else{
            tvSelectNum.setVisibility(View.VISIBLE);
            tvSelectNum.setText(totalCount + "");
            tvCountPrice.setText(CountPriceFormater.format(totalPrice));
        }
    }

}
