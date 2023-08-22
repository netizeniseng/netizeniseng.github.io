package com.netizen.btsjhopeviral.NetizenUtility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.netizen.btsjhopeviral.NetizenFacebook.FBVideoActivity;
import com.netizen.btsjhopeviral.NetizenFacebook.FBVoiceActivity;
import com.netizen.btsjhopeviral.NetizenTelegram.TeleVideoActivity;
import com.netizen.btsjhopeviral.NetizenTelegram.TeleVoiceActivity;
import com.netizen.btsjhopeviral.NetizenWhatsapp.WAVideoActivity;
import com.netizen.btsjhopeviral.NetizenWhatsapp.WAVoiceActivity;
import com.netizen.btsjhopeviral.NetizenActivity.Netizencall_DetailFakeActivity;

public class Netizencall_AppReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Netizencall_DetailFakeActivity.rd_form ==1) {
            if (Netizencall_DetailFakeActivity.rd_vid ==2){
                Intent intent2 = new Intent(context, WAVoiceActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent2);
            } else if (Netizencall_DetailFakeActivity.rd_vid==1){
                Intent intent2 = new Intent(context, WAVideoActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent2);
            } else {
                Intent intent2 = new Intent(context, WAVideoActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent2);
            }
        } else if (Netizencall_DetailFakeActivity.rd_form==2){
            if (Netizencall_DetailFakeActivity.rd_vid ==2){
                Intent intent2 = new Intent(context, FBVoiceActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent2);
            } else  if (Netizencall_DetailFakeActivity.rd_vid ==1){
                Intent intent2 = new Intent(context, FBVideoActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent2);
            } else {
                Intent intent2 = new Intent(context, FBVideoActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent2);
            }
        }else if (Netizencall_DetailFakeActivity.rd_form==3) {
            if (Netizencall_DetailFakeActivity.rd_vid == 2) {
                Intent intent2 = new Intent(context, TeleVoiceActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent2);
            } else if (Netizencall_DetailFakeActivity.rd_vid == 1) {
                Intent intent2 = new Intent(context, TeleVideoActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent2);
            } else {
                Intent intent2 = new Intent(context, TeleVideoActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent2);
            }
        }

    }

}
