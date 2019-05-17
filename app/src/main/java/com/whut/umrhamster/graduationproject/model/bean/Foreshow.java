package com.whut.umrhamster.graduationproject.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Foreshow implements Parcelable {
    private int id;
    private Live live;
    private Date startTime;
    private int duration;
    public Foreshow(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Live getLive() {
        return live;
    }

    public void setLive(Live live) {
        this.live = live;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeParcelable(this.live, flags);
        dest.writeLong(this.startTime != null ? this.startTime.getTime() : -1);
        dest.writeInt(this.duration);
    }

    protected Foreshow(Parcel in) {
        this.id = in.readInt();
        this.live = in.readParcelable(Live.class.getClassLoader());
        long tmpStartTime = in.readLong();
        this.startTime = tmpStartTime == -1 ? null : new Date(tmpStartTime);
        this.duration = in.readInt();
    }

    public static final Parcelable.Creator<Foreshow> CREATOR = new Parcelable.Creator<Foreshow>() {
        @Override
        public Foreshow createFromParcel(Parcel source) {
            return new Foreshow(source);
        }

        @Override
        public Foreshow[] newArray(int size) {
            return new Foreshow[size];
        }
    };
}
