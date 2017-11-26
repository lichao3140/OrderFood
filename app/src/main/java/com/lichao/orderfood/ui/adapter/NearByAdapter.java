package com.lichao.orderfood.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.amap.api.services.core.PoiItem;

import java.util.List;

/**
 * Created by Administrator on 2017-11-26.
 */

public class NearByAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<PoiItem> data;

    public NearByAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setData(List<PoiItem> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
