package org.textbox.java_miniproject;

public class Sports_Booking_Class {

    private String date,Sport,Time,BookieName,BookieID;
    private Boolean status;

    public Sports_Booking_Class(String _date,String _Sport,String _Time,String _BookieName,String _BookieID,Boolean _status) {

        date = _date;
        Sport = _Sport;
        Time = _Time;
        BookieName = _BookieName;
        BookieID = _BookieID;
        status = _status;
    }

    public String getDate(){return date;}
    public String getSport(){return Sport;}
    public String getTime() { return Time; }
    public String getBookieName() { return BookieName; }
    public String getBookieID(){return BookieID;}
    public Boolean getStatus(){return status;}

    public void setDate(String _date){date = _date;}
    public void setSport(String sport) { Sport = sport; }
    public void setTime(String time) { Time = time; }
    public void setBookieName(String bookieName) { BookieName = bookieName; }
    public void setBookieID(String bookieID) { BookieID = bookieID; }
    public void setStatus(Boolean status) { this.status = status; }
}
