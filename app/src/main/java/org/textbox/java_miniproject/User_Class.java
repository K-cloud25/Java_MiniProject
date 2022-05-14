package org.textbox.java_miniproject;

public class User_Class {

    private int id;
    private int OTP;
    private String name,email,password;
    private boolean verified;


    public User_Class(int _id,String _name,String _email,String _password,int _OTP){
        id = _id;
        name = _name;
        email = _email;
        password = _password;
        OTP = _OTP;
        verified = false;
    }

    public User_Class(int _id,String _name, String _email,String _password,int _OTP,boolean _verified){
        id = _id;
        name = _name;
        email = _email;
        password = _password;
        OTP = _OTP;
        verified = _verified;
    }

    public int getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getName() {
        return name;
    }
    public int getOTP() {
        return OTP;
    }
    public String getPassword() {
        return password;
    }
    public Boolean getVerified(){
        return verified;
    }

    public void setID(int _Id){id = _Id;}
    public void setOTP(int _Otp){OTP = _Otp;}
    public void setName(String Name){name = Name;}
    public void setPassword(String _Pass){password = _Pass;}
    public void setEmail(String _email){email = _email;}
    public void setVerified(Boolean _t){verified = _t;}
}
