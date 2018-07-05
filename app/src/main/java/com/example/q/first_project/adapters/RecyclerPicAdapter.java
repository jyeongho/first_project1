package com.example.q.first_project.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.q.first_project.GalleryActivity;
import com.example.q.first_project.MainActivity;
import com.example.q.first_project.R;
import com.example.q.first_project.fragments.FragmentPicture;

public class RecyclerPicAdapter extends RecyclerView.Adapter<RecyclerPicAdapter.ImageViewHolder> {

    private int[] images;
    private Context mContext;

    public RecyclerPicAdapter (int[] images, Context context) {
        this.images = images;
        mContext = context;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_layout, parent,false);
        ImageViewHolder imageViewHolder = new ImageViewHolder(view);


        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder,final int position) {
        final int image_id = this.images[position];

        holder.Album.setImageResource(image_id);

        holder.Album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), GalleryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("image_id", position);
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
                //holder.Album.setImageResource(image_id);
                //holder.pic_name.setText("Image :" + position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.images.length;
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder //implements View.OnClickListener
    {

        ImageView Album;

        public ImageViewHolder(View itemView) {
            super(itemView);
            Album = itemView.findViewById(R.id.album);
            //itemView.setClickable(true);
            //itemView.setOnClickListener(this);
        }

        //@Override
        /*public void onClick(View view) {
            Fragment fragment = new FragmentPicture();
            FragmentManager fragmentManager = ((MainActivity)mContext).getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frag_gallery, fragment).commit();
        }*/
    }
}
