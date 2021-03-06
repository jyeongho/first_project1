package com.example.q.first_project.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.q.first_project.R;
import com.example.q.first_project.adapters.RecyclerPicAdapter;

import java.util.ArrayList;

public class FragmentGallery extends Fragment {

    private View v;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> thumbsDadaList;
    private ArrayList<String> thumbsIDList;


    public FragmentGallery() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frag_gallery, container, false);
        recyclerView = v.findViewById(R.id.recyclerView);
        thumbsIDList = new ArrayList<String>();
        thumbsDadaList = new ArrayList<String>();

        getThumbInfo(thumbsIDList, thumbsDadaList);

        layoutManager = new GridLayoutManager(getContext(), 3);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(layoutManager);

        RecyclerPicAdapter adapter = new RecyclerPicAdapter(thumbsDadaList, getContext());

        recyclerView.setAdapter(adapter);

        return v;


    }
    private void getThumbInfo(ArrayList<String> thumbsIDs, ArrayList<String> thumbsDatas) {
        String[] proj ={MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.SIZE};

        Cursor imageCursor = getContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, proj, null, null, null);

        if (imageCursor != null && imageCursor.moveToFirst()){
            String thumbsID;
            String thumbsImagesID;
            String thumbsData;

            int thumbsIDCol = imageCursor.getColumnIndex(MediaStore.Images.Media._ID);
            int thumbsDataCol = imageCursor.getColumnIndex(MediaStore.Images.Media.DATA);
            int thumbsImagesIDCol = imageCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME);
            int num =0;
            do {
                thumbsID = imageCursor.getString(thumbsIDCol);
                thumbsData = imageCursor.getString(thumbsDataCol);
                thumbsImagesID = imageCursor.getString(thumbsImagesIDCol);
                num++;
                if (thumbsImagesID != null) {
                    thumbsIDs.add(thumbsID);
                    thumbsDatas.add(thumbsData);
                }
            }while (imageCursor.moveToNext());
        }
        imageCursor.close();
        return;
    }
}

