package com.netizen.btsjhopeviral.NetizenUIutama;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.aliendroid.alienads.AlienGDPR;
import com.aliendroid.alienads.AliendroidBanner;
import com.aliendroid.alienads.AliendroidInitialize;
import com.aliendroid.alienads.AliendroidIntertitial;
import com.aliendroid.alienads.AliendroidNative;
import com.netizen.btsjhopeviral.BuildConfig;
import com.netizen.btsjhopeviral.NetizenSettings;
import com.netizen.btsjhopeviral.R;
import com.netizen.btsjhopeviral.NetizenActivity.Netizencall_MainActivity;
import com.netizen.btsjhopeviral.NetizenAdapter.Netizencall_MoreAdapter;
import com.netizen.btsjhopeviral.NetizenJigsaw.JigsawUI.PuzzleMainActivity;
import com.netizen.btsjhopeviral.NetizenModel.Netizencall_MoreList;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.javiersantos.piracychecker.PiracyChecker;
import com.github.javiersantos.piracychecker.enums.Display;
import com.github.javiersantos.piracychecker.enums.InstallerID;
import com.github.javiersantos.piracychecker.utils.LibraryUtilsKt;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.OnFailureListener;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;


import static com.google.android.play.core.install.model.ActivityResult.RESULT_IN_APP_UPDATE_FAILED;
import static com.google.android.play.core.install.model.AppUpdateType.FLEXIBLE;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NetizenMenuActivity extends AppCompatActivity {
    private CardView cdmain, cdfakecall, cdjigsaw, cdabout;
    private static final int REQUEST = 114;
    private static final int MY_REQUEST_CODE = 17326;
    List<Netizencall_MoreList> webLists;
    ReviewInfo reviewInfo;
    ReviewManager manager;
    private RecyclerView recyclerView;
    private Netizencall_MoreAdapter adapter;
    private LinearLayout laymore;
    boolean doubleBackToExitPressedOnce = false;
    /*
    In App Update
     */ AppUpdateManager appUpdateManager;
    com.google.android.play.core.tasks.Task<AppUpdateInfo> appUpdateInfoTask;
    InstallStateUpdatedListener listener = new InstallStateUpdatedListener() {
        @Override
        public void onStateUpdate(InstallState installState) {
            if (installState.installStatus() == InstallStatus.DOWNLOADED) {
                Log.d("InstallDownloded", "InstallStatus sucsses");
                notifyUser();
            }
        }
    };


    void checkWriteData() {
        if (ContextCompat.checkSelfPermission(NetizenMenuActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(NetizenMenuActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        checkWriteData();
//        checkUpdate();
        Review();
        for (String signature : LibraryUtilsKt.getApkSignatures(this)) {
            Log.e("Signature", signature);
        }
        if (NetizenSettings.PROTECT_APP) {
            new PiracyChecker(this).enableGooglePlayLicensing(NetizenSettings.BASE_64_LICENSE_KEY).enableUnauthorizedAppsCheck().display(Display.DIALOG).enableInstallerId(InstallerID.GOOGLE_PLAY, InstallerID.AMAZON_APP_STORE, InstallerID.GALAXY_APPS).saveResultToSharedPreferences("my_app_preferences", "valid_license").start();
        }
        if (NetizenSettings.STATUS_APP.equals("1")) {
            String str = NetizenSettings.LINK_REDIRECT;
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
            finish();
        }

        AlienGDPR.loadGdpr(this, NetizenSettings.SELECT_MAIN_ADS, NetizenSettings.CHILD_DIRECT_GDPR);
        switch (NetizenSettings.SELECT_MAIN_ADS) {
            case "ADMOB":
            case "ADMOB-B":
                AliendroidInitialize.SelectAdsAdmob(this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.INITIALIZE_SDK_BACKUP_ADS);
                break;
            case "GOOGLE-ADS":
                AliendroidInitialize.SelectAdsGoogleAds(this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.INITIALIZE_SDK_BACKUP_ADS);
                break;
            case "APPLOVIN-D":
                AliendroidInitialize.SelectAdsApplovinDis(this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.INITIALIZE_SDK_BACKUP_ADS);
                break;
            case "APPLOVIN-M":
                AliendroidInitialize.SelectAdsApplovinMax(this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.INITIALIZE_SDK_BACKUP_ADS);
                break;
            case "IRON":
                AliendroidInitialize.SelectAdsIron(this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.INITIALIZE_MAIN_SDK, NetizenSettings.INITIALIZE_SDK_BACKUP_ADS);
                break;
            case "STARTAPP":
                AliendroidInitialize.SelectAdsStartApp(this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.INITIALIZE_MAIN_SDK, NetizenSettings.INITIALIZE_SDK_BACKUP_ADS);
                break;
            case "FACEBOOK":
                AliendroidInitialize.SelectAdsFAN(this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.INITIALIZE_SDK_BACKUP_ADS);
                break;
        }
        RelativeLayout layAdsbanner = findViewById(R.id.layAds);
        switch (NetizenSettings.SELECT_MAIN_ADS) {
            case "ADMOB":
                AliendroidNative.SmallNativeAdmob(this, layAdsbanner, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_NATIVES, NetizenSettings.BACKUP_ADS_NATIVES, NetizenSettings.HIGH_PAYING_KEYWORD1, NetizenSettings.HIGH_PAYING_KEYWORD2, NetizenSettings.HIGH_PAYING_KEYWORD3, NetizenSettings.HIGH_PAYING_KEYWORD4, NetizenSettings.HIGH_PAYING_KEYWORD5);
                AliendroidIntertitial.LoadIntertitialAdmob(this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, NetizenSettings.HIGH_PAYING_KEYWORD1, NetizenSettings.HIGH_PAYING_KEYWORD2, NetizenSettings.HIGH_PAYING_KEYWORD3, NetizenSettings.HIGH_PAYING_KEYWORD4, NetizenSettings.HIGH_PAYING_KEYWORD5);
                break;
            case "ADMOB-B":
                AliendroidBanner.SmallBannerAdmob(this, layAdsbanner, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_BANNER, NetizenSettings.BACKUP_ADS_BANNER, NetizenSettings.HIGH_PAYING_KEYWORD1, NetizenSettings.HIGH_PAYING_KEYWORD2, NetizenSettings.HIGH_PAYING_KEYWORD3, NetizenSettings.HIGH_PAYING_KEYWORD4, NetizenSettings.HIGH_PAYING_KEYWORD5);
                AliendroidIntertitial.LoadIntertitialAdmob(this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, NetizenSettings.HIGH_PAYING_KEYWORD1, NetizenSettings.HIGH_PAYING_KEYWORD2, NetizenSettings.HIGH_PAYING_KEYWORD3, NetizenSettings.HIGH_PAYING_KEYWORD4, NetizenSettings.HIGH_PAYING_KEYWORD5);
                break;
            case "APPLOVIN-M":
                AliendroidNative.SmallNativeMax(this, layAdsbanner, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_NATIVES, NetizenSettings.BACKUP_ADS_NATIVES);
                AliendroidIntertitial.LoadIntertitialApplovinMax(this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL);
                break;
            case "APPLOVIN-D":
                AliendroidBanner.SmallBannerApplovinDis(this, layAdsbanner, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_BANNER, NetizenSettings.BACKUP_ADS_BANNER);
                AliendroidIntertitial.LoadIntertitialApplovinDis(this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL);
                break;
            case "STARTAPP":
                AliendroidNative.SmallNativeStartApp(this, layAdsbanner, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_NATIVES, NetizenSettings.BACKUP_ADS_NATIVES);
                AliendroidIntertitial.LoadIntertitialStartApp(this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL);
                break;
            case "IRON":
                AliendroidBanner.SmallBannerIron(this, layAdsbanner, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_BANNER, NetizenSettings.BACKUP_ADS_BANNER);
                AliendroidIntertitial.LoadIntertitialIron(this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL);
                break;
            case "FACEBOOK":
                AliendroidNative.SmallNativeFan(this, layAdsbanner, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_NATIVES, NetizenSettings.BACKUP_ADS_NATIVES);
                AliendroidIntertitial.LoadIntertitialFAN(this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL);
                break;
            case "GOOGLE-ADS":
                AliendroidBanner.SmallBannerGoogleAds(this, layAdsbanner, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_BANNER, NetizenSettings.BACKUP_ADS_BANNER);
                AliendroidIntertitial.LoadIntertitialGoogleAds(this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL);
                break;
        }
        cdmain = findViewById(R.id.cdcontact);
        cdfakecall = findViewById(R.id.cdfakecall);
        cdjigsaw = findViewById(R.id.cdjigsaw);
        cdabout = findViewById(R.id.cdabout);

        cdmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NetizenMenuActivity.this, NetizenMainActivity.class);
                startActivity(intent);
            }
        });

        cdfakecall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NetizenMenuActivity.this, Netizencall_MainActivity.class);
                startActivity(intent);
            }
        });

        cdjigsaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NetizenMenuActivity.this, PuzzleMainActivity.class);
                startActivity(intent);
            }
        });

        cdabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NetizenMenuActivity.this, NetizenGuideActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recmore);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager HorizontalLayout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false);
        recyclerView.setLayoutManager(HorizontalLayout);
        webLists = new ArrayList<>();
        laymore = findViewById(R.id.laymore);
        if (NetizenSettings.VISIBLE_GONE_MOREAPP.equals("1")) {
            laymore.setVisibility(View.VISIBLE);
        } else {
            laymore.setVisibility(View.GONE);
        }

        if (NetizenSettings.ON_OFF_DATA.equals("1")) {
            if (checkConnectivity()) {
                loadUrlDataMore();
            }
        } else {
            loadUrlDataMore();
        }

    }


    private void checkUpdate() {
        appUpdateManager = AppUpdateManagerFactory.create(this);
        appUpdateManager.registerListener(listener);

        appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                Log.d("appUpdateInfo :", "packageName :" + appUpdateInfo.packageName() + ", " + "availableVersionCode :" + appUpdateInfo.availableVersionCode() + ", " + "updateAvailability :" + appUpdateInfo.updateAvailability() + ", " + "installStatus :" + appUpdateInfo.installStatus());

                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && appUpdateInfo.isUpdateTypeAllowed(FLEXIBLE)) {
                    requestUpdate(appUpdateInfo);
                    //Log.d("UpdateAvailable","update is there ");
                } else if (appUpdateInfo.updateAvailability() == 3) {
                    Log.d("Update", "3");
                    notifyUser();
                } else {
                    Toast.makeText(NetizenMenuActivity.this, "No Update Available", Toast.LENGTH_SHORT).show();
                    Log.d("NoUpdateAvailable", "update is not there ");
                }
            }
        });
    }

    private void loadUrlDataMore() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                NetizenSettings.URL_DATA_MORE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("More");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonData = array.getJSONObject(i);
                        Netizencall_MoreList dataUrl = new Netizencall_MoreList();
                        dataUrl.id = jsonData.getInt("id");
                        dataUrl.name = jsonData.getString("title");
                        dataUrl.image_url = jsonData.getString("image");
                        dataUrl.link_url = jsonData.getString("link");
                        webLists.add(dataUrl);
                    }
                    adapter = new Netizencall_MoreAdapter(webLists, NetizenMenuActivity.this);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(NetizenMenuActivity.this);
        requestQueue.add(stringRequest);
    }

    private boolean checkConnectivity() {
        ConnectivityManager connectivityManager = (ConnectivityManager) NetizenMenuActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return info != null && info.isConnected() && info.isAvailable();
    }

    private void requestUpdate(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.FLEXIBLE, NetizenMenuActivity.this, MY_REQUEST_CODE);
            resume();
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_REQUEST_CODE) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    if (resultCode != RESULT_OK) {
                        Toast.makeText(this, "RESULT_OK" + resultCode, Toast.LENGTH_LONG).show();
                        Log.d("RESULT_OK  :", "" + resultCode);
                    }
                    break;
                case Activity.RESULT_CANCELED:

                    if (resultCode != RESULT_CANCELED) {
                        Toast.makeText(this, "RESULT_CANCELED" + resultCode, Toast.LENGTH_LONG).show();
                        Log.d("RESULT_CANCELED  :", "" + resultCode);
                    }
                    break;
                case RESULT_IN_APP_UPDATE_FAILED:

                    if (resultCode != RESULT_IN_APP_UPDATE_FAILED) {

                        Toast.makeText(this, "RESULT_IN_APP_UPDATE_FAILED" + resultCode, Toast.LENGTH_LONG).show();
                        Log.d("RESULT_IN_APP_FAILED:", "" + resultCode);
                    }
            }

            if (resultCode == Activity.RESULT_OK) {
                Uri treeUri = data.getData();
                int takeFlags = data.getFlags();
                takeFlags &= (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                    Log.i("TAG", "takePersistableUriPermission: " + treeUri);
                    this.getContentResolver().takePersistableUriPermission(treeUri, takeFlags);

                }

            }
        }
    }

    private void notifyUser() {

        Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_main), "An update has just been downloaded.", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("RESTART", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUpdateManager.completeUpdate();
            }
        });
        snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }

    private void resume() {
        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                    notifyUser();

                }

            }
        });
    }

    /*
    In app review
     */

    private void Review() {
        manager = ReviewManagerFactory.create(this);
        manager.requestReviewFlow().addOnCompleteListener(new OnCompleteListener<ReviewInfo>() {
            @Override
            public void onComplete(@NonNull Task<ReviewInfo> task) {
                if (task.isSuccessful()) {
                    reviewInfo = task.getResult();
                    manager.launchReviewFlow(NetizenMenuActivity.this, reviewInfo).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            //Toast.makeText(Netizencall_MainActivity.this, "Rating Failed", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // Toast.makeText(Netizencall_MainActivity.this, "Review Completed, Thank You!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                //Toast.makeText(Netizencall_MainActivity.this, "In-App Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void exitapp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(NetizenMenuActivity.this);
        builder.setCancelable(true);
        builder.setIcon(R.drawable.ic_baseline_cancel_24);
        builder.setTitle("Exit App");
        builder.setMessage("Are you sure you want to leave the application?");
        builder.setInverseBackgroundForced(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        exitapp();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }

        }
    }

}