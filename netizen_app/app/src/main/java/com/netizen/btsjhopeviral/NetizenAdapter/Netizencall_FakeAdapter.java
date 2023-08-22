package com.netizen.btsjhopeviral.NetizenAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.netizen.btsjhopeviral.R;
import com.netizen.btsjhopeviral.NetizenActivity.Netizencall_DetailFakeActivity;
import com.netizen.btsjhopeviral.NetizenModel.Netizencall_Item;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;


public class Netizencall_FakeAdapter extends RecyclerView.Adapter {


    public static String judul ;
    public static String gambar ;
    public static String voice;
    public static String video;
    public static List<Netizencall_Item> webLists;
    public Context context;

    public Netizencall_FakeAdapter(List<Netizencall_Item> webLists, Context context) {
        Netizencall_FakeAdapter.webLists = webLists;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        public ImageView avatar_url;


        public ViewHolder(View itemView) {
            super(itemView);
            avatar_url =  itemView.findViewById(R.id.img_fake);


        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.call_fake_list, parent, false);
                return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

          if (holder instanceof ViewHolder) {
                    final Netizencall_Item webList = webLists.get(position);
              final int random = new Random().nextInt(150) + 20;
              int height = (int) (10 + random + 200);
              ((ViewHolder)holder).avatar_url.getLayoutParams().height = height;
                    Picasso.get()
                            .load(webList.getImage_url())
                            .into( ((ViewHolder)holder).avatar_url);

                    ((ViewHolder)holder).avatar_url.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            judul = webList.getNamefake();
                            gambar = webList.getImage_url();
                            voice = webList.getVoice_url();
                            video = webList.getViode_url();
                            Intent intent = new Intent(context, Netizencall_DetailFakeActivity.class);
                            context.startActivity(intent);
                        }
                    });

        }

    }

    public int getItemCount() {
        return webLists.size();
    }

}
