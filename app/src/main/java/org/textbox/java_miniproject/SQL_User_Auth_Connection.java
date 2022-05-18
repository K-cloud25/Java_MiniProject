package org.textbox.java_miniproject;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.material.tabs.TabLayout;

/*  This Class is for only User Related Table  User Authentication Table*/

public class SQL_User_Auth_Connection extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "Java_Mini_DB";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "user_auth";

    // below variable is for our id column.
    private static final String ID = "id";

    // below variable is for our user name column
    private static final String NAME_COL = "name";

    // below variable id for our user password column.
    private static final String Password_COL = "password";

    // below variable is for our user email column.
    private static final String Email_COL = "email";

    // below variable is for our user verified column.
    private static final String Verified_COL = "verified";

    // below variable is for our user OTP column.
    private static final String OTP_COL = "OTP";

    public SQL_User_Auth_Connection(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //This onCreate is basic constructor which will create a table and will remove a old table
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        String query = " CREATE TABLE " + TABLE_NAME + " ("
                + ID + " INTEGER PRIMARY KEY, "
                + NAME_COL + " TEXT, "
                + Password_COL + " TEXT, "
                + Email_COL + " TEXT, "
                + Verified_COL + " BOOLEAN, "
                + OTP_COL + " INT )";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //When Adding a user to Table User this function to store the object user to the Database
    public void addUser(User_Class user) {
        //We add user to user_auth table
        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.

        values.put(ID, user.getId());
        values.put(NAME_COL, user.getName());
        values.put(Password_COL, user.getPassword());
        values.put(Email_COL, user.getEmail());
        values.put(Verified_COL, false);
        values.put(OTP_COL, user.getOTP());

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }


    //This Function is to check if generated OTP is unique or not
    public boolean checkOTP(SQLiteDatabase db, int _OTP) {

        String query = "SELECT * from " + TABLE_NAME + " where " + OTP_COL + " = " + _OTP;

        Cursor c = db.rawQuery(query, null);
        if (c.getCount() <= 0) {
            c.close();
            return false;
        }

        return true;
    }

    //This function is to set the generated otp to a particular user
    public void setOTP(int OTP, int user_ID) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(OTP_COL, OTP);

        db.update(TABLE_NAME, values, "id = ?", new String[]{String.valueOf(user_ID)});
    }

    //This function is used to get a unique UID from list i.e. max value
    public int maxUid(SQLiteDatabase db) {
        String query = "Select max(" + ID + ") from " + TABLE_NAME;

        Cursor s = db.rawQuery(query, null);
        int ret = s.getInt(0);
        return ret;
    }

    //This function is used to return a User_Class object from list i.e. If we change login account
    public User_Class getCUser(int Uid) {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = " + Uid, null);
        Log.println(Log.ASSERT, "TAG", String.valueOf(cursor.getCount()));

        if (cursor.moveToFirst()) {

            int _ID = cursor.getInt(0);
            String _Name = cursor.getString(1);
            String _Password = cursor.getString(2);
            String _Email = cursor.getString(3);
            int _temp = cursor.getInt(4);
            boolean verified = _temp == 1;
            int _OTP = cursor.getInt(5);

            return new User_Class(_ID, _Name, _Password, _Email, _OTP, verified);

        }
        return null;
    }

    public void setVerified(int uid){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Verified_COL,true);

        db.update(TABLE_NAME,values,"id = ?",new String[]{String.valueOf(uid)});
    }

}
