package com.seladanghijau.onebookresepi2016.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.seladanghijau.onebookresepi2016.R;

/**
 * Created by seladanghijau on 12/8/2016.
 */
public class SearchKategoriResepiListAdapter extends BaseAdapter {
    private static LayoutInflater layoutInflater;
    private Context context;
    private String[] kategoriResepiList;
    private int[] resepiCount;
    private Bitmap[] imejKategoriResepiList;

    public SearchKategoriResepiListAdapter(Context context, String[] kategoriResepiList, Bitmap[] imejKategoriResepiList, int[] resepiCount) {
        this.context = context;
        this. kategoriResepiList = kategoriResepiList;
        this.imejKategoriResepiList = imejKategoriResepiList;
        this.resepiCount = resepiCount;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() { return kategoriResepiList.length; }
    public Object getItem(int position) { return position; }
    public long getItemId(int position) { return position; }

    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;
        Holder viewHolder;

        if(getCount() == 0)
            return null;
        else {
            rowView = convertView;
            if (convertView == null) {
                rowView = layoutInflater.inflate(R.layout.layout_kategori_resepi_list, null);

                viewHolder = new Holder();
                viewHolder.tvKategoriResepiName = (TextView) rowView.findViewById(R.id.tvKategoriResepiName);
                viewHolder.tvBilResepi = (TextView) rowView.findViewById(R.id.tvBilResepi);
                viewHolder.ivKategoriBg = (ImageView) rowView.findViewById(R.id.ivKategoriBg);

                rowView.setTag(viewHolder);
            } else
                viewHolder = (Holder) rowView.getTag();

            viewHolder.tvKategoriResepiName.setText(kategoriResepiList[position]);
            viewHolder.tvBilResepi.setText(String.valueOf(resepiCount[position]));
            viewHolder.ivKategoriBg.setImageBitmap(imejKategoriResepiList[position]);

            return rowView;
        }
    }

    static class Holder {
        TextView tvKategoriResepiName, tvBilResepi;
        ImageView ivKategoriBg;
    }
}
