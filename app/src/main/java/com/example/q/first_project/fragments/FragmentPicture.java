package com.example.q.first_project.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.q.first_project.MainActivity;
import com.example.q.first_project.R;

public class FragmentPicture extends Fragment implements MainActivity.OnBackPressedListener {
    private View v;


    public FragmentPicture() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        int image_id = getArguments().getInt("image_id");
        String pic_name = getArguments().getString("pic_name");
        v = inflater.inflate(R.layout.activity_gallery, container, false);

        ImageView image = v.findViewById(R.id.imageView);
        TextView name = v.findViewById(R.id.pic_name);

        image.setImageResource(image_id);
        name.setText(pic_name);




     /*   recyclerView = v.findViewById(R.id.recyclerView);

        ImageViewAdapter adapter = new ImageViewAdapter(images, getContext());

        recyclerView.setAdapter(adapter);
*/
        return v;
    }
    @Override
    public void onBack() {
        Log.e("Other", "onBack()");
        MainActivity activity = (MainActivity)getActivity();
        // 한번 뒤로가기 버튼을 눌렀다면 Listener 를 null 로 해제해줍니다.
        activity.setOnBackPressedListener(null);
        // MainFragment 로 교체
        getFragmentManager().popBackStack();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("Other", "onAttach()");
        ((MainActivity)context).setOnBackPressedListener(this);
    }
}
