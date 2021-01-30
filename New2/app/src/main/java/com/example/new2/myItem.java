package com.example.new2;

public class myItem {
    private String title;
    private String price;
    private String img_url;
    private String url;
    private String color;
    public myItem(String title,String price,String img_url,String url,String color)
    {
        this.title = title;
        this.img_url = img_url;
        this.price = price;
        this.url = url;
        this.color = color;
    }
    public String getImg_url() {
        return img_url;
    }
    public String getprice() {
        return price;
    }
    public String getTitle() {
        return title;
    }
    public String getUrl(){return url;}
    public String getColor(){return color;}

}
