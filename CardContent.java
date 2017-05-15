package com.example.rushyanthreddy.medicinereminder;

import java.util.ArrayList;

/**
 * Created by Rushyanth Reddy on 3/25/2017.
 */
public class CardContent {
    private String tablet_name;
    int time;
    ArrayList<String> days;

    public CardContent() {
    }

    public CardContent(String tablet_name, int time, ArrayList<String> days) {
        this.tablet_name =tablet_name ;
        this.time = time;
        this.days = days;
    }
    public CardContent(CardContent a){
        this.tablet_name =a.tablet_name ;
        this.time = a.time;
        this.days = a.days;
    }


    public String getTablet_name() {
        return tablet_name;
    }

    public void setTablet_name(String name) {
        this.tablet_name = name;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getDays() {
        if(days.size()!=0){
            String stringofdays =days.get(0);
            for(int i=1;i< days.size();i++){
                stringofdays += " "+days.get(i);
            }

            stringofdays="Repeat:"+stringofdays;
            return stringofdays;
        }
        String stringofdays = "No Repeat";
        return stringofdays;

    }

    public void setDays(ArrayList days) {
        this.days = days;
    }
}
