package com.example.musicdemo.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicdemo.Activitys.AlbumActivity;
import com.example.musicdemo.R;
import com.example.musicdemo.models.AlbumModel;

import java.util.List;

public class MusicGridAdapter extends RecyclerView.Adapter<MusicGridAdapter.ViewHolder> {



    private Context mcontext;
    private List<AlbumModel> mDataSourece;


    public MusicGridAdapter (Context context , List<AlbumModel> mDataSourece){
        this.mcontext = context;
        this.mDataSourece = mDataSourece;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.item_grid_music,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final AlbumModel albumModel = mDataSourece.get(position);

        Glide.with(mcontext)
                .load(albumModel.getPoster())
                .into(holder.ivIcon);

        holder.mTvPlayNum.setText(albumModel.getPlayNum());
        holder.mTvName.setText(albumModel.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, AlbumActivity.class);
                intent.putExtra(AlbumActivity.ALBUM_ID,albumModel.getAlbumId());
                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSourece.size();
    }




    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivIcon;
        View itemView;
        TextView mTvPlayNum,mTvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView = itemView;
            ivIcon = itemView.findViewById(R.id.iv_grid_icon);
            mTvPlayNum = itemView.findViewById(R.id.tv_play_num);
            mTvName = itemView.findViewById(R.id.tv_name);
        }
    }
}
