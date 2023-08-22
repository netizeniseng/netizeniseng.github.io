package com.netizen.btsjhopeviral.NetizenUIutama.video;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.aliendroid.alienads.AliendroidIntertitial;
import com.netizen.btsjhopeviral.BuildConfig;
import com.netizen.btsjhopeviral.NetizenSettings;
import com.netizen.btsjhopeviral.NetizenUIutama.NetizenChatActivity;
import com.netizen.btsjhopeviral.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class NetizenVideoActivityReceive extends AppCompatActivity implements SurfaceHolder.Callback{

    Camera camera;
    SurfaceView surfaceView,surfaceView2 ;
    SurfaceHolder surfaceHolder;

    private TextView calling, nameuser;
    private ImageView  adduser;
    private CircleImageView imguser;

    MediaPlayer mediaPlayer ;
    private RelativeLayout cancel, terima, pesan, tolak;
    Handler handler;
   VideoView videoView;
    private LinearLayout atas, bawah;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vid_rec);
        atas = findViewById(R.id.atas);
        bawah = findViewById(R.id.bawah);
        videoView = findViewById(R.id.videoView);
        Uri videoUri = Uri.parse(NetizenChatActivity.LINK_VIDEO);
        MediaController mediaController= new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(videoUri);
        videoView.requestFocus();

        surfaceView = findViewById(R.id.surfaceView);
        surfaceView2 = findViewById(R.id.surfaceView2);
        surfaceView2.setVisibility(View.GONE);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setFormat(PixelFormat.OPAQUE);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);
        handler = new Handler() ;
        mediaPlayer = MediaPlayer.create(this, R.raw.voice);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);
        calling = findViewById(R.id.txtcall);
        nameuser = findViewById(R.id.txtname);
        imguser = findViewById(R.id.imguser);
        adduser = findViewById(R.id.adduser);
        adduser.setVisibility(View.INVISIBLE);
        cancel = findViewById(R.id.layclose2);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                mediaPlayer.stop();
                switch (NetizenSettings.SELECT_MAIN_ADS) {
                    case "ADMOB":
                        AliendroidIntertitial.ShowIntertitialAdmob(NetizenVideoActivityReceive.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0,
                                NetizenSettings.HIGH_PAYING_KEYWORD1, NetizenSettings.HIGH_PAYING_KEYWORD2, NetizenSettings.HIGH_PAYING_KEYWORD3, NetizenSettings.HIGH_PAYING_KEYWORD4, NetizenSettings.HIGH_PAYING_KEYWORD5);
                        break;
                    case "APPLOVIN-D":
                        AliendroidIntertitial.ShowIntertitialApplovinDis(NetizenVideoActivityReceive.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
                        break;
                    case "APPLOVIN-M":
                        AliendroidIntertitial.ShowIntertitialApplovinMax(NetizenVideoActivityReceive.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
                        break;
                    case "IRON":
                        AliendroidIntertitial.ShowIntertitialIron(NetizenVideoActivityReceive.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
                        break;
                    case "STARTAPP":
                        AliendroidIntertitial.ShowIntertitialSartApp(NetizenVideoActivityReceive.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
                        break;
                    case "FACEBOOK":
                        AliendroidIntertitial.ShowIntertitialFAN(NetizenVideoActivityReceive.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
                        break;
                    case "GOOGLE-ADS":
                        AliendroidIntertitial.ShowIntertitialGoogleAds(NetizenVideoActivityReceive.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
                        break;
                    case "UNITY":
                        AliendroidIntertitial.ShowIntertitialUnity(NetizenVideoActivityReceive.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
                        break;
                }
            }
        });

        pesan = findViewById(R.id.laypesan);
        pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                mediaPlayer.stop();
                switch (NetizenSettings.SELECT_MAIN_ADS) {
                    case "ADMOB":
                        AliendroidIntertitial.ShowIntertitialAdmob(NetizenVideoActivityReceive.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0,
                                NetizenSettings.HIGH_PAYING_KEYWORD1, NetizenSettings.HIGH_PAYING_KEYWORD2, NetizenSettings.HIGH_PAYING_KEYWORD3, NetizenSettings.HIGH_PAYING_KEYWORD4, NetizenSettings.HIGH_PAYING_KEYWORD5);
                        break;
                    case "APPLOVIN-D":
                        AliendroidIntertitial.ShowIntertitialApplovinDis(NetizenVideoActivityReceive.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
                        break;
                    case "APPLOVIN-M":
                        AliendroidIntertitial.ShowIntertitialApplovinMax(NetizenVideoActivityReceive.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
                        break;
                    case "IRON":
                        AliendroidIntertitial.ShowIntertitialIron(NetizenVideoActivityReceive.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
                        break;
                    case "STARTAPP":
                        AliendroidIntertitial.ShowIntertitialSartApp(NetizenVideoActivityReceive.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
                        break;
                    case "FACEBOOK":
                        AliendroidIntertitial.ShowIntertitialFAN(NetizenVideoActivityReceive.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
                        break;
                    case "GOOGLE-ADS":
                        AliendroidIntertitial.ShowIntertitialGoogleAds(NetizenVideoActivityReceive.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
                        break;
                }
            }
        });

        tolak = findViewById(R.id.layclose);
        tolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                mediaPlayer.stop();
                switch (NetizenSettings.SELECT_MAIN_ADS) {
                    case "ADMOB":
                        AliendroidIntertitial.ShowIntertitialAdmob(NetizenVideoActivityReceive.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0,
                                NetizenSettings.HIGH_PAYING_KEYWORD1, NetizenSettings.HIGH_PAYING_KEYWORD2, NetizenSettings.HIGH_PAYING_KEYWORD3, NetizenSettings.HIGH_PAYING_KEYWORD4, NetizenSettings.HIGH_PAYING_KEYWORD5);
                        break;
                    case "APPLOVIN-D":
                        AliendroidIntertitial.ShowIntertitialApplovinDis(NetizenVideoActivityReceive.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
                        break;
                    case "APPLOVIN-M":
                        AliendroidIntertitial.ShowIntertitialApplovinMax(NetizenVideoActivityReceive.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
                        break;
                    case "IRON":
                        AliendroidIntertitial.ShowIntertitialIron(NetizenVideoActivityReceive.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
                        break;
                    case "STARTAPP":
                        AliendroidIntertitial.ShowIntertitialSartApp(NetizenVideoActivityReceive.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
                        break;
                    case "FACEBOOK":
                        AliendroidIntertitial.ShowIntertitialFAN(NetizenVideoActivityReceive.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
                        break;
                    case "GOOGLE-ADS":
                        AliendroidIntertitial.ShowIntertitialGoogleAds(NetizenVideoActivityReceive.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
                        break;
                }
            }
        });

        terima = findViewById(R.id.layterima);
        terima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.stop();
                calling.setVisibility(View.GONE);
                nameuser.setVisibility(View.GONE);
                imguser.setVisibility(View.GONE);
                adduser.setVisibility(View.VISIBLE);
                surfaceView.setVisibility(View.GONE);
                surfaceView2.setVisibility(View.VISIBLE);
                surfaceHolder = surfaceView2.getHolder();
                surfaceHolder.addCallback(NetizenVideoActivityReceive.this);
                surfaceHolder.setFormat(PixelFormat.OPAQUE);
                surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);

                videoView.start();
                atas.setVisibility(View.GONE);
                bawah.setVisibility(View.VISIBLE);
                tolak.setVisibility(View.VISIBLE);

            }
        });
        try {
            if (NetizenChatActivity.currentContactData.getImageuser().equals("")) {
                Picasso.get().load(R.drawable.avatar_contact).into(imguser);
            } else {
                Picasso.get().load(new File(NetizenChatActivity.currentContactData.getImageuser())).centerCrop().fit().into( imguser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        nameuser.setText(NetizenChatActivity.currentContactData.getName());

    }




    public void surfaceCreated(SurfaceHolder surfaceHolder ) {

        camera = Camera.open(1);
        Camera.Parameters parameters;
        parameters = camera.getParameters();
        camera.setParameters(parameters);
        camera.setDisplayOrientation(90);
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();


        }catch (Exception e){
        }

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        camera.stopPreview();
        camera.release();
        camera=null;

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
                calling.setVisibility(View.GONE);
                nameuser.setVisibility(View.GONE);
                imguser.setVisibility(View.GONE);
                adduser.setVisibility(View.VISIBLE);
                surfaceView.setVisibility(View.GONE);
                surfaceView2.setVisibility(View.VISIBLE);
                videoView.start();
            }
            @Override
            public void onTick(long millisUntilFinished) {
                surfaceHolder = surfaceView2.getHolder();
                surfaceHolder.addCallback(NetizenVideoActivityReceive.this);
                surfaceHolder.setFormat(PixelFormat.OPAQUE);
                surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);

            }
        }.start();
    }



    public void onBackPressed(){
        mediaPlayer.stop();
        finish();

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