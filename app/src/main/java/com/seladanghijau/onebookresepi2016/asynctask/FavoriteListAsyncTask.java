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
public class FavoriteListAsyncTask extends AsyncTask<Void, Void, Void> {
    private ProgressDialog progressDialog;
    private Context context;
    private ILoader loader;

    private String[] resepiNameList;
    private Bitmap[] bgResepiList;
    private ResepiManager resepiManager;
    private ArrayList<Pair<String, Bitmap>> favoriteResepi;

    public FavoriteListAsyncTask(Context context, ILoader loader, ResepiManager resepiManager) {
        this.context = context;
        this.loader = loader;
        this.resepiManager = resepiManager;
    }

    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Please wait...");
        progressDialog.show();
    }

    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        loader.onLoad(resepiNameList, bgResepiList); // load favorite resepi list

        if(progressDialog.isShowing())
            progressDialog.dismiss();
    }

    protected Void doInBackground(Void... params) {
        // resepi namelist with bg
        favoriteResepi = resepiManager.getFavoriteResepi();
        resepiNameList = new String[favoriteResepi.size()];
        bgResepiList = new Bitmap[favoriteResepi.size()];
        for(int x=0 ; x<favoriteResepi.size() ; x++) {
            Pair<String, Bitmap> stringBitmapPair;
            String resepiName;
            Bitmap resepiBg;

            stringBitmapPair = favoriteResepi.get(x);
            resepiName = stringBitmapPair.first;
            resepiBg = stringBitmapPair.second;

            resepiNameList[x] = resepiName;
            bgResepiList[x] = resepiBg;
        }

        return null;
    }
}
