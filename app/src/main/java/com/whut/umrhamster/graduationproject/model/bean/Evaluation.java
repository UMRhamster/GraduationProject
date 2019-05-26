package com.whut.umrhamster.graduationproject.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Evaluation implements Parcelable {
    private int id;
    private Student student;
    private Teacher teacher;
    private String content;
    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeParcelable(this.student, flags);
        dest.writeParcelable(this.teacher, flags);
        dest.writeString(this.content);
        dest.writeLong(this.date != null ? this.date.getTime() : -1);
    }

    public Evaluation() {
    }

    protected Evaluation(Parcel in) {
        this.id = in.readInt();
        this.student = in.readParcelable(Student.class.getClassLoader());
        this.teacher = in.readParcelable(Teacher.class.getClassLoader());
        this.content = in.readString();
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
    }

    public static final Parcelable.Creator<Evaluation> CREATOR = new Parcelable.Creator<Evaluation>() {
        @Override
        public Evaluation createFromParcel(Parcel source) {
            return new Evaluation(source);
        }

        @Override
        public Evaluation[] newArray(int size) {
            return new Evaluation[size];
        }
    };
}
