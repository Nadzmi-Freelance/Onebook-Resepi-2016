package com.seladanghijau.onebookresepi2016.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.seladanghijau.onebookresepi2016.R;
import com.seladanghijau.onebookresepi2016.provider.ILoader;

/**
 * Created by seladanghijau on 1/9/2016.
 */
public class TipsMasakanAsyncTask extends AsyncTask<Void, Void, Void> {
    private ProgressDialog progressDialog;
    private Context context;
    private ILoader loader;
    private String[] tipsTitle, tipDesc;

    public TipsMasakanAsyncTask(Context context, ILoader loader) {
        this.context = context;
        this.loader = loader;
    }

    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Please wait...");
        progressDialog.show();
    }

    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        loader.onLoad(tipsTitle, tipDesc);

        if(progressDialog.isShowing())
            progressDialog.dismiss();
    }

    protected Void doInBackground(Void... params) {
        tipsTitle = context.getResources().getStringArray(R.array.tipsMasakan_title);
        tipDesc = context.getResources().getStringArray(R.array.tipsMasakan_desc);

        return null;
    }
}
