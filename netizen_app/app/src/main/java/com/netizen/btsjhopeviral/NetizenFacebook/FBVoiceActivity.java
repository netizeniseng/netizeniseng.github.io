package com.netizen.btsjhopeviral.NetizenFacebook;

import static com.netizen.btsjhopeviral.NetizenAdapter.Netizencall_FakeAdapter.gambar;
import static com.netizen.btsjhopeviral.NetizenAdapter.Netizencall_FakeAdapter.judul;
import static com.netizen.btsjhopeviral.NetizenAdapter.Netizencall_FakeAdapter.voice;
import static com.netizen.btsjhopeviral.NetizenSettings.BACKUP_ADS_INTERTITIAL;
import static com.netizen.btsjhopeviral.NetizenSettings.HIGH_PAYING_KEYWORD1;
import static com.netizen.btsjhopeviral.NetizenSettings.HIGH_PAYING_KEYWORD2;
import static com.netizen.btsjhopeviral.NetizenSettings.HIGH_PAYING_KEYWORD3;
import static com.netizen.btsjhopeviral.NetizenSettings.HIGH_PAYING_KEYWORD4;
import static com.netizen.btsjhopeviral.NetizenSettings.HIGH_PAYING_KEYWORD5;
import static com.netizen.btsjhopeviral.NetizenSettings.MAIN_ADS_INTERTITIAL;
import static com.netizen.btsjhopeviral.NetizenSettings.SELECT_MAIN_ADS;
import static com.netizen.btsjhopeviral.NetizenSettings.SELECT_BACKUP_ADS;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.aliendroid.alienads.AliendroidIntertitial;
import com.netizen.btsjhopeviral.NetizenActivity.Netizencall_MainActivity;
import com.netizen.btsjhopeviral.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;


public class FBVoiceActivity extends AppCompatActivity {
    CircleImageView gambrH;
    ImageView gambrB, imgback;
    MediaPlayer mp;
    RelativeLayout terima, tolak, tolak2;
    LinearLayout atas, bawah;
    int Seconds, Minutes, MilliSeconds, hours;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;
    Handler handler;
    TextView calling;
    public Runnable runnable = new Runnable() {

        @SuppressLint({"DefaultLocale", "SetTextI18n"})
        public void run() {
            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = TimeBuff + MillisecondTime;
            Seconds = (int) (UpdateTime / 1000);
            Minutes = Seconds / 60;
            Seconds = Seconds % 60;
            hours = Minutes / 60;
            MilliSeconds = (int) (UpdateTime % 1000);
            calling.setText(String.format("%02d", hours) + ":" + String.format("%02d", Minutes) + ":"
                    + String.format("%02d", Seconds));

            handler.postDelayed(this, 0);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(1280);
        getWindow().setStatusBarColor(1140850688);
        setContentView(R.layout.call_activity_f_b_voice_call);

        handler = new Handler();
        atas = findViewById(R.id.laybawah1);
        bawah = findViewById(R.id.laybawah2);
        calling = findViewById(R.id.txtwaktu);

        mp = MediaPlayer.create(this, R.raw.facebook);
        mp.start();
        mp.setLooping(true);

        tolak = findViewById(R.id.laytolak);
        tolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FBVoiceActivity.this, Netizencall_MainActivity.class);
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
                Intent intent = new Intent(FBVoiceActivity.this, Netizencall_MainActivity.class);
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
                Intent intent = new Intent(FBVoiceActivity.this, Netizencall_MainActivity.class);
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
                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                atas.setVisibility(View.GONE);
                bawah.setVisibility(View.VISIBLE);
                String url = voice;
                mp.stop();
                try {
                    mp = new MediaPlayer();
                    if (url.startsWith("http")) {
                        mp.setDataSource(url);
                        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    } else {
                        AssetFileDescriptor descriptor;
                        descriptor = getAssets().openFd(url);
                        mp.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
                        descriptor.close();
                        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    }
                    mp.prepareAsync();
                    mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.start();
                        }
                    });

                } catch (IllegalArgumentException | IllegalStateException | IOException e) {
                    e.printStackTrace();
                }
            }
        });

        TextView judulH = findViewById(R.id.txtfbname);
        judulH.setText(judul);

        gambrH = findViewById(R.id.fbimguser);
        gambrB = findViewById(R.id.imgback);

        Picasso.get()
                .load(gambar)
                .into(gambrH);
        Picasso.get()
                .load(gambar)
                .into(gambrB);
    }

    private void showIntertitial() {
        switch (SELECT_MAIN_ADS) {
            case "ADMOB":
            case "ADMOB-B":
                AliendroidIntertitial.ShowIntertitialAdmob(this, SELECT_BACKUP_ADS, MAIN_ADS_INTERTITIAL, BACKUP_ADS_INTERTITIAL, 0,
                        HIGH_PAYING_KEYWORD1, HIGH_PAYING_KEYWORD2, HIGH_PAYING_KEYWORD3, HIGH_PAYING_KEYWORD4, HIGH_PAYING_KEYWORD5);
                break;
            case "APPLOVIN-D":
                AliendroidIntertitial.ShowIntertitialApplovinDis(this, SELECT_BACKUP_ADS, MAIN_ADS_INTERTITIAL, BACKUP_ADS_INTERTITIAL, 0);
                break;
            case "APPLOVIN-M":
                AliendroidIntertitial.ShowIntertitialApplovinMax(this, SELECT_BACKUP_ADS, MAIN_ADS_INTERTITIAL, BACKUP_ADS_INTERTITIAL, 0);
                break;
            case "IRON":
                AliendroidIntertitial.ShowIntertitialIron(this, SELECT_BACKUP_ADS, MAIN_ADS_INTERTITIAL, BACKUP_ADS_INTERTITIAL, 0);
                break;
            case "STARTAPP":
                AliendroidIntertitial.ShowIntertitialSartApp(this, SELECT_BACKUP_ADS, MAIN_ADS_INTERTITIAL, BACKUP_ADS_INTERTITIAL, 0);
                break;
            case "FACEBOOK":
                AliendroidIntertitial.ShowIntertitialFAN(this, SELECT_BACKUP_ADS, MAIN_ADS_INTERTITIAL, BACKUP_ADS_INTERTITIAL, 0);
                break;
            case "GOOGLE-ADS":
                AliendroidIntertitial.ShowIntertitialGoogleAds(this, SELECT_BACKUP_ADS, MAIN_ADS_INTERTITIAL, BACKUP_ADS_INTERTITIAL, 0);
                break;
        }
    }

    public void onBackPressed() {
        mp.stop();
        Intent intent = new Intent(FBVoiceActivity.this, Netizencall_MainActivity.class);
        startActivity(intent);
        finish();
    }
}