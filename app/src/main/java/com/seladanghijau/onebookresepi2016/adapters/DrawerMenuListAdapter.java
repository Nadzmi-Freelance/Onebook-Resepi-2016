package com.seladanghijau.onebookresepi2016.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.seladanghijau.onebookresepi2016.R;

/**
 * Created by seladanghijau on 4/8/2016.
 */
public class DrawerMenuListAdapter extends BaseAdapter {
    private static LayoutInflater layoutInflater = null;
    private String[] menuList;
    private TypedArray menuIconList;
    private Context context;

    public DrawerMenuListAdapter(Context context, String[] menuList, TypedArray menuIconList) {
        this.context = context;
        this.menuIconList = menuIconList;
        this.menuList = menuList;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() { return menuList.length; }
    public Object getItem(int position) { return position; }
    public long getItemId(int position) { return position; }

    public View getView(int position, View convertView, ViewGroup parent) {
        Holder viewHolder;
        View rowView;

        rowView = convertView;
        if(convertView == null) {
            rowView = layoutInflater.inflate(R.layout.layout_drawer_menu, null);

            viewHolder = new Holder();
            viewHolder.tempImageView = (ImageView) rowView.findViewById(R.id.ivMenuIcon);
            viewHolder.tempTextView = (TextView) rowView.findViewById(R.id.tvMenuTitle);

            rowView.setTag(viewHolder);
        } else
            viewHolder = (Holder) rowView.getTag();

        viewHolder.tempImageView.setBackground(menuIconList.getDrawable(position));
        viewHolder.tempTextView.setText(menuList[position]);

        return rowView;
    }

    static class Holder {
        TextView tempTextView;
        ImageView tempImageView;
    }
}
