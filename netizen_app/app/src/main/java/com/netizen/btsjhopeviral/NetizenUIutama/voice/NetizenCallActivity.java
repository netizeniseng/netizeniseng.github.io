package com.netizen.btsjhopeviral.NetizenUIutama.voice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aliendroid.alienads.AliendroidIntertitial;
import com.netizen.btsjhopeviral.BuildConfig;
import com.netizen.btsjhopeviral.NetizenUIutama.NetizenChatActivity;
import com.netizen.btsjhopeviral.R;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.netizen.btsjhopeviral.NetizenSettings.BACKUP_ADS_INTERTITIAL;
import static com.netizen.btsjhopeviral.NetizenSettings.HIGH_PAYING_KEYWORD1;
import static com.netizen.btsjhopeviral.NetizenSettings.HIGH_PAYING_KEYWORD2;
import static com.netizen.btsjhopeviral.NetizenSettings.HIGH_PAYING_KEYWORD3;
import static com.netizen.btsjhopeviral.NetizenSettings.HIGH_PAYING_KEYWORD4;
import static com.netizen.btsjhopeviral.NetizenSettings.HIGH_PAYING_KEYWORD5;
import static com.netizen.btsjhopeviral.NetizenSettings.MAIN_ADS_INTERTITIAL;
import static com.netizen.btsjhopeviral.NetizenSettings.SELECT_BACKUP_ADS;
import static com.netizen.btsjhopeviral.NetizenSettings.SELECT_MAIN_ADS;


public class NetizenCallActivity extends AppCompatActivity {

    private TextView calling, nameuser;
    private ImageView imguser, adduser;
    MediaPlayer mediaPlayer ;
    private RelativeLayout cancel;



    int Seconds, Minutes, MilliSeconds, hours ;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        mediaPlayer = MediaPlayer.create(this, R.raw.outgoing_ringtone);
        calling = findViewById(R.id.txtcall);
        nameuser = findViewById(R.id.txtname);

        imguser = findViewById(R.id.imguser);
        adduser = findViewById(R.id.adduser);
        adduser.setVisibility(View.INVISIBLE);
        cancel = findViewById(R.id.layclose);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                if (mediaPlayer!=null){
                    mediaPlayer.stop();
                }

                switch (SELECT_MAIN_ADS) {
                    case "ADMOB":
                    case "ADMOB-B":
                        AliendroidIntertitial.ShowIntertitialAdmob(NetizenCallActivity.this, SELECT_BACKUP_ADS, MAIN_ADS_INTERTITIAL, BACKUP_ADS_INTERTITIAL, 0,
                                HIGH_PAYING_KEYWORD1, HIGH_PAYING_KEYWORD2, HIGH_PAYING_KEYWORD3, HIGH_PAYING_KEYWORD4, HIGH_PAYING_KEYWORD5);
                        break;
                    case "APPLOVIN-D":
                        AliendroidIntertitial.ShowIntertitialApplovinDis(NetizenCallActivity.this, SELECT_BACKUP_ADS, MAIN_ADS_INTERTITIAL, BACKUP_ADS_INTERTITIAL, 0);
                        break;
                    case "APPLOVIN-M":
                        AliendroidIntertitial.ShowIntertitialApplovinMax(NetizenCallActivity.this, SELECT_BACKUP_ADS, MAIN_ADS_INTERTITIAL, BACKUP_ADS_INTERTITIAL, 0);
                        break;
                    case "IRON":
                        AliendroidIntertitial.ShowIntertitialIron(NetizenCallActivity.this, SELECT_BACKUP_ADS, MAIN_ADS_INTERTITIAL, BACKUP_ADS_INTERTITIAL, 0);
                        break;
                    case "STARTAPP":
                        AliendroidIntertitial.ShowIntertitialSartApp(NetizenCallActivity.this, SELECT_BACKUP_ADS, MAIN_ADS_INTERTITIAL, BACKUP_ADS_INTERTITIAL, 0);
                        break;
                    case "FACEBOOK":
                        AliendroidIntertitial.ShowIntertitialFAN(NetizenCallActivity.this, SELECT_BACKUP_ADS, MAIN_ADS_INTERTITIAL, BACKUP_ADS_INTERTITIAL, 0);
                        break;
                    case "GOOGLE-ADS":
                        AliendroidIntertitial.ShowIntertitialGoogleAds(NetizenCallActivity.this, SELECT_BACKUP_ADS, MAIN_ADS_INTERTITIAL, BACKUP_ADS_INTERTITIAL, 0);
                        break;
                }
            }
        });

        if (NetizenChatActivity.currentContactData.getImageuser().equals("")) {
            imguser.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.avatar_contact));

        } else {
            try {
                imguser.setImageBitmap(BitmapFactory.decodeFile(NetizenChatActivity.currentContactData.getImageuser()));
            } catch (Exception e) {
                imguser.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.avatar_contact));
            }

        }
        nameuser.setText(NetizenChatActivity.currentContactData.getName());
        handler = new Handler() ;

        call1();



    }

    public void call1(){
        new CountDownTimer(2000, 1000) {
            @Override
            public void onFinish() {
                calling.setText(R.string.ring);
                mediaPlayer.start();
                mediaPlayer.setLooping(true);
                call2();
            }
            @Override
            public void onTick(long millisUntilFinished) {
            }
        }.start();
    }


    public void call2(){
        new CountDownTimer(7000, 1000) {
            @Override
            public void onFinish() {
                mediaPlayer.stop();
                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                mediaPlayer = MediaPlayer.create(NetizenCallActivity.this, Uri.parse(NetizenChatActivity.LINK_VOICE));
                mediaPlayer.start();
                adduser.setVisibility(View.VISIBLE);


            }
            @Override
            public void onTick(long millisUntilFinished) {
            }
        }.start();
    }

    public Runnable runnable = new Runnable() {

        @SuppressLint({"DefaultLocale", "SetTextI18n"})
        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;
            hours = Minutes/60;

            MilliSeconds = (int) (UpdateTime % 1000);

            calling.setText(String.format("%02d", hours) +":"+ String.format("%02d", Minutes) + ":"
                    + String.format("%02d", Seconds));

            handler.postDelayed(this, 0);
        }

    };

    public void onBackPressed(){
        if (mediaPlayer!=null){
            mediaPlayer.stop();
        }
        finish();
    }
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer!=null){
            mediaPlayer.stop();
        }
    }


    public Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }
    File imagePath;
    public void saveBitmap(Bitmap bitmap) {
        imagePath = new File(Environment.getExternalStorageDirectory() + "/"+ NetizenChatActivity.currentContactData.getName()+".png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }

    public void shareIt() {

        Uri myPhotoFileUri = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", imagePath);


        // Uri uri = Uri.fromFile(imagePath);
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        sharingIntent.putExtra(MediaStore.EXTRA_OUTPUT, myPhotoFileUri);
        sharingIntent.setType("image/*");
        String shareBody = "Fake chat, video and voice call! download on " +"https://play.google.com/store/apps/details?id="
                + BuildConfig.APPLICATION_ID ;
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Whatsfake");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        sharingIntent.putExtra(Intent.EXTRA_STREAM,  myPhotoFileUri);

        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

}