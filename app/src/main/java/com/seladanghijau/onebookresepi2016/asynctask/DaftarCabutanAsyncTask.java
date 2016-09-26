package com.seladanghijau.onebookresepi2016.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;
import android.widget.Toast;

import com.seladanghijau.onebookresepi2016.activities.Cabutan;
import com.seladanghijau.onebookresepi2016.provider.HTTPProvider;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by seladanghijau on 23/9/2016.
 */
public class DaftarCabutanAsyncTask extends AsyncTask<Void, Void, String> {
    private ProgressDialog progressDialog;
    private Context context;
    private String email, phoneNo, nama, noIc;

    public DaftarCabutanAsyncTask(Context context, String email, String phoneNo, String nama, String noIc) {
        this.context = context;
        this.email = email;
        this.phoneNo = phoneNo;
        this.nama = nama;
        this.noIc = noIc;
    }

    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Please wait...");
        progressDialog.show();
    }

    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        extractResult(result);

        if(progressDialog.isShowing())
            progressDialog.dismiss();
    }

    protected String doInBackground(Void... params) {
        HttpURLConnection httpURLConnection;
        List<Pair<String, String>> dataList;

        try {
            dataList = new ArrayList<>();
            dataList.add(new Pair<>("email", email));
            dataList.add(new Pair<>("phoneNo", phoneNo));
            dataList.add(new Pair<>("nama", nama));
            dataList.add(new Pair<>("noIc", noIc));

            httpURLConnection = HTTPProvider.getServerConnection();

            return HTTPProvider.daftar(httpURLConnection, (ArrayList<Pair<String,String>>) dataList);
        } catch (Exception e) { e.printStackTrace(); }

        return "\"daftar\":[{\"code\":2}]";
    }

    private void extractResult(String result) {
        JSONArray jsonArray;
        JSONObject jsonObject;
        String info;

        info = "";
        try {
            // decode response
            jsonObject = new JSONObject(result);
            jsonArray = jsonObject.getJSONArray("daftar");

            for(int x=0 ; x<jsonArray.length() ; x++) {
                JSONObject tempJsonObject;
                String tempCode;

                tempJsonObject = jsonArray.getJSONObject(x);

                // setup result
                tempCode = tempJsonObject.getString("code");

                if(tempCode.equalsIgnoreCase("0")) {
                    String tempUID, tempDate, tempEmail, tempName, tempPhoneNo, tempIc;

                    tempUID = tempJsonObject.getString("daftarUID");
                    tempDate = tempJsonObject.getString("daftarDate");
                    tempEmail = tempJsonObject.getString("daftarEmail");
                    tempName = tempJsonObject.getString("daftarNama");
                    tempPhoneNo = tempJsonObject.getString("daftarPhoneNo");
                    tempIc = tempJsonObject.getString("daftarNoIc");

                    info = "\tUID: " + tempUID + "\n" +
                            "\tTarikh: " + tempDate + "\n" +
                            "\tE-mel: " + tempEmail + "\n" +
                            "\tNama: " + tempName + "\n" +
                            "\tNo. Telefon: " + tempPhoneNo + "\n" +
                            "\tNo. I/C: " + tempIc;
                } else if(tempCode.equalsIgnoreCase("1") || tempCode.equalsIgnoreCase("2")) {
                    ((Cabutan) context).setCabutanPref("");
                    Toast.makeText(context, "Anda gagal didaftarkan. Sila cuba sekali lagi.", Toast.LENGTH_LONG).show();
                    break;
                }
            }

            ((Cabutan) context).setCabutanPref(result);
            ((Cabutan) context).finish();
            Toast.makeText(context, "Anda telah didaftarkan.\n" +
                    "Nantikan panggilan melalui SMS atau e-mel anda.\n\n" +
                    "Info anda: \n" + info, Toast.LENGTH_LONG).show();
        } catch (Exception e) { e.printStackTrace(); }
    }
}
