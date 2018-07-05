package com.example.q.first_project;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.q.first_project.PhotoView.PhotoView;

public class GalleryActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        Bundle bundle = getIntent().getExtras();
        int p = bundle.getInt("image_id");
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new SamplePagerAdapter());
        viewPager.setCurrentItem(p);
    }


    static class SamplePagerAdapter extends PagerAdapter {
        private static final int[] images = {R.drawable.pic1, R.drawable.pic2, R.drawable.pic3, R.drawable.pic4,
                R.drawable.pic5, R.drawable.pic6, R.drawable.pic7, R.drawable.pic8, R.drawable.pic9
                , R.drawable.pic10, R.drawable.pic11, R.drawable.pic12, R.drawable.pic13};

        @Override
        public int getCount() { return images.length;}

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            photoView.setImageResource(images[position]);
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {return view == object; }
    }
}
