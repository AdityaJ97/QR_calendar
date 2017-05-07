package com.example.aditya.qr_calendar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.widget.TimePicker;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.Calendar;
import java.util.Hashtable;



/**
 * Created by aditya on 23/2/17.
 */

public class encode extends Activity {

    EditText editTitle, editLocation, editDescription;
    String EditTextValue1, EditTextValue2, EditTextValue3, EditTextValue4, EditTextValue5, EditTextValue6, EditTextValue ;
    private TimePicker timePicker1, timePicker2;
    private int hour1, minute1, hour2, minute2;
    private CustomDatePicker datePicker;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.encode);
        editTitle = (EditText) findViewById(R.id.edit_title);
        editLocation = (EditText) findViewById(R.id.edit_location);
        editDescription = (EditText) findViewById(R.id.edit_description);

        timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
        timePicker2 = (TimePicker) findViewById(R.id.timePicker2);
        timePicker1.setIs24HourView(true);
        timePicker2.setIs24HourView(true);
        datePicker = (CustomDatePicker) findViewById(R.id.datePicker);

    }

    public void onClick(View view) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();
        if (Build.VERSION.SDK_INT >= 23 )
            hour1 = timePicker1.getHour();
        else
            hour1 = timePicker1.getCurrentHour();
        if (Build.VERSION.SDK_INT >= 23 )
            minute1 = timePicker1.getMinute();
        else
            minute1 = timePicker1.getCurrentMinute();

        if (Build.VERSION.SDK_INT >= 23 )
            hour2 = timePicker2.getHour();
        else
            hour2 = timePicker2.getCurrentHour();
        if (Build.VERSION.SDK_INT >= 23 )
            minute2 = timePicker2.getMinute();
        else
            minute2 = timePicker2.getCurrentMinute();
       // timePicker1.clearFocus();
      //  int hour = timePicker1.getHour();
       // int minute = timePicker1.getMinute();






        EditTextValue1 = editTitle.getText().toString();
        EditTextValue2 = editLocation.getText().toString();
        EditTextValue6 = editDescription.getText().toString();
        EditTextValue3 = (day)+("/")+(month + 1)+("/")+(year)+(" ");
        EditTextValue4 = (hour1) + (":") + (minute1);
        EditTextValue5 = (hour2) + (":") + (minute2);
        EditTextValue = "{\"Title\":" + "\"" + EditTextValue1 + "\"," + "\"Location\":" + "\"" +
                EditTextValue2 + "\"," + "\"Date\":" + "\"" + EditTextValue3 + "\"," + "\"Start\":" +
                "\"" + EditTextValue4 + "\"," + "\"End\":" + "\"" + EditTextValue5 + "\"," +
                "\"Description\":" + "\"" + EditTextValue6 + "\"}";

        Intent intent = new Intent();

        intent.putExtra("Result", EditTextValue);
        setResult(RESULT_OK, intent);
        finish();


    }






}


