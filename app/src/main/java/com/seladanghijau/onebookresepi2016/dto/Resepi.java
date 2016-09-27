package com.seladanghijau.onebookresepi2016.dto;

import android.graphics.Bitmap;
import android.util.Pair;

import java.util.ArrayList;

/**
 * Created by seladanghijau on 7/8/2016.
 */
public class Resepi {
    private int id, category;
    private String name, ringkasan;
    private Bitmap resepiImg;
    private String[] langkah;
    private boolean favourite;
    private ArrayList<Pair<String, String>> bahan;

    // constructors
    public Resepi(int id, String name, String ringkasan, int category, Bitmap resepiImg, String[] langkah, ArrayList<Pair<String, String>> bahan) {
        this.id = id; // resepi id
        this.name = name; // resepi category
        this.ringkasan = ringkasan; // resepi name
        this.category = category; // resepi ringkasan
        this.resepiImg = resepiImg; // resepi img
        this.langkah = langkah; // resepi langkah
        this.bahan = bahan; // resepi bahan pair -> bahanDesc:bahanName
        favourite = false;
    }

    public Resepi(int id, String name, String ringkasan, int category, Bitmap resepiImg, String[] langkah, ArrayList<Pair<String, String>> bahan, boolean favorite) {
        this.id = id; // resepi id
        this.name = name; // resepi category
        this.ringkasan = ringkasan; // resepi name
        this.category = category; // resepi ringkasan
        this.resepiImg = resepiImg; // resepi img
        this.langkah = langkah; // resepi langkah
        this.bahan = bahan; // resepi bahan pair -> bahanDesc:bahanName
        this.favourite = favorite;
    }

    public Resepi(int id, String name, Bitmap resepiImg) {
        this.id = id;
        this.name = name;
        this.resepiImg = resepiImg;
    }

    public Resepi(int id, String name, int category, Bitmap resepiImg) {
        this.id = id;
        this.name = name;
        this.resepiImg = resepiImg;
        this.category = category;
    }

    // setter & getter
    public int getId() { return id; }
    public int getCategory() { return category; }
    public String getName() { return name; }
    public String getRingkasan() { return ringkasan; }
    public Bitmap getResepiImg() { return resepiImg; }
    public String[] getLangkah() { return langkah; }
    public boolean getFavorite() { return  favourite; }
    public ArrayList<Pair<String, String>> getBahan() { return bahan; }

    public void setId(int id) { this.id = id; }
    public void setCategory(int category) { this.category = category; }
    public void setName(String name) { this.name = name; }
    public void setRingkasan(String ringkasan) { this.ringkasan = ringkasan; }
    public void setResepiImg(Bitmap resepiImg) { this.resepiImg = resepiImg; }
    public void setLangkah(String[] langkah) { this.langkah = langkah; }
    public void setFavorite(boolean favorite) { this.favourite = favorite; }
    public void setBahan(ArrayList<Pair<String, String>> bahan) { this.bahan = bahan; }
}
