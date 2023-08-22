package com.netizen.btsjhopeviral.NetizenWhatsapp;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.aliendroid.alienads.AliendroidIntertitial;
import com.netizen.btsjhopeviral.BuildConfig;
import com.netizen.btsjhopeviral.NetizenActivity.Netizencall_MainActivity;
import com.netizen.btsjhopeviral.NetizenSettings;
import com.netizen.btsjhopeviral.R;
import com.netizen.btsjhopeviral.NetizenAdapter.Netizencall_FakeAdapter;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class WAVideoActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    MediaPlayer mp;
    Camera camera;
    SurfaceView surfaceView, surfaceView2;
    SurfaceHolder surfaceHolder;
    VideoView videoView;
    Handler handler;
    private TextView calling, nameuser;
    private ImageView adduser;
    private CircleImageView imguser;
    private RelativeLayout cancel, terima, pesan, tolak;
    private LinearLayout atas, bawah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(1280);
        getWindow().setStatusBarColor(1140850688);
        setContentView(R.layout.call_activity_video_call);
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        mp = MediaPlayer.create(getApplicationContext(), notification);
        mp.start();
        mp.setLooping(true);

        atas = findViewById(R.id.atas);
        bawah = findViewById(R.id.bawah);
        videoView = findViewById(R.id.videoView);

        String uriPath = Netizencall_FakeAdapter.video;
        if (Netizencall_FakeAdapter.video.startsWith("https://")) {
            Uri uri = Uri.parse(uriPath);
            videoView.setVideoURI(uri);
            videoView.requestFocus();
        } else if (Netizencall_FakeAdapter.video.startsWith("http://")) {
            Uri uri = Uri.parse(uriPath);
            videoView.setVideoURI(uri);
            videoView.requestFocus();
        } else {
            String fileName = "android.resource://" + BuildConfig.APPLICATION_ID + "/raw/" + Netizencall_FakeAdapter.video;
            videoView.setVideoURI(Uri.parse(fileName));
            videoView.requestFocus();
        }

        surfaceView = findViewById(R.id.surfaceView);
        surfaceView2 = findViewById(R.id.surfaceView2);
        surfaceView2.setVisibility(View.GONE);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setFormat(PixelFormat.OPAQUE);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                surfaceView.setVisibility(View.VISIBLE);
                float videoRatio = mp.getVideoWidth() / (float) mp.getVideoHeight();
                float screenRatio = videoView.getWidth() / (float)
                        videoView.getHeight();
                surfaceView.getHeight();
                surfaceView2.getHeight();
                float scaleX = videoRatio / screenRatio;
                if (scaleX >= 1f) {
                    videoView.setScaleX(scaleX);
                    surfaceView.setScaleX(scaleX);

                } else {
                    videoView.setScaleY(1f / scaleX);
                    surfaceView.setScaleY(1f / scaleX);

                }
            }
        });
        handler = new Handler();

        calling = findViewById(R.id.txtcall);
        nameuser = findViewById(R.id.txtname);
        imguser = findViewById(R.id.imguser);
        adduser = findViewById(R.id.adduser);
        adduser.setVisibility(View.INVISIBLE);
        cancel = findViewById(R.id.layclose2);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WAVideoActivity.this, Netizencall_MainActivity.class);
                startActivity(intent);
                finish();
                mp.stop();
                showIntertitial();

            }
        });

        pesan = findViewById(R.id.laypesan);
        pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WAVideoActivity.this, Netizencall_MainActivity.class);
                startActivity(intent);
                finish();
                mp.stop();
                showIntertitial();

            }
        });

        tolak = findViewById(R.id.layclose);
        tolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WAVideoActivity.this, Netizencall_MainActivity.class);
                startActivity(intent);
                finish();
                mp.stop();
                showIntertitial();

            }
        });

        terima = findViewById(R.id.layterima);
        terima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                mp.stop();
                calling.setVisibility(View.GONE);
                nameuser.setVisibility(View.GONE);
                imguser.setVisibility(View.GONE);
                adduser.setVisibility(View.VISIBLE);
                surfaceView.setVisibility(View.GONE);
                surfaceView2.setVisibility(View.VISIBLE);
                surfaceHolder = surfaceView2.getHolder();
                surfaceHolder.addCallback(WAVideoActivity.this);
                surfaceHolder.setFormat(PixelFormat.OPAQUE);
                surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);

                videoView.start();
                atas.setVisibility(View.GONE);
                bawah.setVisibility(View.VISIBLE);
                tolak.setVisibility(View.VISIBLE);

            }
        });

        imguser = findViewById(R.id.imguser);
        Picasso.get()
                .load(Netizencall_FakeAdapter.gambar)
                .into(imguser);

        nameuser.setText(Netizencall_FakeAdapter.judul);

    }


    private void showIntertitial() {
        switch (NetizenSettings.SELECT_MAIN_ADS) {
            case "ADMOB":
            case "ADMOB-B":
                AliendroidIntertitial.ShowIntertitialAdmob(this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0,
                        NetizenSettings.HIGH_PAYING_KEYWORD1, NetizenSettings.HIGH_PAYING_KEYWORD2, NetizenSettings.HIGH_PAYING_KEYWORD3, NetizenSettings.HIGH_PAYING_KEYWORD4, NetizenSettings.HIGH_PAYING_KEYWORD5);
                break;
            case "APPLOVIN-D":
                AliendroidIntertitial.ShowIntertitialApplovinDis(this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
                break;
            case "APPLOVIN-M":
                AliendroidIntertitial.ShowIntertitialApplovinMax(this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
                break;
            case "IRON":
                AliendroidIntertitial.ShowIntertitialIron(this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
                break;
            case "STARTAPP":
                AliendroidIntertitial.ShowIntertitialSartApp(this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
                break;
            case "FACEBOOK":
                AliendroidIntertitial.ShowIntertitialFAN(this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
                break;
            case "GOOGLE-ADS":
                AliendroidIntertitial.ShowIntertitialGoogleAds(this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
                break;
        }
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        camera = Camera.open(1);
        Camera.Parameters parameters;
        parameters = camera.getParameters();
        camera.setParameters(parameters);
        camera.setDisplayOrientation(90);
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (Exception e) {
        }

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        camera.stopPreview();
        camera.release();
        camera = null;

    }

    public void onBackPressed() {
        mp.stop();
        Intent intent = new Intent(WAVideoActivity.this, Netizencall_MainActivity.class);
        startActivity(intent);
        finish();
    }
}