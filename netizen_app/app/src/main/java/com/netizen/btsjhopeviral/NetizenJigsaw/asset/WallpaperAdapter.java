package com.netizen.btsjhopeviral.NetizenJigsaw.asset;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.netizen.btsjhopeviral.R;
import com.netizen.btsjhopeviral.NetizenJigsaw.JigsawUI.PuzzleJigsawActivity;
import com.netizen.btsjhopeviral.NetizenJigsaw.JigsawUI.PuzzleMainActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class WallpaperAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 0;
    public static ArrayList<Wallpaper> webLists;
    public Context context;
    public static Intent intent;

    public WallpaperAdapter(ArrayList<Wallpaper> webLists, Context context) {
        this.webLists = webLists;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView html_url;
        public ImageView avatar_url;
        public RelativeLayout linearLayout;
        public Button favoriteImg;

        public ViewHolder(View itemView) {
            super(itemView);
            html_url = (TextView) itemView.findViewById(R.id.username);
            avatar_url = (ImageView) itemView.findViewById(R.id.imageView);
            linearLayout = (RelativeLayout) itemView.findViewById(R.id.linearLayout);


        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.puzzle_list_puzzle, parent, false);
            return new ViewHolder(v);

        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_progressbar, parent, false);
            return new ProgressViewHolder(v);
        }

    }
    private static class ProgressViewHolder extends RecyclerView.ViewHolder {
        private static ProgressBar progressBar;

        private ProgressViewHolder(View v) {
            super(v);
            progressBar = v.findViewById(R.id.progressBar);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            final Wallpaper webList = webLists.get(position);
            ((ViewHolder)holder).html_url.setText(webList.getHtml_url());

                    Picasso.get()
                            .load(webList.getAvatar_url())
                            .into( ((ViewHolder)holder).avatar_url);

            ((ViewHolder)holder).linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PuzzleMainActivity.mp.start();
                    intent = new Intent(context, PuzzleJigsawActivity.class);
                    intent.putExtra("assetName", webList.getAvatar_url());
                    context.startActivity(intent);


                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return webLists.size();
    }

}
