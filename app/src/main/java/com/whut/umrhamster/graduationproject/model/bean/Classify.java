package com.whut.umrhamster.graduationproject.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Classify implements Parcelable {
    private int id;
    private String name;
    private int parent;
    private int icon;

    public Classify(){}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
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
        dest.writeInt(this.icon);
    }

    protected Classify(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.parent = in.readInt();
        this.icon = in.readInt();
    }

    public static final Parcelable.Creator<Classify> CREATOR = new Parcelable.Creator<Classify>() {
        @Override
        public Classify createFromParcel(Parcel source) {
            return new Classify(source);
        }

        @Override
        public Classify[] newArray(int size) {
            return new Classify[size];
        }
    };
}
