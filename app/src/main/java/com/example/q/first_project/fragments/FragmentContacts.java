package com.example.q.first_project.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.q.first_project.R;
import com.example.q.first_project.adapters.ContactsRvAdapter;
import com.example.q.first_project.models.ModelContacts;

import java.util.ArrayList;
import java.util.List;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class FragmentContacts extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private View v;
    private View v2;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    public FragmentContacts() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.frag_contacts, container, false);

        recyclerView = v.findViewById(R.id.rv_contacts);
        recyclerView.setBackgroundColor(Color.rgb(29, 29, 37));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        RecyclerView.LayoutManager layoutManager = linearLayoutManager;

        recyclerView.setLayoutManager(layoutManager);
        ContactsRvAdapter adapter = new ContactsRvAdapter(getContext(), getContacts());

        recyclerView.setAdapter(adapter);

        swipeRefreshLayout = v.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(android.R.color.holo_green_dark),getResources().getColor(android.R.color.holo_red_dark)
                ,getResources().getColor(android.R.color.holo_blue_dark),getResources().getColor(android.R.color.holo_orange_dark) );

        return v;
    }
    @Override
    public void onRefresh() {
        FragmentTransaction ft=getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
        swipeRefreshLayout.setRefreshing(false);
    }

    private List<ModelContacts> getContacts() {

        List<ModelContacts> list = new ArrayList<>();

        Cursor cursor = getContext().getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_ALTERNATIVE);

        int ididx = cursor.getColumnIndex(ContactsContract.Contacts._ID);
        int nameidx = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

        while (cursor.moveToNext()) {

            String id = cursor.getString(ididx);
            Cursor cursor2 = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[]{id}, null);

            int typeidx = cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
            int numidx = cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String mobile = null;
            String home = null;
            while(cursor2.moveToNext()) {
                String num = cursor2.getString(numidx);
                switch ( cursor2.getInt(typeidx)) {
                    case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                        mobile = num;
                        break;
                    case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                        home = num;
                        break;
                }
            }

            list.add(new ModelContacts(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)),
                    mobile, home));


        }

        return list;
    }
}

