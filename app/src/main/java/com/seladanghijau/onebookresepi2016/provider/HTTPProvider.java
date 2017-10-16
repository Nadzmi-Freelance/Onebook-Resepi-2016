package com.seladanghijau.onebookresepi2016.provider;

import android.content.Context;
import android.util.Pair;

import com.seladanghijau.onebookresepi2016.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


public class HTTPProvider {
    public static final int CODE_SUCCESS = 0;
    public static final int CODE_EXIST = 1;
    public static final int CODE_ERROR = 2;

    public static HttpURLConnection getServerConnection(Context context) throws Exception {
        URL daftarUrl;
        HttpURLConnection httpURLConnection;

        daftarUrl = new URL(context.getResources().getString(R.string.daftar_url));
        httpURLConnection = (HttpURLConnection) daftarUrl.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        return httpURLConnection;
    }

    public static String daftar(HttpURLConnection httpURLConnection, ArrayList<Pair<String, String>> dataList) throws Exception {
        InputStream inputStream;
        OutputStream outputStream;
        BufferedReader bufferedReader;
        BufferedWriter bufferedWriter;
        StringBuffer response;
        String result;

        // initialize
        response = new StringBuffer();

        // send data
        outputStream = httpURLConnection.getOutputStream();
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

        bufferedWriter.write(getEncodedUrl(dataList));
        bufferedWriter.flush();

        // retrieve response
        if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            inputStream = httpURLConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            // retrieve response & store in stringbuffer
            String readResponse;
            while((readResponse = bufferedReader.readLine()) != null) {
                response.append(readResponse);
            }

            result = response.toString();
        } else
            return "\"daftar\":[{\"code\":" + CODE_ERROR + "}]";

        // close output stream
        bufferedWriter.close();
        outputStream.close();

        // close input stream
        bufferedReader.close();
        inputStream.close();

        return result;
    }

    private static String getEncodedUrl(ArrayList<Pair<String, String>> dataList) throws UnsupportedEncodingException {
        StringBuilder encodedUrl;
        boolean first;

        first = true;
        encodedUrl = new StringBuilder();
        for(int x=0 ; x<dataList.size() ; x++){
            if (first)
                first = false;
            else
                encodedUrl.append("&");

            encodedUrl.append(URLEncoder.encode(dataList.get(x).first, "UTF-8"));
            encodedUrl.append("=");
            encodedUrl.append(URLEncoder.encode(dataList.get(x).second, "UTF-8"));
        }

        return encodedUrl.toString();
    }
}
