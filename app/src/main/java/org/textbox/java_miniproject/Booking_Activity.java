package org.textbox.java_miniproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Booking_Activity extends AppCompatActivity{

    RecyclerView bookingRV;
    ArrayList<Sports_Booking_Class> arrayList = new ArrayList<>();
    SQL_Sports_Connection dbh;

    String selectedSport,BookieName,BookieID,Timing;

    Button addBkn;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        bookingRV = findViewById(R.id.bookingRV);
        Timing="........";

        dbh = new SQL_Sports_Connection(this);
        dbh.createTable();

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
    }

    void setUpRV(){
        arrayList.clear();

        Booking_Adapter adapterC = new Booking_Adapter(arrayList);
        bookingRV.setHasFixedSize(true);
        bookingRV.setLayoutManager(new LinearLayoutManager(this));
        bookingRV.setAdapter(adapterC);

        arrayList.clear();
        arrayList = dbh.get_Bookings(selectedSport);
        arrayList = removeDuplicates(arrayList);

        Booking_Adapter adapter = new Booking_Adapter(arrayList);
        bookingRV.setHasFixedSize(true);
        bookingRV.setLayoutManager(new LinearLayoutManager(this));
        bookingRV.setAdapter(adapter);
    }

    void addNewBooking(){
        Spinner spinner;
        Button addBtn;
        TextView bookingName,dateTV;

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        dialogBuilder = new AlertDialog.Builder(this);
        View layout = getLayoutInflater().inflate(R.layout.add_booking_popup,null);


        bookingName = layout.findViewById(R.id.sportNameTV);
        dateTV = layout.findViewById(R.id.dateTV);

        dateTV.setText("DATE : "+date);
        bookingName.setText("Sport : "+selectedSport);

        dialogBuilder.setView(layout);
        dialog = dialogBuilder.create();
        dialog.show();

        spinner = layout.findViewById(R.id.timingSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.timing, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Timing = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        addBtn = layout.findViewById(R.id.addbtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Timing.equals("........") || Timing.equals("..............")){
                    Toast.makeText(Booking_Activity.this,"Select Timing",Toast.LENGTH_SHORT).show();
                }
                else{
                    int setTime = dbh.checkTime(selectedSport,Timing);

                    if(setTime != 0){
                        Toast.makeText(Booking_Activity.this," Timing Unavailable",Toast.LENGTH_SHORT).show();
                    }else{
                        Sports_Booking_Class booking = new Sports_Booking_Class(date,selectedSport,Timing,BookieName,BookieID,true);
                        dbh.add_Booking(booking);
                        dialog.dismiss();
                        setUpRV();
                    }
                }
            }
        });

    }

    // Function to remove duplicates from an ArrayList
    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list)
    {

        // Create a new ArrayList
        ArrayList<T> newList = new ArrayList<T>();

        // Traverse through the first list
        for (T element : list) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }
}