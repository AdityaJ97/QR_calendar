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
    Thread thread ;
    public final static int QRcodeWidth = 500 ;
    Bitmap bitmap ;
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
            EditTextValue = "{\"Title\":" + "\"" + EditTextValue1 + "\"," + "\"Date\":" + "\"" + EditTextValue2 + "\"}";

        try {
                bitmap = TextToImageEncode(EditTextValue);
            Intent intent = new Intent();
            intent.putExtra("BitmapImage", bitmap);
            setResult(RESULT_OK, intent);
            onBackPressed();

            } catch (WriterException e) {
                e.printStackTrace();
            }
    }
    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        Color.BLACK:Color.WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

}


