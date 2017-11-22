package com.lichao.orderfood.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lichao.orderfood.R;
import com.lichao.orderfood.presenter.net.bean.GoodsInfo;
import com.lichao.orderfood.presenter.net.bean.GoodsTypeInfo;
import com.lichao.orderfood.ui.BusinessActivity;
import com.lichao.orderfood.ui.fragment.GoodsFragment;
import com.lichao.orderfood.utils.CountPriceFormater;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017-11-22.
 */

public class ShopCartAdapter extends RecyclerView.Adapter {
    public static int ADD = 100;
    public static int DELETE = 101;
    private Context context;
    private List<GoodsInfo> shopCartList;

    public ShopCartAdapter(Context context, List<GoodsInfo> shopCartList) {
        this.context = context;
        this.shopCartList = shopCartList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_cart, null);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GoodsInfo goodsInfo = shopCartList.get(position);
        ((MyViewHolder) holder).tvName.setText(goodsInfo.getName());
        //单件商品购买的总金额  此件商品的数量*单价
        float goodsTotalPrice = goodsInfo.getCount() * goodsInfo.getNewPrice();
        String strGoodsTotalPrice = CountPriceFormater.format(goodsTotalPrice);
        ((MyViewHolder) holder).tvTypeAllPrice.setText(strGoodsTotalPrice);
        ((MyViewHolder) holder).tvCount.setText(goodsInfo.getCount() + "");
        ((MyViewHolder) holder).setPosition(position);
    }

    @Override
    public int getItemCount() {
        if (shopCartList != null && shopCartList.size() > 0) {
            return shopCartList.size();
        }
        return 0;
    }

    /**
     * 重新传递购物车中的数据,再次刷新
     * @param shopCartList
     */
    public void setData(List<GoodsInfo> shopCartList) {
        this.shopCartList = shopCartList;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_type_all_price)
        TextView tvTypeAllPrice;
        @BindView(R.id.ib_minus)
        ImageButton ibMinus;
        @BindView(R.id.tv_count)
        TextView tvCount;
        @BindView(R.id.ib_add)
        ImageButton ibAdd;
        @BindView(R.id.ll)
        LinearLayout ll;
        private int position;

        public void setPosition(int position) {
            this.position = position;
        }

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.ib_add, R.id.ib_minus})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ib_add:
                    doAddOperate(ADD);
                    break;
                case R.id.ib_minus:
                    doDeleteOperate(DELETE);
                    break;
            }
        }

        /**
         * 添加
         * @param operate
         */
        private void doAddOperate(int operate) {
            //根据索引获取需要添加数量的对象
            GoodsInfo goodsInfo = shopCartList.get(position);
            updateGoodsAdapter(goodsInfo, operate);
            updateGoodsTypeAdapter(goodsInfo, operate);
            //更新购物车列表中的商品数量,金额
            notifyDataSetChanged();
            //更新购物车总数量和金额,因为在businessPresenter中已经提供了刷新购物车商品数量和金额的方法,直接调用即可
            ((BusinessActivity) context).businessPresenter.updateShopCart();
        }

        private void doDeleteOperate(int operate) {
            //根据索引获取需要减少数量的对象
            GoodsInfo goodsInfo = shopCartList.get(position);
            //调用更新商品列表数据适配器方法
            updateGoodsAdapter(goodsInfo,operate);
            //调用更新商品分类数据适配器方法
            updateGoodsTypeAdapter(goodsInfo,operate);
            //更新购物车列表中的商品数量,金额
            notifyDataSetChanged();
            //更新购物车总数量和金额,因为在businessPresenter中已经提供了刷新购物车商品数量和金额的方法,直接调用即可
            ((BusinessActivity) context).businessPresenter.updateShopCart();

            //如果点中的条目商品数量为0,此条目需要从列表中删除,从集合中删除
            if (goodsInfo.getCount() == 0) {
                shopCartList.remove(goodsInfo);
                notifyDataSetChanged();
            }

            //如果所有的商品都被减掉,对话框需要隐藏
            if (shopCartList.size() == 0){
                //调用businessActivity中的方法,隐藏对话框
                ((BusinessActivity) context).dismissBottomSheetLayout();
            }
        }

        private void updateGoodsAdapter(GoodsInfo goodsInfo, int operate) {
            //在商品列表中添加一件,每一件商品都有一个唯一性的id,根据唯一性id判断商品列表中那件商品需要添加一件
            int shopCartGoodsId = goodsInfo.getId();
            //获取商品列表的集合businessActivity--->GoodsFragment------>商品列表的数据适配器GoodsAdapter------->商品列表的集合
            GoodsFragment goodsFragment = ((BusinessActivity) context).getGoodsFragment();
            if (goodsFragment != null) {
                List<GoodsInfo> goodsInfoList = goodsFragment.goodsAdapter.getData();
                for (int i = 0; i < goodsInfoList.size(); i++) {
                    if (goodsInfoList.get(i).getId() == shopCartGoodsId) {
                        if (operate == ADD) {
                            //此件商品数量需要增加1件
                            int count = goodsInfoList.get(i).getCount() + 1;
                            goodsInfoList.get(i).setCount(count);
                        } else if (operate == DELETE) {
                            //此件商品数量需要减掉1件
                            if (goodsInfoList.get(i).getCount() > 0) {
                                int count = goodsInfoList.get(i).getCount() - 1;
                                goodsInfoList.get(i).setCount(count);
                            }
                        }
                    }
                }
            }
            goodsFragment.goodsAdapter.notifyDataSetChanged();
        }

        private void updateGoodsTypeAdapter(GoodsInfo goodsInfo, int operate) {
            //在商品列表中添加一件,每一件商品都有一个唯一性的id,根据唯一性id判断商品列表中那件商品需要添加一件
            int shopCartGoodsId = goodsInfo.getId();
            //获取商品列表的集合businessActivity--->GoodsFragment------>商品列表的数据适配器GoodsAdapter------->商品列表的集合
            GoodsFragment goodsFragment = ((BusinessActivity) context).getGoodsFragment();
            if (goodsFragment != null) {
                List<GoodsTypeInfo> goodsTypeInfoList = goodsFragment.getGoodsTypeAdapter().getData();
                for (int i = 0; i < goodsTypeInfoList.size(); i++) {
                    GoodsTypeInfo goodsTypeInfo = goodsTypeInfoList.get(i);
                    if (shopCartGoodsId == goodsTypeInfo.getId()) {
                        if (operate == ADD) {
                            //此分类需要添加一件商品
                            int count = goodsTypeInfo.getCount() + 1;
                            goodsTypeInfo.setCount(count);
                        } else if (operate == DELETE) {
                            if (goodsTypeInfo.getCount() > 0) {
                                //此分类需要减少一件商品
                                int count = goodsTypeInfo.getCount()- 1;
                                goodsTypeInfo.setCount(count);
                            }
                        }
                    }
                }
            }
            goodsFragment.getGoodsTypeAdapter().notifyDataSetChanged();
        }

    }
}
