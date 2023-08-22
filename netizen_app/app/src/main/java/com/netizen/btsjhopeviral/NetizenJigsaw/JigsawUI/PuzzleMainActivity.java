package com.netizen.btsjhopeviral.NetizenJigsaw.JigsawUI;

import static com.google.android.play.core.install.model.ActivityResult.RESULT_IN_APP_UPDATE_FAILED;
import static com.google.android.play.core.install.model.AppUpdateType.FLEXIBLE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.aliendroid.alienads.AliendroidBanner;
import com.aliendroid.alienads.AliendroidNative;
import com.netizen.btsjhopeviral.BuildConfig;
import com.netizen.btsjhopeviral.NetizenSettings;
import com.netizen.btsjhopeviral.R;
import com.github.javiersantos.piracychecker.PiracyChecker;
import com.github.javiersantos.piracychecker.enums.Display;
import com.github.javiersantos.piracychecker.enums.InstallerID;
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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PuzzleMainActivity extends AppCompatActivity {


    String mCurrentPhotoPath;
    private static final int REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 2;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 3;
    static final int REQUEST_IMAGE_GALLERY = 4;
    public static MediaPlayer mp;
    private RadioGroup list_action;

    /*
    Cottume level nember
     */
    public static int sum = 12; // 4 x 3
    public static int row = 4;
    public static int col = 3;

    private FrameLayout adContainerView;
    private CardView sound;
    public static int numsound = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puzzle_activity_main);

        mp = MediaPlayer.create(this, R.raw.click);
        sound = findViewById(R.id.cdsound);
        final ImageView imgsound = findViewById(R.id.imgsound);
        sound.setTag("gray");
        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tag = sound.getTag().toString();
                if (tag.equalsIgnoreCase("gray")) {
                    sound.setTag("red");
                    imgsound.setBackground(getResources().getDrawable(R.drawable.ic_baseline_volume_off_24));
                    numsound = 0;

                } else {
                    sound.setTag("gray");
                    imgsound.setBackground(getResources().getDrawable(R.drawable.ic_baseline_volume_up_24));
                    numsound = 1;
                }
            }
        });
        list_action = findViewById(R.id.list_action);
        list_action.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id) {
                    case R.id.rd_12:
                        sum = 12;
                        row = 4;
                        col = 3;
                        break;
                    case R.id.rd_20:
                        sum = 20;
                        row = 5;
                        col = 4;
                        break;
                    case R.id.rd_30:
                        sum = 30;
                        row = 6;
                        col = 5;
                        break;
                }
            }
        });

//        checkUpdate();
        Review();

        if (NetizenSettings.STATUS_APP.equals("1")) {
            String str = NetizenSettings.LINK_REDIRECT;
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
            finish();
        }
        if (NetizenSettings.PROTECT_APP) {
            new PiracyChecker(this).enableGooglePlayLicensing(NetizenSettings.BASE_64_LICENSE_KEY).enableUnauthorizedAppsCheck().display(Display.DIALOG).enableInstallerId(InstallerID.GOOGLE_PLAY, InstallerID.AMAZON_APP_STORE, InstallerID.GALAXY_APPS).saveResultToSharedPreferences("my_app_preferences", "valid_license").start();
        }


        RelativeLayout layNative = findViewById(R.id.layNative);
        switch (NetizenSettings.SELECT_MAIN_ADS) {
            case "ADMOB":
                AliendroidNative.SmallNativeAdmob(this, layNative, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_NATIVES, NetizenSettings.BACKUP_ADS_NATIVES, NetizenSettings.HIGH_PAYING_KEYWORD1,
                        NetizenSettings.HIGH_PAYING_KEYWORD2, NetizenSettings.HIGH_PAYING_KEYWORD3, NetizenSettings.HIGH_PAYING_KEYWORD4, NetizenSettings.HIGH_PAYING_KEYWORD5);
                break;
            case "ADMOB-B":
                AliendroidBanner.SmallBannerAdmob(this, layNative, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_BANNER, NetizenSettings.BACKUP_ADS_BANNER, NetizenSettings.HIGH_PAYING_KEYWORD1,
                        NetizenSettings.HIGH_PAYING_KEYWORD2, NetizenSettings.HIGH_PAYING_KEYWORD3, NetizenSettings.HIGH_PAYING_KEYWORD4, NetizenSettings.HIGH_PAYING_KEYWORD5);
                break;
            case "APPLOVIN-M":
                AliendroidNative.SmallNativeMax(this, layNative, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_NATIVES, NetizenSettings.BACKUP_ADS_NATIVES);
                break;
            case "APPLOVIN-D":
                AliendroidBanner.SmallBannerApplovinDis(this, layNative, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_BANNER, NetizenSettings.BACKUP_ADS_BANNER);
                break;
            case "STARTAPP":
                AliendroidNative.SmallNativeStartApp(this, layNative, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_NATIVES, NetizenSettings.BACKUP_ADS_NATIVES);
                break;
            case "IRON":
                AliendroidBanner.SmallBannerIron(this, layNative, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_BANNER, NetizenSettings.BACKUP_ADS_BANNER);
                break;
            case "FACEBOOK":
                AliendroidNative.SmallNativeFan(this, layNative, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_NATIVES, NetizenSettings.BACKUP_ADS_NATIVES);
                break;
            case "GOOGLE-ADS":
                AliendroidBanner.SmallBannerGoogleAds(this, layNative, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_BANNER, NetizenSettings.BACKUP_ADS_BANNER);
                break;
        }


    }

    public void onImageFromCameraClick(View view) {
        mp.start();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
            }

            if (photoFile != null) {
                Uri photoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".fileprovider", photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    public void onImageList(View view) {
        mp.start();
        Intent intent = new Intent(getApplicationContext(), PuzzleListActivity.class);
        startActivity(intent);
    }

    private File createImageFile() throws IOException {
        mp.start();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // permission not granted, initiate request
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE);
        } else {
            // Create an image file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */);
            mCurrentPhotoPath = image.getAbsolutePath(); // NetizenSave this to use in the intent

            return image;
        }

        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onImageFromCameraClick(new View(this));
                }

                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Intent intent = new Intent(this, PuzzleJigsawActivity.class);
            intent.putExtra("mCurrentPhotoPath", mCurrentPhotoPath);
            startActivity(intent);
        }
        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK) {
            Uri uri = data.getData();

            Intent intent = new Intent(this, PuzzleJigsawActivity.class);
            intent.putExtra("mCurrentPhotoUri", uri.toString());
            startActivity(intent);
        }

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
        }
    }

    public void onImageFromGalleryClick(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_READ_EXTERNAL_STORAGE);
        } else {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_IMAGE_GALLERY);
        }
    }

    public void onRateClick(View view) {
        String str = "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
    }

    public void onSharClick(View view) {
        String shareLink = "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.shareit) + " " + shareLink);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }


    /*
  In App Update
   */ AppUpdateManager appUpdateManager;
    Task<AppUpdateInfo> appUpdateInfoTask;
    private static final int MY_REQUEST_CODE = 17326;

    ReviewInfo reviewInfo;
    ReviewManager manager;

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
                    Toast.makeText(PuzzleMainActivity.this, "No Update Available", Toast.LENGTH_SHORT).show();
                    Log.d("NoUpdateAvailable", "update is not there ");
                }
            }
        });
    }

    private void requestUpdate(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.FLEXIBLE, PuzzleMainActivity.this, MY_REQUEST_CODE);
            resume();
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    InstallStateUpdatedListener listener = new InstallStateUpdatedListener() {
        @Override
        public void onStateUpdate(InstallState installState) {
            if (installState.installStatus() == InstallStatus.DOWNLOADED) {
                Log.d("InstallDownloded", "InstallStatus sucsses");
                notifyUser();
            }
        }
    };


    private void notifyUser() {

        Snackbar snackbar = Snackbar.make(findViewById(R.id.laymain), "An update has just been downloaded.", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("RESTART", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUpdateManager.completeUpdate();
            }
        });
        snackbar.setActionTextColor(getResources().getColor(R.color.colortext));
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
                    manager.launchReviewFlow(PuzzleMainActivity.this, reviewInfo).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            //Toast.makeText(PuzzleMainActivity.this, "Rating Failed", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // Toast.makeText(PuzzleMainActivity.this, "Review Completed, Thank You!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(PuzzleMainActivity.this, "In-App Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
