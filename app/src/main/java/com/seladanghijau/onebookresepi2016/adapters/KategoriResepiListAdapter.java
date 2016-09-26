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
 * Created by seladanghijau on 7/8/2016.
 */
public class KategoriResepiListAdapter extends BaseAdapter {
    private static LayoutInflater layoutInflater = null;
    private Context context;
    private String[] kategoriResepiList;
    private int[] resepiCount;
    private TypedArray imejKategoriResepiList;

    public KategoriResepiListAdapter(Context context, String[] kategoriResepiList, TypedArray imejKategoriResepiList, int[] resepiCount) {
        this.context = context;
        this.kategoriResepiList = kategoriResepiList;
        this.imejKategoriResepiList = imejKategoriResepiList;
        this.resepiCount = resepiCount;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() { return kategoriResepiList.length; }
    public Object getItem(int position) { return position; }
    public long getItemId(int position) { return position; }

    public View getView(int position, View convertView, ViewGroup parent) {
        Holder viewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_kategori_resepi_list, null);

            viewHolder = new Holder();
            viewHolder.tvKategoriResepiName = (TextView) convertView.findViewById(R.id.tvKategoriResepiName);
            viewHolder.tvBilResepi = (TextView) convertView.findViewById(R.id.tvBilResepi);
            viewHolder.ivKategoriBg = (ImageView) convertView.findViewById(R.id.ivKategoriBg);

            convertView.setTag(viewHolder);
        } else
            viewHolder = (Holder) convertView.getTag();

        viewHolder.tvKategoriResepiName.setText(kategoriResepiList[position]);
        viewHolder.tvBilResepi.setText(String.valueOf(resepiCount[position]));
        viewHolder.ivKategoriBg.setBackground(imejKategoriResepiList.getDrawable(position));

        return convertView;
    }

    static class Holder {
        TextView tvKategoriResepiName, tvBilResepi;
        ImageView ivKategoriBg;
    }
}
