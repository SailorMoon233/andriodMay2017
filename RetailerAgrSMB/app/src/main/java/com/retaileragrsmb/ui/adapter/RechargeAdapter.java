package com.retaileragrsmb.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.retaileragrsmb.R;
import com.retaileragrsmb.model.RechargeModel;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * Created by hitesh on 5/6/2017.
 */

public class RechargeAdapter extends BaseAdapter {

    private Context context;
    private List<RechargeModel> rechargeList;
    private LayoutInflater inflater;

    public RechargeAdapter(Context context, List<RechargeModel> rechargeList) {
        this.context = context;
        this.rechargeList = rechargeList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
//        if(rechargeList != null)
//            return rechargeList.size();
        return 5;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }



        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvMessage)
        TextView tvMessage;
        @BindView(R.id.tvAmount)
        TextView tvAmount;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
