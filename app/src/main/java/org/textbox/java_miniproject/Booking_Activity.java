package org.textbox.java_miniproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class Booking_Activity extends AppCompatActivity {

    RecyclerView bookingRV;
    ArrayList<Sports_Booking_Class> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        bookingRV = findViewById(R.id.bookingRV);


        setUpRV();
    }


    void setUpRV(){
        Booking_Adapter adapter = new Booking_Adapter(arrayList);
        bookingRV.setHasFixedSize(true);
        bookingRV.setLayoutManager(new LinearLayoutManager(this));
        bookingRV.setAdapter(adapter);
    }

}