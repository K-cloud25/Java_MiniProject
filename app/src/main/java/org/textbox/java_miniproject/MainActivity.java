package org.textbox.java_miniproject;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int otp;
    User_Class temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

//        User_Class temp1 = new User_Class(3, "Temp1", "tt", "234", 2143, false);
//        SQL_User_Auth_Connection dbh = new SQL_User_Auth_Connection(this);
//
//        dbh.addUser(temp1);
//        User_Class temp2 = dbh.getCUser(3);
//
//        Log.println(Log.ASSERT, "TAG : ", temp2.getName());
    }

    public static int stringCompare(String str1, String str2) {

        int l1 = str1.length();
        int l2 = str2.length();
        int lmin = Math.min(l1, l2);

        for (int i = 0; i < lmin; i++) {
            int str1_ch = (int) str1.charAt(i);
            int str2_ch = (int) str2.charAt(i);

            if (str1_ch != str2_ch) {
                return str1_ch - str2_ch;
            }
        }

        if (l1 != l2) {
            return l1 - l2;
        } else {
            return 0;
        }
    }

    public void signInOnSignIn(View view) {
        EditText Uid;
        Uid = (EditText) findViewById(R.id.userIdSignIn);
        String uid = Uid.getText().toString();
        int id = Integer.valueOf(uid);
        EditText Pass;
        Pass = (EditText) findViewById(R.id.passOnSignIn);
        String pass = Pass.getText().toString();
        SQL_User_Auth_Connection dbh = new SQL_User_Auth_Connection(this);
        User_Class temp = dbh.getCUser(id);
        if (temp == null)
            Toast.makeText(this, "Erp " + id + " does not exist", Toast.LENGTH_SHORT).show();
        else if (temp.getVerified()== false) Toast.makeText(this, "Your id is not yet verified",Toast.LENGTH_SHORT).show();
        else if (stringCompare(pass, temp.getPassword()) == 0) {
            setContentView(R.layout.main_page);
        } else {
            Toast.makeText(this, "Invalid Erp No./ Password", Toast.LENGTH_SHORT).show();
        }
    }

    public void otpPopUp() {
        setContentView(R.layout.otp_popup);
        final int[] time = {10};
        TextView OTP = (TextView) findViewById(R.id.otp);
        OTP.setText(Integer.toString(otp));
        TextView timer = (TextView) findViewById(R.id.TimerText);
        new CountDownTimer(10000, 1000) {

            public void onTick(long l) {
                timer.setText(String.valueOf(time[0]));
                time[0]--;
            }

            public void onFinish() {
                setContentView(R.layout.otp);
            }
        }.start();
    }


    public void otpVerify(View view) {
        PinView OTP = (PinView) findViewById(R.id.otpPinView);
        String v = OTP.getText().toString();
        if (v == "") {
            Toast.makeText(this, "Please Enter an OTP", Toast.LENGTH_SHORT).show();
        } else {
            String o = Integer.toString(otp);
            if (stringCompare(o, v) == 0) {
                temp.setVerified(true);
                Toast.makeText(this, "You have been verified succcessfully", Toast.LENGTH_SHORT).show();
                setContentView(R.layout.sign_in);
            } else {
                Toast.makeText(this, "Wrong OTP", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void signUpOnSignUp(View view) {
        EditText Uid = (EditText) findViewById(R.id.userIdSignUp);
        String uid = Uid.getText().toString();
        EditText Name = (EditText) findViewById(R.id.nameOnSignUp);
        String name = Name.getText().toString();
        EditText Pass = (EditText) findViewById(R.id.passOnSignUp);
        String pass = Pass.getText().toString();
        String email = uid + "@mitwpu.edu.in";
        SQL_User_Auth_Connection dbh = new SQL_User_Auth_Connection(this);
        if (uid.matches("")) Toast.makeText(this, "Enter an ERP Number", Toast.LENGTH_SHORT).show();
        else if (name.matches("")) Toast.makeText(this, "Enter a Name", Toast.LENGTH_SHORT).show();
        else if (pass.matches(""))
            Toast.makeText(this, "Enter a Password", Toast.LENGTH_SHORT).show();
        else {
            int id = Integer.valueOf(uid);
            if (dbh.getCUser(id) != null)
                Toast.makeText(this, "Erp is already registered", Toast.LENGTH_SHORT).show();
            else {
                Random rnd = new Random();
                otp = rnd.nextInt(9999);
                temp = new User_Class(id, name, email, pass, otp);
                dbh.addUser(temp);
                otpPopUp();
            }
        }
    }


    public void signUpOnSignIn(View view) {
        setContentView(R.layout.sign_up);
    }

    public void signInOnSignUp(View view) {
        setContentView(R.layout.sign_in);
    }
}