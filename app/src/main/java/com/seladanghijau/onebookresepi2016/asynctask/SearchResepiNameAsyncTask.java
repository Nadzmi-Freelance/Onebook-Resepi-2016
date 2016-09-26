package com.seladanghijau.onebookresepi2016.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Pair;
import android.view.LayoutInflater;

import com.seladanghijau.onebookresepi2016.manager.ResepiManager;
import com.seladanghijau.onebookresepi2016.provider.ILoader;

import java.util.ArrayList;

/**
 * Created by seladanghijau on 13/8/2016.
 */
public class SearchResepiNameAsyncTask extends AsyncTask<Void, Void, Void> {
    private LayoutInflater layoutInflater;
    private ProgressDialog progressDialog;
    private Context context;
    private ILoader loader;
    private ResepiManager resepiManager;

    private String resepiName, category;
    private String[] resepiNameList;
    private Bitmap[] imejresepiList;

    public SearchResepiNameAsyncTask(Context context, ILoader loader, ResepiManager resepiManager, String category, String resepiName) {
        this.context = context;
        this.loader = loader;
        this.resepiManager = resepiManager;
        this.resepiName = resepiName;
        this.category = category;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public SearchResepiNameAsyncTask(Context context, ILoader loader, ResepiManager resepiManager, String resepiName) {
        this.context = context;
        this.loader = loader;
        this.resepiManager = resepiManager;
        this.resepiName = resepiName;
        this.category = "";

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Please wait...");
        progressDialog.show();
    }

    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        loader.onLoad(resepiNameList, imejresepiList);

        if(progressDialog.isShowing())
            progressDialog.dismiss();
    }

    protected Void doInBackground(Void... params) {
        ArrayList<Pair<String, Bitmap>> resepiNameListWithImg;

        if(resepiName.isEmpty())
            resepiNameListWithImg = resepiManager.getResepiNameListWithImg();
        else if(category.isEmpty())
            resepiNameListWithImg = resepiManager.getResepiNameListWithImg(resepiName);
        else
            resepiNameListWithImg = resepiManager.getResepiNameListWithImg(resepiManager.getResepiCategoryId(category), resepiName);

        if(resepiNameListWithImg != null) {
            resepiNameList = new String[resepiNameListWithImg.size()];
            imejresepiList = new Bitmap[resepiNameListWithImg.size()];

            for (int x=0 ; x<resepiNameListWithImg.size() ; x++) {
                resepiNameList[x] = resepiNameListWithImg.get(x).first;
                imejresepiList[x] = resepiNameListWithImg.get(x).second;
            }
        } else {
            resepiNameList = null;
            imejresepiList = null;
        }

        return null;
    }
}
