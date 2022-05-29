package org.textbox.java_miniproject;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class DownloadTicket_Activity extends AppCompatActivity {

    Button downloadBtn;

    public final int REQUEST_CODE = 100;
    int pageWidth=700;
    int pageHeight=1200;

    String date,sport,time,BName,BiD;

    Bitmap imageBitMap,scaledImageBits,imageBitMap2,scaledImageBits2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_ticket);

        date = getIntent().getStringExtra("date_");
        sport = getIntent().getStringExtra("sport_");
        time = getIntent().getStringExtra("time_");
        BName = getIntent().getStringExtra("Bname_");
        BiD = getIntent().getStringExtra("id_");

        downloadBtn = findViewById(R.id.ticketbtn);

        imageBitMap = BitmapFactory.decodeResource(getResources(),R.drawable.cricket);
        scaledImageBits = Bitmap.createScaledBitmap(imageBitMap,400,400,false);

        imageBitMap2 = BitmapFactory.decodeResource(getResources(),R.drawable.foot);
        scaledImageBits2 = Bitmap.createScaledBitmap(imageBitMap2,200,200,false);

        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(
                        ContextCompat.checkSelfPermission(getApplicationContext(),READ_EXTERNAL_STORAGE)
                                == PackageManager.PERMISSION_GRANTED
                                && ContextCompat.checkSelfPermission(getApplicationContext(),WRITE_EXTERNAL_STORAGE)
                                ==PackageManager.PERMISSION_GRANTED
                ){
                    pdfgenerate();
                }else{
                    requestForPermissions();
                }

            }
        });
    }

    void requestForPermissions(){
        ActivityCompat.requestPermissions(DownloadTicket_Activity.this,
                new String[]{READ_EXTERNAL_STORAGE,WRITE_EXTERNAL_STORAGE},REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_CODE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED ){
                Toast.makeText(this,"Permissions Granted",Toast.LENGTH_SHORT).show();
            }
        }
    }

    void pdfgenerate(){

        createDir();

        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth,pageHeight,1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        canvas.drawBitmap(scaledImageBits,400,0,paint);

        canvas.drawBitmap(scaledImageBits2,0,1100,paint);

        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(20);


        canvas.drawText("TICKET BOOKING CONFORMATION",350,100,paint);

        canvas.drawText("SPORT : "+sport,100,500,paint);
        canvas.drawText("DATE : "+date,300,500,paint);
        canvas.drawText("TIME : "+time,500,500,paint);

        canvas.drawText("USERNAME : "+BName,100,700,paint);
        canvas.drawText("USERID : "+BiD,300,700,paint);

        pdfDocument.finishPage(page);

        int fileUID = new Random().nextInt(100);

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                "/CODE/TICKET"+fileUID+".pdf");


        try{
            pdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(this,"PDF DOWNLOADED",Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            e.printStackTrace();
            Log.println(Log.ASSERT,"ERROr ",e.getMessage());
        }

        pdfDocument.close();
        finish();
    }

    public void createDir(){
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"CODE");

        if(!file.exists()){
            file.mkdirs();
        }

    }
}