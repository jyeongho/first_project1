package com.example.q.first_project.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.q.first_project.MainActivity;
import com.example.q.first_project.R;
import com.example.q.first_project.adapters.ContactsCallRvAdapter;
import com.example.q.first_project.models.ModelContactsCall;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragmentContactsCall extends Fragment {
    private View v;
    private RecyclerView recyclerView;

    public FragmentContactsCall() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.frag_call_log, container, false);

        recyclerView = v.findViewById(R.id.rv_contacts);

        recyclerView.setBackgroundColor(Color.rgb(255, 255, 255));
        Bundle bundle=getArguments();
        String contact_call_number = bundle.getString("number");
        String contact_call_number2 = bundle.getString("number2");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        RecyclerView.LayoutManager layoutManager = linearLayoutManager;
        recyclerView.setLayoutManager(layoutManager);

        ContactsCallRvAdapter adapter = new ContactsCallRvAdapter(getContext(), getCallLogs(contact_call_number, contact_call_number2 ));

        recyclerView.setAdapter(adapter);
        return v;
    }

    private List<ModelContactsCall> getCallLogs(String string1, String string2) {

        List<ModelContactsCall> list = new ArrayList<>();

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.READ_CALL_LOG}, 1);
        }

        Cursor cursor = getContext().getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, CallLog.Calls.DATE);

        int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);
        int date = cursor.getColumnIndex(CallLog.Calls.DATE);
        int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);

        string1 = string1.replaceAll("-","");
        string1 = string1.replaceAll("\\(","");
        string1 = string1.replaceAll("\\)","");
        string1 = string1.replaceAll(" ","");
        string1 = string1.trim();
        string2 = string2.replaceAll("-","");
        string2 = string2.replaceAll("\\(","");
        string2 = string2.replaceAll("\\)","");
        string2 = string2.replaceAll(" ","");
        string2 = string2.trim();

        while (cursor.moveToNext()) {
            if (cursor.getString(number).equals(string1) || cursor.getString(number).equals(string2)) {
                Date datel = new Date(Long.valueOf(cursor.getString(date)));
                list.add(new ModelContactsCall(cursor.getString(duration), datel.toString()));
            }
        }
        return list;
    }

}
