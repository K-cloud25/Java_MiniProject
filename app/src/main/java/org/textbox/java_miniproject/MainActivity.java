package org.textbox.java_miniproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        User_Class temp1 = new User_Class(3,"Temp1","tt","234",2143,false);
        SQL_User_Auth_Connection dbh = new SQL_User_Auth_Connection(this);

        dbh.addUser(temp1);
        User_Class temp2 = dbh.getCUser(3);

        Log.println(Log.ASSERT,"TAG : ",temp2.getName());

    }
}