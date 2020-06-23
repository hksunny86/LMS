package com.example.lms;

import java.util.Date;

public class Student {

    public String name, book_issued, date;
    public int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student() {
    }

    public Student(int id, String name, String book_issued, String date) {
        this.id = id;
        this.name = name;
        this.book_issued = book_issued;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getBook_issued() {
        return book_issued;
    }

    public void setBook_issued(String book_issued) {
        this.book_issued = book_issued;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
