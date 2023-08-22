package com.netizen.btsjhopeviral.NetizenActivity;

import static com.netizen.btsjhopeviral.NetizenSettings.BACKUP_ADS_BANNER;
import static com.netizen.btsjhopeviral.NetizenSettings.BACKUP_ADS_NATIVES;
import static com.netizen.btsjhopeviral.NetizenSettings.BASE_64_LICENSE_KEY;
import static com.netizen.btsjhopeviral.NetizenSettings.HIGH_PAYING_KEYWORD1;
import static com.netizen.btsjhopeviral.NetizenSettings.HIGH_PAYING_KEYWORD2;
import static com.netizen.btsjhopeviral.NetizenSettings.HIGH_PAYING_KEYWORD3;
import static com.netizen.btsjhopeviral.NetizenSettings.HIGH_PAYING_KEYWORD4;
import static com.netizen.btsjhopeviral.NetizenSettings.HIGH_PAYING_KEYWORD5;
import static com.netizen.btsjhopeviral.NetizenSettings.LINK_REDIRECT;
import static com.netizen.btsjhopeviral.NetizenSettings.MAIN_ADS_BANNER;
import static com.netizen.btsjhopeviral.NetizenSettings.MAIN_ADS_NATIVES;
import static com.netizen.btsjhopeviral.NetizenSettings.ON_OFF_DATA;
import static com.netizen.btsjhopeviral.NetizenSettings.PROTECT_APP;
import static com.netizen.btsjhopeviral.NetizenSettings.SELECT_BACKUP_ADS;
import static com.netizen.btsjhopeviral.NetizenSettings.SELECT_MAIN_ADS;
import static com.netizen.btsjhopeviral.NetizenSettings.STATUS_APP;
import static com.netizen.btsjhopeviral.NetizenSettings.URL_DATA;
import static com.netizen.btsjhopeviral.NetizenSettings.URL_DATA_MORE;
import static com.netizen.btsjhopeviral.NetizenSettings.VISIBLE_GONE_MOREAPP;
import static com.google.android.play.core.install.model.ActivityResult.RESULT_IN_APP_UPDATE_FAILED;
import static com.google.android.play.core.install.model.AppUpdateType.FLEXIBLE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.aliendroid.alienads.AliendroidBanner;
import com.aliendroid.alienads.AliendroidNative;
import com.netizen.btsjhopeviral.BuildConfig;
import com.netizen.btsjhopeviral.R;
import com.netizen.btsjhopeviral.NetizenAdapter.Netizencall_FakeAdapter;
import com.netizen.btsjhopeviral.NetizenAdapter.Netizencall_MoreAdapter;
import com.netizen.btsjhopeviral.NetizenJigsaw.JigsawUI.PuzzleMainActivity;
import com.netizen.btsjhopeviral.NetizenModel.Netizencall_Item;
import com.netizen.btsjhopeviral.NetizenModel.Netizencall_MoreList;
import com.netizen.btsjhopeviral.NetizenUIutama.NetizenMainActivity;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Netizencall_MainActivity extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 17326;
    public static Activity fa;
    public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 5469;
    List<Netizencall_MoreList> webLists;
    ReviewInfo reviewInfo;
    ReviewManager manager;
    List<Netizencall_Item> fakeLists;
    /*
       In App Update
        */ AppUpdateManager appUpdateManager;
    Task<AppUpdateInfo> appUpdateInfoTask;
    InstallStateUpdatedListener listener = new InstallStateUpdatedListener() {
        @Override
        public void onStateUpdate(InstallState installState) {
            if (installState.installStatus() == InstallStatus.DOWNLOADED) {
                Log.d("InstallDownloded", "InstallStatus sucsses");
                notifyUser();
            }
        }
    };
    private RecyclerView recyclerView;
    private Netizencall_MoreAdapter adapter;
    private RecyclerView recFake;
    private Netizencall_FakeAdapter adapterFake;
    private LinearLayout laymore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_activity_main);
        {
            fa = this;
        }
        Review();

        for (String signature : LibraryUtilsKt.getApkSignatures(this)) {
            Log.e("Signature", signature);
        }
        if (PROTECT_APP) {
            new PiracyChecker(this).enableGooglePlayLicensing(BASE_64_LICENSE_KEY).enableUnauthorizedAppsCheck().display(Display.DIALOG).enableInstallerId(InstallerID.GOOGLE_PLAY, InstallerID.AMAZON_APP_STORE, InstallerID.GALAXY_APPS).saveResultToSharedPreferences("my_app_preferences", "valid_license").start();
        }
        if (STATUS_APP.equals("1")) {
            String str = LINK_REDIRECT;
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
            finish();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
            }
        }
        setTitle("");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if (!android.provider.Settings.canDrawOverlays(this)) {
                checkPermission();

            }
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            int LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
            WindowManager.LayoutParams mWindowParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, LAYOUT_FLAG, // Overlay over the other apps.
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE    // This flag will enable the back key press.
                            | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, // make the window to deliver the focus to the BG window.
                    PixelFormat.TRANSPARENT);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recmore);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager HorizontalLayout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(HorizontalLayout);
        webLists = new ArrayList<>();
        laymore = findViewById(R.id.laymore);
        if (VISIBLE_GONE_MOREAPP.equals("1")) {
            laymore.setVisibility(View.VISIBLE);
        } else {
            laymore.setVisibility(View.GONE);
        }

        recFake = (RecyclerView) findViewById(R.id.recfake);
        recFake.setHasFixedSize(true);
        //RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recFake.setLayoutManager(sglm);
        fakeLists = new ArrayList<>();
        if (ON_OFF_DATA.equals("1")) {
            if (checkConnectivity()) {
                loadUrlDataFake();
                loadUrlDataMore();
            }
        } else {
            dataFake();
            loadUrlDataMore();
        }

        Button tbchatbot = findViewById(R.id.tbchatbot);
        tbchatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Netizencall_MainActivity.this, NetizenMainActivity.class);
                startActivity(intent);

            }
        });

        Button tbhome = findViewById(R.id.tbhome);
        tbhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button tbpuzzle = findViewById(R.id.tbpuzzle);
        tbpuzzle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Netizencall_MainActivity.this, PuzzleMainActivity.class);
                startActivity(intent);

            }
        });


        RelativeLayout layNative = findViewById(R.id.layNative);
        switch (SELECT_MAIN_ADS) {
            case "ADMOB":
                AliendroidNative.SmallNativeAdmob(this, layNative, SELECT_BACKUP_ADS, MAIN_ADS_NATIVES, BACKUP_ADS_NATIVES, HIGH_PAYING_KEYWORD1,
                        HIGH_PAYING_KEYWORD2, HIGH_PAYING_KEYWORD3, HIGH_PAYING_KEYWORD4, HIGH_PAYING_KEYWORD5);
                break;
            case "ADMOB-B":
                AliendroidBanner.SmallBannerAdmob(this, layNative, SELECT_BACKUP_ADS, MAIN_ADS_BANNER, BACKUP_ADS_BANNER, HIGH_PAYING_KEYWORD1,
                        HIGH_PAYING_KEYWORD2, HIGH_PAYING_KEYWORD3, HIGH_PAYING_KEYWORD4, HIGH_PAYING_KEYWORD5);
                break;
            case "APPLOVIN-M":
                AliendroidNative.SmallNativeMax(this, layNative, SELECT_BACKUP_ADS, MAIN_ADS_NATIVES, BACKUP_ADS_NATIVES);
                break;
            case "APPLOVIN-D":
                AliendroidBanner.SmallBannerApplovinDis(this, layNative, SELECT_BACKUP_ADS, MAIN_ADS_BANNER, BACKUP_ADS_BANNER);
                break;
            case "STARTAPP":
                AliendroidNative.SmallNativeStartApp(this, layNative, SELECT_BACKUP_ADS, MAIN_ADS_NATIVES, BACKUP_ADS_NATIVES);
                break;
            case "IRON":
                AliendroidBanner.SmallBannerIron(this, layNative, SELECT_BACKUP_ADS, MAIN_ADS_BANNER, BACKUP_ADS_BANNER);
                break;
            case "FACEBOOK":
                AliendroidNative.SmallNativeFan(this, layNative, SELECT_BACKUP_ADS, MAIN_ADS_NATIVES, BACKUP_ADS_NATIVES);
                break;
            case "GOOGLE-ADS":
                AliendroidBanner.SmallBannerGoogleAds(this, layNative, SELECT_BACKUP_ADS, MAIN_ADS_BANNER, BACKUP_ADS_BANNER);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.call_menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_share) {
            String shareLink = "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.shareit) + " " + shareLink);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
            return true;
        } else if (id == R.id.menu_rate) {
            String str = "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
            finish();
            return true;
        } else if (id == R.id.menu_seting) {
            try {
                Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
                intent.putExtra("extra_pkgname", getPackageName());
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return true;
        } else if (id == R.id.menu_update) {
            checkUpdate();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("netizeniseng.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void datamore() {
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset());
            JSONArray jsonArray = jsonObject.getJSONArray("More");
            // Extract data from json and store into ArrayList as class objects
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonData = jsonArray.getJSONObject(i);
                Netizencall_MoreList dataUrl = new Netizencall_MoreList();
                dataUrl.id = jsonData.getInt("id");
                dataUrl.name = jsonData.getString("title");
                dataUrl.image_url = jsonData.getString("image");
                dataUrl.link_url = jsonData.getString("link");
                webLists.add(dataUrl);
            }

            adapter = new Netizencall_MoreAdapter(webLists, Netizencall_MainActivity.this);
            recyclerView.setAdapter(adapter);

        } catch (JSONException e) {
            Toast.makeText(Netizencall_MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void dataFake() {
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset());
            JSONArray jsonArray = jsonObject.getJSONArray("Item");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonData = jsonArray.getJSONObject(i);
                Netizencall_Item dataUrl = new Netizencall_Item();
                dataUrl.Id = jsonData.getInt("id");
                dataUrl.namefake = jsonData.getString("name");
                dataUrl.image_url = jsonData.getString("image_url");
                dataUrl.viode_url = jsonData.getString("video_url");
                dataUrl.voice_url = jsonData.getString("voice_url");
                fakeLists.add(dataUrl);
            }
            adapterFake = new Netizencall_FakeAdapter(fakeLists, Netizencall_MainActivity.this);
            recFake.setAdapter(adapterFake);

        } catch (JSONException e) {
            Toast.makeText(Netizencall_MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private boolean checkConnectivity() {
        ConnectivityManager connectivityManager = (ConnectivityManager) Netizencall_MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return info != null && info.isConnected() && info.isAvailable();
    }


    private void loadUrlDataFake() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("Item");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonData = array.getJSONObject(i);
                        Netizencall_Item dataUrl = new Netizencall_Item();
                        dataUrl.Id = jsonData.getInt("id");
                        dataUrl.namefake = jsonData.getString("name");
                        dataUrl.image_url = jsonData.getString("image_url");
                        dataUrl.viode_url = jsonData.getString("video_url");
                        dataUrl.voice_url = jsonData.getString("voice_url");
                        fakeLists.add(dataUrl);
                    }
                    adapterFake = new Netizencall_FakeAdapter(fakeLists, Netizencall_MainActivity.this);
                    recFake.setAdapter(adapterFake);

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(Netizencall_MainActivity.this);
        requestQueue.add(stringRequest);
    }

     /*
    In app review
     */

    private void loadUrlDataMore() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATA_MORE, new Response.Listener<String>() {
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
                    adapter = new Netizencall_MoreAdapter(webLists, Netizencall_MainActivity.this);
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
        RequestQueue requestQueue = Volley.newRequestQueue(Netizencall_MainActivity.this);
        requestQueue.add(stringRequest);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
            if (!android.provider.Settings.canDrawOverlays(this)) {
                checkPermission();
            } else {

            }
        }

        if (requestCode == MY_REQUEST_CODE) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    if (resultCode != RESULT_OK) {
                        Log.d("RESULT_OK  :", "" + resultCode);
                    }
                    break;
                case Activity.RESULT_CANCELED:
                    if (resultCode != RESULT_CANCELED) {
                        Log.d("RESULT_CANCELED  :", "" + resultCode);
                    }
                    break;
                case RESULT_IN_APP_UPDATE_FAILED:
                    if (resultCode != RESULT_IN_APP_UPDATE_FAILED) {
                        Log.d("RESULT_IN_APP_FAILED:", "" + resultCode);
                    }
            }
        }
    }

    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!android.provider.Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
            }
        }
    }

    private void Review() {
        manager = ReviewManagerFactory.create(this);
        manager.requestReviewFlow().addOnCompleteListener(new OnCompleteListener<ReviewInfo>() {
            @Override
            public void onComplete(@NonNull Task<ReviewInfo> task) {
                if (task.isSuccessful()) {
                    reviewInfo = task.getResult();
                    manager.launchReviewFlow(Netizencall_MainActivity.this, reviewInfo).addOnFailureListener(new OnFailureListener() {
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

            }
        });
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
                    Log.d("UpdateAvailable", "update is there ");
                } else if (appUpdateInfo.updateAvailability() == 3) {
                    Log.d("Update", "3");
                    notifyUser();
                } else {
                    Log.d("NoUpdateAvailable", "update is not there ");
                }
            }
        });
    }

    private void requestUpdate(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.FLEXIBLE, Netizencall_MainActivity.this, MY_REQUEST_CODE);
            resume();
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void notifyUser() {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.frame_container), "An update has just been downloaded.", Snackbar.LENGTH_INDEFINITE);
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


}
