package com.netizen.btsjhopeviral.NetizenUIutama;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.aliendroid.alienads.AlienOpenAds;
import com.aliendroid.alienads.AliendroidInitialize;
import com.aliendroid.sdkads.config.AppPromote;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.netizen.btsjhopeviral.NetizenSettings;
import com.netizen.btsjhopeviral.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NetizenSplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        AppPromote.initializeAppPromote(NetizenSplashActivity.this);
        if (NetizenSettings.ON_OFF_ADS.equals("1")) {
            if (checkConnectivity()) {
                loadUrlData();
            }
        } else {
            switch (NetizenSettings.SELECT_MAIN_ADS) {
                case "ADMOB":
                case "ADMOB-B":
                    AliendroidInitialize.SelectAdsAdmob(NetizenSplashActivity.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.INITIALIZE_SDK_BACKUP_ADS);
                    break;
                case "GOOGLE-ADS":
                    AliendroidInitialize.SelectAdsGoogleAds(NetizenSplashActivity.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.INITIALIZE_SDK_BACKUP_ADS);
                    break;
                case "APPLOVIN-D":
                    AliendroidInitialize.SelectAdsApplovinDis(NetizenSplashActivity.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.INITIALIZE_SDK_BACKUP_ADS);
                    break;
                case "APPLOVIN-M":
                    AliendroidInitialize.SelectAdsApplovinMax(NetizenSplashActivity.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.INITIALIZE_SDK_BACKUP_ADS);
                    break;
                case "IRON":
                    AliendroidInitialize.SelectAdsIron(NetizenSplashActivity.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.INITIALIZE_MAIN_SDK, NetizenSettings.INITIALIZE_SDK_BACKUP_ADS);
                    break;
                case "STARTAPP":
                    AliendroidInitialize.SelectAdsStartApp(NetizenSplashActivity.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.INITIALIZE_MAIN_SDK, NetizenSettings.INITIALIZE_SDK_BACKUP_ADS);
                    break;
                case "FACEBOOK":
                    AliendroidInitialize.SelectAdsFAN(NetizenSplashActivity.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.INITIALIZE_SDK_BACKUP_ADS);
                    break;
            }
            AppPromote.initializeAppPromote(NetizenSplashActivity.this);
            openAds();


            new CountDownTimer(2000, 1000) {
                @Override
                public void onFinish() {
                    Intent intent = new Intent(getBaseContext(), NetizenMenuActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onTick(long millisUntilFinished) {

                }
            }.start();
        }

    }

    private void loadUrlData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                NetizenSettings.URL_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObj = new JSONObject(response);
                    JSONArray contacts = jsonObj.getJSONArray("Ads");
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        NetizenSettings.VISIBLE_GONE_MOREAPP = c.getString("more_app");
                        NetizenSettings.SELECT_MAIN_ADS = c.getString("select_main_ads");
                        NetizenSettings.SELECT_BACKUP_ADS = c.getString("select_backup_ads");

                        NetizenSettings.MAIN_ADS_BANNER = c.getString("main_ads_banner");
                        NetizenSettings.MAIN_ADS_INTERTITIAL = c.getString("main_ads_intertitial");
                        NetizenSettings.MAIN_ADS_NATIVES = c.getString("main_ads_native");

                        NetizenSettings.ADMOB_OPENADS = c.getString("open_ads");

                        NetizenSettings.BACKUP_ADS_BANNER = c.getString("backup_ads_banner");
                        NetizenSettings.BACKUP_ADS_INTERTITIAL = c.getString("backup_ads_intertitial");
                        NetizenSettings.BACKUP_ADS_NATIVES = c.getString("backup_ads_native");

                        NetizenSettings.INITIALIZE_MAIN_SDK = c.getString("initialize_sdk");
                        NetizenSettings.INITIALIZE_SDK_BACKUP_ADS = c.getString("initialize_sdk_backup_ads");

                        NetizenSettings.INTERVAL = c.getInt("interval_intertitial");

                        NetizenSettings.HIGH_PAYING_KEYWORD1 = c.getString("high_paying_keyword_1");
                        NetizenSettings.HIGH_PAYING_KEYWORD2 = c.getString("high_paying_keyword_2");
                        NetizenSettings.HIGH_PAYING_KEYWORD3 = c.getString("high_paying_keyword_3");
                        NetizenSettings.HIGH_PAYING_KEYWORD4 = c.getString("high_paying_keyword_4");
                        NetizenSettings.HIGH_PAYING_KEYWORD5 = c.getString("high_paying_keyword_5");

                        NetizenSettings.STATUS_APP = c.getString("status_app");
                        NetizenSettings.LINK_REDIRECT = c.getString("link_redirect");


                        switch (NetizenSettings.SELECT_MAIN_ADS) {
                            case "ADMOB":
                            case "ADMOB-B":
                                AliendroidInitialize.SelectAdsAdmob(NetizenSplashActivity.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.INITIALIZE_SDK_BACKUP_ADS);
                                break;
                            case "GOOGLE-ADS":
                                AliendroidInitialize.SelectAdsGoogleAds(NetizenSplashActivity.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.INITIALIZE_SDK_BACKUP_ADS);
                                break;
                            case "APPLOVIN-D":
                                AliendroidInitialize.SelectAdsApplovinDis(NetizenSplashActivity.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.INITIALIZE_SDK_BACKUP_ADS);
                                break;
                            case "APPLOVIN-M":
                                AliendroidInitialize.SelectAdsApplovinMax(NetizenSplashActivity.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.INITIALIZE_SDK_BACKUP_ADS);
                                break;
                            case "IRON":
                                AliendroidInitialize.SelectAdsIron(NetizenSplashActivity.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.INITIALIZE_MAIN_SDK, NetizenSettings.INITIALIZE_SDK_BACKUP_ADS);
                                break;
                            case "STARTAPP":
                                AliendroidInitialize.SelectAdsStartApp(NetizenSplashActivity.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.INITIALIZE_MAIN_SDK, NetizenSettings.INITIALIZE_SDK_BACKUP_ADS);
                                break;
                            case "FACEBOOK":
                                AliendroidInitialize.SelectAdsFAN(NetizenSplashActivity.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.INITIALIZE_SDK_BACKUP_ADS);
                                break;
                        }
                        openAds();

                    }
                } catch (JSONException e) {
                    openMain();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                openMain();
                Toast.makeText(NetizenSplashActivity.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(NetizenSplashActivity.this);
        requestQueue.add(stringRequest);

    }


    private boolean checkConnectivity() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return info != null && info.isConnected() && info.isAvailable();
    }

    public void openMain() {
        new CountDownTimer(2000, 1000) {
            @Override
            public void onFinish() {
                Intent intent = new Intent(getBaseContext(), NetizenMenuActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onTick(long millisUntilFinished) {
            }
        }.start();
    }

    private void openAds() {
        AlienOpenAds.LoadOpenAds(NetizenSettings.ADMOB_OPENADS, true);
        AlienOpenAds.AppOpenAdManager.showAdIfAvailable(NetizenSplashActivity.this, new AlienOpenAds.OnShowAdCompleteListener() {
            @Override
            public void onShowAdComplete() {
                openMain();
            }
        });
    }

}
