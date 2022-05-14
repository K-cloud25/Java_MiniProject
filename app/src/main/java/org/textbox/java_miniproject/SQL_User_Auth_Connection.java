package org.textbox.java_miniproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import com.google.android.material.tabs.TabLayout;

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

    public SQL_User_Auth_Connection(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
            String query = "CREATE TABLE " + TABLE_NAME + " ("
                    + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + NAME_COL + " TEXT,"
                    + Password_COL + " TEXT,"
                    + Email_COL + "TEXT,"
                    + Verified_COL + "BOOLEAN,"
                    + OTP_COL + "INT)";

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

    public void addUser(User_Class user){
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
        values.put(OTP_COL, user.getOTP());

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    public boolean checkOTP(SQLiteDatabase db,int _OTP){

        String query = "SELECT * from " + TABLE_NAME +" where "+ OTP_COL + " = " + _OTP;

        Cursor c = db.rawQuery(query,null);
        if(c.getCount() <= 0){
            c.close();
            return false;
        }

        return true;
    }

    public void setOTP(SQLiteDatabase db,int OTP , int user_ID){

        ContentValues values = new ContentValues();

        values.put(OTP_COL,OTP);

        db.update(TABLE_NAME,values,"id = ?", new String[]{String.valueOf(user_ID)});
    }

    public int maxUid(SQLiteDatabase db){
        String query = "Select max("+ID+") from "+TABLE_NAME;

        Cursor s = db.rawQuery(query,null);
        int ret = s.getInt(0);
        return ret;
    }

    public User_Class getCUser(SQLiteDatabase db, int Uid){

        String query = "SELECT * from "+TABLE_NAME+" where " +ID+ " = " + Uid;

        Cursor cursor = db.rawQuery(query,null);

        if(cursor.getCount() == 0){
            return null;
        }else {

            int _id = cursor.getInt(0);
            String _name = cursor.getString(1);
            String _password=  cursor.getString(2);
            String _email = cursor.getString(3);
            boolean _verified;
                    int temp= cursor.getInt(4);
            if(temp == 1){_verified = true;}else{_verified=false;}

            int _otp = cursor.getInt(5);

            return new User_Class(_id,_name,_password,_email, _otp, _verified);

        }
    }


}