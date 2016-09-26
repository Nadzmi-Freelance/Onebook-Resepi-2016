package com.seladanghijau.onebookresepi2016.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by seladanghijau on 10/8/2016.
 */
public class BahanListAdapter extends BaseAdapter { // FIXME - REMAKE
    private LayoutInflater layoutInflater;
    private Context context;

    public BahanListAdapter(Context context) {
        this.context = context;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() { return 0; }
    public Object getItem(int position) { return null; }
    public long getItemId(int position) { return 0; }

    public View getView(int position, View convertView, ViewGroup parent) {
        Holder viewHolder;

        viewHolder = new Holder();

        return null;
    }

    static class Holder {
        View rowView;
    }
}
