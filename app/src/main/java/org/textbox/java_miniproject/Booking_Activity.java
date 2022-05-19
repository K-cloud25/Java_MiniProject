package org.textbox.java_miniproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Booking_Activity extends AppCompatActivity {

    RecyclerView bookingRV;
    ArrayList<Sports_Booking_Class> arrayList;
    SQL_Sports_Connection dbh;

    String selectedSport,BookieName,BookieID;

    Button addBkn;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        bookingRV = findViewById(R.id.bookingRV);

        dbh = new SQL_Sports_Connection(this);

        selectedSport = getIntent().getStringExtra("sport");
        BookieName = getIntent().getStringExtra("BookieName");
        BookieID = getIntent().getStringExtra("BookieID");

        addBkn = findViewById(R.id.Add_Booking);
        addBkn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewBooking();
            }
        });

        setUpRV();
    }


    void setUpRV(){
        Booking_Adapter adapter = new Booking_Adapter(arrayList);
        bookingRV.setHasFixedSize(true);
        bookingRV.setLayoutManager(new LinearLayoutManager(this));
        bookingRV.setAdapter(adapter);
    }

    void addNewBooking(){
        EditText bookingTiming;
        Button addBtn;
        TextView bookingName,dateTV;

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        dialogBuilder = new AlertDialog.Builder(this);
        View layout = getLayoutInflater().inflate(R.layout.add_booking_popup,null);

        bookingTiming = layout.findViewById(R.id.inputDate);
        bookingName = layout.findViewById(R.id.sportNameTV);
        dateTV = layout.findViewById(R.id.dateTV);

        dateTV.setText("DATE : "+date);
        bookingName.setText("Sport : "+selectedSport);

        dialogBuilder.setView(layout);
        dialog = dialogBuilder.create();
        dialog.show();

        addBtn = layout.findViewById(R.id.addbtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputDate = bookingTiming.getText().toString();

                if(dbh.checkTime(selectedSport,inputDate)){

                    Sports_Booking_Class booking = new Sports_Booking_Class(date,selectedSport,inputDate,BookieName,BookieID,true);
                    dbh.add_Booking(booking);
                    dialog.dismiss();
                }
            }
        });

    }
}