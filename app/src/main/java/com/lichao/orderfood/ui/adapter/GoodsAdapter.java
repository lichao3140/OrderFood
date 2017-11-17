package com.lichao.orderfood.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.lichao.orderfood.R;
import com.lichao.orderfood.global.MyApplication;
import com.lichao.orderfood.presenter.net.bean.GoodsInfo;
import com.lichao.orderfood.ui.BusinessActivity;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * 右侧列表的数据适配器
 * Created by Administrator on 2017-11-17.
 */

public class GoodsAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    private Context context;
    private ArrayList<GoodsInfo> data;

    public GoodsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        if (data != null && data.size() > 0) {
            return data.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_goods, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //viewHolder中的控件赋值
        //商品的名称
        viewHolder.tvName.setText(data.get(position).getName());
        //商品的2个价格(现在卖价,历史卖价)
        viewHolder.tvNewprice.setText(data.get(position).getNewPrice() + "");
        viewHolder.tvOldprice.setText(data.get(position).getOldPrice() + "");
        //picasso做图片异步加载
        Picasso.with(context).load(data.get(position).getIcon()).into(viewHolder.ivIcon);
        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        TextView textView = (TextView) View.inflate(context, R.layout.item_type_header, null);
        textView.setText(data.get(position).getTypeName());
        return textView;
    }

    @Override
    public long getHeaderId(int position) {
        //返回头的总个数
        //如果此处的返回值结果只有一种可能,那头就只有一个
        //如果此处的返回值结果又n种可能,那头就有n个
        //返回1
        //返回2
        //返回3
        //项目中一共有11种分类，分类id就有11个，并且每一个id都不一样
        return data.get(position).getTypeId();
    }

    public ArrayList<GoodsInfo> getData() {
        return data;
    }

    public void setData(ArrayList<GoodsInfo> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    class ViewHolder {
        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_zucheng)
        TextView tvZucheng;
        @BindView(R.id.tv_yueshaoshounum)
        TextView tvYueshaoshounum;
        @BindView(R.id.tv_newprice)
        TextView tvNewprice;
        @BindView(R.id.tv_oldprice)
        TextView tvOldprice;
        @BindView(R.id.ib_minus)
        ImageButton ibMinus;
        @BindView(R.id.tv_count)
        TextView tvCount;
        @BindView(R.id.ib_add)
        ImageButton ibAdd;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.ib_add})
        public void onClick(View view) {
            view.setEnabled(false);
            switch (view.getId()) {
                case R.id.ib_add:
                    addGoods(view);
                    break;
            }
        }

        /**
         * @param view  用于定位飞行的+号所在坐标位置的控件
         */
        private void addGoods(View view) {
            //构建3组动画
            //1.旋转
            RotateAnimation rotateAnimation = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF,
                    0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            //2.透明
            AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
            //3.平移(减号按钮，y轴不动,x轴由离自己右侧2个宽度的距离,回到初始位置)
            TranslateAnimation translateAnimation = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 2,
                    Animation.RELATIVE_TO_SELF, 0,
                    Animation.RELATIVE_TO_SELF, 0,
                    Animation.RELATIVE_TO_SELF, 0);
            //4.动画的集合
            AnimationSet animationSet = new AnimationSet(false);
            animationSet.addAnimation(rotateAnimation);
            animationSet.addAnimation(alphaAnimation);
            animationSet.addAnimation(translateAnimation);
            animationSet.setDuration(500);

            ibMinus.startAnimation(animationSet);
            ibMinus.setVisibility(View.VISIBLE);
            tvCount.setVisibility(View.VISIBLE);

            //获取此view(点击的加号)在屏幕上的xy坐标的方法
            int[] sourceLocation = new int[2];
            view.getLocationInWindow(sourceLocation);
            Log.i("","x轴坐标  sourceLocation[0] = "+sourceLocation[0]);
            Log.i("","y轴坐标  sourceLocation[1] = "+sourceLocation[1]);

            //在点击的加号的位置,添加一个用于飞行图片
            ImageView imageView = new ImageView(context);
            imageView.setBackgroundResource(R.drawable.button_add);

            //让图片按照上诉xy的坐标去做放置
            imageView.setX(sourceLocation[0]);
            imageView.setY(sourceLocation[1] - MyApplication.statusBarHeight);

            //获取businessActivity对象,调用其方法,将imageView添加在其帧布局中,并且指定添加图片的宽高
            ((BusinessActivity)context).addImageView(imageView, view.getWidth(), view.getHeight());

            //获取目标位置的数组
            int[] desLocation = ((BusinessActivity)context).getShopCartLocation();

            //让imageView从起点sourceLocation向desLocation位置坐平抛运动
            move(imageView, sourceLocation, desLocation, view);
        }

        private void move(final ImageView imageView, int[] sourceLocation, int[] desLocation,final View view) {
            //起点x坐标
            int startX = sourceLocation[0];
            //起点y坐标
            int startY = sourceLocation[1];

            //终点x坐标
            int endX = desLocation[0];
            //终点y坐标
            int endY = desLocation[1];

            //构建x轴的平移动画,匀速
            TranslateAnimation translateAnimationX = new TranslateAnimation(
                    Animation.ABSOLUTE, 0,
                    Animation.ABSOLUTE, endX - startX,
                    Animation.ABSOLUTE, 0,
                    Animation.ABSOLUTE, 0
            );
            //x轴匀速运动
            translateAnimationX.setInterpolator(new LinearInterpolator());

            //构建y轴的平移动画,加速
            TranslateAnimation translateAnimationY = new TranslateAnimation(
                    Animation.ABSOLUTE, 0,
                    Animation.ABSOLUTE, 0,
                    Animation.ABSOLUTE, 0,
                    Animation.ABSOLUTE, endY - startY
            );
            //y轴做加速运动
            translateAnimationY.setInterpolator(new AccelerateInterpolator());

            AnimationSet animationSet = new AnimationSet(false);
            animationSet.addAnimation(translateAnimationX);
            animationSet.addAnimation(translateAnimationY);
            animationSet.setDuration(500);

            imageView.startAnimation(animationSet);

            animationSet.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    view.setEnabled(true);
                    //移除掉进行平抛运动的图片imageView
                    ((BusinessActivity)context).removeImageView(imageView);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
    }
}
