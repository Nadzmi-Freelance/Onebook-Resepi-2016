package com.seladanghijau.onebookresepi2016.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Pair;

import com.seladanghijau.onebookresepi2016.manager.ResepiManager;
import com.seladanghijau.onebookresepi2016.provider.ILoader;

import java.util.ArrayList;

/**
 * Created by seladanghijau on 11/8/2016.
 */
public class SetupResepiListAsyncTask extends AsyncTask<Void, Void, Void> {
    private Context context;
    private ILoader loader;
    private ProgressDialog progressDialog;

    private String categoryName;
    private int category;
    private String[] resepiNameList;
    private Bitmap[] bgResepiList;
    private ResepiManager resepiManager;
    private ArrayList<Pair<String, Bitmap>> resepiNameListWithImg;

    public SetupResepiListAsyncTask(Context context, ILoader loader, ResepiManager resepiManager, String categoryName) {
        this.context = context;
        this.loader = loader;
        this.resepiManager = resepiManager;
        this.categoryName = categoryName;
    }

    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Please wait...");
        progressDialog.show();
    }

    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        loader.onLoad(category, resepiNameList, bgResepiList); // load resepi list

        if(progressDialog.isShowing())
            progressDialog.dismiss();
    }

    protected Void doInBackground(Void... params) {
        category = resepiManager.getResepiCategoryId(categoryName);

        // resepi namelist with bg
        resepiNameListWithImg = resepiManager.getResepiNameListWithImg(category);
        resepiNameList = new String[resepiNameListWithImg.size()];
        bgResepiList = new Bitmap[resepiNameListWithImg.size()];
        for(int x=0 ; x<resepiNameListWithImg.size() ; x++) {
            Pair<String, Bitmap> stringBitmapPair;
            String resepiName;
            Bitmap resepiBg;

            stringBitmapPair = resepiNameListWithImg.get(x);
            resepiName = stringBitmapPair.first;
            resepiBg = stringBitmapPair.second;

            resepiNameList[x] = resepiName;
            bgResepiList[x] = resepiBg;
        }

        return null;
    }
}
