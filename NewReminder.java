package com.example.rushyanthreddy.medicinereminder;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Rushyanth Reddy on 3/27/2017.
 */
public class NewReminder extends AppCompatActivity {

    AlarmManager alarmManager;
    private PendingIntent pendingIntent[];
    Calendar calendar[];
    private static NewReminder inst;
    String TAG= "Dialog_Box";
    EditText nameoftab,nooftimes;
    RadioButton rb;
    LinearLayout ll1,ll2;
    int i=0,si=0,mi=0,ti=0,wi=0,thi=0,fi=0,sai=0;
    Button sunday,monday,tuesday,wednesday,thursday,friday,saturday,setalarm;
    ArrayList<String> repeateddays = new ArrayList<>();
    ArrayList<String> alaramtimes = new ArrayList<>();
    ColorStateList defaultcolor;


    TimePicker tm;

    List<TextView> alarmsText;
    MainHome mh1;
    MenuItem saveicon,deleteicon;
    private int [] hour;
    private int [] minute;
    public static NewReminder instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_reminder);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.new_reminder_toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        nameoftab=(EditText)findViewById(R.id.new_reminder_edittext_nameoftablet);
        nooftimes=(EditText)findViewById(R.id.new_reminder_edittext_nooftimes);

        rb=(RadioButton)findViewById(R.id.new_reminder_radioButton);
        ll1=(LinearLayout)findViewById(R.id.new_reminder_linearlayout);

        sunday=(Button)findViewById(R.id.new_reminder_button_sunday);
        monday=(Button)findViewById(R.id.new_reminder_button_monday);
        tuesday=(Button)findViewById(R.id.new_reminder_button_tuesday);
        wednesday=(Button)findViewById(R.id.new_reminder_button_wednesday);
        thursday=(Button)findViewById(R.id.new_reminder_button_thursday);
        friday=(Button)findViewById(R.id.new_reminder_button_friday);
        saturday=(Button)findViewById(R.id.new_reminder_button_saturday);
        setalarm=(Button)findViewById(R.id.new_reminder_button_settime);

        ll2=(LinearLayout)findViewById(R.id.new_reminder_linearlayout2);
        ll2.canScrollVertically(View.FOCUS_DOWN);
        //alarm


        setalarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ll2.getChildCount()>0)
                    ll2.removeAllViews();
                hour = new int[Integer.parseInt(String.valueOf(nooftimes.getText()))];
                minute = new int[Integer.parseInt(String.valueOf(nooftimes.getText()))];
                alarmsText = new ArrayList<>(Integer.parseInt(String.valueOf(nooftimes.getText())));
                for(int j=0;j<Integer.parseInt(String.valueOf(nooftimes.getText()));j++){
                    final TextView newAlarm = new TextView(NewReminder.this);

                    newAlarm.setText("Alarm "+(j+1)+" set as - 00:00");
                    newAlarm.setTextColor(R.color.black);
                    newAlarm.setTextSize(25);
                    newAlarm.setHeight(250);

                    ll2.addView(newAlarm);
                    alarmsText.add(newAlarm);

                    final int finalJ = j;

                    newAlarm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final Dialog dialog = new Dialog(NewReminder.this);
                            dialog.setContentView(R.layout.time_picker);
                            Button dialogButtoncancel = (Button) dialog.findViewById(R.id.time_picker_button_cancel);
                            Button dialogButtonok = (Button)dialog.findViewById(R.id.time_picker_button_ok);
                            tm=(TimePicker)dialog.findViewById(R.id.time_picker_timePicker);

                            dialog.setTitle("Set Alarm ");

                            dialogButtoncancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    alaramtimes.clear();
                                    dialog.dismiss();

                                }
                            });
                            dialogButtonok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (Build.VERSION.SDK_INT >= 23 )  {
                                        alaramtimes.add(String.valueOf(tm.getHour())+":"+String.valueOf(tm.getMinute()));
                                        newAlarm.setText(null);
                                        newAlarm.setText("Alram  "+(finalJ+1)+" set as - "+String.valueOf(tm.getHour())+":"+String.valueOf(tm.getMinute()));
                                        hour[finalJ]=tm.getHour();
                                        minute[finalJ]=tm.getMinute();

                                    }
                                    else
                                    {
                                        alaramtimes.add(String.valueOf(tm.getCurrentHour())+":"+String.valueOf(tm.getCurrentMinute()));
                                        newAlarm.setText(null);
                                        newAlarm.setText("Alarm "+(finalJ+1)+" set as - "+String.valueOf(tm.getCurrentHour())+":"+String.valueOf(tm.getCurrentMinute()));
                                        hour[finalJ]=tm.getCurrentHour();
                                        minute[finalJ]=tm.getCurrentMinute();
                                        Toast.makeText(NewReminder.this,"Alarm "+(finalJ+1)+" set as "+(finalJ+1)+" "+
                                                        String.valueOf(tm.getCurrentHour())+":"+String.valueOf(tm.getCurrentMinute()),
                                                Toast.LENGTH_SHORT).show();

                                        for(int k=0;k<alaramtimes.size();k++){
                                            Log.d(TAG,k+"  "+alaramtimes.get(k)+"  "+alaramtimes.size());
                                            Log.d(TAG,String.valueOf(tm.getCurrentHour())+":"+String.valueOf(tm.getCurrentMinute()));
                                        }

                                    }
                                    dialog.dismiss();

                                }
                            });
                            dialog.show();


                        }
                    });

                 }
            }
        });

        ll1.setVisibility(View.GONE);
        rb.setChecked(false);
        sunday.setSelected(false);
        monday.setSelected(false);
        tuesday.setSelected(false);
        wednesday.setSelected(false);
        thursday.setSelected(false);
        friday.setSelected(false);
        saturday.setSelected(false);

        defaultcolor=friday.getTextColors();
        rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i%2==0){
                    ll1.setVisibility(View.VISIBLE);
                    i++;
                }
                else{
                    rb.setChecked(false);
                    sunday.setSelected(false);
                    sunday.setTextColor(defaultcolor);
                    monday.setSelected(false);
                    monday.setTextColor(defaultcolor);
                    tuesday.setSelected(false);
                    tuesday.setTextColor(defaultcolor);
                    wednesday.setSelected(false);
                    wednesday.setTextColor(defaultcolor);
                    thursday.setSelected(false);
                    thursday.setTextColor(defaultcolor);
                    friday.setSelected(false);
                    friday.setTextColor(defaultcolor);
                    saturday.setSelected(false);
                    saturday.setTextColor(defaultcolor);
                    repeateddays.clear();
                    ll1.setVisibility(View.GONE);
                    i=0;
                }
            }
        });
        if(!rb.isChecked()){
            sunday.setSelected(false);
            monday.setSelected(false);
            tuesday.setSelected(false);
            wednesday.setSelected(false);
            thursday.setSelected(false);
            friday.setSelected(false);
            saturday.setSelected(false);
        }


        sunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(si%2==0){
                    sunday.setSelected(true);
                    repeateddays.add("sunday");
                    sunday.setTextColor(R.color.white);
                    si++;
                }
                else{
                    sunday.setSelected(false);
                    sunday.setTextColor(defaultcolor);
                    repeateddays.remove("sunday");
                    si=0;
                }

            }
        });

        monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mi%2==0){
                    monday.setSelected(true);
                    repeateddays.add("monday");
                    monday.setTextColor(R.color.white);
                    mi++;
                }
                else{
                    monday.setSelected(false);
                    monday.setTextColor(defaultcolor);
                    repeateddays.remove("monday");
                    mi=0;
                }

            }
        });

        tuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ti%2==0){
                    tuesday.setSelected(true);
                    repeateddays.add("tuesday");
                    tuesday.setTextColor(R.color.white);
                    ti++;
                }
                else{
                    tuesday.setSelected(false);
                    tuesday.setTextColor(defaultcolor);
                    repeateddays.remove("tuesday");
                    ti=0;
                }

            }
        });

        wednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(wi%2==0){
                    wednesday.setSelected(true);
                    repeateddays.add("wednesday");
                    wednesday.setTextColor(R.color.white);
                    wi++;
                }
                else{
                    wednesday.setSelected(false);
                    wednesday.setTextColor(defaultcolor);
                    repeateddays.remove("wednesday");
                    wi=0;
                }

            }
        });

        thursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(thi%2==0){
                    thursday.setSelected(true);
                    repeateddays.add("thursday");
                    thursday.setTextColor(R.color.white);
                    thi++;
                }
                else{
                    thursday.setSelected(false);
                    thursday.setTextColor(defaultcolor);
                    repeateddays.remove("thursday");
                    thi=0;
                }

            }
        });

        friday.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(fi%2==0){
                    friday.setSelected(true);
                    repeateddays.add("friday");
                    friday.setTextColor(R.color.white);
                    fi++;
                }
                else{
                    friday.setSelected(false);
                    friday.setTextColor(defaultcolor);
                    repeateddays.remove("friday");
                    fi=0;
                }

            }
        });

        saturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sai%2==0){
                    saturday.setSelected(true);
                    repeateddays.add("saturday");
                    saturday.setTextColor(R.color.white);
                    sai++;
                }
                else{
                    saturday.setSelected(false);
                    saturday.setTextColor(defaultcolor);
                    repeateddays.remove("saturday");
                    sai=0;
                }

            }
        });



    }


    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.set_reminder_toolbar_items, menu);
        saveicon = menu.findItem(R.id.toolbar_icon_save);
        deleteicon = menu.findItem(R.id.toolbar_icon_delete);
        saveicon.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                String finalnameoftab = String.valueOf(nameoftab.getText());
                int finalnooftimes = Integer.parseInt(String.valueOf(nooftimes.getText()));
                ArrayList<String> daysofweek = new ArrayList<>();
                daysofweek.clear();

                Log.d("daysof week", String.valueOf(daysofweek.size()));
                if(sunday.isSelected()){
                    daysofweek.add("sun");
                }
                if(monday.isSelected()){
                    daysofweek.add("mon");
                }
                if(tuesday.isSelected()){
                    daysofweek.add("tue");
                }
                if(wednesday.isSelected()){
                    daysofweek.add("wed");
                }
                if(thursday.isSelected()){
                    daysofweek.add("thu");
                }
                if(friday.isSelected()){
                    daysofweek.add("fri");
                }
                if(saturday.isSelected()){
                    daysofweek.add("sat");
                }
                for(int i=0;i<daysofweek.size();i++){
                    Log.d("days of the week",daysofweek.get(i));
                }
              // mh1.prepareCardData(finalameoftab,finalnooftimes,daysofweek);
                /*mh1 = new MainHome();
                CardContent card = new CardContent(finalnameoftab,finalnooftimes,daysofweek);
                mh1.cardsList.add(card);

                mh1.ccAdapter.notifyDataSetChanged();*/
                //Setting alaram
                //[Integer.parseInt(String.valueOf(nooftimes.getText()))];
                calendar = new Calendar[Integer.parseInt(String.valueOf(nooftimes.getText()))];
                pendingIntent = new PendingIntent[Integer.parseInt(String.valueOf(nooftimes.getText()))];
                for(int k=0;k<Integer.parseInt(String.valueOf(nooftimes.getText()));k++){

                    alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    Log.d("MyActivity", "Alarm On");
                    calendar[k] = Calendar.getInstance();
                    calendar[k].set(Calendar.HOUR_OF_DAY,hour[k]);
                    calendar[k].set(Calendar.MINUTE, minute[k]);
                    Intent myIntent = new Intent(NewReminder.this, AlarmReceiver.class);
                    pendingIntent[k] = PendingIntent.getBroadcast(NewReminder.this, 0, myIntent, 0);
                    alarmManager.set(AlarmManager.RTC, calendar[k].getTimeInMillis(), pendingIntent[k]);

                }


                Intent intent =new Intent(NewReminder.this,MainHome.class);
                intent.putExtra("nameoftab",finalnameoftab);
                intent.putExtra("nooftimes",finalnooftimes);
                intent.putExtra("daysofweek",daysofweek);
                startActivity(intent);

                return false;
            }
        });
        deleteicon.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                alaramtimes.clear();
                repeateddays.clear();
                sunday.setSelected(false);
                monday.setSelected(false);
                tuesday.setSelected(false);
                wednesday.setSelected(false);
                thursday.setSelected(false);
                friday.setSelected(false);
                saturday.setSelected(false);

                Intent intent =new Intent(NewReminder.this,MainHome.class);
                startActivity(intent);

                return false;
            }
        });
        return true;
    }
}