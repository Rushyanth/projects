package com.example.rushyanthreddy.medicinereminder;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by Rushyanth Reddy on 3/25/2017.
 */
public class CardContentAdapter extends RecyclerView.Adapter<CardContentAdapter.MyViewHolder> {

    private List<CardContent> cardsList;
    private DatabaseReference mDatabase;
    private FirebaseAuth auth;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tabletname, time, days;

        public MyViewHolder(View view) {
            super(view);
            tabletname = (TextView) view.findViewById(R.id.cardview_content_tabletname);
            time = (TextView) view.findViewById(R.id.cardview_content_time);
            days = (TextView) view.findViewById(R.id.cardview_content_days);
        }
    }


    public CardContentAdapter(List<CardContent> cardsList) {
        this.cardsList = cardsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_main_content, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        String uid=auth.getCurrentUser().getUid();
        Log.d("cc tablet name",String.valueOf(position));
        mDatabase.child("REMINDERS").child(uid).child(String.valueOf(position)).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String tablet_name= dataSnapshot.child("tablet_name").getValue(String.class);
                Log.d("cc tablet name",tablet_name);
                String days= dataSnapshot.child("days").getValue(String.class);
                Log.d("cc days",days);
                String time=dataSnapshot.child("time").getValue(String.class);
                Log.d("cc Time",time);
                holder.tabletname.setText(tablet_name);
                holder.time.setText("no of times a day:" + String.valueOf(time));
                holder.days.setText(days);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
       /* mDatabase.child("REMINDERS").child(uid).child(String.valueOf(position)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CardContent card = dataSnapshot.getValue(CardContent.class);
                if(card!=null){
                    holder.tabletname.setText(card.getTablet_name());
                    holder.time.setText("no of times a day:" + String.valueOf(card.getTime()));
                    holder.days.setText(card.getDays());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
*/    }

    @Override
    public int getItemCount() {
        return cardsList.size();
    }
}