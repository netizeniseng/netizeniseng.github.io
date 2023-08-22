package com.netizen.btsjhopeviral.NetizenFacebook;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.MediaPlayer;
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

public class FBVideoActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    CircleImageView gambrH;
    ImageView gambrB, imgback;
    MediaPlayer mp;
    RelativeLayout terima, tolak, tolak2;
    LinearLayout atas, bawah;
    Handler handler;
    TextView calling;
    Camera camera;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(1280);
        getWindow().setStatusBarColor(1140850688);
        setContentView(R.layout.call_activity_f_b_video_call);

        surfaceView = findViewById(R.id.surfaceView);
        surfaceView.setVisibility(View.GONE);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setFormat(PixelFormat.OPAQUE);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);
        videoView = findViewById(R.id.videoView);
        videoView.setMediaController(null);

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
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                float videoRatio = mp.getVideoWidth() / (float) mp.getVideoHeight();
                float screenRatio = videoView.getWidth() / (float)
                        videoView.getHeight();
                surfaceView.getHeight();
                float scaleX = videoRatio / screenRatio;
                if (scaleX >= 1f) {
                    videoView.setScaleX(scaleX);

                } else {
                    videoView.setScaleY(1f / scaleX);


                }
            }
        });
        handler = new Handler();
        atas = findViewById(R.id.layutama);
        bawah = findViewById(R.id.laybawah2);
        calling = findViewById(R.id.txtwaktu);

        mp = MediaPlayer.create(this, R.raw.facebook);
        mp.start();
        mp.setLooping(true);

        tolak = findViewById(R.id.laytolak);
        tolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FBVideoActivity.this, Netizencall_MainActivity.class);
                startActivity(intent);
                finish();
                mp.stop();
                showIntertitial();

            }
        });

        imgback = findViewById(R.id.imgback2);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FBVideoActivity.this, Netizencall_MainActivity.class);
                startActivity(intent);
                finish();
                mp.stop();
                showIntertitial();
            }
        });

        tolak2 = findViewById(R.id.laytolak2);
        tolak2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FBVideoActivity.this, Netizencall_MainActivity.class);
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
                surfaceView.setVisibility(View.VISIBLE);
                atas.setVisibility(View.GONE);
                bawah.setVisibility(View.VISIBLE);
                gambrB.setVisibility(View.GONE);
                videoView.start();
            }
        });


        TextView judulH = findViewById(R.id.txtfbname);
        judulH.setText(Netizencall_FakeAdapter.judul);

        gambrH = findViewById(R.id.fbimguser);
        gambrB = findViewById(R.id.imgback);

        Picasso.get()
                .load(Netizencall_FakeAdapter.gambar)
                .into(gambrH);
        Picasso.get()
                .load(Netizencall_FakeAdapter.gambar)
                .into(gambrB);
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

    public void onBackPressed() {
        mp.stop();
        Intent intent = new Intent(FBVideoActivity.this, Netizencall_MainActivity.class);
        startActivity(intent);
        finish();
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

}