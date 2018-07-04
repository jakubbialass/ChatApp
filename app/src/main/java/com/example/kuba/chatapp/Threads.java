package com.example.kuba.chatapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kuba on 29.06.2018.
 */

public class Threads implements Parcelable {

    private String text;


    public Threads(String text)
    {
        this.text=text;

    }



    public String getText() { return text;}



    //PARCEL

    public Threads(Parcel in) {
        super();
        readFromParcel(in);
    }

    public static final Parcelable.Creator<Threads> CREATOR = new Parcelable.Creator<Threads>() {
        public Threads createFromParcel(Parcel in) {
            return new Threads(in);
        }

        public Threads[] newArray(int size) {

            return new Threads[size];
        }

    };

    public void readFromParcel(Parcel in) {
        text = in.readString();

    }
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
    }

}
