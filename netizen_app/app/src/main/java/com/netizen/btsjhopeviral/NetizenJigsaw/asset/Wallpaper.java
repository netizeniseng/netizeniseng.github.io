package com.netizen.btsjhopeviral.NetizenJigsaw.asset;

public class Wallpaper {

    private String avatar_url;
    private String html_url;

    public Wallpaper() {

    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public Wallpaper(String jdl, String img) {
        this.html_url = jdl;
        this.avatar_url = img;
    }


}
