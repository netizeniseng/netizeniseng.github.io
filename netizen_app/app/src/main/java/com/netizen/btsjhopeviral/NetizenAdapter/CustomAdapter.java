package com.netizen.btsjhopeviral.NetizenAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.netizen.btsjhopeviral.R;
import com.netizen.btsjhopeviral.NetizenModel.ChatItem;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList <ChatItem> userList;
    LayoutInflater inflter;
    private DateFormat dateFormat;

    public CustomAdapter(Context applicationContext, ArrayList <ChatItem> userList) {
        this.context = applicationContext;
        this.userList = userList;
        this.dateFormat = android.text.format.DateFormat.getTimeFormat(applicationContext);
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.list_user, null);
        TextView country = (TextView) view.findViewById(R.id.txtTitle);
        TextView status = (TextView) view.findViewById(R.id.txtLastChat);
        CircleImageView icon = (CircleImageView) view.findViewById(R.id.imgContact);
        TextView txtTime = view.findViewById(R.id.txtTime);
        txtTime.setText(dateFormat.format(userList.get(i).getTime()));
        country.setText(userList.get(i).getTitle());
        status.setText(userList.get(i).getStatus());
        try {
            if (userList.get(i).getImg().equals("")) {
                Picasso.get().load(R.drawable.avatar_contact).into(icon);
            } else {
                Picasso.get().load(new File(userList.get(i).getImg())).centerCrop().fit().into(icon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }
}