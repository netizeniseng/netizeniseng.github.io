package com.netizen.btsjhopeviral.NetizenUIutama.contact;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.aliendroid.alienads.AliendroidBanner;
import com.aliendroid.alienads.AliendroidNative;
import com.netizen.btsjhopeviral.NetizenSettings;
import com.netizen.btsjhopeviral.R;
import com.netizen.btsjhopeviral.NetizenModel.ChatItem;
import com.netizen.btsjhopeviral.NetizenModel.ContactData;
import com.netizen.btsjhopeviral.NetizenSave.SaveState;
import com.netizen.btsjhopeviral.NetizenUIutama.NetizenMainActivity;
import com.netizen.btsjhopeviral.NetizenUtility.Utils;
import com.squareup.picasso.Picasso;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class NetizenEditContactNew extends AppCompatActivity {
    EditText contactName;
    EditText contactState;
    String picturePath;
    private static int RESULT_LOAD_IMAGE = 1;
    CircleImageView contactImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        contactName = findViewById(R.id.contact_name);
        contactState = findViewById(R.id.contact_state);
        contactImage = findViewById(R.id.imgEditContact);
        contactImage.setImageBitmap(Utils.cropToCircle(BitmapFactory.decodeResource(getResources(), R.drawable.avatar_contact)));

        contactImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);

            }
        });


        this.findViewById(R.id.imgClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        this.findViewById(R.id.imgSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contactName.getText().toString().equals("Donald Trump") ||
                        contactName.getText().toString().equals("donald Trump") ||
                        contactName.getText().toString().equals("donald trump") ||
                        contactName.getText().toString().equals("Donald trump") ||
                        contactName.getText().toString().equals("Joe Biden") ||
                        contactName.getText().toString().equals("joe biden") ||
                        contactName.getText().toString().equals("Joe biden") ||
                        contactName.getText().toString().equals("joe Biden")) {
                    Toast.makeText(NetizenEditContactNew.this, getString(R.string.no_accept), Toast.LENGTH_SHORT).show();
                } else {
                    if (contactState.getText().toString().equals("Donald Trump") ||
                            contactState.getText().toString().equals("donald Trump") ||
                            contactState.getText().toString().equals("donald trump") ||
                            contactState.getText().toString().equals("Donald trump") ||
                            contactState.getText().toString().equals("Joe Biden") ||
                            contactState.getText().toString().equals("joe biden") ||
                            contactState.getText().toString().equals("Joe biden") ||
                            contactState.getText().toString().equals("joe Biden")) {
                        Toast.makeText(NetizenEditContactNew.this, getString(R.string.no_accept), Toast.LENGTH_SHORT).show();
                    } else {
                        if (picturePath == null) {
                            SaveState.createChat(new ContactData(contactName.getText().toString(), contactState.getText().toString(), "", System.currentTimeMillis()));
                            NetizenMainActivity.userList.add(new ChatItem(1, contactName.getText().toString(), "Online", "", System.currentTimeMillis()));
                            finish();
//                            switch (NetizenSettings.SELECT_MAIN_ADS) {
//                                case "ADMOB":
//                                case "ADMOB-B":
//                                    AliendroidIntertitial.ShowIntertitialAdmob(NetizenEditContactNew.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0,
//                                            NetizenSettings.HIGH_PAYING_KEYWORD1, NetizenSettings.HIGH_PAYING_KEYWORD2, NetizenSettings.HIGH_PAYING_KEYWORD3, NetizenSettings.HIGH_PAYING_KEYWORD4, NetizenSettings.HIGH_PAYING_KEYWORD5);
//                                    break;
//                                case "APPLOVIN-D":
//                                    AliendroidIntertitial.ShowIntertitialApplovinDis(NetizenEditContactNew.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
//                                    break;
//                                case "APPLOVIN-M":
//                                    AliendroidIntertitial.ShowIntertitialApplovinMax(NetizenEditContactNew.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
//                                    break;
//                                case "IRON":
//                                    AliendroidIntertitial.ShowIntertitialIron(NetizenEditContactNew.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
//                                    break;
//                                case "STARTAPP":
//                                    AliendroidIntertitial.ShowIntertitialSartApp(NetizenEditContactNew.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
//                                    break;
//                                case "FACEBOOK":
//                                    AliendroidIntertitial.ShowIntertitialFAN(NetizenEditContactNew.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
//                                    break;
//                                case "GOOGLE-ADS":
//                                    AliendroidIntertitial.ShowIntertitialGoogleAds(NetizenEditContactNew.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
//                                    break;
//                            }
                        } else {
                            SaveState.createChat(new ContactData(contactName.getText().toString(), contactState.getText().toString(), picturePath, System.currentTimeMillis()));
                            NetizenMainActivity.userList.add(new ChatItem(1, contactName.getText().toString(), contactState.getText().toString(), picturePath, System.currentTimeMillis()));
                            finish();
//                            switch (NetizenSettings.SELECT_MAIN_ADS) {
//                                case "ADMOB":
//                                case "ADMOB-B":
//                                    AliendroidIntertitial.ShowIntertitialAdmob(NetizenEditContactNew.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0,
//                                            NetizenSettings.HIGH_PAYING_KEYWORD1, NetizenSettings.HIGH_PAYING_KEYWORD2, NetizenSettings.HIGH_PAYING_KEYWORD3, NetizenSettings.HIGH_PAYING_KEYWORD4, NetizenSettings.HIGH_PAYING_KEYWORD5);
//                                    break;
//                                case "APPLOVIN-D":
//                                    AliendroidIntertitial.ShowIntertitialApplovinDis(NetizenEditContactNew.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
//                                    break;
//                                case "APPLOVIN-M":
//                                    AliendroidIntertitial.ShowIntertitialApplovinMax(NetizenEditContactNew.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
//                                    break;
//                                case "IRON":
//                                    AliendroidIntertitial.ShowIntertitialIron(NetizenEditContactNew.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
//                                    break;
//                                case "STARTAPP":
//                                    AliendroidIntertitial.ShowIntertitialSartApp(NetizenEditContactNew.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
//                                    break;
//                                case "FACEBOOK":
//                                    AliendroidIntertitial.ShowIntertitialFAN(NetizenEditContactNew.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
//                                    break;
//                                case "GOOGLE-ADS":
//                                    AliendroidIntertitial.ShowIntertitialGoogleAds(NetizenEditContactNew.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
//                                    break;
//                            }
                        }

                    }


                }
            }
        });

        RelativeLayout layAdsbanner = findViewById(R.id.layAds);
        switch (NetizenSettings.SELECT_MAIN_ADS) {
            case "ADMOB":
                AliendroidNative.SmallNativeAdmob(this, layAdsbanner, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_NATIVES, NetizenSettings.BACKUP_ADS_NATIVES, NetizenSettings.HIGH_PAYING_KEYWORD1,
                        NetizenSettings.HIGH_PAYING_KEYWORD2, NetizenSettings.HIGH_PAYING_KEYWORD3, NetizenSettings.HIGH_PAYING_KEYWORD4, NetizenSettings.HIGH_PAYING_KEYWORD5);
                break;
            case "ADMOB-B":
                AliendroidBanner.SmallBannerAdmob(this, layAdsbanner, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_BANNER, NetizenSettings.BACKUP_ADS_BANNER, NetizenSettings.HIGH_PAYING_KEYWORD1,
                        NetizenSettings.HIGH_PAYING_KEYWORD2, NetizenSettings.HIGH_PAYING_KEYWORD3, NetizenSettings.HIGH_PAYING_KEYWORD4, NetizenSettings.HIGH_PAYING_KEYWORD5);
                break;
            case "APPLOVIN-M":
                AliendroidNative.SmallNativeMax(this, layAdsbanner, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_NATIVES, NetizenSettings.BACKUP_ADS_NATIVES);
                break;
            case "APPLOVIN-D":
                AliendroidBanner.SmallBannerApplovinDis(this, layAdsbanner, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_BANNER, NetizenSettings.BACKUP_ADS_BANNER);
                break;
            case "STARTAPP":
                AliendroidNative.SmallNativeStartApp(this, layAdsbanner, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_NATIVES, NetizenSettings.BACKUP_ADS_NATIVES);
                break;
            case "IRON":
                AliendroidBanner.SmallBannerIron(this, layAdsbanner, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_BANNER, NetizenSettings.BACKUP_ADS_BANNER);
                break;
            case "FACEBOOK":
                AliendroidNative.SmallNativeFan(this, layAdsbanner, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_NATIVES, NetizenSettings.BACKUP_ADS_NATIVES);
                break;
            case "GOOGLE-ADS":
                AliendroidBanner.SmallBannerGoogleAds(this, layAdsbanner, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_BANNER, NetizenSettings.BACKUP_ADS_BANNER);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();

            if (picturePath.equals("")) {
                Picasso.get().load(R.drawable.avatar_contact).into(contactImage);
            } else {
                Picasso.get().load(new File(picturePath)).centerCrop().fit().into(contactImage);
            }

        }

    }

}