package com.driver;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class Message {
    private int id;
    private String content;
    private Date timestamp;


    Message(){

    }
    public Message(String content){
        this.content=content;
        this.id=0;
        this.timestamp= Calendar.getInstance().getTime();

    }
    public Message(int id,String content){
        this.content=content;
        this.timestamp= Calendar.getInstance().getTime();
        this.id=id;

    }

    public void setId(int id) {
        this.id = id;
    }


    public void setContent(String content) {
        this.content = content;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getContent() {
        return content;
    }
}
