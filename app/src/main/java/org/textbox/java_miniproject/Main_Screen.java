package org.textbox.java_miniproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Main_Screen extends AppCompatActivity {

    String BookieName, BookieID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        BookieName = getIntent().getStringExtra("BookieName");
        BookieID = getIntent().getStringExtra("BookieID");
        //Cricket btn onClick
        // 1. Start the Booking Activity throught intent
        // 2. Pass Sport Name  (intent.putExtra(String id,String sports_Name)

    }

    public void CricketClick(View view){
        BookieName = getIntent().getStringExtra("BookieName");
        BookieID = getIntent().getStringExtra("BookieID");
        String sport = "cricket";
        Intent Booking_Activity = new Intent(this,Booking_Activity.class);
        Booking_Activity.putExtra("BookieName",BookieName);
        Booking_Activity.putExtra("BookieID",BookieID);
        Booking_Activity.putExtra("sport",sport);
        startActivity(Booking_Activity);
    }
    public void FootballClick(View view){
        BookieName = getIntent().getStringExtra("BookieName");
        BookieID = getIntent().getStringExtra("BookieID");
        String sport = "football";
        Intent Booking_Activity = new Intent(this,Booking_Activity.class);
        Booking_Activity.putExtra("BookieName",BookieName);
        Booking_Activity.putExtra("BookieID",BookieID);
        Booking_Activity.putExtra("sport",sport);
        startActivity(Booking_Activity);
    }
    public void BasketballClick(View view){
        BookieName = getIntent().getStringExtra("BookieName");
        BookieID = getIntent().getStringExtra("BookieID");
        String sport = "basketball";
        Intent Booking_Activity = new Intent(this,Booking_Activity.class);
        Booking_Activity.putExtra("BookieName",BookieName);
        Booking_Activity.putExtra("BookieID",BookieID);
        Booking_Activity.putExtra("sport",sport);
        startActivity(Booking_Activity);
    }
    public void ChessClick(View view){
        BookieName = getIntent().getStringExtra("BookieName");
        BookieID = getIntent().getStringExtra("BookieID");
        String sport = "chess";
        Intent Booking_Activity = new Intent(this,Booking_Activity.class);
        Booking_Activity.putExtra("BookieName",BookieName);
        Booking_Activity.putExtra("BookieID",BookieID);
        Booking_Activity.putExtra("sport",sport);
        startActivity(Booking_Activity);
    }
}