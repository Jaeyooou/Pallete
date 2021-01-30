package com.example.new2;


public class Citem {
    private Integer number;
    private String title;
    private String person;
    private Integer cnum;
    private String date;
    public Citem(Integer number, String title, String person, Integer cnum, String date)
    {
        this.number = number;
        this.title = title;
        this.person = person;
        this.cnum = cnum;
        this.date = date;
    }
    public Integer Getnumber(){return number;}
    public String Gettitle(){return title;}
    public String Getperson(){return person;}
    public Integer Getcnum(){return cnum;}
    public String Getdate(){return date;}


}
