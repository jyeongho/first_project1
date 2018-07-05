package com.example.q.first_project;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.q.first_project.adapters.ViewPagerAdapter;
import com.example.q.first_project.fragments.FragmentContacts;
import com.example.q.first_project.fragments.FragmentGallery;
import com.example.q.first_project.fragments.FragmentLoadInsta;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_STORAGE= 1111;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private static final int REQUEST_CODE = 1234;
    private boolean mPermissions;

    private final int[] ICONS = {R.drawable.human4, R.drawable.galleryicon, R.drawable.ic_filter};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkPermission();

    }

    public void changeActivity(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }


    private long pressedTime = 0;
    public interface OnBackPressedListener {
        public void onBack();
    }
    private OnBackPressedListener mBackListener;

    public void setOnBackPressedListener(OnBackPressedListener listener) {
        mBackListener =listener;
    }

    @Override
    public void onBackPressed() {
        if (mBackListener != null) {
            mBackListener.onBack();
            Log.e("!!!", "Listener is not null");
        } else {
            Log.e("!!!", "Listener is null");
            if ( pressedTime == 0 ) {
                Snackbar.make(findViewById(R.id.main_layout),
                        " 한 번 더 누르면 종료됩니다." , Snackbar.LENGTH_LONG).show();
                pressedTime = System.currentTimeMillis();
            }
            else {
                int seconds = (int) (System.currentTimeMillis() - pressedTime);

                if ( seconds > 2000 ) {
                    Snackbar.make(findViewById(R.id.main_layout),
                            " 한 번 더 누르면 종료됩니다." , Snackbar.LENGTH_LONG).show();
                    pressedTime = 0 ;
                }
                else {
                    super.onBackPressed();
                    Log.e("!!!", "onBackPressed : finish, killProcess");
                    finish();
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            }
        }
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                new AlertDialog.Builder(this).setTitle("알림").setMessage("저장소 권한이 거부되었습니다. 사용을 원하시면 설정에서 해당 권한을 직접 허용하셔야 합니다.").setNeutralButton("설정", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package:" +getPackageName()));
                        startActivity(intent);
                    }
                })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setCancelable(false).create().show();
            } else {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_CALL_LOG,Manifest.permission.READ_CONTACTS, Manifest.permission.READ_PHONE_NUMBERS, Manifest.permission.WRITE_CONTACTS }, MY_PERMISSION_STORAGE);
            }
        } else{
            setContentView(R.layout.activity_main);

            tabLayout = findViewById(R.id.tablayout);
            viewPager = findViewById(R.id.viewpager);

            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

            adapter.addFragment(new FragmentContacts(), "Contacts");
            adapter.addFragment(new FragmentGallery(), "Gallery");
            adapter.addFragment(new FragmentLoadInsta(), "Filter");

            viewPager.setAdapter(adapter);

            tabLayout.setupWithViewPager(viewPager);

            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                tab.setIcon(ICONS[i]);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch ( requestCode) {
            case MY_PERMISSION_STORAGE:
                for(int i=0; i<grantResults.length; i++) {
                    if(grantResults[i]<0){
                        Toast.makeText(MainActivity.this, "해당 권한을 활성화 하셔야 합니다." , Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                setContentView(R.layout.activity_main);

                tabLayout = findViewById(R.id.tablayout);
                viewPager = findViewById(R.id.viewpager);

                ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

                adapter.addFragment(new FragmentContacts(), "Contacts");
                adapter.addFragment(new FragmentGallery(), "Gallery");
                adapter.addFragment(new FragmentLoadInsta(), "Filter");

                viewPager.setAdapter(adapter);

                tabLayout.setupWithViewPager(viewPager);

                for (int i = 0; i < tabLayout.getTabCount(); i++) {
                    TabLayout.Tab tab = tabLayout.getTabAt(i);
                    tab.setIcon(ICONS[i]);
                }
                break;
        }
    }
}
