package sk.upjs.ics.s.vyletnik;

import java.io.Serializable;



public class Record implements Serializable{

    private String name;
    private String content;
    private int timestamp;
    private String photo;

    /* TODO gps suradnice aj v databaze */

    public Record(){
        //empty constructor
    }

    public Record(String name, String content, int date){
        this.name = name;
        this.content = content;
        this.timestamp = date;
    }

    public Record(String name, String content, int date, String photo){
        this.name = name;
        this.content = content;
        this.timestamp = date;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}
