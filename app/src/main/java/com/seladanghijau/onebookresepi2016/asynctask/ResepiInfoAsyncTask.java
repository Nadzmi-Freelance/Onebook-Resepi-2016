package com.seladanghijau.onebookresepi2016.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.seladanghijau.onebookresepi2016.dto.Resepi;
import com.seladanghijau.onebookresepi2016.manager.ResepiManager;
import com.seladanghijau.onebookresepi2016.provider.ILoader;

/**
 * Created by seladanghijau on 11/8/2016.
 */
public class ResepiInfoAsyncTask extends AsyncTask<Void, Void, Void> {
    private Context context;
    private ILoader loader;
    private ResepiManager resepiManager;

    private Resepi resepiInfo;
    private String namaResepi;
    private int resepiId;

    public ResepiInfoAsyncTask(Context context, ILoader loader, ResepiManager resepiManager, String namaResepi) {
        this.context = context;
        this.loader = loader;
        this.resepiManager = resepiManager;
        this.namaResepi = namaResepi;
    }

    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        loader.onLoad(resepiInfo);
    }

    protected Void doInBackground(Void... params) {
        resepiId = resepiManager.getResepiId(namaResepi);
        resepiInfo = resepiManager.getResepiInfo(resepiId);

        return null;
    }
}
