package com.seladanghijau.onebookresepi2016.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Pair;

import com.seladanghijau.onebookresepi2016.dto.Resepi;
import com.seladanghijau.onebookresepi2016.provider.ResepiProvider;

import java.util.ArrayList;

/**
 * Created by seladanghijau on 7/8/2016.
 */
public class ResepiManager {
    private ResepiProvider resepiProvider;
    private Context context;

    public ResepiManager(Context context) {
        this.context = context;

        try {
            resepiProvider = new ResepiProvider(context);
            resepiProvider.initDB();
        } catch (Exception e) { e.printStackTrace(); }
    }

    // util methods --------------------------------------------------------------------------------
    public int getResepiCategoryId(String categoryName) {
        return resepiProvider.getResepiCategoryId(categoryName);
    }

    public ArrayList<Resepi> getResepiList() {
        return resepiProvider.getResepiList();
    }

    public ArrayList<Resepi> getResepiList(int resepiCategory) {
        return resepiProvider.getResepiList(resepiCategory);
    }

    public ArrayList<Pair<String, Bitmap>> getFavoriteResepi() {
        return resepiProvider.getFavoriteResepi();
    }

    public void addFavorite(int resepiId) {
        resepiProvider.addFavorite(resepiId);
    }

    public void removeFavorite(int resepiId){
        resepiProvider.removeFavorite(resepiId);
    }

    public String[] getResepiNameList(int resepiCategory) {
        return resepiProvider.getResepiNameList(resepiCategory);
    }

    public String[] getResepiNameList() {
        return resepiProvider.getResepiNameList();
    }

    public ArrayList<Pair<String, Bitmap>> getResepiNameListWithImg(int resepiCategory, int resepiId) {
        return resepiProvider.getResepiNameListWithImg(resepiCategory, resepiId);
    }

    public ArrayList<Pair<String, Bitmap>> getResepiNameListWithImg(int resepiCategory) {
        return resepiProvider.getResepiNameListWithImg(resepiCategory);
    }

    public ArrayList<Pair<String, Bitmap>> getResepiNameListWithImg() {
        return resepiProvider.getResepiNameListWithImg();
    }

    public ArrayList<Pair<String, Bitmap>> getResepiCategoryListWithImg() {
        return resepiProvider.getResepiCategoryListWithImg();
    }

    public ArrayList<Pair<String, Bitmap>> getResepiCategoryListWithImg(int categoryId) {
        return resepiProvider.getResepiCategoryListWithImg(categoryId);
    }

    public ArrayList<Pair<String, Bitmap>> getResepiNameListWithImg(int resepiCategory, String resepiName) {
        return resepiProvider.getResepiNameListWithImg(resepiCategory, resepiName);
    }

    public ArrayList<Pair<String, Bitmap>> getResepiNameListWithImg(String resepiName) {
        return resepiProvider.getResepiNameListWithImg(resepiName);
    }

    public int getResepiId(String resepiName) {
        return resepiProvider.getResepiId(resepiName);
    }

    public int getResepiCount(int resepiCategory) {
        return resepiProvider.getResepiCount(resepiCategory);
    }

    public Resepi getResepiInfo(int resepiId) {
        return resepiProvider.getResepiInfo(resepiId);
    }
    // ---------------------------------------------------------------------------------------------
}
