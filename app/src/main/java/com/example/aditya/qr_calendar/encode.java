package com.example.aditya.qr_calendar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
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

    EditText editTitle, editLocation;
    String EditTextValue1, EditTextValue2, EditTextValue3, EditTextValue4, EditTextValue5, EditTextValue6, EditTextValue ;
    private TimePicker timePicker1, timePicker2;
    private int day, month, year;
    private DatePicker datePicker;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.encode);
        editTitle = (EditText) findViewById(R.id.edit_title);
        editLocation = (EditText) findViewById(R.id.edit_location);
        timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
        timePicker2 = (TimePicker) findViewById(R.id.timePicker2);
        timePicker1.setIs24HourView(true);
        timePicker2.setIs24HourView(true);
        datePicker = (DatePicker) findViewById(R.id.datePicker);

    }

    public void onClick(View view) {
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        EditTextValue1 = editTitle.getText().toString();
        EditTextValue2 = editLocation.getText().toString();
        EditTextValue3 = (day)+("/")+(month + 1)+("/")+(year)+(" ");
        EditTextValue = "{\"Title\":" + "\"" + EditTextValue1 + "\"," + "\"Location\":" + "\"" +
                EditTextValue2 + "\"}" + "{\"Date\":" + "\"" + EditTextValue3 + "\"," + "{\"Starttime\":" +
                "\"" + EditTextValue4 + "\"," + "{\"Endtime\":" + "\"" + EditTextValue5 + "\"," +
                "{\"Description\":" + "\"" + EditTextValue6 + "\"";

        Intent intent = new Intent();
            intent.putExtra("Title", EditTextValue1);
            intent.putExtra("Location", EditTextValue2);
        intent.putExtra("Result", EditTextValue);
            setResult(RESULT_OK, intent);
            finish();


    }





}


