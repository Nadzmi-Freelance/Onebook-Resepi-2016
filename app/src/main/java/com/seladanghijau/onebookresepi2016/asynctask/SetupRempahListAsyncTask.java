package com.seladanghijau.onebookresepi2016.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.AsyncTask;

import com.seladanghijau.onebookresepi2016.R;
import com.seladanghijau.onebookresepi2016.manager.ResepiManager;
import com.seladanghijau.onebookresepi2016.provider.ILoader;

/**
 * Created by seladanghijau on 18/9/2016.
 */
public class SetupRempahListAsyncTask extends AsyncTask<Void, Void, Void> {
    private ProgressDialog progressDialog;
    private Context context;
    private ILoader loader;
    private ResepiManager resepiManager;
    private TypedArray rempahImgList;

    public SetupRempahListAsyncTask(Context context, ILoader loader, ResepiManager resepiManager) {
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

        loader.onLoad(rempahImgList);

        if(progressDialog.isShowing())
            progressDialog.dismiss();
    }

    protected Void doInBackground(Void... params) {
        rempahImgList = context.getResources().obtainTypedArray(R.array.rempah_list);

        return null;
    }
}
