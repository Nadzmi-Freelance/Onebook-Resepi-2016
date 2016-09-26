package com.seladanghijau.onebookresepi2016.adapters;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.seladanghijau.onebookresepi2016.R;

import java.util.ArrayList;

/**
 * Created by seladanghijau on 31/8/2016.
 */
public class ResepiBahanAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Pair<String, String>> bahan;
    private String[] currDesc;

    public ResepiBahanAdapter(Context context, ArrayList<Pair<String, String>> bahan) {
        this.context = context;
        this.bahan = bahan;
        currDesc = new String[bahan.size()];

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() { return bahan.size(); }
    public Object getItem(int position) { return position; }
    public long getItemId(int position) { return position; }

    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;
        Holder viewHolder;
        boolean showSeperator;

        rowView = convertView;
        if(convertView == null) {
            rowView = layoutInflater.inflate(R.layout.layout_resepi_bahan, null);

            viewHolder = new Holder();
            viewHolder.tvBahanDesc = (TextView) rowView.findViewById(R.id.tvBahanDesc);
            viewHolder.tvBahanName = (TextView) rowView.findViewById(R.id.tvBahanName);

            rowView.setTag(viewHolder);
        } else
            viewHolder = (Holder) rowView.getTag();

        currDesc[position] = bahan.get(position).first;
        if(position != 0)
            if(!currDesc[position].equalsIgnoreCase(currDesc[position-1]))
                showSeperator = true;
            else
                showSeperator = false;
        else
            showSeperator = true;

        viewHolder.tvBahanName.setText(bahan.get(position).second);
        if(showSeperator) {
            viewHolder.tvBahanDesc.setVisibility(View.VISIBLE);
            viewHolder.tvBahanDesc.setText("Bahan " + bahan.get(position).first);
        } else
            viewHolder.tvBahanDesc.setVisibility(View.GONE);

        return rowView;
    }

    class Holder {
        TextView tvBahanDesc, tvBahanName;
    }
}
