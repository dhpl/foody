package com.philong.foody.util;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Long on 9/4/2017.
 */

public class DownloadJson extends AsyncTask<String, Void, List<LatLng>> {

    protected  GetPolyLine mProtocol;

    public DownloadJson(GetPolyLine protocol) {
        mProtocol = protocol;
    }

    @Override
    protected List<LatLng> doInBackground(String... strings) {

        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(strings[0]);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = "";
            while((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line + "\n");
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            return parseJsonPolyLine(stringBuilder.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            httpURLConnection.disconnect();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<LatLng> latLngs) {
        super.onPostExecute(latLngs);
        mProtocol.compleGetPolyLine(latLngs);
    }

    public interface GetPolyLine{
        void compleGetPolyLine(List<LatLng> latLngs);
    }

    public List<LatLng> parseJsonPolyLine(String s){
        try {
            List<LatLng> latLngs = new ArrayList<>();
            JSONObject jsonRoot = new JSONObject(s);
            JSONArray jsonArray = jsonRoot.getJSONArray("routes");
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            JSONObject jsonObjectOverviewPolyLine = jsonObject.getJSONObject("overview_polyline");
            String points = jsonObjectOverviewPolyLine.getString("points");
            latLngs = PolyUtil.decode(points);
            return latLngs;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
