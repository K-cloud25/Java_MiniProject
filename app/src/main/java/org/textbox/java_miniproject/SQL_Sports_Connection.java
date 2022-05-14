package org.textbox.java_miniproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Locale;

public class SQL_Sports_Connection extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "Java_Mini_DB";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "sports_table";

    // below variable is for our date column.
    private static final String DATE_COL = "date";

    // below variable is for our sport name column
    private static final String SPORT_COL = "sport";

    // below variable id for our booked timing column.
    private static final String TIMINGS_COL = "time";

    // below variable is for our Booked Satus
    private static final String STATUS_COL = "status";

    // below variable is for our user verified column.
    private static final String BOOKIE_NAME_COL = "bookieName";

    // below variable is for our user OTP column.
    private static final String BOOKIE_UID_COL = "bookieUID";

    public SQL_Sports_Connection(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + DATE_COL + " TEXT, "
                + SPORT_COL + " TEXT,"
                + TIMINGS_COL + " TEXT,"
                + STATUS_COL + "BOOLEAN,"
                + BOOKIE_NAME_COL + "TEXT,"
                + BOOKIE_UID_COL + "INT)";

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

    public void add_Booking(Sports_Booking_Class booking){
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
        values.put(DATE_COL, booking.getDate());
        values.put(SPORT_COL, booking.getSport());
        values.put(TIMINGS_COL, booking.getTime());
        values.put(STATUS_COL, booking.getStatus());
        values.put(BOOKIE_NAME_COL, booking.getBookieName());
        values.put(BOOKIE_UID_COL,booking.getBookieID());

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    public void rem_Booking(Sports_Booking_Class booking){

        String query = "DELETE from "+TABLE_NAME + " WHERE  "
                +DATE_COL+" = " +booking.getDate()
                +SPORT_COL+" = " + booking.getSport()
                +TIMINGS_COL+ " = " + booking.getTime()
                +STATUS_COL+ " = " + booking.getStatus().toString()
                +BOOKIE_NAME_COL+ " = " + booking.getBookieName()
                +BOOKIE_UID_COL+ " = " + booking.getBookieID();

        SQLiteDatabase db = this.getWritableDatabase();

        db.rawQuery(query,null);
        db.close();
    }

    public ArrayList<Sports_Booking_Class> get_Bookings(String _sportName){
        ArrayList<Sports_Booking_Class> array = new ArrayList<>();

        SQLiteDatabase db  = this.getWritableDatabase();

        String query = "SELECT * from "+TABLE_NAME+" WHERE " + SPORT_COL +" = "+_sportName ;

        Cursor c  = db.rawQuery(query,null);

        for(int i = 0 ; i < c.getCount(); i++){

            boolean t;
            t = c.getInt(3) == 1;

            Sports_Booking_Class new_Obj = new Sports_Booking_Class(
                    c.getString(0),
                    c.getString(1),
                    c.getString(2),
                    c.getString(4),
                    c.getString(5),
                    t
            );

            array.add(new_Obj);
        }

        db.close();
        return array;
    }

}
