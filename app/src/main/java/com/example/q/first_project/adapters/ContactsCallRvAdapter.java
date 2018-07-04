package com.example.q.first_project.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.q.first_project.R;
import com.example.q.first_project.models.ModelContactsCall;

import java.util.List;

public class ContactsCallRvAdapter extends RecyclerView.Adapter<ContactsCallRvAdapter.ViewHolder>{

    private LayoutInflater layoutInflater;
    private Context mContext;

    private List<ModelContactsCall> mlistContactsCall;

    public ContactsCallRvAdapter(Context context, List<ModelContactsCall> listContactsCall) {
        mContext = context;
        mlistContactsCall = listContactsCall;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(mContext);

        View view = layoutInflater.inflate(R.layout.item_contacts_call, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        TextView duration, date;

        duration = holder.duration;
        date = holder.date;

        duration.setText(mlistContactsCall.get(position).getDuration());
        date.setText(mlistContactsCall.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return mlistContactsCall.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView duration, date;
        public ViewHolder(View itemView) {
            super(itemView);

            duration = itemView.findViewById(R.id.duration);
            date = itemView.findViewById(R.id.date);
        }

    }
}
