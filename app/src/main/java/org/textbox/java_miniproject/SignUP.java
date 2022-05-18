package org.textbox.java_miniproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;

import org.w3c.dom.Text;

import java.util.Random;

public class SignUP extends AppCompatActivity {

    int otp;
    User_Class temp;

    EditText uidET,nameET,passET,emailET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        uidET = findViewById(R.id.userIdSignUp);
        nameET = findViewById(R.id.nameOnSignUp);
        passET = findViewById(R.id.passOnSignUp);

    }


    public void signInOnSignUp(View view) {
        setContentView(R.layout.sign_in);
    }

    public void signUpOnSignUp(View view) {
        String uid = uidET.getText().toString();
        String name = nameET.getText().toString();
        String pass = passET.getText().toString();

        String email = uid + "@mitwpu.edu.in";

        SQL_User_Auth_Connection dbh = new SQL_User_Auth_Connection(this);

        if(TextUtils.isEmpty(uid)){ uidET.setError("Enter an ERP Number"); }
        else if(TextUtils.isEmpty(name)){nameET.setError("Enter a Name");}
        else if(TextUtils.isEmpty(pass)){passET.setError("Enter a Password");}
        else {
            int id = Integer.valueOf(uid);
            if (dbh.getCUser(id) != null)
                Toast.makeText(this, "Erp is already registered", Toast.LENGTH_SHORT).show();
            else {
                Random rnd = new Random();
                otp = rnd.nextInt(9999);
                temp = new User_Class(id, name, email, pass, otp);
                dbh.addUser(temp);
                dbh.setOTP(otp,id);
                otpPopUp();
            }
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

        SQL_User_Auth_Connection dbh = new SQL_User_Auth_Connection(this);

        if (v == "") {
            Toast.makeText(this, "Please Enter an OTP", Toast.LENGTH_SHORT).show();
        } else {
            String o = Integer.toString(otp);

            if (TextUtils.equals(o,v)) {
                temp.setVerified(true);
                dbh.setVerified(temp.getId());
                Toast.makeText(this, "You have been verified succcessfully", Toast.LENGTH_SHORT).show();

                Intent login_Page = new Intent(this,MainActivity.class);
                login_Page.putExtra("userID",temp.getId());
                startActivity(login_Page);
                finish();
            } else {
                Toast.makeText(this, "Wrong OTP", Toast.LENGTH_SHORT).show();
            }
        }
    }
}