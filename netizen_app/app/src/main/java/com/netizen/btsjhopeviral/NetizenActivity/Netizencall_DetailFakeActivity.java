package com.netizen.btsjhopeviral.NetizenActivity;

import static com.netizen.btsjhopeviral.NetizenSettings.BACKUP_ADS_BANNER;
import static com.netizen.btsjhopeviral.NetizenSettings.BACKUP_ADS_NATIVES;
import static com.netizen.btsjhopeviral.NetizenSettings.HIGH_PAYING_KEYWORD1;
import static com.netizen.btsjhopeviral.NetizenSettings.HIGH_PAYING_KEYWORD2;
import static com.netizen.btsjhopeviral.NetizenSettings.HIGH_PAYING_KEYWORD3;
import static com.netizen.btsjhopeviral.NetizenSettings.HIGH_PAYING_KEYWORD4;
import static com.netizen.btsjhopeviral.NetizenSettings.HIGH_PAYING_KEYWORD5;
import static com.netizen.btsjhopeviral.NetizenSettings.MAIN_ADS_BANNER;
import static com.netizen.btsjhopeviral.NetizenSettings.MAIN_ADS_NATIVES;
import static com.netizen.btsjhopeviral.NetizenSettings.SELECT_MAIN_ADS;
import static com.netizen.btsjhopeviral.NetizenSettings.SELECT_BACKUP_ADS;
import static com.netizen.btsjhopeviral.NetizenSettings.TIMER_A;
import static com.netizen.btsjhopeviral.NetizenSettings.TIMER_B;
import static com.netizen.btsjhopeviral.NetizenSettings.TIMER_C;
import static com.netizen.btsjhopeviral.NetizenSettings.TIMER_D;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.aliendroid.alienads.AliendroidBanner;
import com.aliendroid.alienads.AliendroidMediumBanner;
import com.aliendroid.alienads.AliendroidNative;
import com.netizen.btsjhopeviral.R;
import com.netizen.btsjhopeviral.NetizenFacebook.FBVideoActivity;
import com.netizen.btsjhopeviral.NetizenFacebook.FBVoiceActivity;
import com.netizen.btsjhopeviral.NetizenTelegram.TeleVideoActivity;
import com.netizen.btsjhopeviral.NetizenTelegram.TeleVoiceActivity;
import com.netizen.btsjhopeviral.NetizenUtility.Netizencall_AppReceiver;
import com.netizen.btsjhopeviral.NetizenWhatsapp.WAVideoActivity;
import com.netizen.btsjhopeviral.NetizenWhatsapp.WAVoiceActivity;
import com.netizen.btsjhopeviral.NetizenAdapter.Netizencall_FakeAdapter;
import com.squareup.picasso.Picasso;

import java.util.Calendar;


public class Netizencall_DetailFakeActivity extends AppCompatActivity {
    protected static final String TAG = Netizencall_DetailFakeActivity.class.getSimpleName();
    private static final int ALARM_REQUEST_CODE = 134;
    public static int rd_vid = 1;
    public static int rd_form = 1;
    public static int rd_time = 1;
    public static String status_time = "Wait for 2 seconds";
    private final int NOTIFICATION_ID = 1;
    private PendingIntent pendingIntent;
    private RadioGroup list_action, list_form, list_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_detail_fake_activity);

        TextView judulH = findViewById(R.id.txtjudul);


        list_action = findViewById(R.id.list_action);
        list_action.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id) {
                    case R.id.rd_vid:
                        rd_vid = 1;
                        break;
                    case R.id.rd_voic:
                        rd_vid = 2;
                        break;

                }
            }
        });

        list_form = findViewById(R.id.lict_form);
        list_form.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id) {
                    case R.id.rdwa:
                        rd_form = 1;
                        break;
                    case R.id.rdfb:
                        rd_form = 2;
                        break;
                    case R.id.rdduo:
                        rd_form = 3;
                        break;
                }
            }
        });

        list_time = findViewById(R.id.list_time);
        list_time.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id) {
                    case R.id.rd1:
                        rd_time = 1;
                        status_time = "Wait for 2 seconds";
                        break;
                    case R.id.rd10:
                        rd_time = TIMER_A;
                        status_time = "Wait for 10 seconds";
                        break;
                    case R.id.rd30:
                        rd_time = TIMER_B;
                        status_time = "Wait for 30 seconds";
                        break;
                    case R.id.rd60:
                        rd_time = TIMER_C;
                        status_time = "Wait for 1 minutes";
                        break;
                    case R.id.rd300:
                        rd_time = TIMER_D;
                        status_time = "Wait for 5 minutes";
                        break;
                }
            }
        });

        judulH.setText(Netizencall_FakeAdapter.judul);
        ImageView gambrH = findViewById(R.id.imageheader);
        Picasso.get().load(Netizencall_FakeAdapter.gambar).into(gambrH);


        Intent alarmIntent = new Intent(this, Netizencall_AppReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, ALARM_REQUEST_CODE, alarmIntent, PendingIntent.FLAG_IMMUTABLE);

        Button tbtutor = findViewById(R.id.tblstart);
        tbtutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rd_time == 1) {
                    if (rd_form == 1) {
                        if (rd_vid == 2) {
                            Intent intent2 = new Intent(Netizencall_DetailFakeActivity.this, WAVoiceActivity.class);
                            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent2);
                        } else if (rd_vid == 1) {
                            Intent intent2 = new Intent(Netizencall_DetailFakeActivity.this, WAVideoActivity.class);
                            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent2);
                        } else {
                            Intent intent2 = new Intent(Netizencall_DetailFakeActivity.this, WAVideoActivity.class);
                            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent2);
                        }
                    } else if (rd_form == 2) {
                        if (rd_vid == 2) {
                            Intent intent2 = new Intent(Netizencall_DetailFakeActivity.this, FBVoiceActivity.class);
                            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent2);
                        } else if (rd_vid == 1) {
                            Intent intent2 = new Intent(Netizencall_DetailFakeActivity.this, FBVideoActivity.class);
                            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent2);
                        } else {
                            Intent intent2 = new Intent(Netizencall_DetailFakeActivity.this, FBVideoActivity.class);
                            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent2);
                        }
                    } else if (rd_form == 3) {
                        if (rd_vid == 2) {
                            Intent intent2 = new Intent(Netizencall_DetailFakeActivity.this, TeleVoiceActivity.class);
                            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent2);
                        } else if (rd_vid == 1) {
                            Intent intent2 = new Intent(Netizencall_DetailFakeActivity.this, TeleVideoActivity.class);
                            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent2);
                        } else {
                            Intent intent2 = new Intent(Netizencall_DetailFakeActivity.this, TeleVideoActivity.class);
                            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent2);
                        }
                    }
                } else {
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.SECOND, rd_time);
                    AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    manager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
                    Toast.makeText(Netizencall_DetailFakeActivity.this, status_time, Toast.LENGTH_SHORT).show();
                    finish();
                    Netizencall_MainActivity.fa.finish();
                }
            }
        });


        RelativeLayout layNative = findViewById(R.id.layNative);
        switch (SELECT_MAIN_ADS) {
            case "ADMOB":
                AliendroidNative.SmallNativeAdmob(this, layNative, SELECT_BACKUP_ADS, MAIN_ADS_NATIVES, BACKUP_ADS_NATIVES, HIGH_PAYING_KEYWORD1,
                        HIGH_PAYING_KEYWORD2, HIGH_PAYING_KEYWORD3, HIGH_PAYING_KEYWORD4, HIGH_PAYING_KEYWORD5);
                break;
            case "ADMOB-B":
                AliendroidMediumBanner.MediumBannerAdmob(this, layNative, SELECT_BACKUP_ADS, MAIN_ADS_BANNER, BACKUP_ADS_BANNER, HIGH_PAYING_KEYWORD1,
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
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

}