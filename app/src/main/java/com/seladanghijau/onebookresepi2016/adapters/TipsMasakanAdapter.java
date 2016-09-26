package com.seladanghijau.onebookresepi2016.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.seladanghijau.onebookresepi2016.R;

/**
 * Created by seladanghijau on 18/9/2016.
 */
public class TipsMasakanAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    private String[] tipTitle, tipDesc;
    private boolean[] visible;

    public TipsMasakanAdapter(Context context, String[] tipTitle, String[] tipDesc) {
        this.context = context;
        this.tipTitle = tipTitle;
        this.tipDesc = tipDesc;
        visible = new boolean[tipDesc.length];

        // initialize visible
        for(int x=0 ; x<visible.length ; x++) {
            visible[x] = true;
        }

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() { return tipTitle.length; }
    public Object getItem(int position) { return null; }
    public long getItemId(int position) { return 0; }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_tips_masakan, null);

            viewHolder = new ViewHolder();
            viewHolder.tvTipTitle = (TextView) convertView.findViewById(R.id.tvTipTitle);
            viewHolder.tvTipDesc = (TextView) convertView.findViewById(R.id.tvTipDesc);

            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.tvTipTitle.setText(tipTitle[position]);
        viewHolder.tvTipDesc.setText(tipDesc[position]);
        viewHolder.tvTipDesc.setVisibility(visible[position] ? View.VISIBLE : View.GONE);

        return convertView;
    }

    class ViewHolder {
        TextView tvTipTitle, tvTipDesc;
    }

    // util method ---------------------------------------------------------------------------------
    public void setVisible(boolean[] visible) {
        this.visible = visible;
        notifyDataSetChanged();
    }
    // ---------------------------------------------------------------------------------------------
}
