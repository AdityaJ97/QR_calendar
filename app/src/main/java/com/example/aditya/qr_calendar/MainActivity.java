package com.example.aditya.qr_calendar;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.R.attr.bitmap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int ENCODED_IMAGE_REQUEST = 1;
    String Titlestr, Datestr, Locationstr, Descriptionstr, Starttimestr, Endtimestr, EditTextValue;
    private TextView textViewTitle, textViewDate, textViewLocation, textViewDescription, textViewStarttime, textViewEndtime;
    private IntentIntegrator qrScan;
    public final static int QRcodeWidth = 500 ;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        qrScan = new IntentIntegrator(this);
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textViewDate = (TextView) findViewById(R.id.textViewDate);
        textViewLocation = (TextView) findViewById(R.id.textViewLocation);
        textViewDescription = (TextView) findViewById(R.id.textViewDescription);
        textViewStarttime = (TextView) findViewById(R.id.textViewStarttime);
        textViewEndtime= (TextView) findViewById(R.id.textViewEndtime);
        imageView = (ImageView)findViewById(R.id.imageView2);
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if (type.startsWith("image/*")) {
                handleSendImage(intent);
            }
        }

    }



    private void handleSendImage(Intent intent) {
        Uri imageUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (imageUri != null) {
            try {
                Bitmap bmap = BitmapFactory.decodeStream(new FileInputStream(imageUri.getPath()));
                int width = bmap.getWidth(), height = bmap.getHeight();
                int[] pixels = new int[width * height];
                bmap.getPixels(pixels, 0, width, 0, 0, width, height);
                bmap.recycle();

                LuminanceSource source = new RGBLuminanceSource(width,height,pixels);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                Reader reader = new MultiFormatReader();
                Result result = reader.decode(bitmap);
                JSONObject obj = new JSONObject(result.getText());
                //setting values to textviews
                textViewTitle.setText(obj.getString("Title"));
                textViewDate.setText(obj.getString("Date"));
                textViewLocation.setText(obj.getString("Location"));
                textViewDescription.setText(obj.getString("Description"));
            }
            catch (NotFoundException e){
                e.printStackTrace();
            }
            catch (ChecksumException e){
                e.printStackTrace();
            }
            catch (FileNotFoundException e){
                e.printStackTrace();
            }
            catch (FormatException e){
                e.printStackTrace();
            }
            catch (JSONException e) {
                e.printStackTrace();
                //Toast.makeText(this, result.getText(), Toast.LENGTH_LONG).show();
            }
        }

    }
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
        getMenuInflater().inflate(R.menu.main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        int id = item.getItemId();
        if (id == R.id.nav_scan) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }
            qrScan.initiateScan();


        } else if (id == R.id.nav_gallery) {
        } else if (id == R.id.nav_encode) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }
            Intent intent = new Intent(getApplicationContext(), encode.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, ENCODED_IMAGE_REQUEST);
            //startActivity(intent);

        } else if (id == R.id.nav_share) {
            ImageView image = (ImageView)findViewById(R.id.imageView2);
            Bitmap bmp = ((BitmapDrawable)image.getDrawable()).getBitmap();
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File imageFile = new File(path,"code_image.png");

            try {
                FileOutputStream fileOutPutStream = new FileOutputStream(imageFile);
                bmp.compress(Bitmap.CompressFormat.PNG, 80, fileOutPutStream);
                fileOutPutStream.flush();
                fileOutPutStream.close();
            }
            catch(FileNotFoundException e){
                e.printStackTrace();
            }
            catch( IOException e){
                e.printStackTrace();
            }

            Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            Uri uri = Uri.fromFile(imageFile);
            intent.setType("image/*");
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
            intent.putExtra(android.content.Intent.EXTRA_TEXT, "");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            startActivity(Intent.createChooser(intent, "Share Code Image"));

        }


        return true;
    }
    public void saveToCalendar(View sview) {
        //layout.addView(textView);
        Titlestr = textViewTitle.getText().toString();
        Datestr = textViewDate.getText().toString();
        Locationstr = textViewLocation.getText().toString();
        Descriptionstr = textViewDescription.getText().toString();
       /* SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/YY");
        try {
            Date date = fmt.parse(Datestr);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        Datestr = Datestr.split(" ") [0];
        int day1, month1, year1, day2, month2, year2;
        day1 = Integer.parseInt( Datestr.split("/")[0] );
        month1 = Integer.parseInt( Datestr.split("/")[1] );
        year1 = Integer.parseInt( Datestr.split("/")[2] );
        //  day1 = 24;
       // month1 = 5;
        //year1 = 2017;
        Intent calendarIntent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(year1, month1 - 1, day1, 10, 30);
        Calendar endTime = Calendar.getInstance();
        endTime.set(year1, month1 - 1, day1, 12, 30);
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis());
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis());
        calendarIntent.putExtra(CalendarContract.Events.TITLE, Titlestr);
        calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, Locationstr);
        calendarIntent.putExtra(CalendarContract.Events.DESCRIPTION, Descriptionstr);
        startActivity(calendarIntent);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ENCODED_IMAGE_REQUEST) {

            Titlestr = data.getStringExtra("Title");
            textViewTitle.setText(Titlestr);
            Datestr = data.getStringExtra("Date");
            textViewDate.setText(Datestr);
            Locationstr = data.getStringExtra("Location");
            textViewLocation.setText(Locationstr);
            Descriptionstr = data.getStringExtra("Description");
            textViewDescription.setText(Descriptionstr);
            Starttimestr = data.getStringExtra("Starttime");
            textViewStarttime.setText(Starttimestr);
            Endtimestr = data.getStringExtra("Endtime");
            textViewEndtime.setText(Endtimestr);



            EditTextValue = data.getStringExtra("Result");

            Bitmap bitmap = null;
            try {
                bitmap = TextToImageEncode(EditTextValue);

            } catch (WriterException e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(bitmap);

        }
       else if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                    //setting values to textviews
                    textViewTitle.setText(obj.getString("Title"));
                    textViewDate.setText(obj.getString("Date"));
                    textViewLocation.setText(obj.getString("Location"));
                    textViewDescription.setText(obj.getString("Description"));

                } catch (JSONException e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
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
