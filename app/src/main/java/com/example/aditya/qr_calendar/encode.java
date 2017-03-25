package com.example.aditya.qr_calendar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.Hashtable;



/**
 * Created by aditya on 23/2/17.
 */

public class encode extends Activity {

    EditText editTitle, editDate;
    String EditTextValue1, EditTextValue2, EditTextValue ;
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.encode);
        editTitle = (EditText) findViewById(R.id.edit_title);
        editDate = (EditText) findViewById(R.id.edit_date);


    }

    public void onClick(View view) {


            EditTextValue1 = editTitle.getText().toString();
            EditTextValue2 = editDate.getText().toString();

            Intent intent = new Intent();
            intent.putExtra("Title", EditTextValue1);
            intent.putExtra("Date", EditTextValue2);
            setResult(RESULT_OK, intent);
            finish();


    }


}


