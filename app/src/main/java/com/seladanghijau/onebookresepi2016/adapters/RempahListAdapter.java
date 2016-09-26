package com.seladanghijau.onebookresepi2016.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by seladanghijau on 18/9/2016.
 */
public class RempahListAdapter extends BaseAdapter {
    private Context context;
    private TypedArray rempahList;

    public RempahListAdapter(Context context, TypedArray rempahList) {
        this.context = context;
        this.rempahList = rempahList;
    }

    public int getCount() { return rempahList.length(); }
    public Object getItem(int position) { return position; }
    public long getItemId(int position) { return position; }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        holder = new ViewHolder();
        if(convertView == null) {
            holder.imageView = new ImageView(context);
            holder.imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
            holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else
            holder.imageView = (ImageView) convertView;

        holder.imageView.setImageDrawable(rempahList.getDrawable(position));
        holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        return holder.imageView;
    }

    class ViewHolder {
        private ImageView imageView;
    }
}
