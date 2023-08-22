package com.netizen.btsjhopeviral.NetizenModel;

public  class ChatItem {
    private int id;
    private String title;
    private String img;
    private String status;
    private long time;
    public ChatItem(int id, String title, String status, String img, long tm) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.time = tm;
        this.img = img;
    }
    public long getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }
    public String getStatus () {
        return status;
    }

    public String getImg() {
        return img;
    }

    public int getId() {
        return id;
    }
}