package com.whut.umrhamster.graduationproject.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Classification implements Parcelable {
    private int id;
    private String name;
    private int parent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.parent);
    }

    public Classification() {
    }

    protected Classification(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.parent = in.readInt();
    }

    public static final Parcelable.Creator<Classification> CREATOR = new Parcelable.Creator<Classification>() {
        @Override
        public Classification createFromParcel(Parcel source) {
            return new Classification(source);
        }

        @Override
        public Classification[] newArray(int size) {
            return new Classification[size];
        }
    };
}
