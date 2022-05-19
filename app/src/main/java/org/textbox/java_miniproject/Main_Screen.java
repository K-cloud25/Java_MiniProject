package org.textbox.java_miniproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Main_Screen extends AppCompatActivity {

    String BookieName, BookieID;

    Button cricketBtn,footballbtn,chessBtn,basketballBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        BookieName = getIntent().getStringExtra("BookieName");
        BookieID = getIntent().getStringExtra("BookieID");

        cricketBtn = findViewById(R.id.cricketBtn);
        footballbtn = findViewById(R.id.footballBtn);
        chessBtn = findViewById(R.id.chessBtn);
        basketballBtn = findViewById(R.id.basketballBtn);



        cricketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextActivity("cricket");
            }
        });
        footballbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextActivity("football");
            }
        });
        chessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextActivity("chess");
            }
        });
        basketballBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextActivity("basketball");
            }
        });

    }


    public void nextActivity(String sport){

        Intent intent = new Intent(this,Booking_Activity.class);
        intent.putExtra("BookieName",BookieName);
        intent.putExtra("BookieID",BookieID);
        intent.putExtra("sport",sport);

        startActivity(intent);
        finish();
    }
}