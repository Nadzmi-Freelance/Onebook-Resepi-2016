package com.seladanghijau.onebookresepi2016.provider;

import android.content.res.TypedArray;
import android.graphics.Bitmap;

import com.seladanghijau.onebookresepi2016.dto.Resepi;

/**
 * Created by seladanghijau on 11/8/2016.
 */
public interface ILoader {
    void onLoadMenuDrawer(String[] drawerMenuList, TypedArray ikonDrawerMenuList); // for menu drawer
    void onLoad(int[] resepiCount, String[] kategoriResepiList, TypedArray imejKategoriResepiList); // for main activity
    void onLoad(int[] resepiCount, String[] kategoriResepiList, Bitmap[] imejKategoriResepiList);
    void onLoad(int category, String[] resepiNameList, Bitmap[] bgResepiList); // for resepi list activity
    void onLoad(Resepi resepiInfo); // for resepi info activity
    void onLoad(String[] resepiNameList, Bitmap[] bgResepiList);
    void onLoad(String[] tipTitle, String[] tipDesc); // for tips masakan activity
    public void onLoad(TypedArray rempahImgList); // for rempah list
}
