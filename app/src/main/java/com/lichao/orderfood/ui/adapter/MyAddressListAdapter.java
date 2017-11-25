package com.lichao.orderfood.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.lichao.orderfood.R;
import com.lichao.orderfood.model.ReceiptAddressDao;
import com.lichao.orderfood.model.bean.ReceiptAddressBean;
import com.lichao.orderfood.ui.AddAddressActivity;
import com.lichao.orderfood.ui.AddressListActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017-11-23.
 */

public class MyAddressListAdapter extends RecyclerView.Adapter {

    private int[] bgLabels;
    private String[] addressLabels;
    private Context context;
    private List<ReceiptAddressBean> data;
    private final ReceiptAddressDao receiptAddresDao;

    public MyAddressListAdapter(Context context, List<ReceiptAddressBean> data) {
        this.context = context;
        this.data = data;
        addressLabels = new String[]{"家", "公司", "学校"};
        bgLabels = new int[]{
                Color.parseColor("#fc7251"),//家  橙色
                Color.parseColor("#468ade"),//公司 蓝色
                Color.parseColor("#02c14b"),//学校   绿色
        };
        receiptAddresDao = new ReceiptAddressDao(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_receipt_address, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ReceiptAddressBean receiptAddressBean = data.get(position);
        ((MyViewHolder) holder).tvName.setText(receiptAddressBean.getName());
        ((MyViewHolder) holder).tvSex.setText(receiptAddressBean.getSex());

        if (!TextUtils.isEmpty(receiptAddressBean.getPhone())
                && !TextUtils.isEmpty(receiptAddressBean.getPhoneOther())) {
            ((MyViewHolder) holder).tvPhone.setText(receiptAddressBean.getPhone() + "," + receiptAddressBean.getPhoneOther());
        }
        if (!TextUtils.isEmpty(receiptAddressBean.getPhone())
                && TextUtils.isEmpty(receiptAddressBean.getPhoneOther())) {
            ((MyViewHolder) holder).tvPhone.setText(receiptAddressBean.getPhone());
        }
        ((MyViewHolder) holder).tvAddress.setText(receiptAddressBean.getReceiptAddress() + receiptAddressBean.getDetailAddress());

        if (!TextUtils.isEmpty(receiptAddressBean.getLabel())) {
            ((MyViewHolder) holder).tvLabel.setVisibility(View.VISIBLE);
            ((MyViewHolder) holder).tvLabel.setText(receiptAddressBean.getLabel());
            //设置tvLabel背景颜色,根据label中的字符串,获取索引值,根据索引值去指定背景颜色
            int index = getIndex(receiptAddressBean.getLabel());
            ((MyViewHolder) holder).tvLabel.setBackgroundColor(bgLabels[index]);
        } else {
            ((MyViewHolder) holder).tvLabel.setVisibility(View.GONE);
        }

        if (data.get(position).isSelect() == 1){
            //此条目被选中
            ((MyViewHolder) holder).cb.setVisibility(View.VISIBLE);
            ((MyViewHolder) holder).cb.setChecked(true);
        }else{
            //此条目未被选中
            ((MyViewHolder) holder).cb.setVisibility(View.GONE);
            ((MyViewHolder) holder).cb.setChecked(false);
        }
        ((MyViewHolder) holder).setPosition(position);

        ((MyViewHolder) holder).ivEdit.setVisibility(View.VISIBLE);
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

    @Override
    public int getItemCount() {
        if (data != null && data.size() > 0) {
            return data.size();
        }
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cb)
        CheckBox cb;
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
        @BindView(R.id.iv_edit)
        ImageView ivEdit;
        private int position;

        @OnClick({R.id.iv_edit})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.iv_edit:
                    ReceiptAddressBean receiptAddressBean = data.get(position);
                    Intent intent = new Intent(context, AddAddressActivity.class);
                    intent.putExtra("address", receiptAddressBean);
                    context.startActivity(intent);
                    break;
            }
        }

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i = 0; i < data.size(); i++) {
                        //点中view对象后,获取点中条目的在集合中的对象
                        ReceiptAddressBean receiptAddressBean = data.get(i);
                        if (i == position) {
                            //更新data集合中的isSelect字段的值为1
                            receiptAddressBean.setSelect(1);
                        } else {
                            //更新data集合中的isSelect字段的值为0
                            receiptAddressBean.setSelect(0);
                        }
                        //更新数据库表中的isSelect字段的值为1
                        receiptAddresDao.update(receiptAddressBean);
                    }
                    notifyDataSetChanged();
                    //点击后需要结束此界面,将数据传递给前一个界面
                    Intent intent = new Intent();
                    intent.putExtra("receiptAddress", data.get(position));
                    ((AddressListActivity) context).setResult(101, intent);
                    ((AddressListActivity) context).finish();
                }
            });
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}
