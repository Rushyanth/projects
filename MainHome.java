package com.example.rushyanthreddy.medicinereminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView recyclerView;

    private ArrayList<CardContent> cardsList = new ArrayList<CardContent>();
    private CardContentAdapter ccAdapter;
    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        ccAdapter = new CardContentAdapter(cardsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(ccAdapter);

        Bundle i = getIntent().getExtras();
        if (i != null) {
            String x = i.getString("nameoftab");
            int y = i.getInt("nooftimes");
            ArrayList<String> z = i.getStringArrayList("daysofweek");
            Log.d("tabname", x);
            Log.d("alarmtime", String.valueOf(y));
            Log.d("days", String.valueOf(z));
            prepareCardData(x, y, z);
        }
        /*String x= "Tablet Name";
        int y =0;
        ArrayList<String> z = new ArrayList<>();
        z.add("No of days");*/


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainHome.this, NewReminder.class);

                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void prepareCardData(String tabname, int alarmtime, ArrayList<String> days) {

        mDatabase = FirebaseDatabase.getInstance().getReference();
        final CardContent card = new CardContent(tabname, alarmtime, days);
        //cardsList.add(card);
       // Log.d("cards", String.valueOf(cardsList.size()));
        final String uid = mAuth.getCurrentUser().getUid();
        if (mDatabase.child("REMINDERS").child(uid).getKey() != null) {
            Log.d("reminders","entered if"+mDatabase.child("REMINDERS").child(uid).getKey() );
            mDatabase.child("REMINDERS").child(uid).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    long i = dataSnapshot.getChildrenCount();
                    mDatabase.child("REMINDERS").child(uid).child(String.valueOf(i)).setValue(card);
                    Log.d("reminders","entered if if:  "+mDatabase.child("REMINDERS").child(uid).getKey()+"  "+String.valueOf(i));


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

        } else {
            Log.d("reminders","entered else");
            mDatabase.child("REMINDERS").child(uid).child("0").setValue(card);
        }

        ccAdapter.notifyDataSetChanged();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            mAuth.signOut();
            Intent i = new Intent(MainHome.this, MainActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
