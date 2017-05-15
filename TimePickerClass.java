package com.example.rushyanthreddy.medicinereminder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TimePicker;

/**
 * Created by Rushyanth Reddy on 3/28/2017.
 */
public class TimePickerClass extends AppCompatActivity {
    Button cancel,ok;
    TimePicker tm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_picker);

        cancel=(Button)findViewById(R.id.time_picker_button_cancel);
        ok=(Button)findViewById(R.id.time_picker_button_ok);

        tm=(TimePicker)findViewById(R.id.time_picker_timePicker);

    }
}
