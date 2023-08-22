package com.netizen.btsjhopeviral.NetizenJigsaw.JigsawUI;

import static com.netizen.btsjhopeviral.NetizenSettings.BACKUP_ADS_BANNER;
import static com.netizen.btsjhopeviral.NetizenSettings.BACKUP_ADS_NATIVES;
import static com.netizen.btsjhopeviral.NetizenSettings.HIGH_PAYING_KEYWORD1;
import static com.netizen.btsjhopeviral.NetizenSettings.HIGH_PAYING_KEYWORD2;
import static com.netizen.btsjhopeviral.NetizenSettings.HIGH_PAYING_KEYWORD3;
import static com.netizen.btsjhopeviral.NetizenSettings.HIGH_PAYING_KEYWORD4;
import static com.netizen.btsjhopeviral.NetizenSettings.HIGH_PAYING_KEYWORD5;
import static com.netizen.btsjhopeviral.NetizenSettings.MAIN_ADS_BANNER;
import static com.netizen.btsjhopeviral.NetizenSettings.MAIN_ADS_NATIVES;
import static com.netizen.btsjhopeviral.NetizenSettings.ON_OFF_DATA;
import static com.netizen.btsjhopeviral.NetizenSettings.SELECT_MAIN_ADS;
import static com.netizen.btsjhopeviral.NetizenSettings.SELECT_BACKUP_ADS;
import static com.netizen.btsjhopeviral.NetizenSettings.URL_DATA;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aliendroid.alienads.AliendroidBanner;
import com.aliendroid.alienads.AliendroidNative;
import com.netizen.btsjhopeviral.R;
import com.netizen.btsjhopeviral.NetizenJigsaw.asset.Wallpaper;
import com.netizen.btsjhopeviral.NetizenJigsaw.asset.WallpaperAdapter;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class PuzzleListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private WallpaperAdapter adapter;
    ArrayList<Wallpaper> webLists;

    private FrameLayout adContainerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puzzle_activity_list);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        // recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(PuzzleListActivity.this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        webLists = new ArrayList<>();



        if (ON_OFF_DATA.equals("1")) {
            if (checkConnectivity()) {
                loadUrlData();
            } else {
                ambildata();
            }

        } else {
            ambildata();
        }

        RelativeLayout layAdsbanner = findViewById(R.id.layNative);
        switch (SELECT_MAIN_ADS) {
            case "ADMOB":
            case "APPLOVIN-M":
                break;
            case "ADMOB-B":
                AliendroidBanner.SmallBannerAdmob(this, layAdsbanner, SELECT_BACKUP_ADS, MAIN_ADS_BANNER, BACKUP_ADS_BANNER, HIGH_PAYING_KEYWORD1, HIGH_PAYING_KEYWORD2, HIGH_PAYING_KEYWORD3, HIGH_PAYING_KEYWORD4, HIGH_PAYING_KEYWORD5);
                break;
            case "APPLOVIN-D":
                AliendroidBanner.SmallBannerApplovinDis(this, layAdsbanner, SELECT_BACKUP_ADS, MAIN_ADS_BANNER, BACKUP_ADS_BANNER);
                break;
            case "STARTAPP":
                AliendroidNative.SmallNativeStartApp(this, layAdsbanner, SELECT_BACKUP_ADS, MAIN_ADS_NATIVES, BACKUP_ADS_NATIVES);
                break;
            case "IRON":
                AliendroidBanner.SmallBannerIron(this, layAdsbanner, SELECT_BACKUP_ADS, MAIN_ADS_BANNER, BACKUP_ADS_BANNER);
                break;
            case "FACEBOOK":
                AliendroidNative.SmallNativeFan(this, layAdsbanner, SELECT_BACKUP_ADS, MAIN_ADS_NATIVES, BACKUP_ADS_NATIVES);
                break;
            case "GOOGLE-ADS":
                AliendroidBanner.SmallBannerGoogleAds(this, layAdsbanner, SELECT_BACKUP_ADS, MAIN_ADS_BANNER, BACKUP_ADS_BANNER);
                break;
        }


    }

    private void loadUrlData() {

        final ProgressDialog progressDialog = new ProgressDialog(PuzzleListActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray array = jsonObject.getJSONArray("Data");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jo = array.getJSONObject(i);
                        Wallpaper developers = new Wallpaper(jo.getString("title"), jo.getString("image"));

                        webLists.add(developers);

                    }

                    adapter = new WallpaperAdapter(webLists, PuzzleListActivity.this);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(PuzzleListActivity.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(PuzzleListActivity.this);
        requestQueue.add(stringRequest);
    }

    private boolean checkConnectivity() {
        boolean enabled = true;

        ConnectivityManager connectivityManager = (ConnectivityManager) PuzzleListActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();

        if ((info == null || !info.isConnected() || !info.isAvailable())) {
            return false;
        } else {
            return true;
        }


    }

    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = this.getAssets().open("netizeniseng.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    public void ambildata() {
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset());
            JSONArray jsonArray = jsonObject.getJSONArray("Data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonData = jsonArray.getJSONObject(i);
                Wallpaper developers = new Wallpaper(jsonData.getString("title"), jsonData.getString("image"));
                webLists.add(developers);
            }
            adapter = new WallpaperAdapter(webLists, PuzzleListActivity.this);
            recyclerView.setAdapter(adapter);

            if (SELECT_MAIN_ADS.equals("ADMOB") || SELECT_MAIN_ADS.equals("APPLOVIN-M")) {
                PuzzleAdmobNativeAdAdapter puzzleAdmobNativeAdAdapter = PuzzleAdmobNativeAdAdapter.Builder.with(MAIN_ADS_NATIVES,//admob native ad id
                                adapter,//current NetizenAdapter
                                "small"//Set the size "small", "medium" or "custom"
                        ).adItemInterval(5)//Repeat interval
                        .build();
                recyclerView.setAdapter(puzzleAdmobNativeAdAdapter);
            }

        } catch (JSONException e) {
            Toast.makeText(PuzzleListActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

}