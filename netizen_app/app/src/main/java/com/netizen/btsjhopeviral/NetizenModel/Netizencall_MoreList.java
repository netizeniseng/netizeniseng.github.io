package com.netizen.btsjhopeviral.NetizenModel;



public class Netizencall_MoreList {

    public int id;
    public String image_url;
    public String name;
    public String link_url;

    public Netizencall_MoreList() {

    }
    public int getId() {
        return id;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getLink_url() {
        return link_url;
    }

    public String getName() {
        return name;
    }



    public Netizencall_MoreList(int id, String jdl, String img, String ps) {
        this.id = id;
        this.name = jdl;
        this.image_url = img;
        this.link_url = ps;

    }



}
